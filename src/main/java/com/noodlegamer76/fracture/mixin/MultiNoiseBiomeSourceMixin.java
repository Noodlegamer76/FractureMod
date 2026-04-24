package com.noodlegamer76.fracture.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiNoiseBiomeSource.class)
public class MultiNoiseBiomeSourceMixin {

    @Inject(
            method = "getNoiseBiome(IIILnet/minecraft/world/level/biome/Climate$Sampler;)Lnet/minecraft/core/Holder;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void fracture$voidZone(int x, int y, int z,
                                   Climate.Sampler sampler,
                                   CallbackInfoReturnable<Holder<Biome>> cir) {

      //var data = VoidZoneCache.DATA;
      //Holder<Biome> biome = VoidZoneCache.BIOME;
      //if (data == null || biome == null) return;

      //if (data.isInside(x << 2, z << 2)) {
      //    cir.setReturnValue(biome);
      //}
    }
}