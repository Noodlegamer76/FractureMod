package com.noodlegamer76.fracture.client.structure.visualizer;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;

import java.util.List;

public class GenVarDebugRenderer {

    public static void renderGui(ImGuiContext ctx, List<GenVar<?>> vars) {
        for (GenVar<?> var : vars) {
            renderGuiVar(ctx, var);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void renderGuiVar(ImGuiContext ctx, GenVar<T> var) {
        if (var.getValue() == null) {
            return;
        }

        GenVarValueView<T> view =
                (GenVarValueView<T>) GenVarRendererRegistry.get(var.getValue().getClass());

        if (view != null) {
            view.renderGui(ctx, var);
        }
    }
}