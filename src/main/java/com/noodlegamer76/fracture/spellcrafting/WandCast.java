package com.noodlegamer76.fracture.spellcrafting;

import com.noodlegamer76.fracture.spellcrafting.spells.SpellTypes;
import com.noodlegamer76.fracture.spellcrafting.spells.item.SpellItem;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;

public class WandCast {
    IItemHandler handler;
    CompoundTag nbt;
    CardHolderManager manager;
    ServerPlayer caster;
    ItemStack wand;

    int casts = 1;
    //how many spells are cast every time the players activates the spell

    float castDelay = 20;
    //measured in ticks, how long it takes to cast the next spell in the wand queue

    float rechargeTime = 20;
    //measured in ticks, when the spell queue reaches the end, the wand much charge this long in seconds to refresh the spell queue

    int maxMana = 100;
    //how much mana the wand holds when full

    float manaRechargeSpeed = 1;
    //a flat number representing how much mana is regenerated per tick

    int capacity = 27;
    //how many slots are available to put spells and modifiers into

    boolean shouldCast = true;

    int startSlot;

    CastState state;
    boolean resetting = false;

    //implementation notes:
    //capacity, added
    //casts, added
    //mana recharge speed, added
    //max mana, added

    //4 more to go

    public WandCast(Level level, ServerPlayer player, ItemStack castItem) {
        //inventory slots index starts at 0

        //make handler and nbt
        LazyOptional<IItemHandler> iItemHandler = castItem.getCapability(ForgeCapabilities.ITEM_HANDLER, null);
        iItemHandler.ifPresent(this::setHandler);
        this.caster = player;
        nbt = castItem.getTag();
        manager = new CardHolderManager();
        state = new CastState(castItem);
        wand = castItem;

        capacity = nbt.getInt("capacity");
        castDelay = nbt.getInt("castDelay");
        casts = nbt.getInt("casts");
        rechargeTime = nbt.getInt("rechargeTime");
        maxMana = nbt.getInt("maxMana");
        manaRechargeSpeed = nbt.getInt("manaRechargeSpeed");
        startSlot = nbt.getInt("slot");

        reconstructCardPositions();
        castSpell(level, player, castItem);

    }

    public void castSpell(Level level, ServerPlayer caster, ItemStack castItem) {


        while (shouldCast) {

            if (wand.getTag().getFloat("currentCastDelay") > 0) {
                return;
            }

            int slot = nbt.getInt("slot");

            //set the slot to the next spell
            int slotSet = slot;
            for (int i = slot + 1; i < capacity; i++) {
                ItemStack stack = handler.extractItem(i, 1, true);
                if (stack.getItem() instanceof SpellItem) {
                    slotSet = i;
                    break;
                }
            }

            //get the next spell from the deck
            Spell spell = manager.grabFromDeck();
            if (spell != null) {
                System.out.println(spell.getName().getString());
                state.addSpell(spell);
            }


            nbt.putInt("slot", slotSet);
            casts -= 1;

            //reset if there's no more spells
            if (manager.getDeck().spells.isEmpty()) {
                System.out.println("resetting");
                nbt.putInt("slot", -1);
                resetting = true;
                shouldCast = false;
            }


            if (casts <= 0) {
                //cast the state if out of spell draws

                break;
            }

        }

        state.cast();
        if (resetting) {
            float rechargeTime = getRechargeTime();
            wand.getTag().putFloat("currentCastDelay", rechargeTime);
            wand.getTag().putFloat("lastRechargeTime", rechargeTime);
        }

    }

    private float getRechargeTime() {
        float rechargeTime = 0;
        for (Spell spell: manager.getDiscard().spells) {
            rechargeTime += spell.getRechargeTime();
        }
        for (Spell spell: manager.getHand().spells) {
            rechargeTime += spell.getRechargeTime();
        }
        for (Spell spell: manager.getDeck().spells) {
            rechargeTime += spell.getRechargeTime();
        }
        return rechargeTime;
    }

    private void reconstructCardPositions() {
        manager.clearAll();
        for (int i = 0; i < capacity; i++) {
            Item item = handler.extractItem(i, 1, true).getItem();
            if (item instanceof SpellItem spellItem) {
                Spell spell = spellItem.getSpell(wand, caster);

                if (i <= startSlot && spell != null) {
                    manager.getDiscard().addCard(spell);
                }
                if (i > startSlot && spell != null) {
                    manager.getDeck().addCard(spell);
                }
            }

        }
    }

    //check if there are any spells remaining, including wrapping.
    //returns -1 if there isn't, else returns the closest slot that has it
    //this doesn't pass the initial slot the wand was on when the spell was cast
    private int hasSpellsLeft(int slot) {
        for (int i = slot; i < capacity; i++) {
            if (handler.getStackInSlot(i).getItem() instanceof SpellItem) {
                System.out.println("spell remaining, before wrapping");
                return i;
            }
        }
        for (int i = 0; i < startSlot; i++) {
            if (handler.getStackInSlot(i).getItem() instanceof SpellItem) {
                System.out.println("spell remaining, wrapping");
                return i;
            }
        }
        System.out.println("no spells remaining, resetting");
        return -1;
    }

    //updates or resets which slot the wand should start on
    //includes wrapping
    private void setSlot(int slot) {
        //either resets wand queue or goes to the next slot
        if (slot + 1 >= capacity) {
            nbt.putInt("slot", 0);
            shouldCast = false;
            shouldCast = hasSpellsLeft(slot) < startSlot && hasSpellsLeft(slot) != -1;

            System.out.println("RESET");
        }
        else {
            nbt.putInt("slot", slot + 1);
            System.out.println("ADD 1");
        }

    }

    public void setHandler(IItemHandler handler) {
        this.handler = handler;
    }
}
