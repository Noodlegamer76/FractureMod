package com.noodlegamer76.fracture.worldgen.megastructure.structure;

import com.noodlegamer76.fracture.worldgen.megastructure.rules.RandomPosInNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Temporary class for definitions
public class Structures {
    private static final Structures instance = new Structures();

    public static Structures getInstance() {
        return instance;
    }

    private final List<StructureDefinition> structures = Collections.synchronizedList(new ArrayList<>());
    private int nextId;

    public void setupStructures() {
        clearDefinitions();


        StructureDefinition structureDefinition = new StructureDefinition();
        addDefinition(structureDefinition);

        Structure test = new Structure(100, 4);
        test.addRule(new RandomPosInNode());

        structureDefinition.addStructure(test);
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
