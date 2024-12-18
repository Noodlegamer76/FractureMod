package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.ExplosionSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class ExplosionSpellItem extends SpellItem {
    public ExplosionSpellItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Spell getSpell(ItemStack stack, Entity entity) {
        return new ExplosionSpell(stack, entity);
    }
}
