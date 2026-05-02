package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph;

public record GraphEdge(
        GraphNode from,
        GraphNode to,
        String tag,
        double length
) {
}