package com.noodlegamer76.fracture.client.structure.visualizer;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;

public interface GenVarValueView<T> {
    void renderGui(ImGuiContext ctx, GenVar<T> var);
}