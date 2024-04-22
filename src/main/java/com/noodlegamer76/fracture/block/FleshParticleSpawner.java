package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.particles.InitParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FleshParticleSpawner extends Block {
    public FleshParticleSpawner(Properties pProperties) {
        super(pProperties);
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        double d0 = (double)i + random.nextDouble();
        double d1 = (double)j + 0.7D;
        double d2 = (double)k + random.nextDouble();
        level.addParticle(InitParticles.BLOOD_PARTICLES.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int l = 0; l < 14; ++l) {
            blockpos$mutableblockpos.set(i + Mth.nextInt(random, -10, 10), j - random.nextInt(10), k + Mth.nextInt(random, -10, 10));
            BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
            if (blockstate.isCollisionShapeFullBlock(level, blockpos$mutableblockpos) && !blockstate.isCollisionShapeFullBlock(level, blockpos$mutableblockpos.below())) {
                level.addParticle(InitParticles.BLOOD_PARTICLES.get(),
                        (double)blockpos$mutableblockpos.getX() + random.nextDouble(),
                        (double)blockpos$mutableblockpos.getY(),
                        (double)blockpos$mutableblockpos.getZ() + random.nextDouble(),
                        0.0D, 0.0D, 0.0D);
            }
        }
    }
}
