package com.noodlegamer76.fracture.worldgen.megastructure.rules;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import net.minecraft.util.RandomSource;

import java.util.List;

public interface StructureRule {
    void run(WorldAccess access, Node n, RandomSource random, StructureInstance instance);

    String getDescription();


    default boolean shouldRun(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        return true;
    }
}
