package com.noodlegamer76.fracture.worldgen.megastructure.structure.access;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nullable;

public interface WorldAccess {
    int getHeight(int x, int z);

    BlockPos origin();

    RandomSource random();

    long getSeed();

    @Nullable
    MinecraftServer getServer();

    default int getMinBuildHeight() {
        return -64;
    }

    default int getMaxBuildHeight() {
        return 320;
    }

    @Nullable
    FeaturePlaceContext<NoneFeatureConfiguration> getFeatureContext();

    ResourceKey<Level> getDimension();
}
