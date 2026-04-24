package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import javax.annotation.Nullable;

public class GenVar<T> {
    private String name;
    private T value;
    private Class<T> type;
    private final boolean cacheable;

    public GenVar(String name, T object) {
        this(name, object, false);
    }

    @SuppressWarnings("unchecked")
    public GenVar( String name, T object, boolean cacheable) {
        this.name = name;
        value = object;
        this.cacheable = cacheable;
        this.type = (Class<T>) object.getClass();
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    public Class<T> getType() {
        return type;
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