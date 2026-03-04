package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structures;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FractureMod.MODID)
public class ServerSetupEvent {

    @SubscribeEvent
    public static void serverSetup(ServerStartedEvent event) {
        Structures.getInstance().setupStructures(event.getServer());
    }
}
