package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PolygonIndexing {
    private PolygonIndexing() {}

    /**
     * Build a mapping from chunkKey (ChunkPos.asLong) -> list of edge indices that overlap that chunk.
     * width is the expansion in BLOCKS (not chunks). Use width=0 for exact bounding boxes.
     */
    public static Map<Long, List<Integer>> buildEdgeChunkIndex(Polygon poly, double width) {
        Map<Long, List<Integer>> map = new HashMap<>();
        List<Polygon.Edge> edges = poly.edges();

        for (int ei = 0; ei < edges.size(); ei++) {
            Polygon.Edge e = edges.get(ei);
            Vec3 a = e.start();
            Vec3 b = e.end();

            double minX = Math.min(a.x, b.x) - width;
            double maxX = Math.max(a.x, b.x) + width;
            double minZ = Math.min(a.z, b.z) - width;
            double maxZ = Math.max(a.z, b.z) + width;

            int minChunkX = (int) Math.floor(minX) >> 4;
            int maxChunkX = (int) Math.floor(maxX) >> 4;
            int minChunkZ = (int) Math.floor(minZ) >> 4;
            int maxChunkZ = (int) Math.floor(maxZ) >> 4;

            for (int cx = minChunkX; cx <= maxChunkX; cx++) {
                for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                    long key = ChunkPos.asLong(cx, cz);
                    map.computeIfAbsent(key, k -> new ArrayList<>()).add(ei);
                }
            }
        }

        return map;
    }

    public static Map<Long, List<Integer>> buildVertexChunkIndex(Polygon poly, double expand) {
        Map<Long, List<Integer>> map = new HashMap<>();
        List<Vec3> verts = poly.vertices();

        for (int vi = 0; vi < verts.size(); vi++) {
            Vec3 v = verts.get(vi);
            double minX = v.x - expand;
            double maxX = v.x + expand;
            double minZ = v.z - expand;
            double maxZ = v.z + expand;

            int minChunkX = (int) Math.floor(minX) >> 4;
            int maxChunkX = (int) Math.floor(maxX) >> 4;
            int minChunkZ = (int) Math.floor(minZ) >> 4;
            int maxChunkZ = (int) Math.floor(maxZ) >> 4;

            for (int cx = minChunkX; cx <= maxChunkX; cx++) {
                for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                    long key = ChunkPos.asLong(cx, cz);
                    map.computeIfAbsent(key, k -> new ArrayList<>()).add(vi);
                }
            }
        }

        return map;
    }
}