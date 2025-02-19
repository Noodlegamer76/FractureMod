package com.noodlegamer76.fracture.spellcrafting.spells.item;


import com.noodlegamer76.fracture.spellcrafting.spells.spell.DoubleCastInaccurateSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class DoubleCastInaccurateSpellItem extends SpellItem {
    public DoubleCastInaccurateSpellItem(Properties properties, boolean register) {
        super(properties, register);
    }


    @Override
    public Spell getSpell(ItemStack stack, Entity entity) {
        return new DoubleCastInaccurateSpell(stack, entity);
    }

    @Override
    public Component getName() {
        return Component.literal("Double Cast, Inaccurate");
    }
}
