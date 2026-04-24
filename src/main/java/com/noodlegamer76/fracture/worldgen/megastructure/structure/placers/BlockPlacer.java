package com.noodlegamer76.fracture.worldgen.megastructure.structure.placers;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.AABB;

public class BlockPlacer extends Placer {
    private final BlockState blockState;
    private final BlockPlacerCondition condition;

    public BlockPlacer(AABB boundingBox, BlockState state, BlockPlacerCondition condition) {
        super(boundingBox);
        this.blockState = state;
        this.condition = condition;
    }

    @Override
    public void place(FeaturePlaceContext<NoneFeatureConfiguration> ctx, RandomSource random, StructureInstance instance) {
        int chunkMinX = ctx.origin().getX();
        int chunkMaxX = chunkMinX + 16;
        int chunkMinZ = ctx.origin().getZ();
        int chunkMaxZ = chunkMinZ + 16;

        int minX = Math.max((int) getBoundingBox().minX, chunkMinX);
        int maxX = Math.min((int) getBoundingBox().maxX, chunkMaxX);
        int minZ = Math.max((int) getBoundingBox().minZ, chunkMinZ);
        int maxZ = Math.min((int) getBoundingBox().maxZ, chunkMaxZ);

        int minY = ctx.level().getMinBuildHeight();
        int maxY = ctx.level().getMaxBuildHeight();

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    pos.set(x, y, z);
                    if (condition.shouldPlace(pos, ctx, random, instance)) {
                        ctx.level().setBlock(pos, blockState, 2);
                    }
                }
            }
        }
    }

    public BlockPlacerCondition getCondition() {
        return condition;
    }

    @Override
    public AABB getBoundingBox() {
        return super.getBoundingBox();
    }

    public BlockState getBlockState() {
        return blockState;
    }
}
