package com.noodlegamer76.fracture.worldgen.megastructure.structure;


import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;

@FunctionalInterface
public interface StructureGenerateCondition {
    boolean shouldGenerate(WorldAccess access, StructureDefinition definition);
}
