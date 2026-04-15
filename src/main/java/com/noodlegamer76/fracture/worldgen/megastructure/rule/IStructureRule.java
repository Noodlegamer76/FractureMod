package com.noodlegamer76.fracture.worldgen.megastructure.rule;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import net.minecraft.util.RandomSource;

@FunctionalInterface
public interface IStructureRule {
    boolean shouldGenerate(WorldAccess access, RandomSource random, Structure structure);
}
