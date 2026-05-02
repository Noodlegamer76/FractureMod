package com.noodlegamer76.fracture.client.structure.visualizer;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph.GraphEdge;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph.GraphNode;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph.StructureGraph;
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
        //Vec2
        register(
                Vec2.class,
                (ctx, var) -> {
                    MegastructureOverlayTab overlay = ctx.overlay;
                    ImDrawList draw = ImGui.getWindowDrawList();

                    float x = overlay.worldXToCanvasX(var.getValue().x);
                    float y = overlay.worldZToCanvasY(var.getValue().y);

                    addPositionCircle(draw, var.getName(), x, y);
                }
        );

        //StructureGraph
        register(StructureGraph.class, (ctx, var) -> {
            MegastructureOverlayTab overlay = ctx.overlay;
            ImDrawList draw = ImGui.getWindowDrawList();

            StructureGraph graph = var.getValue();

            if (graph == null || graph.edges() == null) {
                return;
            }

            for (GraphEdge edge : graph.edges()) {
                if (edge == null || edge.from() == null || edge.to() == null) {
                    continue;
                }

                Vec2 from = edge.from().pos();
                Vec2 to = edge.to().pos();

                if (from == null || to == null) {
                    continue;
                }

                int color = switch (edge.tag()) {
                    case "WALL" -> 0xFFFF4444;
                    case "PATH" -> 0xFF44FF44;
                    default -> 0xFFFFFFFF;
                };

                String label = edge.tag() + " (" + (int) edge.length() + ")";

                addGraphLine(
                        draw,
                        overlay,
                        from,
                        to,
                        color,
                        label
                );
            }

            if (graph.nodes() == null) {
                return;
            }

            for (GraphNode node : graph.nodes()) {
                if (node == null || node.pos() == null) {
                    continue;
                }

                float x = overlay.worldXToCanvasX(node.pos().x);
                float y = overlay.worldZToCanvasY(node.pos().y);

                addPositionCircle(
                        draw,
                        node.tag(),
                        x,
                        y
                );
            }
        });
    }

    public static void addPositionCircle(ImDrawList draw, String name, float x, float y) {
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
                name
        );
    }

    public static void addGraphLine(ImDrawList draw,
                                    MegastructureOverlayTab overlay,
                                    Vec2 a,
                                    Vec2 b,
                                    int color,
                                    String name) {

        float x1 = overlay.worldXToCanvasX(a.x);
        float y1 = overlay.worldZToCanvasY(a.y);

        float x2 = overlay.worldXToCanvasX(b.x);
        float y2 = overlay.worldZToCanvasY(b.y);

        draw.addLine(x1, y1, x2, y2, color, 1.5f);

        float mx = (x1 + x2) * 0.5f;
        float my = (y1 + y2) * 0.5f;

        draw.addText(mx + 4, my + 4, 0xFFFFFFFF, name);
    }
}