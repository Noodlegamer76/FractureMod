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
    private final StructureGenerateCondition spawnCondition;

    public StructureDefinition(StructureGenerateCondition spawnCondition) {
        this.spawnCondition = spawnCondition;
    }

    public StructureDefinition() {
        this.spawnCondition = (access, definition) -> true;
    }


    /**
     * Generates structures within the provided world context. Structures are generated
     * based on their node levels, and depending on the {@code onlyCenter} flag, either
     * only the center node or a 3x3 grid of nodes around the origin is processed.
     * After all structure generation, placers defined in the structure instance
     * are used to place additional elements into the world.
     *
     * @param access the world access object providing context such as origin, randomness, and dimensions
     * @param instance the structure instance defining the current generation context, including placers and variables
     * @param onlyCenter if true, structures will only process the center node; if false, a 3x3 grid of nodes will be processed. This is for debug purposes.
     */
    public void generate(WorldAccess access, StructureInstance instance, boolean onlyCenter) {
        for (Structure structure : structures) {
            int level = structure.getNodeLevel();
            Node originNode = new Node(access.origin().getX(), access.origin().getZ(), level);

            if (onlyCenter) {
                RandomSource random = StructMath.getNodeRandom(originNode, access, structure.getId());
                structure.generate(access, originNode, random, instance);
            }
            else {
                for (Node n : StructMath.get3x3Nodes(originNode)) {
                    instance.clearGenVars();
                    RandomSource random = StructMath.getNodeRandom(n, access, structure.getId());

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

    public List<Structure> getStructures() {
        return structures;
    }

    public void addStructure(Structure structure) {
        structures.add(structure);
        structures.sort(Comparator.comparingInt(Structure::getPriority).reversed());
    }

    public StructureGenerateCondition getSpawnCondition() {
        return spawnCondition;
    }
}