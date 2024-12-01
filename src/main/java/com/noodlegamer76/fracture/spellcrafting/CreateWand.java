package com.noodlegamer76.fracture.spellcrafting;

import net.minecraft.nbt.CompoundTag;

public class CreateWand {
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

    int casts = 1;
    //how many slots are available to put spells and modifiers into

    private void calculate(CompoundTag nbt) {

    }

    public CompoundTag createStats(CompoundTag nbt) {
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
