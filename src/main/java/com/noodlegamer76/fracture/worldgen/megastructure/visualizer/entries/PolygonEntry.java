package com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Polygon;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;

public class PolygonEntry<T extends GenVar<Polygon>> extends VisualizerEntry<T> {
    public PolygonEntry(T var) {
        super(var);
    }

    @Override
    public void renderData() {

    }

    @Override
    public void renderVisualization() {

    }

    @Override
    public void renderInWorld(LevelRenderer renderer, PoseStack poseStack, int renderTick, float partialTicks, MultiBufferSource bufferSource) {

    }
}
