package com.noodlegamer76.fracture.client.renderers.entity;

import com.noodlegamer76.fracture.client.models.geomodels.FleshRattlerModel;
import com.noodlegamer76.fracture.entity.FleshRattlerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FleshRattlerRenderer extends GeoEntityRenderer<FleshRattlerEntity> {
    public FleshRattlerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,  new FleshRattlerModel());
    }

    @Override
    public RenderType getRenderType(FleshRattlerEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(texture);
    }
}
