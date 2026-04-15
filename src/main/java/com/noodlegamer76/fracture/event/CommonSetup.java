package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {

    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {

    }
}
