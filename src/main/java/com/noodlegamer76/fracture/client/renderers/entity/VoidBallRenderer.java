package com.noodlegamer76.fracture.client.renderers.entity;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import com.noodlegamer76.fracture.entity.projectile.VoidBall;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VoidBallRenderer extends SpellRenderer<VoidBall> {
    public VoidBallRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<VoidBall>(new ResourceLocation(FractureMod.MODID, "void_ball"), false));
    }

    @Override
    public RenderType getRenderType(VoidBall animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return ModRenderTypes.SKYBOX;
    }
}
