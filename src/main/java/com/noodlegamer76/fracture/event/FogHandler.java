package com.noodlegamer76.fracture.event;

import com.mojang.blaze3d.shaders.FogShape;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.ModDimensionKeys;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FogHandler {

    @SubscribeEvent
    public static void onFog(ViewportEvent.RenderFog event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (mc.player.level().dimension() == ModDimensionKeys.BOREAS) {
            event.setNearPlaneDistance(0.0f);
            event.setFarPlaneDistance(64.0f);
            event.setFogShape(FogShape.SPHERE);
        }
    }

    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (mc.player.level().dimension() == ModDimensionKeys.BOREAS) {
            event.setRed(0.85f);
            event.setGreen(0.9f);
            event.setBlue(1f);
        }
    }
}