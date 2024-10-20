package com.noodlegamer76.fracture.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BloodSlimeBlock extends HalfTransparentBlock {
    public BloodSlimeBlock(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public boolean isStickyBlock(BlockState state) {
        return state.is(InitBlocks.BLOOD_SLIME_BLOCK.get());
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        if (state.getBlock() == InitBlocks.BLOOD_SLIME_BLOCK.get()) return true;
        if (other.isSlimeBlock()) return false;
        return state.isStickyBlock() || other.isStickyBlock();
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        pEntity.makeStuckInBlock(pState, new Vec3(0.35D, 0.15D, 0.35D));
    }
}
