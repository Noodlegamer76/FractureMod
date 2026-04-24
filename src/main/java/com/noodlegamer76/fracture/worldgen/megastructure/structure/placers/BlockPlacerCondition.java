package com.noodlegamer76.fracture.worldgen.megastructure.structure.placers;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

@FunctionalInterface
public interface BlockPlacerCondition {

    /**
     * This check runs for every block on the surface of the chunk.
     * @param pos The position being checked.
     * @param ctx The Feature context.
     * @param randomSource Random Number Generator.
     * @param instance Instance of a
     * @return Whether it should place the block on the surface or not
     */
    boolean shouldPlace(BlockPos pos, FeaturePlaceContext<NoneFeatureConfiguration> ctx, RandomSource randomSource, StructureInstance instance);
}