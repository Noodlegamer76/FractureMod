package com.noodlegamer76.fracture.spellcrafting.data;

import com.noodlegamer76.fracture.util.InitCapabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.Map;

public class SpellHelper {
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
}