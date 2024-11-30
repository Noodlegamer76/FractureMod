package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.VoidBallSpell;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class VoidBallSpellItem extends SpellItem {
    public VoidBallSpellItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Spell getSpell(ItemStack stack, LivingEntity caster) {
        return new VoidBallSpell(stack, caster);
    }
}
