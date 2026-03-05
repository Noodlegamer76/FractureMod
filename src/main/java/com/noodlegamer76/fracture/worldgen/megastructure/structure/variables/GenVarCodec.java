package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import net.minecraft.nbt.CompoundTag;

public interface GenVarCodec<T> {
    void encode(CompoundTag parent, String name, T value);
    T decode(CompoundTag parent, String name);
    Class<T> valueClass();

    static <T> GenVarCodec<T> of(GenVarWriter<T> writer, GenVarReader<T> reader, Class<T> clazz) {
        return new GenVarCodec<>() {
            @Override
            public void encode(CompoundTag parent, String name, T value) {
                CompoundTag sub = new CompoundTag();
                writer.write(sub, "value", value);
                parent.put(name, sub);
            }

            @Override
            public T decode(CompoundTag parent, String name) {
                CompoundTag sub = parent.getCompound(name);
                return reader.read(sub, "value");
            }

            @Override
            public Class<T> valueClass() {
                return clazz;
            }
        };
    }

    @FunctionalInterface
    interface GenVarWriter<T> {
        void write(CompoundTag tag, String key, T value);
    }

    @FunctionalInterface
    interface GenVarReader<T> {
        T read(CompoundTag tag, String key);
    }
}