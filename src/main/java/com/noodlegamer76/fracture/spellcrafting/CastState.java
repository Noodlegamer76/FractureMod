package com.noodlegamer76.fracture.spellcrafting;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;

public class CastState {
    private CardHolder stateSpells = new CardHolder();

    public void cast() {
        for (Spell spell: stateSpells.spells) {
            spell.cast();
        }
    }

    public void addSpell(Spell spell) {
        stateSpells.addCard(spell);
    }
}
