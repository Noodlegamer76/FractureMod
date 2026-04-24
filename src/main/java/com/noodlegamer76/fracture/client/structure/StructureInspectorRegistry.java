package com.noodlegamer76.fracture.client.structure;

import imgui.ImGui;
import net.minecraft.world.phys.Vec2;

import java.util.HashMap;
import java.util.Map;

public class StructureInspectorRegistry {
    private static final Map<Class<?>, StructureValueView<?>> views = new HashMap<>();

    public static <T> void register(Class<T> type, StructureValueView<? super T> view) {
        views.put(type, view);
    }

    @SuppressWarnings("unchecked")
    public static <T> StructureValueView<T> get(Class<T> type) {
        return (StructureValueView<T>) views.get(type);
    }

    public static boolean has(Class<?> type) {
        return views.containsKey(type);
    }

    static {
        //Vec2
        register(Vec2.class, (label, value) -> {
            if (ImGui.treeNode(label + " (Vec2)")) {
                ImGui.text("X: " + value.x);
                ImGui.text("Y: " + value.y);
                ImGui.treePop();
            }
        });
    }
}