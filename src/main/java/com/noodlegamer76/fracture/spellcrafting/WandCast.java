package com.noodlegamer76.fracture.spellcrafting;

import com.noodlegamer76.fracture.spellcrafting.spells.item.SpellItem;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

/**
 * Represents a wand casting system that allows a living entity to cast spells using a wand.
 * The system organizes spells into a queue and handles the scheduling of spell casting based on
 * various wand configurations such as mana capacity, cast delay, and recharge time.
 */
public class WandCast {
    IItemHandler handler;
    CompoundTag nbt;
    CardHolderManager manager;
    Entity caster;
    ItemStack wand;

    public int casts = 1;
    //how many spells are cast every time the players activates the spell

    public float castDelay = 20;
    //measured in ticks, how long it takes to cast the next spell in the wand queue

    public float rechargeTime = 20;
    //measured in ticks, when the spell queue reaches the end, the wand much charge this long in seconds to refresh the spell queue

    public int maxMana = 100;
    //how much mana the wand holds when full

    public float manaRechargeSpeed = 1;
    //a flat number representing how much mana is regenerated per tick

    public int capacity = 27;
    //how many slots are available to put spells and modifiers into

    public boolean resetting = false;

    public int startSlot;

    CastState state;

    public WandCast(Level level, Entity caster, ItemStack castItem) {
        //inventory slots index starts at 0

        //make handler and nbt
        LazyOptional<IItemHandler> iItemHandler = castItem.getCapability(ForgeCapabilities.ITEM_HANDLER, null);
        iItemHandler.ifPresent(this::setHandler);
        this.caster = caster;
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
        castSpell(level, caster, castItem);

    }

    public void castSpell(Level level, Entity caster, ItemStack castItem) {

        if (wand.getTag().getFloat("currentCastDelay") > 0) {
            return;
        }

        while (casts > 0) {



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
                //System.out.println(spell.getName().getString());
                state.addSpell(spell, this);
            }
            else {
                Spell discardedSpell = manager.grabFromDiscard();
                if (discardedSpell != null) {
                    //System.out.println(discardedSpell.getName().getString() + " got, wrapping");
                    state.addSpell(discardedSpell, this);
                }
            }


            nbt.putInt("slot", slotSet);
            casts -= 1;

            //reset if there's no more spells
            if (manager.getDeck().spells.isEmpty()) {
                //System.out.println("resetting");
                nbt.putInt("slot", -1);
                resetting = true;
            }


            if (casts <= 0) {
                //cast the state if out of spell draws

                break;
            }

        }

        state.cast();
        if (resetting) {
            float rechargeTime = getRechargeTime();
            wand.getTag().putFloat("currentCastDelay", rechargeTime +
                    wand.getTag().getFloat("rechargeTime"));
            wand.getTag().putFloat("lastRechargeTime", rechargeTime +
                    wand.getTag().getFloat("rechargeTime"));
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

    public void setHandler(IItemHandler handler) {
        this.handler = handler;
    }
}
