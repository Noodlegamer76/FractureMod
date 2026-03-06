package com.noodlegamer76.fracture.worldgen.megastructure.structure.access;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.StructureUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.Nullable;

public class FeatureWorldAccess implements WorldAccess {
    private final FeaturePlaceContext<NoneFeatureConfiguration> ctx;

    public FeatureWorldAccess(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getHeight(int x, int z) {
        return StructureUtils.getSurfaceHeight(ctx.level().getLevel(), x, z);
    }

    @Override
    public BlockPos origin() {
        return ctx.origin();
    }

    @Override
    public RandomSource random() {
        return ctx.random();
    }

    @Override
    public long getSeed() {
        return ctx.level().getSeed();
    }

    @Override
    public MinecraftServer getServer() {
        return ctx.level().getServer();
    }

    @Override
    public @Nullable FeaturePlaceContext<NoneFeatureConfiguration> getFeatureContext() {
        return ctx;
    }
}
