package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.VoidBallSpell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class VoidBallSpellItem extends SpellItem {
    public VoidBallSpellItem(Properties properties, boolean register) {
        super(properties, register);
    }


    @Override
    public Spell getSpell(ItemStack stack, Entity caster) {
        return new VoidBallSpell(stack, caster);
    }

    @Override
    public Component getName() {
        return Component.literal("Void Ball");
    }
}
