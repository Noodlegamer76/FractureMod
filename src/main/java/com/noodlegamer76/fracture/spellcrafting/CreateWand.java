package com.noodlegamer76.fracture.spellcrafting;

import com.noodlegamer76.fracture.spellcrafting.wand.CreativeWand;
import com.noodlegamer76.fracture.spellcrafting.wand.FrozenSpellBook;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class CreateWand {
    float castDelay = 0;
    //measured in ticks, how long it takes to case the next spell in the wand queue

    float rechargeTime = 0;
    //measured in ticks, when the spell queue reaches the end, the wand much charge this long in seconds to refresh the spell queue

    int maxMana = 1000;
    //how much mana the wand holds when full

    float manaRechargeSpeed = 10;
    //a flat number representing how much mana is regenerated per tick

    int capacity = 99;
    //how many slots are available to put spells and modifiers into

    int casts = 1;
    //how many slots are available to put spells and modifiers into


    private ItemStack itemStack;

    private void calculate(CompoundTag nbt) {
        if (itemStack.getItem() instanceof FrozenSpellBook) {
            createFrozenSpellbook();
        }
        else if (itemStack.getItem() instanceof CreativeWand) {
            createCreativeWand();
        }
    }

    private void createFrozenSpellbook() {
        castDelay = 10;
        rechargeTime = 30;
        maxMana = new Random().nextInt(50, 151);
        manaRechargeSpeed = (float) (new Random().nextFloat(0.5f, 1.25f));
        capacity = new Random().nextInt(2, 6);
        //usually one, 25% chance to be 2
        casts = (int) (Math.random() + 1.25);
    }

    private void createCreativeWand() {
        castDelay = -1000;
        rechargeTime = -1000;
        maxMana = 100000;
        manaRechargeSpeed = 100000;
        capacity = 120;
        casts = 1;
    }

    public CompoundTag createStats(CompoundTag nbt, ItemStack itemStack) {
        this.itemStack = itemStack;
        calculate(nbt);

        nbt.putInt("slot", 0);
        nbt.putBoolean("isCreated", true);
        nbt.putFloat("castDelay", castDelay);
        nbt.putFloat("rechargeTime", rechargeTime);
        nbt.putInt("maxMana", maxMana);
        nbt.putFloat("manaRechargeSpeed", manaRechargeSpeed);
        nbt.putFloat("capacity", capacity);
        nbt.putInt("casts", casts);

        nbt.putFloat("currentMana", maxMana);
        nbt.putFloat("currentCastDelay", 0);
        nbt.putFloat("lastRechargeTime", 0);

        return nbt;
    }
}
