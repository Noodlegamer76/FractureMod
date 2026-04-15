package com.noodlegamer76.fracture.worldgen.megastructure.structure.structures;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import net.minecraft.util.RandomSource;

import java.util.List;

public class VoidRegion extends Structure {
    protected VoidRegion(int priority) {
        super(priority);
    }

    @Override
    public int getMaxSize() {
        return 2047;
    }

    @Override
    public void generate(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        
    }

    @Override
    public List<GenVar<?>> getGenVariables() {
        return List.of();
    }
}
