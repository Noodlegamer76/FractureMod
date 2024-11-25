package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.*;
import com.noodlegamer76.fracture.entity.monster.*;
import com.noodlegamer76.fracture.network.PacketHandler;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerMobEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(InitEntities.FLESH_RATTLER.get(), FleshRattlerEntity.createAttributes().build());
        event.put(InitEntities.ANKLE_BITER.get(), AnkleBiterEntity.createAttributes().build());
        event.put(InitEntities.FLESH_WALKER.get(), FleshWalkerEntity.createAttributes().build());
        event.put(InitEntities.FLESH_SLIME.get(), FleshSlimeEntity.createAttributes().build());
        event.put(InitEntities.BLOOD_SLIME.get(), BloodSlimeEntity.createAttributes().build());
        event.put(InitEntities.ABDOMINAL_SNOWMAN.get(), AbdominalSnowman.createAttributes().build());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.register();
        });
    }




}
