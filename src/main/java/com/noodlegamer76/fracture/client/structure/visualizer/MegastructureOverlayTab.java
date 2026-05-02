package com.noodlegamer76.fracture.client.structure.visualizer;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import net.minecraft.core.BlockPos;

import java.util.List;

public class MegastructureOverlayTab {
    private static final int[] NODE_COLORS = {
        0xFF00FF00, // Level 0 - Bright Green
        0xFF00FFFF, // Level 1 - Cyan
        0xFF0080FF, // Level 2 - Sky Blue
        0xFF0000FF, // Level 3 - Blue
        0xFF8000FF, // Level 4 - Purple
        0xFFFF00FF, // Level 5 - Magenta
        0xFFFF0080, // Level 6 - Hot Pink
        0xFFFF0000, // Level 7 - Red
        0xFFFF8000, // Level 8 - Orange
        0xFFFFFF00, // Level 9 - Yellow
        0xFF80FF00, // Level 10 - Lime
        0xFF00FF80, // Level 11 - Spring Green
        0xFF00FFAA, // Level 12 - Turquoise
        0xFF0088FF, // Level 13 - Light Blue
        0xFF4040FF, // Level 14 - Cornflower
        0xFF8040FF, // Level 15 - Violet
        0xFFFF40FF, // Level 16 - Pink
        0xFFFF4080, // Level 17 - Rose
        0xFFFF4040, // Level 18 - Coral
        0xFFFF8040, // Level 19 - Light Orange
        0xFFFFCC00, // Level 20 - Gold
        0xFFCCFF00, // Level 21 - Chartreuse
        0xFF88FF88  // Level 22 - Pale Green
    };

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
        ImGui.begin("Megastructure Visualizer",
                ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse);

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
        
        if (showNodes.get()) {
            drawNodeHierarchy(draw, ctx.editor.getOrigin());
        }

        List<GenVar<?>> vars = instance != null ? instance.getGenVars() : List.of();
        GenVarDebugRenderer.renderGui(ctx, vars);

        if (isMouseOverCanvas() && ImGui.isMouseClicked(1)) {
            ImGui.openPopup("canvas_context");
        }

        if (ImGui.beginPopup("canvas_context")) {

            float worldX = canvasYToWorldX(ImGui.getMousePosX());
            float worldY = canvasYToWorldZ(ImGui.getMousePosY());

            if (ImGui.button("Set Origin Here")) {
                System.out.println("Origin set to: " + worldX + ", " + worldY);

                ctx.editor.setOrigin(new BlockPos((int) worldX, 0, (int) worldY));
                ctx.editor.regenerateDummyInstance();

                ImGui.closeCurrentPopup();
            }

            ImGui.separator();
            ImGui.text("World: " + worldX + ", " + worldY);

            ImGui.endPopup();
        }

        ImGui.end();
    }

    private void drawNodeHierarchy(ImDrawList draw, BlockPos origin) {
        int originX = origin.getX();
        int originZ = origin.getZ();

        for (int level = 0; level <= 22; level++) {
            int nodeSize = 16 << level;

            int nodeX = Math.floorDiv(originX, nodeSize) * nodeSize;
            int nodeZ = Math.floorDiv(originZ, nodeSize) * nodeSize;

            float x1 = worldXToCanvasX(nodeX);
            float y1 = worldZToCanvasY(nodeZ);
            float x2 = worldXToCanvasX(nodeX + nodeSize);
            float y2 = worldZToCanvasY(nodeZ + nodeSize);

            int color = NODE_COLORS[level];

            float thickness = level < 6 ? 1.0f : (level < 12 ? 2.0f : 3.0f);
            draw.addRect(x1, y1, x2, y2, color, 0, 0, thickness);
        }

        for (int level = 22; level >= 0; level--) {
            int nodeSize = 16 << level;
            
            int nodeX = Math.floorDiv(originX, nodeSize) * nodeSize;
            int nodeZ = Math.floorDiv(originZ, nodeSize) * nodeSize;

            float x1 = worldXToCanvasX(nodeX);
            float y1 = worldZToCanvasY(nodeZ);

            float screenSize = zoom * nodeSize / CELL_SIZE;
            if (screenSize > 40) {
                String label = "L" + level + " (" + nodeSize + ")";

                float textWidth = ImGui.calcTextSize(label).x;
                float textHeight = ImGui.calcTextSize(label).y;

                float labelX = x1 + 4;
                float labelY = y1 + 4;

                draw.addRectFilled(
                    labelX - 2, 
                    labelY - 2, 
                    labelX + textWidth + 2, 
                    labelY + textHeight + 2, 
                    0xCC000000
                );

                draw.addText(labelX, labelY, NODE_COLORS[level], label);
            }
        }

        float originCanvasX = worldXToCanvasX(originX);
        float originCanvasY = worldZToCanvasY(originZ);
        
        draw.addCircleFilled(originCanvasX, originCanvasY, 5.0f, 0xFFFF0000);
        draw.addCircle(originCanvasX, originCanvasY, 7.0f, 0xFFFFFFFF, 0, 2.0f);

        String originLabel = "Origin";
        float originTextWidth = ImGui.calcTextSize(originLabel).x;
        float originTextHeight = ImGui.calcTextSize(originLabel).y;
        float originLabelX = originCanvasX + 10;
        float originLabelY = originCanvasY - 5;
        
        draw.addRectFilled(
            originLabelX - 2,
            originLabelY - 2,
            originLabelX + originTextWidth + 2,
            originLabelY + originTextHeight + 2,
            0xCC000000
        );
        draw.addText(originLabelX, originLabelY, 0xFFFFFFFF, originLabel);
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
            float mouseWorldX = canvasYToWorldX(ImGui.getMousePosX());
            float mouseWorldY = canvasYToWorldZ(ImGui.getMousePosY());

            float factor = wheel > 0 ? 1.1f : 1f / 1.1f;
            zoom *= factor;
            zoom = Math.max(0.1f, Math.min(zoom, 10f));

            float newMouseWorldX = canvasYToWorldX(ImGui.getMousePosX());
            float newMouseWorldY = canvasYToWorldZ(ImGui.getMousePosY());

            offsetX += (newMouseWorldX - mouseWorldX) * zoom;
            offsetY += (newMouseWorldY - mouseWorldY) * zoom;
        }
    }

    public float worldXToCanvasX(float worldX) {
        float gridX = worldX / CELL_SIZE;
        return canvasX + gridX * (CELL_SIZE * zoom) + offsetX;
    }

    public float worldZToCanvasY(float worldZ) {
        float gridZ = worldZ / CELL_SIZE;
        return canvasY + gridZ * (CELL_SIZE * zoom) + offsetY;
    }

    public float canvasYToWorldX(float screenX) {
        float gridX = (screenX - canvasX - offsetX) / (CELL_SIZE * zoom);
        return gridX * CELL_SIZE;
    }

    public float canvasYToWorldZ(float screenY) {
        float gridZ = (screenY - canvasY - offsetY) / (CELL_SIZE * zoom);
        return gridZ * CELL_SIZE;
    }

    public int getSelectedDefinition() {
        return selectedDefinition;
    }

    public int getSelectedStructure() {
        return selectedStructure;
    }
}