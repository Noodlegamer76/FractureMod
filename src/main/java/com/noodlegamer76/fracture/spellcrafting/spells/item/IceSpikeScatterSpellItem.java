package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.IceSpikeScatterSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class IceSpikeScatterSpellItem extends SpellItem {
    public IceSpikeScatterSpellItem(Properties properties, boolean register) {
        super(properties, register);
    }


    @Override
    public Spell getSpell(ItemStack stack, Entity entity) {
        return new IceSpikeScatterSpell(stack, entity);
    }

    @Override
    public Component getName() {
        return Component.literal("Ice Shatter");
    }
}
