package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.SummonMagicSword;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class SummonMagicSwordSpellItem extends SpellItem {
    public SummonMagicSwordSpellItem(Properties properties, boolean register) {
        super(properties, register);
    }


    @Override
    public Spell getSpell(ItemStack stack, Entity entity) {
        return new SummonMagicSword(stack, entity);
    }

    @Override
    public Component getName() {
        return Component.literal("Summon Magic Sword");
    }
}
