package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.ChunkHeightMap;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PolygonChunkHeightSampler {
    public static ChunkHeightMap sampleAllChunks(Wall wall, int layers) {
        Polygon polygon = wall.polygon();
        List<Vec3> verts = polygon.vertices();
        int[] vertexHeights = wall.vertexHeight();

        int n = verts.size();
        if (n < 3) return new ChunkHeightMap(Map.of());

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

        int minChunkX = (int)Math.floor(minX) >> 4;
        int maxChunkX = (int)Math.ceil(maxX) >> 4;
        int minChunkZ = (int)Math.floor(minZ) >> 4;
        int maxChunkZ = (int)Math.ceil(maxZ) >> 4;

        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;

        for (int h : vertexHeights) {
            if (h < minHeight) minHeight = h;
            if (h > maxHeight) maxHeight = h;
        }

        Map<Long, Integer> heightMap = new HashMap<>();

        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {

                if (!PolygonUtils.isChunkInsidePolygon(polygon, chunkX, chunkZ)) continue;

                double px = (chunkX << 4) + 8.0;
                double pz = (chunkZ << 4) + 8.0;

                double weightedHeight = 0.0;
                double totalWeight = 0.0;
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

                double rawHeight;

                if (exactHit) {
                    rawHeight = exactHeight;
                } else {
                    rawHeight = weightedHeight / totalWeight;

                    if (layers > 1 && maxHeight > minHeight) {
                        double t = (rawHeight - minHeight) / (double)(maxHeight - minHeight);
                        int layer = (int)Math.round(t * (layers - 1));
                        rawHeight = minHeight +
                                (layer * (maxHeight - minHeight) / (double)(layers - 1));
                    }
                }

                heightMap.put(pack(chunkX, chunkZ), (int)Math.round(rawHeight));
            }
        }

        return new ChunkHeightMap(heightMap);
    }

    private static long pack(int x, int z) {
        return (((long)x) << 32) | (z & 0xffffffffL);
    }
}