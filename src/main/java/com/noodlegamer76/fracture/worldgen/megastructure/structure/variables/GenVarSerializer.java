package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;
import net.minecraft.nbt.CompoundTag;

public interface GenVarSerializer<T> {
    void serialize(CompoundTag tag, String name, T value);
    T deserialize(CompoundTag tag, String name);
    Class<T> getValueClass();
    VisualizerEntry<GenVar<T>> visualizerEntry(GenVar<T> genVar);

    @FunctionalInterface
    interface Writer<T> {
        void write(CompoundTag tag, String name, T value);
    }

    @FunctionalInterface
    interface Reader<T> {
        T read(CompoundTag tag, String name);
    }

    @FunctionalInterface
    interface ClassGetter<T> {
        Class<T> get();
    }

    @FunctionalInterface
    interface VisualizerEntryGetter<T> {
        VisualizerEntry<GenVar<T>> get(GenVar<T> genVar);
    }

    static <T> GenVarSerializer<T> create(Writer<T> writer, Reader<T> reader, ClassGetter<T> classGetter, VisualizerEntryGetter<T> visualizerEntry) {
        return new GenVarSerializer<>() {
            @Override
            public void serialize(CompoundTag tag, String name, T value) {
                writer.write(tag, name, value);
            }

            @Override
            public T deserialize(CompoundTag tag, String name) {
                return reader.read(tag, name);
            }

            @Override
            public Class<T> getValueClass() {
                return classGetter.get();
            }

            @Override
            public VisualizerEntry<GenVar<T>> visualizerEntry(GenVar<T> genVar) {
                return visualizerEntry.get(genVar);
            }
        };
    }
}
