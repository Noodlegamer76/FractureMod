package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.GiantSnowballWithTriggerSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class GiantSnowballWithTriggerSpellItem extends SpellItem {
    public GiantSnowballWithTriggerSpellItem(Properties properties, boolean register) {
        super(properties, register);
    }

    @Override
    public Spell getSpell(ItemStack stack, Entity entity) {
        return new GiantSnowballWithTriggerSpell(stack, entity);
    }

    @Override
    public Component getName() {
        return Component.literal("Giant Snowball with Trigger");
    }
}
