package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.RandomShotSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class RandomShotSpellItem extends SpellItem {
    public RandomShotSpellItem(Properties properties, boolean register) {
        super(properties, register);
    }


    @Override
    public Spell getSpell(ItemStack stack, Entity entity) {
        return new RandomShotSpell(stack, entity);
    }

    @Override
    public Component getName() {
        return Component.literal("Random Shot");
    }
}
