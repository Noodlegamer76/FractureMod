package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.spellcrafting.modifiers.modifier.Modifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class Spell {
    ItemStack stack;
    ArrayList<Modifier>  modifiers;

    Spell(ItemStack stack, ArrayList<Modifier> modifiers) {
        this.stack = stack;
        this.modifiers = modifiers;
    }

    public void cast() {

    }
}
