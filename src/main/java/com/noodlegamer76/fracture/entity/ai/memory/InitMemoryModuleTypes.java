package com.noodlegamer76.fracture.entity.ai.memory;

import com.mojang.serialization.Codec;
import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class InitMemoryModuleTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, FractureMod.MODID);

    public static final RegistryObject<MemoryModuleType<Boolean>> ATTACK_DELAY = MEMORY_MODULE_TYPES.register("attack_delay",
            () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));
}
