package com.noodlegamer76.fracture.worldgen.megastructure.structure;

import com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.castle.PlaceStoneTowersFromTilesRule;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.castle.ShrinkStructureGraphPolygonsRule;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.geometry.GetTilesInPolygons;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.pos.NodePos;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.pos.RandomPosInNode;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.paths.AnchorGraphRule;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.paths.FootprintGeneratorRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Structures {
    private static final Structures instance = new Structures();

    public static Structures getInstance() {
        return instance;
    }

    private final List<StructureDefinition> structures = Collections.synchronizedList(new ArrayList<>());
    private int nextId;

    public void setupStructures() {
        clearDefinitions();

        //setupBoreasFactory();
    }

    public void setupBoreasFactory() {
        StructureDefinition boreasFactory = new StructureDefinition();
        addDefinition(boreasFactory);

        String buildingPolygons = ShrinkStructureGraphPolygonsRule.BUILDING_POLYGONS_KEY;
        String buildingTiles = "building_tiles";

        Structure layout = new Structure(100, 6);
        layout.addRule(new NodePos());
        layout.addRule(new FootprintGeneratorRule());
        layout.addRule(new AnchorGraphRule());
        layout.addRule(new ShrinkStructureGraphPolygonsRule(8));
        boreasFactory.addStructure(layout);

        Structure placement = new Structure(95, 1);
        placement.addRule(new GetTilesInPolygons(buildingPolygons, buildingTiles, 8));
        placement.addRule(new PlaceStoneTowersFromTilesRule(buildingTiles));

        boreasFactory.addStructure(placement);

    }

    public void addDefinition(StructureDefinition definition) {
        structures.add(definition);
    }

    public void clearDefinitions() {
        structures.clear();
        nextId = 0;
    }

    public List<StructureDefinition> getStructures() {
        return structures;
    }

    public int nextId() {
        return nextId++;
    }
}
