package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.TripleCastSpell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class TripleCastSpellItem extends SpellItem {
    public TripleCastSpellItem(Properties properties, boolean register) {
        super(properties, register);
    }


    @Override
    public Spell getSpell(ItemStack stack, Entity entity) {
        return new TripleCastSpell(stack, entity);
    }

    @Override
    public Component getName() {
        return Component.literal("Triple Cast");
    }
}
