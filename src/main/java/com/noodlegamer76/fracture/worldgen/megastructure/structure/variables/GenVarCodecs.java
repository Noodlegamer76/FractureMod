package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public final class GenVarCodecs {
    public static final GenVarCodec<Integer> INT = GenVarCodec.of(CompoundTag::putInt, CompoundTag::getInt, Integer.class);
    public static final GenVarCodec<Double> DOUBLE = GenVarCodec.of(CompoundTag::putDouble, CompoundTag::getDouble, Double.class);
    public static final GenVarCodec<Float> FLOAT = GenVarCodec.of(CompoundTag::putFloat, CompoundTag::getFloat, Float.class);
    public static final GenVarCodec<Boolean> BOOLEAN = GenVarCodec.of(CompoundTag::putBoolean, CompoundTag::getBoolean, Boolean.class);
    public static final GenVarCodec<String> STRING = GenVarCodec.of(CompoundTag::putString, CompoundTag::getString, String.class);
    public static final GenVarCodec<byte[]> BYTE_ARRAY = GenVarCodec.of(CompoundTag::putByteArray, CompoundTag::getByteArray, byte[].class);
    public static final GenVarCodec<Long> LONG = GenVarCodec.of(CompoundTag::putLong, CompoundTag::getLong, Long.class);

    public static final GenVarCodec<Vec3> VEC3 = GenVarCodec.of((tag, key, v) -> {
        CompoundTag t = new CompoundTag();
        t.putDouble("x", v.x);
        t.putDouble("y", v.y);
        t.putDouble("z", v.z);
        tag.put(key, t);
    }, (tag, key) -> {
        CompoundTag t = tag.getCompound(key);
        return new Vec3(t.getDouble("x"), t.getDouble("y"), t.getDouble("z"));
    }, Vec3.class);
}