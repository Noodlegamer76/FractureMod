package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.entity.block.FrostedIceCrystalsEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FrostedIceCrystals extends Block implements EntityBlock {

    public FrostedIceCrystals(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FrostedIceCrystalsEntity(pPos, pState);
    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        return true;
    }
}
