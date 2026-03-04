package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FractureRegistries {
    public static final ResourceLocation GEN_VAR_SERIALIZERS = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "gen_var_serializers");

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.create(new RegistryBuilder<GenVarSerializer<?>>()
                .setName(GEN_VAR_SERIALIZERS));
    }
}
