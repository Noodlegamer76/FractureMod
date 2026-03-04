package com.noodlegamer76.fracture.debug;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Polygon;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.WallGenerator;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImVec2;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class WallDebugApp {

    // ── Window ───────────────────────────────────────────────────────────────
    private static final int WIN_W = 700;
    private static final int WIN_H = 620;

    // ── Canvas ───────────────────────────────────────────────────────────────
    private static final float PADDING  = 30f;
    private static final float CANVAS_W = 540f;
    private static final float CANVAS_H = 540f;
    private static final float BOX_SIZE = 256f;
    private static final float SCALE    = (Math.min(CANVAS_W, CANVAS_H) - PADDING * 2) / BOX_SIZE;

    // ── State ────────────────────────────────────────────────────────────────
    private static Polygon       wall;
    private static RandomSource  rng       = RandomSource.create(System.currentTimeMillis());
    private static int           edgeLen   = 10;
    private static int           maxTries  = 60;
    private static int           angle     = 90;
    private static ImBoolean showVerts = new ImBoolean(true);
    private static ImBoolean showStart = new ImBoolean(true);

    public static void main(String[] args) {
        // ── GLFW init ────────────────────────────────────────────────────────
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) throw new IllegalStateException("GLFW init failed");

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

        long window = GLFW.glfwCreateWindow(WIN_W, WIN_H, "Wall Generator Debug", 0, 0);
        if (window == 0) throw new IllegalStateException("Window creation failed");

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GL.createCapabilities();

        // ── ImGui init ───────────────────────────────────────────────────────
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        ImGui.styleColorsDark();

        ImGuiImplGlfw implGlfw = new ImGuiImplGlfw();
        ImGuiImplGl3  implGl3  = new ImGuiImplGl3();
        implGlfw.init(window, true);
        implGl3.init("#version 330 core");

        regen();

        // ── Render loop ──────────────────────────────────────────────────────
        while (!GLFW.glfwWindowShouldClose(window)) {
            GLFW.glfwPollEvents();

            implGlfw.newFrame();
            ImGui.newFrame();

            renderUI();

            ImGui.render();
            GL11.glClearColor(0.1f, 0.1f, 0.1f, 1f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            implGl3.renderDrawData(ImGui.getDrawData());
            GLFW.glfwSwapBuffers(window);
        }

        // ── Cleanup ──────────────────────────────────────────────────────────
        implGl3.dispose();
        implGlfw.dispose();
        ImGui.destroyContext();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    // ─────────────────────────────────────────────────────────────────────────

    private static void regen() {
        rng = RandomSource.create(System.currentTimeMillis());
        wall = WallGenerator.generate(
                new WallGenerator.Settings(0, 0, 256, 256, edgeLen, angle, maxTries),
                rng
        );
    }

    private static final ImInt edgeLenBuf  = new ImInt(10);
    private static final ImInt maxTriesBuf = new ImInt(60);
    private static final ImInt angleBuf    = new ImInt(90);

    private static void renderUI() {
        ImGui.setNextWindowPos(0, 0);
        ImGui.setNextWindowSize(WIN_W, WIN_H);
        ImGui.begin("##root",
                imgui.flag.ImGuiWindowFlags.NoTitleBar |
                        imgui.flag.ImGuiWindowFlags.NoResize   |
                        imgui.flag.ImGuiWindowFlags.NoMove     |
                        imgui.flag.ImGuiWindowFlags.NoScrollbar);

        // ── Controls row ─────────────────────────────────────────────────────
        ImGui.text("Edge Length"); ImGui.sameLine();
        ImGui.setNextItemWidth(80);
        if (ImGui.inputInt("##el", edgeLenBuf))  edgeLen  = Math.max(1, edgeLenBuf.get());

        ImGui.sameLine(); ImGui.text("Max Tries"); ImGui.sameLine();
        ImGui.setNextItemWidth(80);
        if (ImGui.inputInt("##mt", maxTriesBuf)) maxTries = Math.max(3, maxTriesBuf.get());

        ImGui.sameLine(); ImGui.text("Angle"); ImGui.sameLine();
        ImGui.setNextItemWidth(60);
        if (ImGui.inputInt("##ang", angleBuf))   angle    = Math.max(1, angleBuf.get());

        ImGui.sameLine();
        if (ImGui.button("Regenerate")) regen();

        ImGui.sameLine();
        ImGui.checkbox("Verts", showVerts);
        ImGui.sameLine();
        ImGui.checkbox("Start", showStart);

        ImGui.text("Edges: " + (wall != null ? wall.edges().size() : 0));
        ImGui.separator();

        // ── Canvas ────────────────────────────────────────────────────────────
        ImVec2 origin = new ImVec2();
        ImGui.getCursorScreenPos(origin);
        ImGui.invisibleButton("canvas", CANVAS_W, CANVAS_H);
        ImDrawList dl = ImGui.getWindowDrawList();

        // Background
        dl.addRectFilled(origin.x, origin.y,
                origin.x + CANVAS_W, origin.y + CANVAS_H, 0xFF_18_18_18);

        // Grid lines (every edgeLen unit)
        int gridStep = Math.max(1, edgeLenBuf.get());
        for (int gx = 0; gx <= (int) BOX_SIZE; gx += gridStep) {
            float sx = origin.x + PADDING + gx * SCALE;
            dl.addLine(sx, origin.y + PADDING,
                    sx, origin.y + PADDING + BOX_SIZE * SCALE, 0xFF_2A_2A_2A);
        }
        for (int gz = 0; gz <= (int) BOX_SIZE; gz += gridStep) {
            float sy = origin.y + PADDING + gz * SCALE;
            dl.addLine(origin.x + PADDING, sy,
                    origin.x + PADDING + BOX_SIZE * SCALE, sy, 0xFF_2A_2A_2A);
        }

        // Bounding box
        dl.addRect(
                origin.x + PADDING,             origin.y + PADDING,
                origin.x + PADDING + BOX_SIZE * SCALE,
                origin.y + PADDING + BOX_SIZE * SCALE,
                0xFF_55_55_55, 0, 0, 1.5f
        );

        if (wall != null && !wall.edges().isEmpty()) {
            for (Polygon.Edge edge : wall.edges()) {
                float x1 = origin.x + PADDING + (float) edge.start().x * SCALE;
                float y1 = origin.y + PADDING + (float) edge.start().z * SCALE;
                float x2 = origin.x + PADDING + (float) edge.end().x   * SCALE;
                float y2 = origin.y + PADDING + (float) edge.end().z   * SCALE;

                // Edge colour by angleSnapDegrees
                int col = angleColor(edge.angleDegrees());
                dl.addLine(x1, y1, x2, y2, col, 2.5f);

                if (showVerts.get()) dl.addCircleFilled(x1, y1, 3.5f, 0xFF_00_CF_FF);
            }
            // Last vertex dot
            if (showVerts.get()) {
                Polygon.Edge last = wall.edges().get(wall.edges().size() - 1);
                dl.addCircleFilled(
                        origin.x + PADDING + (float) last.end().x * SCALE,
                        origin.y + PADDING + (float) last.end().z * SCALE,
                        3.5f, 0xFF_00_CF_FF
                );
            }

            // Start point
            if (showStart.get() && wall != null && !wall.edges().isEmpty()) {
                Vec3 first = wall.edges().get(0).start();
                dl.addCircleFilled(
                        origin.x + PADDING + (float) first.x * SCALE,
                        origin.y + PADDING + (float) first.z * SCALE,
                        6f, 0xFF_44_44_FF
                );
            }

            // Legend
            float lx = origin.x + CANVAS_W - 120, ly = origin.y + 4;
            dl.addLine(lx, ly+4,  lx+20, ly+4,  angleColor(0),   2f); ImGui.dummy(0, 0);
            dl.addLine(lx, ly+14, lx+20, ly+14, angleColor(90),  2f);
            dl.addLine(lx, ly+24, lx+20, ly+24, angleColor(180), 2f);
            dl.addLine(lx, ly+34, lx+20, ly+34, angleColor(270), 2f);
            // tiny labels via drawList text isn't clean in imgui-java so skip
        }

        ImGui.end();
    }

    /** Colour-code edges by cardinal direction for easy orientation reading. */
    private static int angleColor(int deg) {
        return switch (((deg % 360) + 360) % 360) {
            case 0   -> 0xFF_00_FF_00; // East  – green
            case 90  -> 0xFF_00_BFFF; // South – cyan
            case 180 -> 0xFF_FF_88_00; // West  – orange
            case 270 -> 0xFF_FF_44_88; // North – pink
            default  -> 0xFF_AA_AA_AA; // other – grey
        };
    }
}