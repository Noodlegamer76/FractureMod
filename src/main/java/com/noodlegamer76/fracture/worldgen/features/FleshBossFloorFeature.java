package com.noodlegamer76.fracture.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FleshBossFloorFeature extends Feature<NoneFeatureConfiguration> {
    public FleshBossFloorFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        BlockPos.MutableBlockPos pos = pContext.origin().mutable();
        BlockPos origin = pContext.origin();
        BlockState state = Blocks.BARRIER.defaultBlockState();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 4; y++) {
                    pos.set(origin.getX() + x, 64 - y, origin.getZ() + z);
                    pContext.level().setBlock(pos, state, 2);
                }
            }
        }

        return true;
    }
}
