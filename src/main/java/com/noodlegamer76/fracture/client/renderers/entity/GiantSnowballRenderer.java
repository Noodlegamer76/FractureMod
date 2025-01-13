package com.noodlegamer76.fracture.client.renderers.entity;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.projectile.GiantSnowballProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GiantSnowballRenderer extends SpellRenderer<GiantSnowballProjectile> {
    public GiantSnowballRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(FractureMod.MODID, "giant_snowball")));
    }
}
