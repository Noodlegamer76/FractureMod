package com.noodlegamer76.fracture.worldgen.megastructure.structure;

import com.google.common.collect.ImmutableList;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.Placer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
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
        
        for (Map.Entry<Integer, List<Structure>> structures: definition.getStructures().entrySet()) {
            for (Structure structure: structures.getValue()) {
                for (GenVar<?> genVar: structure.getGenVariables()) {
                    addGenVar(copyOf(genVar));
                }
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

    public void renameGenVar(String oldName, String newName) {
        GenVar<?> var = genVars.remove(oldName);
        if (var != null) {
            genVars.put(newName, var);
            var.setName(newName);
        }
    }

    public List<GenVar<?>> getGenVars() {
        return genVars.values().stream().collect(ImmutableList.toImmutableList());
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> GenVar<T> getGenVar(String name, GenVarType<T> type) {
        GenVar<?> raw = genVars.get(name);
        if (raw == null) return null;

        if (raw.getType() != type) {
            FractureMod.LOGGER.error("Serializer mismatch for GenVar: " + name);
            return null;
        }

        return (GenVar<T>) raw;
    }

}
