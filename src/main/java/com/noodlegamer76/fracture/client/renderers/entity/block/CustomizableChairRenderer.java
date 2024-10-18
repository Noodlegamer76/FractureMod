package com.noodlegamer76.fracture.client.renderers.entity.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.block.CustomizableChairEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class CustomizableChairRenderer extends GeoBlockRenderer<CustomizableChairEntity> {
    public CustomizableChairRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(FractureMod.MODID, "inverted_cube")));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, CustomizableChairEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public GeoBlockRenderer<CustomizableChairEntity> addRenderLayer(GeoRenderLayer<CustomizableChairEntity> renderLayer) {
        return super.addRenderLayer(new GeoRenderLayer<CustomizableChairEntity>(this) {
            @Override
            public GeoModel<CustomizableChairEntity> getGeoModel() {
                return new DefaultedBlockGeoModel<>(new ResourceLocation(FractureMod.MODID, "layer"));
            }
        });
    }
}
