package com.noodlegamer76.fracture.worldgen.megastructure.rules.logic;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.StructureRule;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import net.minecraft.util.RandomSource;

import java.util.List;

public class LoopRules implements StructureRule {
    private final List<StructureRule> rules;
    private final IterationSupplier iterationSupplier;

    public LoopRules(List<StructureRule> rules, IterationSupplier iterationSupplier) {
        this.rules = rules;
        this.iterationSupplier = iterationSupplier;
    }

    public LoopRules(List<StructureRule> rules, int count) {
        this.rules = rules;
        this.iterationSupplier = ((worldAccess, n, random, instance) -> count);
    }

    @Override
    public void run(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        int loopCount = iterationSupplier.getIterationCount(access, n, random, instance);

        instance.setGenVar("loop_count", loopCount);
        for (int i = 0; i < loopCount; i++) {
            instance.setGenVar("loop_index", i);
            for (StructureRule rule : rules) {
                rule.run(access, n, random, instance);
            }
        }

        instance.removeGenVar("loop_index");
        instance.removeGenVar("loop_count");
    }

    @Override
    public String getDescription() {
        return "Loops a set of rules " + rules.size() + " times.";
    }


    public interface IterationSupplier {
        int getIterationCount(WorldAccess worldAccess, Node n, RandomSource random, StructureInstance instance);
    }
}
