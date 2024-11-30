package com.noodlegamer76.fracture.spellcrafting;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.world.item.ItemStack;

public class CastState {
    float currentMana;
    float maxMana;
    private CardHolder stateSpells = new CardHolder();
    ItemStack wand;

    public CastState(ItemStack wand) {
        this.wand = wand;
        currentMana = wand.getTag().getFloat("currentMana");
        maxMana = wand.getTag().getFloat("maxMana");
    }

    public void cast() {
        for (Spell spell: stateSpells.spells) {
            if (currentMana >= spell.getManaCost()) {
                spell.cast();
                currentMana -= spell.getManaCost();
            }
            else {
                System.out.println("no mana");
            }
        }
        wand.getTag().putFloat("currentMana", currentMana);

    }

    public void addSpell(Spell spell) {
        stateSpells.addCard(spell);
    }
}
