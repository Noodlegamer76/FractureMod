package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class GenVarRegistry {
    private static final Map<ResourceLocation, GenVarType<?>> TYPES = new LinkedHashMap<>();

    private GenVarRegistry() {}

    public static <T> GenVarType<T> register(GenVarType<T> type) {
        ResourceLocation id = type.id();
        if (TYPES.containsKey(id)) {
            throw new IllegalStateException("GenVarType already registered: " + id);
        }
        TYPES.put(id, type);
        return type;
    }

    @SuppressWarnings("unchecked")
    public static <T> GenVarType<T> get(ResourceLocation id) {
        return (GenVarType<T>) TYPES.get(id);
    }

    public static Map<ResourceLocation, GenVarType<?>> all() {
        return Collections.unmodifiableMap(TYPES);
    }
}