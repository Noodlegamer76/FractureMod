package com.noodlegamer76.fracture.event;


import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.renderers.SkullchomperRenderer;
import com.noodlegamer76.fracture.client.renderers.entity.*;
import com.noodlegamer76.fracture.client.renderers.entity.block.*;
import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.block.InitBlockEntities;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRenderers {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModBoatRenderer.INKWOOD_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModBoatRenderer.INKWOOD_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(InitEntities.ANKLE_BITER.get(), AnkleBiterRenderer::new);
        event.registerEntityRenderer(InitEntities.FLESH_WALKER.get(), FleshWalkerRenderer::new);
        event.registerEntityRenderer(InitEntities.FLESH_SLIME.get(), FleshSlimeRenderer::new);
        event.registerEntityRenderer(InitEntities.COMPACT_TNT.get(), CompactTntRenderer::new);
        event.registerEntityRenderer(InitEntities.ABDOMINAL_SNOWMAN.get(), AbdominalSnowmanRenderer::new);
        event.registerEntityRenderer(InitEntities.KNOWLEDGEABLE_SNOWMAN.get(), KnowledgeableSnowmanRenderer::new);
        event.registerEntityRenderer(InitEntities.COMPARABLE_SNOWMAN.get(), ComparableSnowmanRenderer::new);
        event.registerEntityRenderer(InitEntities.MOOSICLE.get(), MoosicleRenderer::new);
        event.registerEntityRenderer(InitEntities.SKULLCHOMPER.get(), SkullchomperRenderer::new);

        event.registerBlockEntityRenderer(InitBlockEntities.INKWOOK_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.INKWOOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.BOREAS_PORTAL.get(), BoreasPortalRenderer::new);
    }
}
