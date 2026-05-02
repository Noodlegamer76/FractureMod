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
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import java.util.ArrayList;
import java.util.List;

public class CastleAnchorGraphRule implements StructureRule {
    public static final String CASTLE_ANCHORS_KEY =
            CastleFootprontGeneratorRule.CASTLE_ANCHORS_KEY;

    public static final String CASTLE_GRAPH_KEY = "castle_graph";

    private static final GeometryFactory GEOMETRY_FACTORY =
            new GeometryFactory();

    @Override
    public void run(
            WorldAccess access,
            Node n,
            RandomSource random,
            StructureInstance instance
    ) {
        GenVar<CastleFootprontGeneratorRule.Anchors> anchorsVar =
                instance.getGenVar(
                        n,
                        CASTLE_ANCHORS_KEY,
                        CastleFootprontGeneratorRule.Anchors.class
                );

        if (anchorsVar == null) {
            FractureMod.LOGGER.error(
                    this.getClass().getSimpleName()
                            + " ran before variable ["
                            + CASTLE_ANCHORS_KEY
                            + "] was set"
            );
            return;
        }

        CastleFootprontGeneratorRule.Anchors anchors =
                anchorsVar.getValue();

        DelaunayTriangulationBuilder builder =
                new DelaunayTriangulationBuilder();

        builder.setSites(anchors.points());

        Geometry edgeGeometry =
                builder.getEdges(GEOMETRY_FACTORY);

        List<GraphEdge> edges = new ArrayList<>();

        for (int i = 0; i < edgeGeometry.getNumGeometries(); i++) {
            Geometry geometry = edgeGeometry.getGeometryN(i);

            if (!(geometry instanceof LineString line)) {
                continue;
            }

            if (line.getNumPoints() < 2) {
                continue;
            }

            double x1 = line.getCoordinateN(0).x;
            double z1 = line.getCoordinateN(0).y;

            double x2 = line.getCoordinateN(1).x;
            double z2 = line.getCoordinateN(1).y;

            GraphNode from = findClosestAnchor(
                    anchors.anchors(),
                    (float) x1,
                    (float) z1
            );

            GraphNode to = findClosestAnchor(
                    anchors.anchors(),
                    (float) x2,
                    (float) z2
            );

            if (from == null || to == null || from.equals(to)) {
                continue;
            }

            String type = determineEdgeType(from, to);

            edges.add(new GraphEdge(
                    from,
                    to,
                    type,
                    line.getLength()
            ));
        }

        instance.setGenVar(
                CASTLE_GRAPH_KEY,
                new StructureGraph(
                        anchors.anchors(),
                        edges,
                        edgeGeometry
                ),
                true
        );
    }

    private GraphNode findClosestAnchor(
            List<GraphNode> anchors,
            float x,
            float z
    ) {
        GraphNode closest = null;
        double bestDistance = Double.MAX_VALUE;

        for (GraphNode anchor : anchors) {
            float dx = anchor.pos().x - x;
            float dz = anchor.pos().y - z;

            double dist = (dx * dx) + (dz * dz);

            if (dist < bestDistance) {
                bestDistance = dist;
                closest = anchor;
            }
        }

        return closest;
    }

    private String determineEdgeType(
            GraphNode a,
            GraphNode b
    ) {
        boolean wallA =
                a.tag().equals(CastleLayoutTag.WALL.name());

        boolean wallB =
                b.tag().equals(CastleLayoutTag.WALL.name());

        if (wallA && wallB) {
            return "WALL";
        }

        return "PATH";
    }

    @Override
    public String getDescription() {
        return "Builds connectivity graph from castle anchors";
    }
}