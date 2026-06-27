package com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.castle;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.StructureRule;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.HollowRoomPlacer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.StructureUtils;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.geometry.Polygons;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

public class PlaceStoneTowersFromTilesRule implements StructureRule {
    private final String tileKey;

    public PlaceStoneTowersFromTilesRule(String tileKey) {
        this.tileKey = tileKey;
    }

    @Override
    public void run(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        GenVar<Polygons> tileVar = instance.getGenVar(
                n,
                tileKey,
                Polygons.class
        );

        if (tileVar == null) {
            FractureMod.LOGGER.error(
                    this.getClass().getSimpleName()
                            + " ran before variable ["
                            + tileKey
                            + "] was set"
            );
            return;
        }

        Polygons tiles = tileVar.getValue();
        BlockState state = Blocks.STONE_BRICKS.defaultBlockState();

        for (Polygon polygon : tiles.polygons()) {
            Coordinate[] coordinates = polygon.getExteriorRing().getCoordinates();

            if (coordinates.length == 0) continue;

            double minX = coordinates[0].x;
            double maxX = coordinates[0].x;
            double minZ = coordinates[0].y;
            double maxZ = coordinates[0].y;

            for (int i = 1; i < coordinates.length; i++) {
                Coordinate c = coordinates[i];

                if (c.x < minX) minX = c.x;
                if (c.x > maxX) maxX = c.x;
                if (c.y < minZ) minZ = c.y;
                if (c.y > maxZ) maxZ = c.y;
            }

            int height = 0;

            AABB boundingBox = new AABB(minX, height, minZ, maxX, height + 100, maxZ);

            HollowRoomPlacer placer = new HollowRoomPlacer(boundingBox, state);
            instance.addPlacer(placer);
        }
    }

    @Override
    public String getDescription() {
        return "Places stone towers from tiles.";
    }
}
