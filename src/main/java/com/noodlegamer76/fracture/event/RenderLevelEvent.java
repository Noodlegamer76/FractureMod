package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.RenderFog;
import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.item.armor.InvertedGlasses;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, value = Dist.CLIENT)
public class RenderLevelEvent {

    @SubscribeEvent
    public static void renderLevelEvent(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            assert Minecraft.getInstance().player != null;
            ItemStack item = Minecraft.getInstance().player.getItemBySlot(EquipmentSlot.HEAD);

            if (item.getItem() != InitItems.INVERTED_GLASSES.get()) {
                if (!(Minecraft.getInstance().gameRenderer.currentEffect() == null) &&
                        (Minecraft.getInstance().gameRenderer.currentEffect().getName().equals("minecraft:shaders/post/flip.json"))) {

                    Minecraft.getInstance().gameRenderer.shutdownEffect();
                }
            }
        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            //RenderFog.renderFog(event.getPoseStack());
        }
    }
}
