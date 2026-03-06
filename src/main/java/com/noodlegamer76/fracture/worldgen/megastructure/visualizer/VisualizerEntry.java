package com.noodlegamer76.fracture.worldgen.megastructure.visualizer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;

public abstract class VisualizerEntry<T extends GenVar<?>> {
    T var;

    public VisualizerEntry(T var) {
        this.var = var;
    }

    public T getVar() {
        return var;
    }

    public abstract void renderData();

    public abstract void renderVisualization();

    public abstract void renderInWorld(LevelRenderer renderer, PoseStack poseStack, int renderTick, float partialTicks, MultiBufferSource bufferSource);
}
