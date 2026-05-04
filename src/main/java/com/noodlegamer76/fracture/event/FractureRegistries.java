package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.spellcrafting.graph.Node;
import com.noodlegamer76.fracture.spellcrafting.nodes.NodeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import static net.minecraftforge.fml.common.Mod.*;

@EventBusSubscriber(modid = FractureMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class FractureRegistries {
    public static final ResourceLocation NODE_TYPE = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "node_type");

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.create(new RegistryBuilder<NodeType<? extends Node>>()
                .setName(NODE_TYPE));
    }
}
