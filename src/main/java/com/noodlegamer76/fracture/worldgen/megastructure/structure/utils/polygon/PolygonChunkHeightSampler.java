package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.ChunkHeightMap;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PolygonChunkHeightSampler {
    public static ChunkHeightMap sampleAllChunks(Wall wall, int layers, double riseStrength) {
        Polygon polygon = wall.polygon();
        List<Vec3> verts = polygon.vertices();
        int[] vertexHeights = wall.vertexHeight();
        double edgeLength = wall.edgeLength();

        int n = verts.size();
        if (n < 3) return new ChunkHeightMap(Map.of(), edgeLength);

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        for (Vec3 v : verts) {
            minX = Math.min(minX, v.x);
            maxX = Math.max(maxX, v.x);
            minZ = Math.min(minZ, v.z);
            maxZ = Math.max(maxZ, v.z);
        }

        int minTileX = (int)Math.floor(minX / edgeLength);
        int maxTileX = (int)Math.ceil(maxX / edgeLength);
        int minTileZ = (int)Math.floor(minZ / edgeLength);
        int maxTileZ = (int)Math.ceil(maxZ / edgeLength);

        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;

        for (int h : vertexHeights) {
            minHeight = Math.min(minHeight, h);
            maxHeight = Math.max(maxHeight, h);
        }

        Map<Long, Integer> heightMap = new HashMap<>();

        for (int tileX = minTileX; tileX <= maxTileX; tileX++) {
            for (int tileZ = minTileZ; tileZ <= maxTileZ; tileZ++) {

                double px = tileX * edgeLength + edgeLength * 0.5;
                double pz = tileZ * edgeLength + edgeLength * 0.5;

                if (!PolygonUtils.isPointInPolygon(polygon, px, pz)) continue;

                double weightedHeight = 0;
                double totalWeight = 0;
                boolean exactHit = false;
                int exactHeight = 0;

                for (int i = 0; i < n; i++) {
                    Vec3 v = verts.get(i);

                    double dx = px - v.x;
                    double dz = pz - v.z;
                    double distSq = dx * dx + dz * dz;

                    if (distSq < 1e-6) {
                        exactHit = true;
                        exactHeight = vertexHeights[i];
                        break;
                    }

                    double weight = 1.0 / distSq;
                    weightedHeight += vertexHeights[i] * weight;
                    totalWeight += weight;
                }

                double rawHeight = exactHit ? exactHeight : weightedHeight / totalWeight;

                double minDistToEdge = Double.POSITIVE_INFINITY;

                for (int i = 0; i < n; i++) {
                    Vec3 a = verts.get(i);
                    Vec3 b = verts.get((i + 1) % n);

                    double abx = b.x - a.x;
                    double abz = b.z - a.z;

                    double apx = px - a.x;
                    double apz = pz - a.z;

                    double abLenSq = abx * abx + abz * abz;

                    double t = (apx * abx + apz * abz) / abLenSq;
                    t = Math.max(0.0, Math.min(1.0, t));

                    double cx = a.x + abx * t;
                    double cz = a.z + abz * t;

                    double dx = px - cx;
                    double dz = pz - cz;

                    double dist = Math.sqrt(dx * dx + dz * dz);

                    if (dist < minDistToEdge) minDistToEdge = dist;
                }

                double maxInteriorDist = edgeLength * 4.0;
                double t = Math.min(minDistToEdge / maxInteriorDist, 1.0);

                rawHeight += riseStrength * t;


                if (layers > 1) {
                    double range = (maxHeight - minHeight) + riseStrength;
                    double step = range / (layers - 1);

                    rawHeight = Math.round((rawHeight - minHeight) / step) * step + minHeight;
                }

                heightMap.put(pack(tileX, tileZ), (int)Math.round(rawHeight));
            }
        }

        return new ChunkHeightMap(heightMap, edgeLength);
    }

    private static long pack(int x, int z) {
        return (((long) x) << 32) | (z & 0xffffffffL);
    }
}