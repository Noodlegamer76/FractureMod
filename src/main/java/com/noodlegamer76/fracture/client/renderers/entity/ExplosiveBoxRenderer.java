package com.noodlegamer76.fracture.client.renderers.entity;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.projectile.ExplosiveBox;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ExplosiveBoxRenderer extends GeoEntityRenderer<ExplosiveBox> {
    public ExplosiveBoxRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(FractureMod.MODID, "explosive_box")));
    }
}
