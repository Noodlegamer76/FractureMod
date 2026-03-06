package com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.fracture.gui.structure.StructureInstanceVisualizer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.ChunkHeightMap;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;

public class HeightmapEntry<T extends GenVar<ChunkHeightMap>> extends VisualizerEntry<T> {
    public HeightmapEntry(T var) {
        super(var);
    }

    @Override
    public void renderData() {
        ChunkHeightMap map = getVar().getValue();
        if (map == null) return;

        ImGui.text(getVar().getName() + ": " + map.heights().size() + " chunks");
    }

    @Override
    public void renderVisualization() {
        ChunkHeightMap map = getVar().getValue();
        if (map == null || map.heights().isEmpty()) return;

        ImDrawList dl = ImGui.getWindowDrawList();
        StructureInstanceVisualizer vis = StructureInstanceVisualizer.getInstance();
        ImVec2 panOffset = vis.getCenterPanelCoords();
        float zoom = vis.getZoom();
        ImVec2 windowPos = ImGui.getWindowPos();

        float edgeLength = (float) map.edgeLength();

        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;
        for (int h : map.heights().values()) {
            if (h < minHeight) minHeight = h;
            if (h > maxHeight) maxHeight = h;
        }
        int range = Math.max(1, maxHeight - minHeight);

        for (var entry : map.heights().entrySet()) {
            long packed = entry.getKey();
            int tileX = (int)(packed >> 32);
            int tileZ = (int)packed;
            int height = entry.getValue();

            float t = (float)(height - minHeight) / range;
            int color = 0xFF0000FF | ((int)((1.0f - t) * 255) << 8);

            float x0 = windowPos.x + panOffset.x + tileX * edgeLength * zoom;
            float y0 = windowPos.y + panOffset.y + tileZ * edgeLength * zoom;
            float x1 = x0 + (edgeLength * zoom);
            float y1 = y0 + (edgeLength * zoom);

            dl.addRectFilled(x0, y0, x1, y1, color);

            ImVec2 mouse = ImGui.getMousePos();
            if (mouse.x >= x0 && mouse.x <= x1 && mouse.y >= y0 && mouse.y <= y1) {
                ImGui.beginTooltip();
                ImGui.text("Tile: [" + tileX + ", " + tileZ + "]");
                ImGui.text("Height: " + height);
                ImGui.endTooltip();
            }
        }
    }

    @Override
    public void renderInWorld(LevelRenderer renderer, PoseStack poseStack, int renderTick, float partialTicks, MultiBufferSource bufferSource) {
        ChunkHeightMap map = getVar().getValue();
        if (map == null || map.heights().isEmpty()) return;

        double edgeLength = map.edgeLength();

        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;
        for (int h : map.heights().values()) {
            if (h < minHeight) minHeight = h;
            if (h > maxHeight) maxHeight = h;
        }
        int range = Math.max(1, maxHeight - minHeight);

        Vec3 cam = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.debugFilledBox());

        for (var entry : map.heights().entrySet()) {
            long packed = entry.getKey();
            int tileX = (int)(packed >> 32);
            int tileZ = (int)packed;
            int height = entry.getValue();

            float t = (float)(height - minHeight) / range;
            float r = 1.0f;
            float g = 1.0f - t;
            float b = 0.0f;

            double x0 = tileX * edgeLength - cam.x;
            double z0 = tileZ * edgeLength - cam.z;
            double x1 = x0 + edgeLength;
            double z1 = z0 + edgeLength;
            double y  = height - cam.y;

            LevelRenderer.addChainedFilledBoxVertices(poseStack, consumer,
                    x0, y, z0,
                    x1, y + 0.2, z1,
                    r, g, b, 0.6f);
        }
    }
}