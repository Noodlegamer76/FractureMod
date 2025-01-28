package com.noodlegamer76.fracture.client.renderers.entity;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.monster.GiantRobot;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GiantRobotRenderer extends GeoEntityRenderer<GiantRobot> {
    public GiantRobotRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(FractureMod.MODID, "giant_robot")));
    }
}
