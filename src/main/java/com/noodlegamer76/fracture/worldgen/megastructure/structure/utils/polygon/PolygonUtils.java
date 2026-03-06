package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PolygonUtils {
    public static boolean isChunkInsidePolygon(Polygon polygon, int chunkX, int chunkZ, int expand) {
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

            if (intersects) inside = !inside;
        }

        if (inside) return true;

        if (expand <= 0) return false;

        double expandSq = expand * expand;

        for (int i = 0; i < n; i++) {
            Vec3 a = verts.get(i);
            Vec3 b = verts.get((i + 1) % n);

            double dx = b.x - a.x;
            double dz = b.z - a.z;

            double t = ((px - a.x) * dx + (pz - a.z) * dz) / (dx * dx + dz * dz);
            t = Math.max(0, Math.min(1, t));

            double closestX = a.x + t * dx;
            double closestZ = a.z + t * dz;

            double distX = px - closestX;
            double distZ = pz - closestZ;

            if (distX * distX + distZ * distZ <= expandSq) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPointInPolygon(Polygon polygon, double x, double z) {
        List<Vec3> verts = polygon.vertices();
        int count = verts.size();
        boolean inside = false;
        for (int i = 0, j = count - 1; i < count; j = i++) {
            double xi = verts.get(i).x, zi = verts.get(i).z;
            double xj = verts.get(j).x, zj = verts.get(j).z;
            boolean intersect = ((zi > z) != (zj > z)) &&
                    (x < (xj - xi) * (z - zi) / (zj - zi) + xi);
            if (intersect) inside = !inside;
        }
        return inside;
    }
}
