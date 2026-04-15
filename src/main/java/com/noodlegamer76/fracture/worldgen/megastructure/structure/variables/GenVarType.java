package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import net.minecraft.resources.ResourceLocation;

public record GenVarType<T>(GenVarCodec<T> codec, GenVarVisualizer<T> visualizer, ResourceLocation id) {
}