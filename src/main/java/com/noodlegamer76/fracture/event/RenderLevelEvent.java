package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.gui.structure.StructureInstanceVisualizer;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderLevelEvent {

    @SubscribeEvent
    public static void renderVisualizer(RenderLevelStageEvent event) {
        if (true) {
            //return;
        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
            for (VisualizerEntry<?> entry: StructureInstanceVisualizer.getInstance().getVisualizers().values()) {
                entry.renderInWorld(
                        event.getLevelRenderer(),
                        event.getPoseStack(),
                        event.getRenderTick(),
                        event.getPartialTick(),
                        Minecraft.getInstance().renderBuffers().bufferSource()
                );
            }
        }
    }
}
