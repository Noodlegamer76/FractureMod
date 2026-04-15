package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.render.world.BorealisRenderer;
import com.noodlegamer76.fracture.worldgen.ModDimensionKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, value = Dist.CLIENT)
public class RenderLevelEvent {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        Level level = Minecraft.getInstance().level;
        if (level != null && level.dimension().equals(ModDimensionKeys.BOREAS)) {
            BorealisRenderer.render(event.getPoseStack(), event.getPartialTick(), event.getRenderTick());
        }
    }
}
