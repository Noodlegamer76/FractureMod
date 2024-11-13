package com.noodlegamer76.fracture.spellcrafting;

import com.noodlegamer76.fracture.spellcrafting.modifiers.ModifierTypes;
import com.noodlegamer76.fracture.spellcrafting.modifiers.item.ModifierItem;
import com.noodlegamer76.fracture.spellcrafting.modifiers.modifier.Modifier;
import com.noodlegamer76.fracture.spellcrafting.spells.item.SpellItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;

public class WandCast {
    IItemHandler handler;
    CompoundTag nbt;

    int casts = 1;
    //how many spells are cast every time the players activates the spell

    float castDelay = 20;
    //measured in ticks, how long it takes to case the next spell in the wand queue

    float rechargeTime = 20;
    //measured in ticks, when the spell queue reaches the end, the wand much charge this long in seconds to refresh the spell queue

    int maxMana = 100;
    //how much mana the wand holds when full

    float manaRechargeSpeed = 1;
    //a flat number representing how much mana is regenerated per tick

    int capacity = 27;
    //how many slots are available to put spells and modifiers into

    boolean shouldCast = true;

    ArrayList<Modifier> modifiers = new ArrayList<>();


    public WandCast(Level level, ServerPlayer  player, ItemStack castItem) {
        //inventory slots index starts at 0

        //make handler and nbt
        LazyOptional<IItemHandler> iItemHandler = castItem.getCapability(ForgeCapabilities.ITEM_HANDLER, null);
        iItemHandler.ifPresent(this::setHandler);
        nbt = castItem.getTag();

        capacity = nbt.getInt("capacity");
        castDelay = nbt.getInt("castDelay");
        casts = nbt.getInt("casts");
        rechargeTime = nbt.getInt("rechargeTime");
        maxMana = nbt.getInt("maxMana");
        manaRechargeSpeed = nbt.getInt("manaRechargeSpeed");

        castSpell(level, player, castItem);

    }

    public void castSpell(Level level, ServerPlayer  player, ItemStack castItem) {
        while (shouldCast) {
            int slot = nbt.getInt("slot");
            ItemStack current = handler.getStackInSlot(slot);

            if (current.getItem() instanceof ModifierItem modifier) {
                //test which modifier is for the modifier item and add it to the list
                modifiers.add(ModifierTypes.getModifierTypes(modifier));
            }

            if (current.getItem() instanceof SpellItem spell) {
                //add spell cast class with that takes in modifiers
                casts -= 1;
            }
            if (casts == 0) {
                shouldCast = false;
                System.out.println("NO CASTS REMAINING");
            }
            setSlot(slot);
        }

        //increase slot count as its stuck on 1
    }

    //updates or resets which slot the wand should start on
    private void setSlot(int slot) {
        //either resets wand queue or goes to the next slot
        if (slot + 1 >= capacity) {
            nbt.putInt("slot", 0);
            shouldCast = false;
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
