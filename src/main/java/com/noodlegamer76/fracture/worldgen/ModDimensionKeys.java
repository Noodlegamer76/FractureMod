package com.noodlegamer76.fracture.worldgen;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;


public class ModDimensionKeys {
    public static final ResourceKey<Level> BOREAS = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "boreas_dimension"));
    public static final ResourceKey<Level> FLESH = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "flesh_dimension"));
}
