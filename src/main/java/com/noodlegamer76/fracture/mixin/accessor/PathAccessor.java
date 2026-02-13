package com.noodlegamer76.fracture.mixin.accessor;

import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Path.class)
public interface PathAccessor {

    @Accessor(value = "nodes")
    List<Node> fracture$getNodes();
}
