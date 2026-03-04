package com.noodlegamer76.fracture.worldgen.megastructure.structure;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.StructMath;
import com.noodlegamer76.fracture.worldgen.megastructure.rule.IStructureRule;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public abstract class Structure {
    private final List<IStructureRule> rules = new ArrayList<>();
    private final int priority;
    private final int id;

    protected Structure(int priority) {
        this.priority = priority;
        this.id = Structures.getInstance().nextId();
    }

    public abstract int getMaxSize();

    public abstract void generate(FeaturePlaceContext<NoneFeatureConfiguration> ctx, Node n, RandomSource random, StructureInstance instance);

    public abstract List<GenVar<?>> getGenVariables();

    public VisualizerEntry getVisualizer() {
        return null;
    }

    public boolean shouldGenerate(FeaturePlaceContext<NoneFeatureConfiguration> ctx, RandomSource random) {
        for (IStructureRule rule: rules) {
            if (!rule.shouldGenerate(ctx, random, this)) {
                return false;
            }
        }
        return true;
    }

    public int getNodeLevel() {
        int size = getMaxSize();
        return StructMath.getQuadTreeLevel(size);
    }

    public int getPriority() {
        return priority;
    }

    public List<IStructureRule> getRules() {
        return rules;
    }

    public void addRule(IStructureRule rule) {
        rules.add(rule);
    }

    public int getId() {
        return id;
    }
}
