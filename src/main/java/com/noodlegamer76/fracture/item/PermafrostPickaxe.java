package com.noodlegamer76.fracture.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PermafrostPickaxe extends PickaxeItem {
    public PermafrostPickaxe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace())).is(Blocks.AIR)) {
            context.getLevel().setBlock(context.getClickedPos().relative(context.getClickedFace()), Blocks.BLUE_ICE.defaultBlockState(), 2);
            context.getItemInHand().hurtAndBreak(2, context.getPlayer(), (player) -> {});
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
