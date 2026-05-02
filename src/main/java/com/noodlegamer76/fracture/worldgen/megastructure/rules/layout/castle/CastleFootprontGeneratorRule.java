package com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.castle;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.StructureRule;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph.GraphNode;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec2;
import org.locationtech.jts.geom.*;

import java.util.ArrayList;
import java.util.List;

public class CastleFootprontGeneratorRule implements StructureRule {
    public static final String CASTLE_ANCHORS_KEY = "castle_anchors";

    private static final float MIN_DISTANCE = 100f;
    private static final float MIN_DISTANCE_SQ = MIN_DISTANCE * MIN_DISTANCE;

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

    @Override
    public void run(
            WorldAccess access,
            Node n,
            RandomSource random,
            StructureInstance instance
    ) {
        GenVar<Vec2> posVar = instance.getGenVar(n, "pos", Vec2.class);

        if (posVar == null || posVar.getValue() == null) {
            FractureMod.LOGGER.error(
                    this.getClass().getSimpleName()
                            + " ran before variable [Vec2 pos] was set"
            );
            return;
        }

        List<GraphNode> anchors = new ArrayList<>();
        List<Coordinate> coordinates = new ArrayList<>();

        Vec2 origin = posVar.getValue();

        anchors.add(new GraphNode(
                CastleLayoutTag.ORIGIN.name(),
                origin,
                GEOMETRY_FACTORY.createPoint(
                        new Coordinate(origin.x, origin.y)
                )
        ));

        int size = n.getSize();
        float half = size * 0.5f;

        float minX = origin.x - half;
        float maxX = origin.x + half;
        float minZ = origin.y - half;
        float maxZ = origin.y + half;

        Coordinate[] footprint = new Coordinate[]{
                new Coordinate(minX, minZ),
                new Coordinate(maxX, minZ),
                new Coordinate(maxX, maxZ),
                new Coordinate(minX, maxZ),
                new Coordinate(minX, minZ)
        };

        Polygon castleBounds = GEOMETRY_FACTORY.createPolygon(footprint);

        addAnchor(anchors, coordinates, CastleLayoutTag.WALL.name(), new Vec2(minX, minZ));
        addAnchor(anchors, coordinates, CastleLayoutTag.WALL.name(), new Vec2(maxX, minZ));
        addAnchor(anchors, coordinates, CastleLayoutTag.WALL.name(), new Vec2(maxX, maxZ));
        addAnchor(anchors, coordinates, CastleLayoutTag.WALL.name(), new Vec2(minX, maxZ));

        addAnchor(
                anchors,
                coordinates,
                CastleLayoutTag.WALL.name(),
                new Vec2((minX + maxX) * 0.5f, minZ)
        );

        addAnchor(
                anchors,
                coordinates,
                CastleLayoutTag.WALL.name(),
                new Vec2(maxX, (minZ + maxZ) * 0.5f)
        );

        addAnchor(
                anchors,
                coordinates,
                CastleLayoutTag.WALL.name(),
                new Vec2((minX + maxX) * 0.5f, maxZ)
        );

        addAnchor(
                anchors,
                coordinates,
                CastleLayoutTag.WALL.name(),
                new Vec2(minX, (minZ + maxZ) * 0.5f)
        );

        for (int i = 0; i < random.nextInt(12, 24); i++) {
            int attempts = 0;
            boolean placed = false;

            while (attempts < 10 && !placed) {
                attempts++;

                float tX = random.nextFloat();
                float tZ = random.nextFloat();

                float x = minX + (maxX - minX) * tX;
                float z = minZ + (maxZ - minZ) * tZ;

                float dx = x - origin.x;
                float dz = z - origin.y;

                x = origin.x + (dx * 0.8f);
                z = origin.y + (dz * 0.8f);

                Point point = GEOMETRY_FACTORY.createPoint(
                        new Coordinate(x, z)
                );

                if (!castleBounds.contains(point)) {
                    continue;
                }

                boolean tooClose = false;

                for (GraphNode a : anchors) {
                    if (!a.tag().equals(CastleLayoutTag.INTERIOR.name())) {
                        continue;
                    }

                    float ox = a.pos().x - x;
                    float oz = a.pos().y - z;

                    float distSq = (ox * ox) + (oz * oz);

                    if (distSq < MIN_DISTANCE_SQ) {
                        tooClose = true;
                        break;
                    }
                }

                if (tooClose) {
                    continue;
                }

                anchors.add(new GraphNode(
                        CastleLayoutTag.INTERIOR.name(),
                        new Vec2(x, z),
                        point
                ));

                coordinates.add(point.getCoordinate());
                placed = true;
            }
        }

        MultiPoint layoutPoints = GEOMETRY_FACTORY.createMultiPointFromCoords(
                coordinates.toArray(new Coordinate[0])
        );

        instance.setGenVar(
                CASTLE_ANCHORS_KEY,
                new Anchors(
                        anchors,
                        castleBounds,
                        layoutPoints
                ),
                true
        );
    }

    private void addAnchor(
            List<GraphNode> anchors,
            List<Coordinate> coordinates,
            String tag,
            Vec2 pos
    ) {
        Point point = GEOMETRY_FACTORY.createPoint(
                new Coordinate(pos.x, pos.y)
        );

        anchors.add(new GraphNode(
                tag,
                pos,
                point
        ));

        coordinates.add(point.getCoordinate());
    }

    @Override
    public String getDescription() {
        return "Generates the silhouette of a castle";
    }

    public record Anchors(
            List<GraphNode> anchors,
            Polygon bounds,
            MultiPoint points
    ) {
    }
}