package com.noodlegamer76.fracture.worldgen.megastructure.rules.logic;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.StructureRule;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import net.minecraft.util.RandomSource;

import java.util.List;

public class ClearVars implements StructureRule {
    private final List<String> vars;

    public ClearVars(List<String> vars) {
        this.vars = vars;
    }

    public ClearVars() {
        this.vars = null;
    }

    @Override
    public void run(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        if (vars == null) {
            instance.clearGenVars();
            return;
        }

        for (String var : vars) {
            instance.removeGenVar(var);
        }
    }

    @Override
    public String getDescription() {
        return "Clears the specified variables from the Structure Instance";
    }
}
