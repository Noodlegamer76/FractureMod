package com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.castle;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.StructureRule;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph.GraphEdge;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph.GraphNode;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph.StructureGraph;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec2;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CastleGraphCullingRule implements StructureRule {
    public static final String CASTLE_GRAPH_KEY =
            CastleAnchorGraphRule.CASTLE_GRAPH_KEY;

    @Override
    public void run(
            WorldAccess access,
            Node n,
            RandomSource random,
            StructureInstance instance
    ) {
        GenVar<StructureGraph> graphVar =
                instance.getGenVar(
                        n,
                        CASTLE_GRAPH_KEY,
                        StructureGraph.class
                );

        if (graphVar == null || graphVar.getValue() == null) {
            FractureMod.LOGGER.error(
                    this.getClass().getSimpleName()
                            + " ran before variable ["
                            + CASTLE_GRAPH_KEY
                            + "] was set"
            );
            return;
        }

        StructureGraph graph = graphVar.getValue();

        List<GraphEdge> keptEdges = new ArrayList<>();
        Set<GraphNode> keptNodes = new HashSet<>();

        for (GraphEdge edge : graph.edges()) {
            if (!isLineIntersecting(
                    edge.from().pos(),
                    edge.to().pos(),
                    n
            )) {
                continue;
            }

            keptEdges.add(edge);
            keptNodes.add(edge.from());
            keptNodes.add(edge.to());
        }

        instance.setGenVar(
                CASTLE_GRAPH_KEY,
                new StructureGraph(
                        new ArrayList<>(keptNodes),
                        keptEdges,
                        graph.rawGeometry()
                ),
                true
        );
    }

    private boolean isLineIntersecting(
            Vec2 a,
            Vec2 b,
            Node n
    ) {
        if (isInside(a, n) || isInside(b, n)) {
            return true;
        }

        float x1 = a.x;
        float y1 = a.y;
        float x2 = b.x;
        float y2 = b.y;

        int x = n.getX();
        int y = n.getZ();
        int size = n.getSize();

        return
                Line2D.linesIntersect(
                        x1, y1,
                        x2, y2,
                        x, y,
                        x + size, y
                )
                        ||
                        Line2D.linesIntersect(
                                x1, y1,
                                x2, y2,
                                x, y + size,
                                x + size, y + size
                        )
                        ||
                        Line2D.linesIntersect(
                                x1, y1,
                                x2, y2,
                                x, y,
                                x, y + size
                        )
                        ||
                        Line2D.linesIntersect(
                                x1, y1,
                                x2, y2,
                                x + size, y,
                                x + size, y + size
                        );
    }

    private boolean isInside(
            Vec2 p,
            Node n
    ) {
        int x = n.getX();
        int y = n.getZ();
        int size = n.getSize();

        return
                p.x >= x
                        && p.x <= x + size
                        && p.y >= y
                        && p.y <= y + size;
    }

    @Override
    public String getDescription() {
        return "Culls a Castle Graph to only edges and nodes that influence the current node";
    }
}