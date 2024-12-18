package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ExplosionSpell extends Spell {
    public ExplosionSpell(ItemStack stack, Entity caster) {
        super(stack, caster);
    }

    @Override
    public void cast() {
       level.explode(null, caster.getX(), caster.getY(), caster.getZ(), 2.0f + (damageMultiplier - 1), Level.ExplosionInteraction.TNT);
    }

    @Override
    public Component getName() {
        return Component.literal("explosion");
    }

    @Override
    public float getManaCost() {
        return 20;
    }

    @Override
    public float getRechargeTime() {
        return 30;
    }

    @Override
    public float getCastDelay() {
        return 15;
    }

    @Override
    public Item getCastItem() {
        return InitItems.EXPLOSION_SPELL_ITEM.get();
    }
}
