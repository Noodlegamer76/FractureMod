package com.noodlegamer76.fracture.spellcrafting.data;

import com.noodlegamer76.fracture.spellcrafting.spells.item.SpellItem;
import com.noodlegamer76.fracture.util.InitCapabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpellHelper {
    //spell amounts for packet on client
    public static ArrayList<Integer> amounts;

    public static Map<Component, Integer> getPlayerSpells(Player player) {
        return player.getCapability(InitCapabilities.PLAYER_SPELL_DATA)
                .map(spellData -> new HashMap<>(spellData.getSpells()))
                .orElseGet(HashMap::new);
    }

    public static PlayerSpellData getSpellData(Player player) {
        LazyOptional<PlayerSpellData> optional = player.getCapability(InitCapabilities.PLAYER_SPELL_DATA);

        if (!optional.isPresent()) {
            return player.getCapability(InitCapabilities.PLAYER_SPELL_DATA)
                    .orElseThrow(() -> new RuntimeException("Failed to get or create PlayerSpellData"));
        }

        return optional.orElseGet(PlayerSpellData::new);
    }

    public static ItemStack getSpellItemFromName(String name) {
        for (SpellItem item : SpellItem.spellItems) {
            if (name.equals(item.getName().getString())) {
                return new ItemStack(item);
            }
        }
        return ItemStack.EMPTY;
    }
}