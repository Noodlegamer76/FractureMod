package com.noodlegamer76.fracture.client.renderers.entity.block;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.RenderFog;
import com.noodlegamer76.fracture.entity.block.FogEmitterEntity;
import com.noodlegamer76.fracture.event.ShaderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL44;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import static com.noodlegamer76.fracture.event.ShaderEvents.FOG_RENDERTYPE;

public class FogEmitterRenderer extends GeoBlockRenderer<FogEmitterEntity> {

    public FogEmitterRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(FractureMod.MODID, "inverted_cube")));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, FogEmitterEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        RenderSystem.setShader(() -> ShaderEvents.fog);
        GL44.glUseProgram(ShaderEvents.fog.getId());

        RenderSystem.activeTexture(GL44.GL_TEXTURE0);
        RenderSystem.bindTexture(Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
        RenderSystem.glUniform1i(GlStateManager._glGetUniformLocation(ShaderEvents.fog.getId(),  "Depth"), 0);

        RenderSystem.activeTexture(GL44.GL_TEXTURE1);
        RenderSystem.bindTexture(Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
        RenderSystem.glUniform1i(GlStateManager._glGetUniformLocation(ShaderEvents.fog.getId(),  "Color"), 1);

        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();

        poseStack.pushPose();
        poseStack.translate(-animatable.getBlockPos().getX(), -animatable.getBlockPos().getY(), -animatable.getBlockPos().getZ());
        poseStack.translate(camera.getPosition().x() - 0.5, camera.getPosition().y() - 0.5, camera.getPosition().z() - 0.5);
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();

        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);

    }

    @Override
    public RenderType getRenderType(FogEmitterEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return FOG_RENDERTYPE;
    }

    @Override
    public boolean shouldRenderOffScreen(FogEmitterEntity pBlockEntity) {
        return true;
    }
}