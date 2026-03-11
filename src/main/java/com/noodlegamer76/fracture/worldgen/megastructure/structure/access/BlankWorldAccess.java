package com.noodlegamer76.fracture.worldgen.megastructure.structure.access;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.Nullable;

public class BlankWorldAccess implements WorldAccess {
    private long seed;
    private BlockPos origin;
    private RandomSource random;

    public BlankWorldAccess(long seed, BlockPos origin) {
        this.seed = seed;
        this.origin = origin;
        this.random = RandomSource.create(seed);
    }

    @Override
    public int getHeight(int x, int z) {
        return 0;
    }

    @Override
    public BlockPos origin() {
        return origin;
    }

    @Override
    public RandomSource random() {
        return random;
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public @Nullable MinecraftServer getServer() {
        return null;
    }

    @Override
    public @Nullable FeaturePlaceContext<NoneFeatureConfiguration> getFeatureContext() {
        return null;
    }

    @Override
    public ResourceKey<Level> getDimension() {
        return Level.OVERWORLD;
    }

    public void setOrigin(BlockPos origin) {
        this.origin = origin;
    }

    public void setSeed(long seed) {
        this.seed = seed;
        this.random = RandomSource.create(seed);
    }
}
