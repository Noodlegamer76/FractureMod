package com.noodlegamer76.fracture.worldgen.megastructure.visualizer;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarSerializer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class StructureInstanceSerializer {
    public static void serialize(CompoundTag tag, StructureInstance instance) {
        CompoundTag data = new CompoundTag();
        for (GenVar<?> var: instance.getGenVars()) {
            CompoundTag varTag = new CompoundTag();
            serializeGenVar(varTag, var);
            data.put(var.getName(), varTag);
        }
        tag.put("data", data);
    }

    public static List<GenVar<?>> deserialize(CompoundTag tag) {
        CompoundTag dataTag = tag.getCompound("data");
        List<GenVar<?>> vars = new ArrayList<>();

        for (String key : dataTag.getAllKeys()) {
            CompoundTag varTag = dataTag.getCompound(key);
            if (!varTag.contains("serializer_id")) continue;

            ResourceLocation serializerId = ResourceLocation.parse(varTag.getString("serializer_id"));
            GenVar<?> var = deserializeGenVar(varTag, key, serializerId);
            vars.add(var);
        }

        return vars;
    }

    @SuppressWarnings("unchecked")
    private static <T> GenVar<T> deserializeGenVar(CompoundTag tag, String name, ResourceLocation serializerId) {
        RegistryObject<GenVarSerializer<T>> genVarSerializerRegistry =
                (RegistryObject<GenVarSerializer<T>>) (RegistryObject<?>)
                        GenVarSerializers.getGenVarSerializer(serializerId);

        if (genVarSerializerRegistry == null) {
            throw new IllegalArgumentException("No GenVarSerializer found for id: " + serializerId);
        }

        GenVarSerializer<T> serializer = genVarSerializerRegistry.get();
        T value = serializer.deserialize(tag, name);
        return new GenVar<>(value, genVarSerializerRegistry, name);
    }

    private static <T> void serializeGenVar(CompoundTag tag, GenVar<T> var) {
        CompoundTag data = new CompoundTag();
        var.getSerializer().serialize(tag, var.getName(), var.getValue());
        tag.put("value", data);
        tag.putString("serializer_id", var.getSerializerRegistryObject().getId().toString());
    }
}
