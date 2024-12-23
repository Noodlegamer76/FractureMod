package com.noodlegamer76.fracture.client.renderers.entity.block;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import com.noodlegamer76.fracture.client.util.RenderCubeAroundPlayer;
import com.noodlegamer76.fracture.entity.block.FogEmitterEntity;
import com.noodlegamer76.fracture.event.RenderLevelEventsForFog;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL44;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FogEmitterRenderer extends GeoBlockRenderer<FogEmitterEntity> {

    public FogEmitterRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(FractureMod.MODID, "inverted_cube")));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, FogEmitterEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {


        //poseStack.pushPose();
        ////poseStack.translate(-animatable.getBlockPos().getX(), -animatable.getBlockPos().getY(), -animatable.getBlockPos().getZ());
        ////poseStack.translate(camera.getPosition().x() - 0.5, camera.getPosition().y() - 0.5, camera.getPosition().z() - 0.5);
        ////Minecraft.getInstance().getMainRenderTarget().blitToScreen(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
        ////super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        //RenderCubeAroundPlayer.renderFog(poseStack, null);
        //poseStack.popPose();

        RenderLevelEventsForFog.positions.add(null);

    }

    @Override
    public RenderType getRenderType(FogEmitterEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return ModRenderTypes.FOG_RENDERTYPE;
    }

    @Override
    public boolean shouldRenderOffScreen(FogEmitterEntity pBlockEntity) {
        return true;
    }
}
