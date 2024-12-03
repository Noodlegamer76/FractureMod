package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.spellcrafting.CastState;
import com.noodlegamer76.fracture.spellcrafting.WandCast;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TripleCastSpell extends Spell {
    public TripleCastSpell(ItemStack stack, LivingEntity caster) {
        super(stack, caster);
    }

    @Override
    public void cast() {

    }

    @Override
    public WandCast applyCastEffects(CastState state, WandCast cast) {
        cast.casts += 3;
        return super.applyCastEffects(state, cast);
    }

    @Override
    public Component getName() {
        return Component.literal("Triple Cast");
    }

    @Override
    public float getManaCost() {
        return 7.5f;
    }

    @Override
    public float getRechargeTime() {
        return 5f;
    }

    @Override
    public float getCastDelay() {
        return 5f;
    }

    @Override
    public Item getCastItem() {
        return InitItems.TRIPLE_CAST_SPELL_ITEM.get();
    }
}
