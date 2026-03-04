package com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries;

import com.noodlegamer76.fracture.gui.structure.StructureInstanceVisualizer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.ChunkHeightMap;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;

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

        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;
        for (int h : map.heights().values()) {
            if (h < minHeight) minHeight = h;
            if (h > maxHeight) maxHeight = h;
        }
        int range = Math.max(1, maxHeight - minHeight);

        for (var entry : map.heights().entrySet()) {
            long packed = entry.getKey();
            int chunkX = (int)(packed >> 32);
            int chunkZ = (int)packed;
            int height = entry.getValue();

            float t = (float)(height - minHeight) / range;
            int color = ((int)(t * 255) << 16) | ((int)((1.0f - t) * 255) << 8) | 0xFF000000; // ARGB

            float x0 = windowPos.x + panOffset.x + (chunkX << 4) * zoom;
            float y0 = windowPos.y + panOffset.y + (chunkZ << 4) * zoom;
            float x1 = x0 + (16 * zoom);
            float y1 = y0 + (16 * zoom);

            dl.addRectFilled(x0, y0, x1, y1, color);

            ImVec2 mouse = ImGui.getMousePos();
            if (mouse.x >= x0 && mouse.x <= x1 && mouse.y >= y0 && mouse.y <= y1) {
                ImGui.beginTooltip();
                ImGui.text("Chunk: [" + chunkX + ", " + chunkZ + "]");
                ImGui.text("Height: " + height);
                ImGui.endTooltip();
            }
        }
    }
}