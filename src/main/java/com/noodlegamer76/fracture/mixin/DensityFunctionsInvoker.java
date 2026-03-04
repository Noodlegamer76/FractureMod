package com.noodlegamer76.fracture.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DensityFunctions.class)
public interface DensityFunctionsInvoker {

    @Invoker("register")
    static Codec<? extends DensityFunction> register(Registry<Codec<? extends DensityFunction>> pRegistry, String pName, KeyDispatchDataCodec<? extends DensityFunction> pCodec) {
        return null;
    }
}
