package com.noodlegamer76.fracture.worldgen.megastructure.structure;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.StructureRule;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

public class Structure {
    private final List<StructureRule> rules = new ArrayList<>();
    private final int priority;
    private final int id;
    private final int nodeLevel;

    public Structure(int priority, int nodeLevel) {
        this.priority = priority;
        this.id = Structures.getInstance().nextId();
        this.nodeLevel = nodeLevel;
    }

    public void generate(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        for (StructureRule rule: rules) {
            rule.run(access, n, random, instance);
        }
    }

    public int getNodeLevel() {
        return nodeLevel;
    }

    public int getPriority() {
        return priority;
    }

    public List<StructureRule> getRules() {
        return rules;
    }

    public void addRule(StructureRule rule) {
        rules.add(rule);
    }

    public int getId() {
        return id;
    }
}
