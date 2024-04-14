package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.entity.block.FleshyGroupSpawnerEntity;
import com.noodlegamer76.fracture.entity.block.InitBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FleshyGroupSpawner extends BaseEntityBlock {
    public FleshyGroupSpawner(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FleshyGroupSpawnerEntity(pPos, pState);
    }

    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, InitBlockEntities.FLESHY_GROUP_SPAWNER.get(), FleshyGroupSpawnerEntity::tick);
    }
}