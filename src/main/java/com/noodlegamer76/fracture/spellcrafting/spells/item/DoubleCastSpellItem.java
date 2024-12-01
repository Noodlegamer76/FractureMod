package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.DoubleCastSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DoubleCastSpellItem extends SpellItem {
    public DoubleCastSpellItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Spell getSpell(ItemStack stack, LivingEntity entity) {
        return new DoubleCastSpell(stack, entity);
    }
}
