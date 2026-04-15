package com.noodlegamer76.fracture.worldgen.megastructure.structure;

import com.google.common.collect.ImmutableList;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.Placer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarCache;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureInstance {
    private final StructureDefinition definition;
    private final List<Placer> placers = new ArrayList<>();
    private final Map<String, GenVar<?>> genVars = new HashMap<>();

    public StructureInstance(StructureDefinition definition) {
        this.definition = definition;

        for (Structure structure: definition.getStructures()) {
            for (GenVar<?> genVar: structure.getGenVariables()) {
                if (genVars.containsKey(genVar.getName())) {
                    FractureMod.LOGGER.error("Duplicate GenVar: " + genVar.getName() + ". This should not happen!");
                }
                addGenVar(genVar);
            }
        }
    }

    private static <T> GenVar<T> copyOf(GenVar<T> original) {
        return new GenVar<>(original.getType(), original.getName(), original.isCacheable());
    }

    public void addPlacer(Placer placer) {
        placers.add(placer);
    }

    public List<Placer> getPlacers() {
        return placers;
    }

    public void generate(WorldAccess access) {
        definition.generate(access, this);
    }

    public StructureDefinition getDefinition() {
        return definition;
    }

    public void addGenVar(GenVar<?> genVar) {
        genVars.put(genVar.getName(), genVar);
    }

    public List<GenVar<?>> getGenVars() {
        return genVars.values().stream().collect(ImmutableList.toImmutableList());
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> GenVar<T> getGenVar(Node node, String name, GenVarType<T> type) {
        GenVar<?> raw = genVars.get(name);
        if (raw == null) {
            return null;
        }

        if (raw.getType() != type) {
            FractureMod.LOGGER.error("Serializer mismatch for GenVar: " + name);
            return null;
        }

        GenVar<T> result = (GenVar<T>) raw;

        if (result.getValue() == null) {
            GenVar<T> cached = GenVarCache.instance().getVar(node, name, type);
            if (cached != null && cached.getValue() != null) {
                result.setValue(cached.getValue());
            }
        }

        return result;
    }
}
