package com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.geometry;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.StructureRule;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.StructureUtils;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.geometry.Polygons;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import net.minecraft.util.RandomSource;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.operation.overlay.PolygonBuilder;

import java.util.ArrayList;
import java.util.List;

public class GetTilesInPolygons implements StructureRule {
    private final String inputKey;
    private final String outputKey;
    private final int tileSize;

    public GetTilesInPolygons(String inputKey, String outputKey, int tileSize) {
        this.inputKey = inputKey;
        this.outputKey = outputKey;
        this.tileSize = tileSize;
    }

    @Override
    public boolean shouldRun(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        return !instance.hasGenVar(outputKey);
    }

    @Override
    public void run(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        GenVar<Polygons> polygonsVar = instance.getGenVar(
                n,
                inputKey,
                Polygons.class
        );

        if (polygonsVar == null) {
            FractureMod.LOGGER.error(
                    this.getClass().getSimpleName()
                            + " ran before variable ["
                            + inputKey
                            + "] was set"
            );
            return;
        }

        Polygons polygons = polygonsVar.getValue();

        List<Polygon> tiles = new ArrayList<>();

        Polygon nodePolygon = StructureUtils.getNodePolygon(n);

        for (Polygon polygon : polygons.polygons()) {
            if (!polygon.intersects(nodePolygon)) continue;

            List<Polygon> tilesInside = StructureUtils.getAlignedTilesInside(polygon, tileSize);
            tiles.addAll(tilesInside);
        }

        Polygons tilePolygons = new Polygons(tiles);
        instance.setGenVar(n, new GenVar<>(outputKey, tilePolygons, true));
    }

    @Override
    public String getDescription() {
        return "Gets the tiles in the polygons given";
    }
}
