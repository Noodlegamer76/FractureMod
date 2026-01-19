package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.renderers.entity.ModBoatRenderer;
import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.fluid.InitFluids;
import com.noodlegamer76.fracture.util.ModWoodTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = FractureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(InitFluids.SOURCE_BLOOD.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitFluids.FLOWING_BLOOD.get(), RenderType.translucent());

        Sheets.addWoodType(ModWoodTypes.INKWOOD);
        EntityRenderers.register(InitEntities.MOD_BOAT.get(), pContext -> new ModBoatRenderer(pContext, false));
        EntityRenderers.register(InitEntities.MOD_CHEST_BOAT.get(), pContext -> new ModBoatRenderer(pContext, true));
    }
}
