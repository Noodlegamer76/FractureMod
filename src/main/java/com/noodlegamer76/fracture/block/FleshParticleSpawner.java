package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.entity.monster.*;
import com.noodlegamer76.fracture.particles.InitParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FleshParticleSpawner extends Block {
    public FleshParticleSpawner(Properties pProperties) {
        super(pProperties);
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (!level.getBlockState(pos.below()).isSolid()) {
            for(int i = 0; i < 10; ++i) {
                double d0 = (Math.random() * 2 - 1);
                double d1 = -1;
                double d2 = (Math.random() * 2 - 1);
                Vec3 direction = new Vec3(d0, d1, d2).normalize();
                level.addParticle(InitParticles.BLOOD_PARTICLES.get(),
                        0.5 + pos.getX() + Math.random() - 0.5, pos.getY() + Math.random() - 0.5, 0.5 + pos.getZ() + Math.random() - 0.5,
                        direction.x * (Math.random() * 2), direction.y * (Math.random() * 2), direction.z * (Math.random() * 2));
            }
        }
        if (!level.getBlockState(pos.above()).isSolid()) {
            for(int i = 0; i < 10; ++i) {
                double d0 = (Math.random() * 2 - 1);
                double d1 = 1;
                double d2 = (Math.random() * 2 - 1);
                Vec3 direction = new Vec3(d0, d1, d2).normalize();
                level.addParticle(InitParticles.BLOOD_PARTICLES.get(),
                        0.5 + pos.getX() + Math.random() - 0.5, pos.getY() + Math.random() + 0.5, 0.5 + pos.getZ() + Math.random() - 0.5,
                        direction.x * (Math.random() * 2), direction.y * (Math.random() * 6), direction.z * (Math.random() * 2));
            }
        }
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (pEntity instanceof BloodSlimeEntity ||
                pEntity instanceof FleshSlimeEntity ||
                pEntity instanceof AnkleBiterEntity ||
                pEntity instanceof FleshWalkerEntity ||
                (pEntity instanceof SkullChomper)) {
            return;
        }
        pEntity.hurt(pLevel.damageSources().generic(), 1);
    }
}
