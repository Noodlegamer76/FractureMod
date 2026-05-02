package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph;

import net.minecraft.world.phys.Vec2;
import org.locationtech.jts.geom.Point;

public record GraphNode(
        String tag,
        Vec2 pos,
        Point geometry
) {
}