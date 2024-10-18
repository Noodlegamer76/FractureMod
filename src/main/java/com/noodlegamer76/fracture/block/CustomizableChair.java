package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.entity.block.CustomizableChairEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CustomizableChair extends CustomizableFurniture implements EntityBlock {

    public CustomizableChair(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CustomizableChairEntity(pPos, pState);
    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        return true;
    }

    @Override
    public void spawnAfterBreak(BlockState pState, ServerLevel pLevel, BlockPos pPos, ItemStack pStack, boolean pDropExperience) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        ItemStack drop = new ItemStack((asItem().getDefaultInstance().getItemHolder()), 1);
        entity.saveToItem(drop);
        pLevel.addFreshEntity(drop.getEntityRepresentation());
        super.spawnAfterBreak(pState, pLevel, pPos, pStack, pDropExperience);
    }
}
