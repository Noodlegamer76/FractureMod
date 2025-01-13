package com.noodlegamer76.fracture.item;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class MagicSword extends SwordItem {
    public Spell spell;

    public MagicSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public void setSpell(ItemStack stack, Spell spell) {
        spell = spell;
        stack.setDamageValue((int) (stack.getMaxDamage() * spell.damageMultiplier));
    }
}
