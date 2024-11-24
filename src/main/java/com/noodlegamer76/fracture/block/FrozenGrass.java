package com.noodlegamer76.fracture.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FrozenGrass extends SnowPlant {
    public FrozenGrass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity.getType() == EntityType.ITEM) {
            return;
        }
        pLevel.destroyBlock(pPos, true, pEntity);
    }
}
