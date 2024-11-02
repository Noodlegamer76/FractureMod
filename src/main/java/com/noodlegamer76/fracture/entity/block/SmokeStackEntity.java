package com.noodlegamer76.fracture.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SmokeStackEntity extends BlockEntity {
    public SmokeStackEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.SMOKE_STACK.get(), pPos, pBlockState);
    }

    public static void particleTick(Level pLevel, BlockPos pPos, BlockState pState, SmokeStackEntity pBlockEntity) {
        RandomSource random = pLevel.random;
        for (int i = 0; i < 5; i++) {
            pLevel.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pPos.getX() + 0.5, pPos.getY() + 1.0, pPos.getZ() + 0.5,
                    0.5 * ((random.nextDouble() - 0.5) / 5), 4 * ((random.nextDouble() - 0.5) / 5), 0.5 * ((random.nextDouble() - 0.5) / 5));

        }

    }
}
