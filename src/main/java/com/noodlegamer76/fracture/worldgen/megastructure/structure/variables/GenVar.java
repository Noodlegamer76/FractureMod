package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class GenVar<T> {
    private T value;
    private final RegistryObject<GenVarSerializer<T>> serializer;
    private String name;

    public GenVar(T value, RegistryObject<GenVarSerializer<T>> serializer, String name) {
        this.value = value;
        this.serializer = serializer;
        this.name = name;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    public GenVarSerializer<T> getSerializer() {
        return serializer.get();
    }

    public Class<T> getValueClass() {
        return serializer.get().getValueClass();
    }

    public RegistryObject<GenVarSerializer<T>> getSerializerRegistryObject() {
        return serializer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
