package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import javax.annotation.Nullable;

public class GenVar<T> {
    private String name;
    private final GenVarType<T> type;
    private T value;
    private final boolean cacheable;

    public GenVar(GenVarType<T> type, String name) {
        this(type, name, false);
    }

    /** @param cacheable If true, the value computed for a given Node is reused across
     *                   all chunks that share that Node instead of being regenerated. */
    public GenVar(GenVarType<T> type, String name, boolean cacheable) {
        this.name = name;
        this.type = type;
        this.cacheable = cacheable;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    public GenVarType<T> getType() {
        return type;
    }

    public Class<T> getValueClass() {
        return type.codec().valueClass();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCacheable() {
        return cacheable;
    }
}