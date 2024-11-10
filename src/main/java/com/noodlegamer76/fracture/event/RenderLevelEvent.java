package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.RenderCubeAroundPlayer;
import com.noodlegamer76.fracture.client.util.RenderFog;
import com.noodlegamer76.fracture.client.util.SkyBoxRenderer;
import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.item.armor.InvertedGlasses;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, value = Dist.CLIENT)
public class RenderLevelEvent {
    public static ArrayList<ArrayList<Integer>> positions = new ArrayList<>();
    public static final ResourceLocation TEXTURE = new ResourceLocation(FractureMod.MODID, "textures/environment/layer1/skybox1");

    @SubscribeEvent
    public static void renderLevelEvent(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            assert Minecraft.getInstance().player != null;
            ItemStack item = Minecraft.getInstance().player.getItemBySlot(EquipmentSlot.HEAD);

            if (item.getItem() != InitItems.INVERTED_GLASSES.get()) {
                if (!(Minecraft.getInstance().gameRenderer.currentEffect() == null) &&
                        (Minecraft.getInstance().gameRenderer.currentEffect().getName().equals("minecraft:shaders/post/flip.json"))) {

                    Minecraft.getInstance().gameRenderer.shutdownEffect();
                }
            }
        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {

            //ArrayList<ArrayList<Integer>> integer = new ArrayList<>();
            //for(int i = 0; i < positions.size(); i++) {
//
            //}



            for (ArrayList<Integer> array: positions) {
                ArrayList<Integer> current = array;
                int skybox = current.get(0);
                int rotationSpeed = current.get(1);;
                int transparency = current.get(2);; //max 255
                int minRenderDistance = current.get(3);;
                int maxRenderDistance = current.get(4);;
                int renderPriority = current.get(4); //order of skybox rendering

                SkyBoxRenderer.render(event.getPoseStack(), event.getRenderTick(), event.getPartialTick(),
                        -transparency + 255, (float) rotationSpeed / 360,
                        skybox, maxRenderDistance, TEXTURE);
            }
            positions.clear();
        }
    }
}
