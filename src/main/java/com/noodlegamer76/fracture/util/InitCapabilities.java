package com.noodlegamer76.fracture.util;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.spellcrafting.data.PlayerSpellData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = FractureMod.MODID)
public class InitCapabilities {
    public static final Capability<PlayerSpellData> PLAYER_SPELL_DATA = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerSpellData.class);
    }
}
