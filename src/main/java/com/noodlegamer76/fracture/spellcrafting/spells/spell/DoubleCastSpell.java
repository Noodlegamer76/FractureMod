package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.spellcrafting.CastState;
import com.noodlegamer76.fracture.spellcrafting.WandCast;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DoubleCastSpell extends Spell {
    public DoubleCastSpell(ItemStack stack, Entity caster) {
        super(stack, caster);
    }

    @Override
    public WandCast applyCastEffects(CastState state, WandCast cast) {
        return super.applyCastEffects(state, cast);
    }

    @Override
    public int draws() {
        return 2;
    }

    @Override
    public Item getCastItem() {
        return InitItems.DOUBLE_CAST_SPELL_ITEM.get();
    }

    @Override
    public Component getName() {
        return Component.literal("Double Cast");
    }

    @Override
    public float getManaCost() {
        return 5f;
    }

    @Override
    public float getRechargeTime() {
        return 3f;
    }

    @Override
    public float getCastDelay() {
        return 3f;
    }
}
