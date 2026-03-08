package com.noodlegamer76.fracture.worldgen.megastructure.structure;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.StructMath;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.Placer;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StructureDefinition {
    private final List<Structure> structures = new ArrayList<>();
    private int highestNodeLevel = 0;

    public void generate(WorldAccess access, StructureInstance instance) {
        for (Structure structure : structures) {
            int level = structure.getNodeLevel();
            Node originNode = new Node(access.origin().getX(), access.origin().getZ(), level);

            for (Node n : StructMath.get3x3Nodes(originNode)) {
                RandomSource random = StructMath.getNodeRandom(n, access, structure.getId());

                if (!structure.shouldGenerate(access, random, n, instance)) continue;

                structure.generate(access, n, random, instance);
            }
        }

        if (access.getFeatureContext() != null) {
            for (Placer placer : instance.getPlacers()) {
                placer.place(access.getFeatureContext(), access.random(), instance);
            }
        }
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public void addStructure(Structure structure) {
        int level = structure.getNodeLevel();

        structures.add(structure);
        structures.sort(Comparator.comparingInt(Structure::getPriority).reversed());

        if (level > highestNodeLevel) highestNodeLevel = level;
    }

    public int getHighestNodeLevel() {
        return highestNodeLevel;
    }
}