package com.noodlegamer76.fracture.worldgen.megastructure.structure;

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


       //StructureDefinition castle = new StructureDefinition();
       //addDefinition(castle);

       //Structure castleLayout = new Structure(100, 6);
       //castleLayout.addRule(new RandomPosInNode());
       //castleLayout.addRule(new CastleFootprontGeneratorRule());
       //castleLayout.addRule(new CastleAnchorGraphRule());
       //castle.addStructure(castleLayout);

       //Structure castleGraphCulling = new Structure(90, 4);
       //castleGraphCulling.addRule(new CastleGraphCullingRule());
       //castle.addStructure(castleGraphCulling);
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
