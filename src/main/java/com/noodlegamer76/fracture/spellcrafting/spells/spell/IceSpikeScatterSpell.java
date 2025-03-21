package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class IceSpikeScatterSpell extends Spell {
    public IceSpikeScatterSpell(ItemStack stack, Entity caster) {
        super(stack, caster);
    }

    @Override
    public void cast() {

    }

    @Override
    public Component getName() {
        return Component.literal("Ice Shatter");
    }

    @Override
    public float getManaCost() {
        return 50;
    }

    @Override
    public float getRechargeTime() {
        return 25;
    }

    @Override
    public float getCastDelay() {
        return 20;
    }

    @Override
    public Item getCastItem() {
        return InitItems.ICE_SPIKE_SCATTER_SPELL_ITEM.get();
    }
}
