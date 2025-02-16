package com.noodlegamer76.fracture.spellcrafting.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class PlayerSpellData {

    final Map<Component, Integer> spells = new HashMap<>();

    public void unlockSpell(Component spell, int amount) {
        spells.put(spell, spells.getOrDefault(spell, 0) + amount);
    }

    public int getSpellAmount(Component spell) {
        return spells.getOrDefault(spell, 0);
    }

    public Map<Component, Integer> getSpells() {
        return spells;
    }

    public boolean hasSpell(Component spell) {
        return spells.containsKey(spell);
    }

    public void resetSpell(Component spell) {
        spells.put(spell, 0);
    }

    public void saveNBTData(CompoundTag tag) {
        ListTag spellList = new ListTag();
        for (Map.Entry<Component, Integer> entry : spells.entrySet()) {
            CompoundTag spellTag = new CompoundTag();
            spellTag.putString("Spell", entry.getKey().getString());
            spellTag.putInt("Amount", entry.getValue());
            spellList.add(spellTag);
        }
        tag.put("Spells", spellList);
    }

    public void loadNBTData(CompoundTag tag) {
        spells.clear();
        ListTag spellList = tag.getList("Spells", Tag.TAG_COMPOUND);
        for (int i = 0; i < spellList.size(); i++) {
            CompoundTag spellTag = spellList.getCompound(i);
            Component spell = Component.literal(spellTag.getString("Spell"));
            int amount = spellTag.getInt("Amount");
            spells.put(spell, amount);
        }
    }
}
