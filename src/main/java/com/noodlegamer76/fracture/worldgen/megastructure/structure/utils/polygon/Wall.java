package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon;

public record Wall(
        Polygon polygon,
        int[] surface,
        int[] edgeHeight,
        int[] vertexHeight,
        boolean[] isTower,
        double edgeLength)
{

}
