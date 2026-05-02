package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph;

import org.locationtech.jts.geom.Geometry;

import java.util.List;

public record StructureGraph(
        List<GraphNode> nodes,
        List<GraphEdge> edges,
        Geometry rawGeometry
) {
}