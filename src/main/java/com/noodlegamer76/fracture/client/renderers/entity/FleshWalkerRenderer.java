package com.noodlegamer76.fracture.client.renderers.entity;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.monster.FleshWalkerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FleshWalkerRenderer extends GeoEntityRenderer<FleshWalkerEntity> {
    public FleshWalkerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(FractureMod.MODID, "flesh_walker"), true));
    }

    @Override
    public float getMotionAnimThreshold(FleshWalkerEntity animatable) {
        return 0.005F;
    }

}
