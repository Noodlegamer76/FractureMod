package com.noodlegamer76.fracture.client.render.entity;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.projectile.IceCube;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IceCubeRenderer extends GeoEntityRenderer<IceCube> {
    public IceCubeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "ice_cube")));
    }
}
