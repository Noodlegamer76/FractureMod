package com.noodlegamer76.fracture.spellcrafting.spells;

import com.noodlegamer76.fracture.spellcrafting.modifiers.modifier.Modifier;
import com.noodlegamer76.fracture.spellcrafting.spells.item.SpellItem;
import com.noodlegamer76.fracture.spellcrafting.spells.item.VoidBallSpellItem;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.VoidBallSpell;

import java.util.ArrayList;

public class SpellTypes {

    public static Spell getSpellTypes(SpellItem item, ArrayList<Modifier> list) {

        if (item instanceof VoidBallSpellItem) {
            return new VoidBallSpell(item, list);
        }
        //didnt find spell from item
        return null;
    }
}
