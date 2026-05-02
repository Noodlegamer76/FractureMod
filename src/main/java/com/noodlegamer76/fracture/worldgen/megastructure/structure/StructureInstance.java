package com.noodlegamer76.fracture.worldgen.megastructure.structure;

import com.google.common.collect.ImmutableList;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.Placer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarCache;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureInstance {
    private final StructureDefinition definition;
    private final List<Placer> placers = new ArrayList<>();
    private final Map<String, GenVar<?>> vars = new HashMap<>();

    public StructureInstance(StructureDefinition definition) {
        this.definition = definition;
    }

    public void addPlacer(Placer placer) {
        placers.add(placer);
    }

    public List<Placer> getPlacers() {
        return placers;
    }

    public void generate(WorldAccess access, boolean onlyCenter) {
        definition.generate(access, this, onlyCenter);
    }

    public StructureDefinition getDefinition() {
        return definition;
    }

    public void setGenVar(GenVar<?> genVar) {
        vars.put(genVar.getName(), genVar);
    }

    public void setGenVar(String name, Object object) {
        vars.put(name, new  GenVar<>(name, object));
    }

    public void setGenVar(String name, Object object, boolean cachable) {
        vars.put(name, new  GenVar<>(name, object, cachable));
    }

    public List<GenVar<?>> getGenVars() {
        return vars.values().stream().collect(ImmutableList.toImmutableList());
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> GenVar<T> getGenVar(Node node, String name, Class<T> type) {
        GenVar<?> raw = vars.get(name);
        if (raw == null) {
            return null;
        }

        if (!type.isAssignableFrom(raw.getType())) {
            FractureMod.LOGGER.error("Clazz mismatch for GenVar: " + name);
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

    public void clearGenVars() {
        vars.clear();
    }

    public void removeGenVar(String name) {
        vars.remove(name);
    }
}
