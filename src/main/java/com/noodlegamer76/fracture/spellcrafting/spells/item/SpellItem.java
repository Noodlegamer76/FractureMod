package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.SpellcraftingItem;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class SpellItem extends SpellcraftingItem {
    public SpellItem(Properties pProperties) {
        super(pProperties);
    }

    public abstract Spell getSpell(ItemStack stack, Entity entity);
}
