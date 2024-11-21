package com.noodlegamer76.fracture.event;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import com.noodlegamer76.fracture.client.util.RenderCubeAroundPlayer;
import com.noodlegamer76.fracture.client.util.RenderFog;
import com.noodlegamer76.fracture.client.util.SkyBoxRenderer;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL44;

import java.io.IOException;
import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, value = Dist.CLIENT)
public class RenderLevelEventsForFog {
    public static ArrayList<ArrayList<Integer>> positions = new ArrayList<>();

    @SubscribeEvent
    public static void renderLevelEvent(RenderLevelStageEvent event) throws IOException {


        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {


        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            for (int i = 0; i < positions.size(); i++) {

            }
            RenderCubeAroundPlayer.renderFog(event.getPoseStack(), null);
        }
        //if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER && Minecraft.useFancyGraphics()) {
        //    for (int i = 0; i < positions.size(); i++) {
        //        RenderCubeAroundPlayer.renderFog(new PoseStack(), null);
        //    }
        //}

    }
}
