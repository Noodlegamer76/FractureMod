package com.noodlegamer76.fracture.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import com.noodlegamer76.fracture.client.util.RenderCubeAroundPlayer;
import com.noodlegamer76.fracture.client.util.RenderFog;
import com.noodlegamer76.fracture.client.util.SkyBoxRenderer;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, value = Dist.CLIENT)
public class RenderLevelEventsForFog {
    public static ArrayList<ArrayList<Integer>> positions = new ArrayList<>();

    @SubscribeEvent
    public static void renderLevelEvent(RenderLevelStageEvent event) {

        //I HATE THIS
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            ModRenderTypes.fog.setSampler("Depth", Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
            ModRenderTypes.fog.setSampler("Color", Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
            for (int i = 0; i < positions.size(); i++) {
                RenderCubeAroundPlayer.renderFog(new PoseStack(), null);
            }
        }
        //if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER && Minecraft.useFancyGraphics()) {
        //    for (int i = 0; i < positions.size(); i++) {
        //        RenderCubeAroundPlayer.renderFog(new PoseStack(), null);
        //    }
        //}

    }
}
