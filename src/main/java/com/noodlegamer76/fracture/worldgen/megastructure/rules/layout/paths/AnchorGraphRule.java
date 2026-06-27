package com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.paths;

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
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import java.util.ArrayList;
import java.util.List;

public class AnchorGraphRule implements StructureRule {
    public static final String PATH_ANCHORS_KEY =
            FootprintGeneratorRule.PATH_ANCHORS_KEY;

    public static final String PATH_GRAPH_KEY = "path_graph";

    private static final GeometryFactory GEOMETRY_FACTORY =
            new GeometryFactory();

    @Override
    public boolean shouldRun(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        return !instance.hasGenVar(PATH_GRAPH_KEY);
    }

    @Override
    public void run(
            WorldAccess access,
            Node n,
            RandomSource random,
            StructureInstance instance
    ) {
        GenVar<FootprintGeneratorRule.Anchors> anchorsVar =
                instance.getGenVar(
                        n,
                        PATH_ANCHORS_KEY,
                        FootprintGeneratorRule.Anchors.class
                );

        if (anchorsVar == null) {
            FractureMod.LOGGER.error(
                    this.getClass().getSimpleName()
                            + " ran before variable ["
                            + PATH_ANCHORS_KEY
                            + "] was set"
            );
            return;
        }

        FootprintGeneratorRule.Anchors anchors =
                anchorsVar.getValue();

        DelaunayTriangulationBuilder builder =
                new DelaunayTriangulationBuilder();

        builder.setSites(anchors.points());

        Geometry triangleGeometry = builder.getTriangles(GEOMETRY_FACTORY);
        List<Polygon> triangles = new ArrayList<>();

        for (int i = 0; i < triangleGeometry.getNumGeometries(); i++) {
            Geometry geometry = triangleGeometry.getGeometryN(i);
            if (geometry instanceof Polygon polygon) {
                triangles.add(polygon);
            }
        }

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
                n,
                PATH_GRAPH_KEY,
                new StructureGraph(
                        anchors.anchors(),
                        edges,
                        edgeGeometry,
                        triangles
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
                a.tag().equals(LayoutTag.WALL.name());

        boolean wallB =
                b.tag().equals(LayoutTag.WALL.name());

        if (wallA && wallB) {
            return "WALL";
        }

        return "PATH";
    }

    @Override
    public String getDescription() {
        return "Builds connectivity graph from anchors";
    }
}