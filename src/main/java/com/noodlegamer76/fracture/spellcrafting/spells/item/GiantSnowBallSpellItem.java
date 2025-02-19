package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.GiantSnowballSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class GiantSnowBallSpellItem extends SpellItem {
    public GiantSnowBallSpellItem(Properties properties, boolean register) {
        super(properties, register);
    }


    @Override
    public Spell getSpell(ItemStack wand, Entity caster) {
        return new GiantSnowballSpell(wand, caster);
    }

    @Override
    public Component getName() {
        return Component.literal("Giant Snowball");
    }
}
