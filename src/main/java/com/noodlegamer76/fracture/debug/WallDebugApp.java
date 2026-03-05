package com.noodlegamer76.fracture.debug;

import com.noodlegamer76.fracture.gui.structure.StructureInstanceVisualizer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureDefinition;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structures;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class WallDebugApp {
    private static final int WIN_W = 700;
    private static final int WIN_H = 620;

    public static void main(String[] args) {
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

        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        ImGui.styleColorsDark();

        ImGuiImplGlfw implGlfw = new ImGuiImplGlfw();
        ImGuiImplGl3  implGl3  = new ImGuiImplGl3();
        implGlfw.init(window, true);
        implGl3.init("#version 330 core");

        Structures.getInstance().setupStructures();

        while (!GLFW.glfwWindowShouldClose(window)) {
            GLFW.glfwPollEvents();

            implGlfw.newFrame();
            ImGui.newFrame();

            StructureInstanceVisualizer visualizer = StructureInstanceVisualizer.getInstance();
            StructureDefinition def = Structures.getInstance().getStructures().get(0);
            StructureInstance instance = new StructureInstance(def);
            visualizer.setVars(instance.getGenVars());
            visualizer.render();

            ImGui.render();
            GL11.glClearColor(0.1f, 0.1f, 0.1f, 1f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            implGl3.renderDrawData(ImGui.getDrawData());
            GLFW.glfwSwapBuffers(window);
        }

        implGl3.dispose();
        implGlfw.dispose();
        ImGui.destroyContext();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}