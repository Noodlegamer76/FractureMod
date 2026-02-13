package com.noodlegamer76.fracture.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.monster.boss.FleshNub;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FleshNubRenderer extends GeoEntityRenderer<FleshNub> {
    public FleshNubRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "flesh_nub"), true));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, FleshNub animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public float getMotionAnimThreshold(FleshNub animatable) {
        return 0.007F;
    }
}
