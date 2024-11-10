package com.noodlegamer76.fracture.client.models.geomodels;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.monster.FleshRattlerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FleshRattlerModel extends GeoModel<FleshRattlerEntity> {
    private final ResourceLocation modelResource = new ResourceLocation(FractureMod.MODID, "geo/flesh_rattler.geo.json");
    private final ResourceLocation textureResource = new ResourceLocation(FractureMod.MODID, "textures/entity/flesh_rattler");
    private final ResourceLocation animationResource = new ResourceLocation(FractureMod.MODID, "animations/flesh_rattler.animation.json");

    @Override
    public ResourceLocation getModelResource(FleshRattlerEntity animatable) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureResource(FleshRattlerEntity animatable) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationResource(FleshRattlerEntity animatable) {
        return animationResource;
    }
}
