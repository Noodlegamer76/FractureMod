package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreateEntityAttributes {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(InitEntities.FLESH_RATTLER.get(), FleshRattlerEntity.createAttributes().build());
        event.put(InitEntities.ANKLE_BITER.get(), AnkleBiterEntity.createAttributes().build());
        event.put(InitEntities.FLESH_WALKER.get(), FleshWalkerEntity.createAttributes().build());
        event.put(InitEntities.FLESH_SLIME.get(), FleshSlimeEntity.createAttributes().build());
    }
}
