package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import javax.annotation.Nullable;

public class GenVar<T> {
    private String name;
    private final GenVarType<T> type;
    private T value;

    public GenVar(T value, GenVarType<T> type, String name) {
        this.name = name;
        this.type = type;
        this.value = value;
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
}
