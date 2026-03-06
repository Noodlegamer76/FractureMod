package com.noodlegamer76.fracture.worldgen.megastructure.structure;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.StructMath;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.Placer;
import net.minecraft.util.RandomSource;

import java.util.*;

public class StructureDefinition {
    // Integer is node level
    private final TreeMap<Integer, List<Structure>> structures =
            new TreeMap<>(Comparator.reverseOrder());
    private int highestNodeLevel = 0;

    public void generate(WorldAccess access, StructureInstance instance) {
        for (List<Structure> list : structures.values()) {
            for (Structure structure : list) {
                int level = structure.getNodeLevel();
                Node center = new Node(access.origin().getX(), access.origin().getZ(), level);
                List<Node> nodes = StructMath.get3x3Nodes(center);

                for (Node n : nodes) {
                    RandomSource random = StructMath.getNodeRandom(n, access, structure.getId());

                    if (!structure.shouldGenerate(access, random)) continue;

                    structure.generate(access, n, random, instance);
                }
            }
        }

        if (access.getFeatureContext() != null) {
            for (Placer placer : instance.getPlacers()) {
                placer.place(access.getFeatureContext(), access.random(), instance);
            }
        }
    }

    public Map<Integer, List<Structure>> getStructures() {
        return structures;
    }

    public void addStructure(Structure structure) {
        int level = structure.getNodeLevel();
        List<Structure> list = structures.computeIfAbsent(level, k -> new ArrayList<>());

        list.add(structure);
        list.sort(Comparator.comparingInt(Structure::getPriority).reversed());

        if (level > highestNodeLevel) highestNodeLevel = level;
    }

    public int getHighestNodeLevel() {
        return highestNodeLevel;
    }
}