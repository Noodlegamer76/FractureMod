package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils;

import java.util.Map;

public record ChunkHeightMap(Map<Long, Integer> heights) {
    public Integer getHeight(int chunkX, int chunkZ) {
        return heights.get(pack(chunkX, chunkZ));
    }

    public boolean contains(int chunkX, int chunkZ) {
        return heights.containsKey(pack(chunkX, chunkZ));
    }

    private static long pack(int x, int z) {
        return (((long)x) << 32) | (z & 0xffffffffL);
    }
}