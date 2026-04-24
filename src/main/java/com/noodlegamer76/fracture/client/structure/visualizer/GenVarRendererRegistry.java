package com.noodlegamer76.fracture.client.structure.visualizer;

import imgui.ImDrawList;
import imgui.ImGui;
import net.minecraft.world.phys.Vec2;

import java.util.HashMap;
import java.util.Map;

public class GenVarRendererRegistry {
    private static final Map<Class<?>, GenVarValueView<?>> VIEWS = new HashMap<>();

    public static <T> void register(Class<T> type, GenVarValueView<T> view) {
        VIEWS.put(type, view);
    }

    @SuppressWarnings("unchecked")
    public static <T> GenVarValueView<T> get(Class<T> type) {
        return (GenVarValueView<T>) VIEWS.get(type);
    }

    public static boolean has(Class<?> type) {
        return VIEWS.containsKey(type);
    }

    static {
        register(
                Vec2.class,
                (ctx, var) -> {
                    MegastructureOverlayTab overlay = ctx.overlay;
                    ImDrawList draw = ImGui.getWindowDrawList();

                    float x = overlay.worldToCanvasX(var.getValue().x);
                    float y = overlay.worldToCanvasY(var.getValue().y);

                    draw.addCircleFilled(
                            x,
                            y,
                            5f,
                            0xFF00FFFF
                    );

                    draw.addCircle(
                            x,
                            y,
                            7f,
                            0xFFFFFFFF
                    );

                    draw.addText(
                            x + 8,
                            y,
                            0xFFFFFFFF,
                            var.getName()
                    );
                }
        );
    }
}