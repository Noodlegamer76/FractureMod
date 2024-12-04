package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.spellcrafting.CastState;
import com.noodlegamer76.fracture.spellcrafting.WandCast;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RandomShotSpell extends Spell {
    public RandomShotSpell(ItemStack stack, Entity caster) {
        super(stack, caster);
    }

    @Override
    public WandCast applyCastEffects(CastState state, WandCast cast) {
        state.inaccuracyMultiplier += 1000;
        return super.applyCastEffects(state, cast);
    }

    @Override
    public int draws() {
        return 1;
    }

    @Override
    public Component getName() {
        return Component.literal("Random Shot");
    }

    @Override
    public float getManaCost() {
        return 3;
    }

    @Override
    public float getRechargeTime() {
        return -15;
    }

    @Override
    public float getCastDelay() {
        return -20;
    }

    @Override
    public Item getCastItem() {
        return InitItems.RANDOM_SHOT_SPELL_ITEM.get();
    }
}
