package com.noodlegamer76.fracture.item;

import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.entity.block.FleshyGroupSpawnerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

public class TestItem extends Item {
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        if (!level.isClientSide) {
            System.out.println("SERVER SIDE");
            BlockPos pos = player.blockPosition();

            level.setBlock(pos, InitBlocks.FLESHY_GROUP_SPAWNER.get().defaultBlockState(), 2);
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof FleshyGroupSpawnerEntity) {
                System.out.println("IS INSTANCE");
                FleshyGroupSpawnerEntity fleshyGroupSpawnerEntity = (FleshyGroupSpawnerEntity) blockentity;
                fleshyGroupSpawnerEntity.setEntityIds(EntityType.HORSE, level.getRandom(), 0);
                fleshyGroupSpawnerEntity.setEntityIds(EntityType.CHICKEN, level.getRandom(), 1);
                fleshyGroupSpawnerEntity.setEntityIds(EntityType.COW, level.getRandom(), 2);
                fleshyGroupSpawnerEntity.setEntityIds(EntityType.RABBIT, level.getRandom(), 3);
            }
        }
        return super.use(level, player, usedHand);
    }
}
