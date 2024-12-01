package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.spellcrafting.CastState;
import com.noodlegamer76.fracture.spellcrafting.WandCast;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DoubleCastSpell extends Spell {
    public DoubleCastSpell(ItemStack stack, LivingEntity caster) {
        super(stack, caster);
    }

    @Override
    public void cast() {

    }

    @Override
    public WandCast applyCastEffects(CastState state, WandCast cast) {
        cast.casts += 2;
        return super.applyCastEffects(state, cast);
    }

    @Override
    public Component getName() {
        return Component.literal("Double Cast");
    }

    @Override
    public float getManaCost() {
        return 0;
    }

    @Override
    public float getRechargeTime() {
        return 0;
    }

    @Override
    public float getCastDelay() {
        return 0;
    }
}
