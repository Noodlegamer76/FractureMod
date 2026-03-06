package com.noodlegamer76.fracture.gui.structure;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiWindowFlags;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private int windowWidth = 1280;
    private int windowHeight = 720;
    private static final float CELL_SIZE = 16.0f;

    private List<GenVar<?>> vars = new ArrayList<>();
    private Map<GenVar<?>, VisualizerEntry<?>> visualizers = Map.of();
    private Minecraft mc = Minecraft.getInstance();
    private WorldAccess access;

    long currentSeed = System.currentTimeMillis();
    float[] currentPos = new float[] {0, 0};

    private float contextWorldX;
    private float contextWorldZ;

    private final ImVec2 centerPanelCoords = new ImVec2();

    public ImVec2 getCenterPanelCoords() {
        return centerPanelCoords;
    }

    public float getZoom() {
        return zoom;
    }

    public void render() {
        mc = Minecraft.getInstance();
        if (mc != null) {
            windowWidth = Minecraft.getInstance().getWindow().getWidth();
            windowHeight = Minecraft.getInstance().getWindow().getHeight();
        }
        else {
            int[] width = new int[1];
            int[] height = new int[1];
            long window = GLFW.glfwGetCurrentContext();
            GLFW.glfwGetWindowSize(window, width, height);
            windowWidth = width[0];
            windowHeight = height[0];
        }
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

        setNextWindowPos(300, 0);
        setNextWindowSize(windowWidth - 300, windowHeight);
        begin("Main Panel", flags);

        if (button("Set Position To Player Position")) {
            Vec3 position;
            if (mc != null && mc.player != null) {
                position = mc.player.position();
            }
            else {
                position = new Vec3(0, 0, 0);
            }
            zoom = 1.0f;
            float centerX = (windowWidth - 300) / 2.0f;
            float centerY = windowHeight / 2.0f;
            centerPanelCoords.set(
                    centerX - (float) position.x * zoom,
                    centerY - (float) position.z * zoom
            );
        }

        if (access != null) {
            inputFloat2("current Position", currentPos);

            if (button("New Seed")) {
                currentSeed = System.currentTimeMillis();
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

        int color = 0xFFAAAAAA;

        float scaledCell = CELL_SIZE * zoom;
        float offsetX = centerPanelCoords.x % scaledCell;
        float offsetY = centerPanelCoords.y % scaledCell;

        for (float x = offsetX; x < winW; x += scaledCell) {
            drawList.addLine(windowPos.x + x, windowPos.y, windowPos.x + x, windowPos.y + winH, color);
        }
        for (float y = offsetY; y < winH; y += scaledCell) {
            drawList.addLine(windowPos.x, windowPos.y + y, windowPos.x + winW, windowPos.y + y, color);
        }

        ImDrawList dl = ImGui.getWindowDrawList();

        for (GenVar<?> var : vars) {
            renderVarVisualizer(var);
            separator();
        }


        Vec3 pos;
        if (mc != null && mc.player != null) {
            pos = mc.player.position();
        }
        else {
            pos = new Vec3(0, 0, 0);
        }
        float px = windowPos.x + centerPanelCoords.x + (float) pos.x * zoom;
        float py = windowPos.y + centerPanelCoords.y + (float) pos.z * zoom;
        dl.addCircleFilled(px, py, 6.0f * zoom, 0xFF00FFFF);
        dl.addCircleFilled(px, py, 2.0f * zoom, 0xFFFF0000);

        if (ImGui.isWindowHovered() && ImGui.isMouseClicked(1)) {

            ImVec2 mouse = ImGui.getMousePos();

            contextWorldX = (mouse.x - windowPos.x - centerPanelCoords.x) / zoom;
            contextWorldZ = (mouse.y - windowPos.y - centerPanelCoords.y) / zoom;

            ImGui.openPopup("world_context_menu");
        }

        if (ImGui.beginPopup("world_context_menu")) {
            if (ImGui.menuItem("Teleport here")) {

                if (mc.player != null) {
                    int y = Mth.floor(mc.player.getY());

                    mc.player.connection.sendCommand(
                            "tp " + contextWorldX + " " + y + " " + contextWorldZ
                    );
                }
            }

            ImGui.endPopup();
        }

        endChild();
        end();
    }

    @SuppressWarnings("unchecked")
    public <T> void renderVarData(GenVar<T> var) {
        VisualizerEntry<GenVar<T>> entry = (VisualizerEntry<GenVar<T>>) visualizers.get(var);
        entry.renderData();
    }

    @SuppressWarnings("unchecked")
    public <T> void renderVarVisualizer(GenVar<T> var) {
        VisualizerEntry<GenVar<T>> entry = (VisualizerEntry<GenVar<T>>) visualizers.get(var);
        entry.renderVisualization();
    }

    public void setVars(List<GenVar<?>> vars, @Nullable WorldAccess access) {
        this.vars = vars;
        visualizers = new HashMap<>();
        for (GenVar<?> var: vars) {
            VisualizerEntry<?> entry = createVisualizer(var);
            visualizers.put(var, entry);
        }

        this.access = access;
    }

    @SuppressWarnings("unchecked")
    public <T extends GenVar<?>> VisualizerEntry<T> createVisualizer(GenVar var) {
        return (VisualizerEntry<T>) var.getType().visualizer().create(var);
    }

    public float[] getCurrentPos() {
        return currentPos;
    }

    public long getCurrentSeed() {
        return currentSeed;
    }

    public Map<GenVar<?>, VisualizerEntry<?>> getVisualizers() {
        return visualizers;
    }
}