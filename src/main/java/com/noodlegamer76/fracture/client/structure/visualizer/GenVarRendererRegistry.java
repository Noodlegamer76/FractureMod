package com.noodlegamer76.fracture.client.structure.visualizer;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.geometry.Polygons;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph.GraphEdge;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph.GraphNode;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph.StructureGraph;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import net.minecraft.world.phys.Vec2;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

import java.util.HashMap;
import java.util.List;
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

            if (graph == null) {
                return;
            }

            if (graph.triangles() != null) {
                int triangleColor = 0x44FFFF00;

                for (Polygon triangle : graph.triangles()) {
                    if (triangle == null || triangle.getExteriorRing() == null) {
                        continue;
                    }

                    Coordinate[] coords = triangle.getExteriorRing().getCoordinates();

                    if (coords.length < 4) {
                        continue;
                    }

                    ImVec2[] points = new ImVec2[] {
                            new ImVec2(
                                    overlay.worldXToCanvasX((float) coords[0].x),
                                    overlay.worldZToCanvasY((float) coords[0].y)
                            ),
                            new ImVec2(
                                    overlay.worldXToCanvasX((float) coords[1].x),
                                    overlay.worldZToCanvasY((float) coords[1].y)
                            ),
                            new ImVec2(
                                    overlay.worldXToCanvasX((float) coords[2].x),
                                    overlay.worldZToCanvasY((float) coords[2].y)
                            )
                    };

                    draw.addConvexPolyFilled(points, 3, triangleColor);
                }
            }

            if (graph.edges() != null) {
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
            }

            if (graph.nodes() != null) {
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
            }
        });

        //Polygons
        register(Polygons.class, (ctx, var) -> {
            List<Polygon> polygons = var.getValue().polygons();
            MegastructureOverlayTab overlay = ctx.overlay;
            ImDrawList draw = ImGui.getWindowDrawList();

            int polygonColor = 0x44FFFF00;

            for (Polygon polygon : polygons) {
                if (polygon == null || polygon.getExteriorRing() == null) {
                    continue;
                }

                Coordinate[] coords = polygon.getExteriorRing().getCoordinates();

                if (coords.length < 4) {
                    continue;
                }

                int numPoints = coords.length - 1;
                ImVec2[] points = new ImVec2[numPoints];

                for (int i = 0; i < numPoints; i++) {
                    points[i] = new ImVec2(
                            overlay.worldXToCanvasX((float) coords[i].x),
                            overlay.worldZToCanvasY((float) coords[i].y)
                    );
                }

                draw.addConvexPolyFilled(points, numPoints, polygonColor);
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