package com.noodlegamer76.fracture.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BloodyBookshelfBlock extends Block {
    public BloodyBookshelfBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader world, BlockPos pos) {
        return 2.0f; 
    }
}