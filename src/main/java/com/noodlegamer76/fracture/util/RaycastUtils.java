package com.noodlegamer76.fracture.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.ArrayList;
import java.util.List;

public class RaycastUtils {
    public record RayHit(Vec3 position, Vec3 normal) {}

    /**
     * Performs a raycast through a 3D level, detecting intersections with non-air blocks along the path.
     * At each intersection, the method calculates the position of the hit and the normal vector of the surface.
     *
     * @param level The 3D level to perform the raycast in.
     * @param start The starting point of the ray in 3D space.
     * @param direction The direction vector of the ray. This will be normalized before usage.
     * @param maxDistance The maximum distance the ray can travel.
     * @return A list of {@code RayHit} objects representing the positions and surface normals of the intersections.
     */
    public static List<RayHit> raycast(Level level, Vec3 start, Vec3 direction, double maxDistance) {
        List<RayHit> hits = new ArrayList<>();
        direction = direction.normalize();

        int x = Mth.floor(start.x);
        int y = Mth.floor(start.y);
        int z = Mth.floor(start.z);

        int stepX = direction.x > 0 ? 1 : -1;
        int stepY = direction.y > 0 ? 1 : -1;
        int stepZ = direction.z > 0 ? 1 : -1;

        double tMaxX = intbound(start.x, direction.x);
        double tMaxY = intbound(start.y, direction.y);
        double tMaxZ = intbound(start.z, direction.z);

        double tDeltaX = stepX / direction.x;
        double tDeltaY = stepY / direction.y;
        double tDeltaZ = stepZ / direction.z;

        double t = 0;
        boolean inBlock = false;

        while (t < maxDistance) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = level.getBlockState(pos);

            if (!state.getCollisionShape(level, pos, CollisionContext.empty()).isEmpty() && !inBlock) {
                Vec3 normal;

                if (tMaxX - tDeltaX < tMaxY - tDeltaY && tMaxX - tDeltaX < tMaxZ - tDeltaZ) normal = new Vec3(-stepX,0,0);
                else if (tMaxY - tDeltaY < tMaxZ - tDeltaZ) normal = new Vec3(0,-stepY,0);
                else normal = new Vec3(0,0,-stepZ);

                double entryT = Math.max(t, Math.min(tMaxX - tDeltaX, Math.min(tMaxY - tDeltaY, tMaxZ - tDeltaZ)));
                hits.add(new RayHit(start.add(direction.scale(entryT)), normal));
                inBlock = true;
            } else if (state.getCollisionShape(level, pos, CollisionContext.empty()).isEmpty() && inBlock) {
                Vec3 normal;

                if (tMaxX < tMaxY && tMaxX < tMaxZ) normal = new Vec3(stepX,0,0);
                else if (tMaxY < tMaxZ) normal = new Vec3(0,stepY,0);
                else normal = new Vec3(0,0,stepZ);

                hits.add(new RayHit(start.add(direction.scale(t)), normal));
                inBlock = false;
            }

            if (tMaxX < tMaxY) {
                if (tMaxX < tMaxZ) {
                    x += stepX;
                    t = tMaxX;
                    tMaxX += tDeltaX;
                } else {
                    z += stepZ;
                    t = tMaxZ;
                    tMaxZ += tDeltaZ;
                }
            } else {
                if (tMaxY < tMaxZ) {
                    y += stepY;
                    t = tMaxY;
                    tMaxY += tDeltaY;
                } else {
                    z += stepZ;
                    t = tMaxZ;
                    tMaxZ += tDeltaZ;
                }
            }
        }

        return hits;
    }

    private static double intbound(double s, double ds) {
        if (ds == 0) return Double.MAX_VALUE;
        else if (ds > 0) return (Math.ceil(s) - s) / ds;
        else return (s - Math.floor(s)) / -ds;
    }
}