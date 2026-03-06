package com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fracture.gui.structure.StructureInstanceVisualizer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Polygon;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Wall;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;

public class WallEntry<T extends GenVar<Wall>> extends VisualizerEntry<T> {
    public WallEntry(T var) {
        super(var);
    }

    @Override
    public void renderData() {
        ImGui.text(getVar().getName() + ": " + getVar().getValueClass().getSimpleName());
    }

    @Override
    public void renderVisualization() {
        if (getVar().getValue() == null) return;

        Polygon polygon = getVar().getValue().polygon();
        ImDrawList dl = ImGui.getWindowDrawList();

        StructureInstanceVisualizer vis = StructureInstanceVisualizer.getInstance();
        ImVec2 panOffset = vis.getCenterPanelCoords();
        float zoom = vis.getZoom();
        ImVec2 windowPos = ImGui.getWindowPos();

        Vec3 lastLine = null;
        for (Vec3 vec : polygon.vertices()) {
            float sx = windowPos.x + panOffset.x + (float) vec.x * zoom;
            float sy = windowPos.y + panOffset.y + (float) vec.z * zoom;

            if (lastLine != null) {
                float lx = windowPos.x + panOffset.x + (float) lastLine.x * zoom;
                float ly = windowPos.y + panOffset.y + (float) lastLine.z * zoom;
                dl.addLine(lx, ly, sx, sy, 0xFF00FF00);
            }

            dl.addCircleFilled(sx, sy, 4.0f * zoom, 0xFF00FF00);
            lastLine = vec;
        }
    }

    @Override
    public void renderInWorld(LevelRenderer renderer, PoseStack poseStack, int renderTick, float partialTicks, MultiBufferSource bufferSource) {

    }
}
