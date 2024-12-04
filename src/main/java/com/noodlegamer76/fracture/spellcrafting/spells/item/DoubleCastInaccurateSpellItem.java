package com.noodlegamer76.fracture.spellcrafting.spells.item;


import com.noodlegamer76.fracture.spellcrafting.spells.spell.DoubleCastInaccurateSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DoubleCastInaccurateSpellItem extends SpellItem {
    public DoubleCastInaccurateSpellItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Spell getSpell(ItemStack stack, Entity entity) {
        return new DoubleCastInaccurateSpell(stack, entity);
    }
}
