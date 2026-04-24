package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.world.VoidZoneCache;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structures;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FractureMod.MODID)
public class ServerLifeEvents {

    @SubscribeEvent
    public static void serverStarted(ServerStartedEvent event) {
        Structures.getInstance().setupStructures();
        ServerLevel overworld = event.getServer().getLevel(ServerLevel.OVERWORLD);
        VoidZoneCache.load(overworld);
    }

    @SubscribeEvent
    public static void serverStopped(ServerStoppedEvent event) {
        Structures.getInstance().setupStructures();
        VoidZoneCache.clear();
    }
}
