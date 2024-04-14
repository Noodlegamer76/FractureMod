package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.datagen.ModBlockTagGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RootsBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FleshRootsBlock extends RootsBlock {
    public FleshRootsBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.isFaceSturdy(pLevel, pPos, Direction.UP);
    }
}
