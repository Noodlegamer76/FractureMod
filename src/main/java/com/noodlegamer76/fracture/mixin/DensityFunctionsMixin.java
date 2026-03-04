package com.noodlegamer76.fracture.mixin;

import com.mojang.serialization.Codec;
import com.noodlegamer76.fracture.worldgen.gen.DistanceFromCenterDensityFunction;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DensityFunctions.class)
public class DensityFunctionsMixin {

    @Inject(method = "bootstrap", at = @At("HEAD"))
    private static void bootstrap(Registry<Codec<? extends DensityFunction>> registry, CallbackInfoReturnable<Codec<? extends DensityFunction>> cir) {
        //DensityFunctionsInvoker.register(registry, "distance_from_center", DistanceFromCenterDensityFunction.CODEC);
    }
}
