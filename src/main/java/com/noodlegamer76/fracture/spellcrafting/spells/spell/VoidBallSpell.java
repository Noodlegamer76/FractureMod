package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.projectile.VoidBall;
import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.spellcrafting.CastState;
import com.noodlegamer76.fracture.spellcrafting.WandCast;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class VoidBallSpell extends Spell {
    public VoidBallSpell(ItemStack wand, LivingEntity caster) {
        super(wand, caster);
    }

    @Override
    public void cast() {
        VoidBall ball = new VoidBall(InitEntities.VOID_BALL.get(), caster, level);
        ball.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0, 2.0f, 10.0f);
        level.addFreshEntity(ball);
    }

    @Override
    public Component getName() {
        return Component.literal("Void Ball");
    }

    @Override
    public float getManaCost() {
        return 10;
    }

    @Override
    public float getRechargeTime() {
        return 20;
    }

    @Override
    public float getCastDelay() {
        return 20;
    }

    @Override
    public Item getCastItem() {
        return InitItems.VOID_BALL_SPELL_ITEM.get();
    }


}
