package com.noodlegamer76.fracture.gui.structure;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiWindowFlags;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static imgui.ImGui.*;

public class StructureInstanceVisualizer {

    private static final StructureInstanceVisualizer INSTANCE = new StructureInstanceVisualizer();

    public static StructureInstanceVisualizer getInstance() {
        return INSTANCE;
    }

    private StructureInstanceVisualizer() {
    }

    private float zoom = 1.0f;
    private static final float ZOOM_MIN = 0.1f;
    private static final float ZOOM_MAX = 10.0f;

    private List<GenVar<?>> vars = new ArrayList<>();

    private final ImVec2 centerPanelCoords = new ImVec2();

    public ImVec2 getCenterPanelCoords() {
        return centerPanelCoords;
    }

    public float getZoom() {
        return zoom;
    }

    public void render() {
        renderLeftPanel();
        renderCenterPanel();
    }

    public void renderLeftPanel() {
        int flags = ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.NoScrollbar;

        begin("Data Panel", flags);
        setWindowPos(0, 0);
        setWindowSize(300, getIO().getDisplaySizeY());

        text("Vars:");
        for (GenVar<?> var : vars) {
            renderVarData(var);
        }

        end();
    }

    public void renderCenterPanel() {
        int flags = ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoSavedSettings;

        Minecraft mc = Minecraft.getInstance();
        int windowWidth = mc.getWindow().getWidth();
        int windowHeight = mc.getWindow().getHeight();

        setNextWindowPos(300, 0);
        setNextWindowSize(windowWidth - 300, windowHeight);
        begin("Main Panel", flags);

        if (button("Set Position To Player Position")) {
            if (mc.player != null) {
                Vec3 position = mc.player.position();
                zoom = 1.0f;
                float centerX = (windowWidth - 300) / 2.0f;
                float centerY = windowHeight / 2.0f;
                centerPanelCoords.set(
                        centerX - (float) position.x * zoom,
                        centerY - (float) position.z * zoom
                );
            }
        }

        beginChild("ScrollableArea", windowWidth - 300, windowHeight - getFrameHeightWithSpacing(), true, ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse);

        ImDrawList drawList = getWindowDrawList();

        ImVec2 windowPos = getWindowPos();
        float winW = getWindowWidth();
        float winH = getWindowHeight();

        if (isWindowHovered()) {
            if (isMouseDragging(0)) {
                ImVec2 delta = getIO().getMouseDelta();
                centerPanelCoords.x += delta.x;
                centerPanelCoords.y += delta.y;
            }

            float wheel = getIO().getMouseWheel();
            if (wheel != 0) {
                ImVec2 mousePos = getMousePos();

                float worldX = (mousePos.x - windowPos.x - centerPanelCoords.x) / zoom;
                float worldY = (mousePos.y - windowPos.y - centerPanelCoords.y) / zoom;

                zoom = Mth.clamp(zoom * (1.0f + wheel * 0.1f), ZOOM_MIN, ZOOM_MAX);

                centerPanelCoords.x = mousePos.x - windowPos.x - worldX * zoom;
                centerPanelCoords.y = mousePos.y - windowPos.y - worldY * zoom;
            }
        }

        int cellSize = 128;
        int color = 0xFFAAAAAA;

        float scaledCell = cellSize * zoom;
        float offsetX = centerPanelCoords.x % scaledCell;
        float offsetY = centerPanelCoords.y % scaledCell;

        for (float x = offsetX; x < winW; x += scaledCell) {
            drawList.addLine(windowPos.x + x, windowPos.y, windowPos.x + x, windowPos.y + winH, color);
        }
        for (float y = offsetY; y < winH; y += scaledCell) {
            drawList.addLine(windowPos.x, windowPos.y + y, windowPos.x + winW, windowPos.y + y, color);
        }

        ImDrawList dl = ImGui.getWindowDrawList();

        if (mc.player != null) {
            Vec3 pos = mc.player.position();
            float px = windowPos.x + centerPanelCoords.x + (float) pos.x * zoom;
            float py = windowPos.y + centerPanelCoords.y + (float) pos.z * zoom;
            dl.addCircleFilled(px, py, 6.0f * zoom, 0xFF00FFFF);
        }

        for (GenVar<?> var : vars) {
            renderVarVisualizer(var);
            separator();
        }

        endChild();
        end();
    }

    public static <T> void renderVarData(GenVar<T> var) {
        VisualizerEntry<GenVar<T>> entry = var.getSerializer().visualizerEntry(var);
        entry.renderData();
    }

    public static <T> void renderVarVisualizer(GenVar<T> var) {
        VisualizerEntry<GenVar<T>> entry = var.getSerializer().visualizerEntry(var);
        entry.renderVisualization();
    }

    public void setVars(List<GenVar<?>> vars) {
        this.vars = vars;
    }
}