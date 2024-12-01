package com.noodlegamer76.fracture.spellcrafting;

import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.world.item.ItemStack;

import javax.swing.*;

public class CardHolderManager {
    private CardHolder deck;
    private CardHolder hand;
    private CardHolder discard;

    public CardHolderManager() {
        deck = new CardHolder();
        hand = new CardHolder();
        discard = new CardHolder();
    }

    public CardHolder getDeck() {
        return deck;
    }

    public CardHolder getDiscard() {
        return discard;
    }

    public CardHolder getHand() {
        return hand;
    }

    public void clearAll() {
        hand.clear();
        deck.clear();
        discard.clear();
    }

    public Spell grabFromDeck(int slot) {
        if (deck.spells.size() < slot + 1) {
            return null;
        }
        Spell spell = deck.spells.get(slot);
        deck.spells.remove(slot);
        hand.spells.add(spell);
        return spell;
    }

    public Spell grabFromDiscard(int slot) {
        if (discard.spells.size() < slot + 1) {
            return null;
        }
        Spell spell = discard.spells.get(slot);
        discard.spells.remove(slot);
        hand.spells.add(spell);
        return spell;
    }

    public Spell grabFromDeck() {
        return grabFromDeck(0);
    }

    public Spell grabFromDiscard() {
        return grabFromDiscard(0);
    }

    public Spell discardHandSlot(int slot) {
        if (hand.spells.size() < slot) {
            return null;
        }
        Spell spell = hand.spells.get(slot);
        hand.spells.remove(slot);
        discard.spells.add(spell);
        return spell;
    }

    public void discardHand() {
        for (int i = 0; i < hand.spells.size(); i++) {
            discard.addCard(hand.spells.get(i));
        }
        hand.clear();
    }

}
