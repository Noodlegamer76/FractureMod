package com.noodlegamer76.fracture.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BloodSlimeBlock extends Block {
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
}
