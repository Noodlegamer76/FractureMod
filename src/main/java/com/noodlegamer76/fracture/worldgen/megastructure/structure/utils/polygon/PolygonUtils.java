package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.ChunkPosHeight;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;

public class PolygonUtils {
    /**
     * Calculates the height at the specified chunk coordinates within a polygonal wall structure,
     * using the weighted heights of the wall's vertices. If layers are specified, the height is adjusted
     * to fit into discrete height layers.
     *
     * @param wall the wall containing the polygon and its vertex height data
     * @param chunkX the x-coordinate of the chunk
     * @param chunkZ the z-coordinate of the chunk
     * @param layers the number of height layers to segment the height value into (if greater than 1)
     * @return a ChunkPosHeight object containing chunk coordinates and calculated height, or null if
     *         the chunk is outside the polygon
     */
    public static ChunkPosHeight getChunkHeight(Wall wall, int chunkX, int chunkZ, int layers) {
        Polygon polygon = wall.polygon();

        if (!isChunkInsidePolygon(polygon, chunkX, chunkZ)) {
            return null;
        }

        double px = (chunkX << 4) + 8.0;
        double pz = (chunkZ << 4) + 8.0;

        List<Vec3> verts = polygon.vertices();
        int[] vertexHeights = wall.vertexHeight();

        double weightedHeight = 0.0;
        double totalWeight = 0.0;

        for (int i = 0; i < verts.size(); i++) {
            Vec3 v = verts.get(i);

            double dx = px - v.x;
            double dz = pz - v.z;
            double distSq = dx * dx + dz * dz;

            if (distSq < 1e-6) {
                return new ChunkPosHeight(chunkX, chunkZ, vertexHeights[i]);
            }

            double weight = 1.0 / distSq;

            weightedHeight += vertexHeights[i] * weight;
            totalWeight += weight;
        }

        double rawHeight = weightedHeight / totalWeight;

        int min = Arrays.stream(vertexHeights).min().orElse(0);
        int max = Arrays.stream(vertexHeights).max().orElse(0);

        if (layers > 1 && max > min) {
            double t = (rawHeight - min) / (double)(max - min);
            int layer = (int)Math.round(t * (layers - 1));
            rawHeight = min + (layer * (max - min) / (double)(layers - 1));
        }

        return new ChunkPosHeight(chunkX, chunkZ, (int)Math.round(rawHeight));
    }

    public static boolean isChunkInsidePolygon(Polygon polygon, int chunkX, int chunkZ) {
        double px = (chunkX << 4) + 8.0;
        double pz = (chunkZ << 4) + 8.0;

        List<Vec3> verts = polygon.vertices();
        int n = verts.size();
        if (n < 3) return false;

        boolean inside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            Vec3 vi = verts.get(i);
            Vec3 vj = verts.get(j);

            boolean intersects =
                    ((vi.z > pz) != (vj.z > pz)) &&
                            (px < (vj.x - vi.x) * (pz - vi.z) / (vj.z - vi.z) + vi.x);

            if (intersects) {
                inside = !inside;
            }
        }

        return inside;
    }
}
