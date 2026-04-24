package com.noodlegamer76.fracture.client.structure;

import com.noodlegamer76.fracture.NativeLibraryLoader;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.RandomPosInNode;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureDefinition;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structures;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.BlankWorldAccess;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import net.minecraft.core.BlockPos;
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
            @Override
            public void requestRegenerate(boolean sameSeed) {
                super.requestRegenerate(sameSeed);
                
                if (sameSeed) {
                    System.out.println("Regenerating with same seed...");
                    regenerateDummyInstance();
                } else {
                    System.out.println("Regenerating with new seed...");
                    setupDummyStructures();
                    regenerateDummyInstance();
                }
            }
        };

        editor = new MegastructureViewer(adapter);

        adapter.recordEvent("init", "launcher", "Standalone debugger started");
        adapter.recordEvent("structure_load", "Structures", "Loaded dummy structures");
    }

    private void setupDummyStructures() {
        Structures.getInstance().clearDefinitions();

        StructureDefinition def = new StructureDefinition();
        Structures.getInstance().addDefinition(def);

        Structure testStructure = new Structure(100, 4);
        testStructure.addRule(new RandomPosInNode());
        def.addStructure(testStructure);

        Structure testStructure2 = new Structure(50, 3);
        testStructure2.addRule(new RandomPosInNode());
        def.addStructure(testStructure2);
    }

    private void regenerateDummyInstance() {
        StructureDefinition def = Structures.getInstance().getStructures().get(0);
        StructureInstance instance = new StructureInstance(def);

        BlankWorldAccess access = new BlankWorldAccess(
            System.currentTimeMillis(),
            new BlockPos(0, 64, 0)
        );

        instance.generate(access);

        setLastInstanceViaReflection(instance);
        
        adapter.recordEvent("generate", "instance", "Generated test instance");
    }

    private void setLastInstanceViaReflection(StructureInstance instance) {
        try {
            var generatorClass = Class.forName("com.noodlegamer76.fracture.worldgen.megastructure.MegaStructureGenerator");
            var field = generatorClass.getDeclaredField("lastInstance");
            field.setAccessible(true);
            field.set(null, instance);
        } catch (Exception e) {
            System.err.println("Failed to set last instance: " + e.getMessage());
        }
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