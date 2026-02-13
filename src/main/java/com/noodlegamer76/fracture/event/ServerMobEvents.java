package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.monster.*;
import com.noodlegamer76.fracture.entity.monster.boss.FleshNub;
import com.noodlegamer76.fracture.network.PacketHandler;
import net.minecraft.world.entity.animal.Cow;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerMobEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(InitEntities.ANKLE_BITER.get(), AnkleBiterEntity.createAttributes().build());
        event.put(InitEntities.FLESH_WALKER.get(), FleshWalkerEntity.createAttributes().build());
        event.put(InitEntities.FLESH_SLIME.get(), FleshSlimeEntity.createAttributes().build());
        event.put(InitEntities.ABDOMINAL_SNOWMAN.get(), AbdominalSnowman.createAttributes().build());
        event.put(InitEntities.KNOWLEDGEABLE_SNOWMAN.get(), KnowledgeableSnowman.createAttributes().build());
        event.put(InitEntities.COMPARABLE_SNOWMAN.get(), ComparableSnowman.createAttributes().build());
        event.put(InitEntities.MOOSICLE.get(), Cow.createAttributes().build());
        event.put(InitEntities.SKULLCHOMPER.get(), SkullChomper.createAttributes().build());
        event.put(InitEntities.FLESH_NUB.get(), FleshNub.createAttributes().build());
        event.put(InitEntities.PLAYER_MIMIC.get(), PlayerMimic.createAttributes().build());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::register);
    }
}
