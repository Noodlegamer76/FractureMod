package com.noodlegamer76.fracture.client.structure;

import com.noodlegamer76.fracture.NativeLibraryLoader;
import com.noodlegamer76.fracture.worldgen.megastructure.MegaStructureGenerator;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureDefinition;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structures;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.BlankWorldAccess;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImLong;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class StandaloneImGuiLauncher {
    private long window;

    private final ImGuiImplGlfw implGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 implGl3 = new ImGuiImplGl3();

    private MegastructureEditorAdapter adapter;
    private MegastructureViewer editor;

    public static void main(String[] args) {
        NativeLibraryLoader.loadNatives();
        new StandaloneImGuiLauncher().run();
    }

    public void run() {
        initWindow();
        initImGui();
        loop();
        cleanup();
    }

    private void initWindow() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to init GLFW");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        window = GLFW.glfwCreateWindow(1600, 900, "Fracture Megastructure Debugger", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);

        GL.createCapabilities();
    }

    private void initImGui() {
        ImGui.createContext();

        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);

        implGlfw.init(window, true);
        implGl3.init("#version 150");

        setupDummyStructures();

        adapter = new MegastructureEditorAdapter() {
            public void requestRegenerate(ImLong seed) {
                System.out.println("Regenerating...");
                setupDummyStructures();
                regenerateDummyInstance(seed);
            }
        };

        editor = new MegastructureViewer(adapter);

        adapter.recordEvent("init", "launcher", "Standalone debugger started");
        adapter.recordEvent("structure_load", "Structures", "Loaded dummy structures");
    }

    private void setupDummyStructures() {
        Structures.getInstance().setupStructures();
    }

    public void regenerateDummyInstance(ImLong seed) {
        StructureDefinition def = Structures.getInstance().getStructures().get(0);
        StructureInstance instance = new StructureInstance(def);

        BlankWorldAccess access = new BlankWorldAccess(
                seed.get(),
                editor.getOrigin()
        );

        instance.generate(access, true);

        MegaStructureGenerator.setLastInstance(instance);
        
        adapter.recordEvent("generate", "instance", "Generated test instance");
    }

    private void loop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            GLFW.glfwPollEvents();

            implGlfw.newFrame();
            ImGui.newFrame();

            editor.draw();

            ImGui.render();

            GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            implGl3.renderDrawData(ImGui.getDrawData());

            GLFW.glfwSwapBuffers(window);
        }
    }

    private void cleanup() {
        implGl3.dispose();
        implGlfw.dispose();
        ImGui.destroyContext();

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}