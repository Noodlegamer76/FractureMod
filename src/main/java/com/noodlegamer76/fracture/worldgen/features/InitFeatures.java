package com.noodlegamer76.fracture.worldgen.features;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, FractureMod.MODID);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> GiantCrystal = FEATURES.register(
            "giant_crystal",
            () -> new GiantCrystal(NoneFeatureConfiguration.CODEC)
    );
}
