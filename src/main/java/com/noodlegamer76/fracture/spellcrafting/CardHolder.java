package com.noodlegamer76.fracture.spellcrafting;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;

import java.util.ArrayList;

public class CardHolder {
    ArrayList<Spell> spells = new ArrayList<>();

    public void addCard(Spell spell) {
        spells.add(spell);
    }

    public void removeCard(int slot) {
        spells.remove(slot);
    }

    public void clear() {
        spells.clear();
    }
}
