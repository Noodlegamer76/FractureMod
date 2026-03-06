package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon;

import java.util.List;
import java.util.Map;

public record Wall(
        Polygon polygon,
        int[] surface,
        int[] edgeHeight,
        int[] vertexHeight,
        boolean[] isTower,
        double edgeLength,
        Map<Long, List<Integer>> edgeChunks,
        Map<Long, List<Integer>> vertexChunks)
{

}
