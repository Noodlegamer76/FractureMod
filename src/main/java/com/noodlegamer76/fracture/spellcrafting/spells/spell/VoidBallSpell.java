package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.projectile.VoidBall;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class VoidBallSpell extends Spell {
    public VoidBallSpell(ItemStack stack, Player player) {
        super(stack, player);
    }

    @Override
    public void cast() {
        VoidBall ball = new VoidBall(InitEntities.VOID_BALL.get(), player, level);
        ball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.0f, 0.0f);
        level.addFreshEntity(ball);
    }

    @Override
    public Component getName() {
        return Component.literal("Void Ball");
    }
}
