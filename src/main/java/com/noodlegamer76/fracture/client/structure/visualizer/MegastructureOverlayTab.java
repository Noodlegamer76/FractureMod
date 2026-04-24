package com.noodlegamer76.fracture.client.structure.visualizer;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

import java.util.List;

public class MegastructureOverlayTab {
    public static final int CELL_SIZE = 16;

    public final ImBoolean showNodes = new ImBoolean(true);
    public final ImBoolean showBounds = new ImBoolean(true);
    public final ImBoolean showRulesInfluence = new ImBoolean(false);
    public final ImBoolean showGridDebug = new ImBoolean(false);
    public final ImBoolean freezeSelection = new ImBoolean(false);

    private int selectedDefinition = -1;
    private int selectedStructure = -1;

    private float offsetX = 0;
    private float offsetY = 0;

    private float canvasX = 0;
    private float canvasY = 0;
    private float canvasW = 0;
    private float canvasH = 0;

    private float zoom = 1.0f;

    private float cameraWorldX = 0;
    private float cameraWorldY = 0;

    private boolean dragging = false;
    private float lastMouseX;
    private float lastMouseY;

    public void setSelection(int def, int struct) {
        if (!freezeSelection.get()) {
            selectedDefinition = def;
            selectedStructure = struct;
        }
    }

    public void renderGui(ImGuiContext ctx, StructureInstance instance) {
        float width = 1600;
        float height = 900;

        ImGui.setNextWindowSize(width, height);
        ImGui.setNextWindowPos(0, 20);
        ImGui.begin("Megastructure Visualizer", ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse);

        ImGui.text("Megastructure Overlay Canvas");
        ImGui.separator();

        drawSettingsPanel();
        ImGui.separator();

        handleInput();

        canvasX = ImGui.getCursorScreenPosX();
        canvasY = ImGui.getCursorScreenPosY();
        canvasW = width;
        canvasH = ImGui.getContentRegionAvailY();

        ImDrawList draw = ImGui.getWindowDrawList();

        drawGrid(draw, canvasX, canvasY, canvasW, canvasH);

        List<GenVar<?>> vars = instance != null ? instance.getGenVars() : List.of();

        GenVarDebugRenderer.renderGui(ctx, vars);

        ImGui.end();
    }

    private void drawSettingsPanel() {
        if (ImGui.treeNode("Overlay Settings")) {

            ImGui.checkbox("Show Nodes", showNodes);
            ImGui.checkbox("Show Bounds", showBounds);
            ImGui.checkbox("Rule Influence", showRulesInfluence);
            ImGui.checkbox("Grid Debug", showGridDebug);
            ImGui.checkbox("Freeze Selection", freezeSelection);

            ImGui.separator();

            ImGui.text("Camera");
            ImGui.text("OffsetX: " + offsetX);
            ImGui.text("OffsetY: " + offsetY);
            ImGui.text("Zoom: " + zoom);

            ImGui.separator();

            ImGui.text("Selection Sync");
            ImGui.text("Def: " + selectedDefinition);
            ImGui.text("Struct: " + selectedStructure);

            ImGui.treePop();
        }
    }

    private void drawGrid(ImDrawList draw, float startX, float startY, float width, float height) {
        float cell = CELL_SIZE * zoom;

        float firstX = ((offsetX % cell) + cell) % cell;
        float firstY = ((offsetY % cell) + cell) % cell;

        int color = 0xFF222222;

        for (float x = firstX; x <= width; x += cell) {
            draw.addLine(startX + x, startY, startX + x, startY + height, color);
        }

        for (float y = firstY; y <= height; y += cell) {
            draw.addLine(startX, startY + y, startX + width, startY + y, color);
        }
    }

    private boolean isMouseOverCanvas() {
        float mx = ImGui.getMousePosX();
        float my = ImGui.getMousePosY();

        return mx >= canvasX && mx <= canvasX + canvasW &&
                my >= canvasY && my <= canvasY + canvasH;
    }

    private void handleInput() {
        if (!ImGui.isWindowHovered()) return;

        float mouseX = ImGui.getMousePosX();
        float mouseY = ImGui.getMousePosY();

        if (ImGui.isMouseClicked(0)) {
            dragging = true;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }

        if (!ImGui.isMouseDown(0)) {
            dragging = false;
        }

        if (dragging) {
            offsetX += mouseX - lastMouseX;
            offsetY += mouseY - lastMouseY;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }

        float wheel = ImGui.getIO().getMouseWheel();

        if (wheel != 0 && isMouseOverCanvas()) {
            float mouseWorldX = canvasToWorldX(ImGui.getMousePosX());
            float mouseWorldY = canvasToWorldY(ImGui.getMousePosY());

            float factor = wheel > 0 ? 1.1f : 1f / 1.1f;
            zoom *= factor;
            zoom = Math.max(0.1f, Math.min(zoom, 10f));

            float newMouseWorldX = canvasToWorldX(ImGui.getMousePosX());
            float newMouseWorldY = canvasToWorldY(ImGui.getMousePosY());

            offsetX += (newMouseWorldX - mouseWorldX) * zoom;
            offsetY += (newMouseWorldY - mouseWorldY) * zoom;
        }
    }

    public float worldToCanvasX(float worldX) {
        return canvasX + (worldX - cameraWorldX) * zoom + offsetX;
    }

    public float worldToCanvasY(float worldY) {
        return canvasY + (worldY - cameraWorldY) * zoom + offsetY;
    }

    public float canvasToWorldX(float screenX) {
        return ((screenX - canvasX - offsetX) / zoom) + cameraWorldX;
    }

    public float canvasToWorldY(float screenY) {
        return ((screenY - canvasY - offsetY) / zoom) + cameraWorldY;
    }

    public int getSelectedDefinition() {
        return selectedDefinition;
    }

    public int getSelectedStructure() {
        return selectedStructure;
    }
}