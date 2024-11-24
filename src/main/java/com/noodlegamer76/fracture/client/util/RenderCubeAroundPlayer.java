package com.noodlegamer76.fracture.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.fracture.event.RenderLevelEventsForFog;
import net.minecraft.client.Camera;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterShadersEvent;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL44;

import javax.annotation.Nullable;
import java.nio.FloatBuffer;

import static com.noodlegamer76.fracture.event.RenderEventsForFbos.postFbo;

public class RenderCubeAroundPlayer {
    public static float near = (float) Minecraft.getInstance().gameRenderer.getMainCamera().getNearPlane().getBottomLeft().length();

    public static void renderCube(PoseStack poseStack, int ticks, float partialTick, int alpha, float speed,
                                  ResourceLocation frontTexture, ResourceLocation backTexture, ResourceLocation leftTexture,
                                  ResourceLocation rightTexture, ResourceLocation topTexture, ResourceLocation bottomTexture) {

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        for(int i = 0; i < 6; ++i) {
            if (frontTexture != null) {
                switch(i) {
                    case 0: RenderSystem.setShaderTexture(0, frontTexture); break;
                    case 1: RenderSystem.setShaderTexture(0, rightTexture); break;
                    case 2: RenderSystem.setShaderTexture(0, leftTexture); break;
                    case 3: RenderSystem.setShaderTexture(0, backTexture); break;
                    case 4: RenderSystem.setShaderTexture(0, bottomTexture); break;
                    case 5: RenderSystem.setShaderTexture(0, topTexture); break;
                }
            }
            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees((ticks + partialTick) * speed));
            if (i == 0) {

                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 1) {

                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(-90));
            }

            if (i == 2) {

                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(90));
            }

            if (i == 3) {

                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            }

            if (i == 4) {

                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 5) {

                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }
            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferbuilder.vertex(matrix4f, -near, -near, -near).uv(0.0F, 0.0F).color(255, 255, 255, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, -near, -near, near).uv(0.0F, 1.0F).color(255, 255, 255, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, near, -near, near).uv(1.0F, 1.0F).color(255, 255, 255, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, near, -near, -near).uv(1.0F, 0.0F).color(255, 255, 255, alpha).endVertex();
            tesselator.end();
            poseStack.popPose();
        }
    }

    public static void renderCubeFog(PoseStack poseStack, float minDistance, float maxDistance, Vector3f fogColor, int doSkyFog) {

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        Vec3 pos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        float[] cameraPos = {(float) pos.x, (float) pos.y, (float) pos.z};

        RenderSystem.setShader(() -> ModRenderTypes.fog);
        GlStateManager._glUseProgram(ModRenderTypes.fog.getId());

        if (ModRenderTypes.fog.getUniform("CameraPos") != null) {
            ModRenderTypes.fog.getUniform("CameraPos").set(cameraPos);
        }
        Matrix4f matrix4f2 = poseStack.last().pose();
        if (ModRenderTypes.fog.getUniform("ModelView2") != null) {
            ModRenderTypes.fog.getUniform("ModelView2").set(matrix4f2);
        }

        if (ModRenderTypes.fog.getUniform("FColor") != null) {
            ModRenderTypes.fog.getUniform("FColor").set(fogColor);
        }

        if (ModRenderTypes.fog.getUniform("MinFogDistance") != null) {
            ModRenderTypes.fog.getUniform("MinFogDistance").set(minDistance);
        }

        if (ModRenderTypes.fog.getUniform("MaxFogDistance") != null) {
            ModRenderTypes.fog.getUniform("MaxFogDistance").set(maxDistance);
        }
        if (ModRenderTypes.fog.getUniform("DoSkyFog") != null) {
            ModRenderTypes.fog.getUniform("DoSkyFog").set((float) doSkyFog);
        }


        if (Minecraft.getInstance().options.graphicsMode().get().getId() == GraphicsStatus.FABULOUS.getId()) {
            GlStateManager.glActiveTexture(GL44.GL_TEXTURE0);
            GlStateManager._bindTexture(Minecraft.getInstance().levelRenderer.getTranslucentTarget().getDepthTextureId());
            GlStateManager._glUniform1i(GL44.glGetUniformLocation(ModRenderTypes.fog.getId(), "Depth"), 0);
        }
        else {
            GlStateManager.glActiveTexture(GL44.GL_TEXTURE0);
            GlStateManager._bindTexture(Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
            GlStateManager._glUniform1i(GL44.glGetUniformLocation(ModRenderTypes.fog.getId(), "Depth"), 0);
        }
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();

        for(int i = 0; i < 6; i++) {

            poseStack.pushPose();

            poseStack.translate(0, 2,0);

            if (i == 0) {

                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 1) {

                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(-90));
            }

            if (i == 2) {

                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(90));
            }

            if (i == 3) {

                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            }

            if (i == 4) {

                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 5) {

                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder.vertex(matrix4f, -10, -10, -10).endVertex();
            bufferbuilder.vertex(matrix4f, -10, -10, 10).endVertex();
            bufferbuilder.vertex(matrix4f, 10, -10, 10).endVertex();
            bufferbuilder.vertex(matrix4f, 10,  -10, -10).endVertex();
            tesselator.end();


            poseStack.popPose();
        }
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        GL44.glMemoryBarrier(GL44.GL_FRAMEBUFFER_BARRIER_BIT);
        RenderLevelEventsForFog.positions.clear();
    }

    public static void renderCubeNormal(PoseStack poseStack) {

        int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);
        GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, postFbo);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        Vec3 pos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        float[] cameraPos = {(float) pos.x, (float) pos.y, (float) pos.z};

        RenderSystem.setShader(() -> ModRenderTypes.normal);
        GlStateManager._glUseProgram(ModRenderTypes.normal.getId());

        if (ModRenderTypes.normal.getUniform("CameraPos") != null) {
            ModRenderTypes.normal.getUniform("CameraPos").set(cameraPos);
        }
        Matrix4f matrix4f2 = poseStack.last().pose();
        if (ModRenderTypes.normal.getUniform("ModelView2") != null) {
            ModRenderTypes.normal.getUniform("ModelView2").set(matrix4f2);
        }


        if (Minecraft.getInstance().options.graphicsMode().get().getId() == GraphicsStatus.FABULOUS.getId()) {
            GlStateManager.glActiveTexture(GL44.GL_TEXTURE0);
            GlStateManager._bindTexture(Minecraft.getInstance().levelRenderer.getTranslucentTarget().getDepthTextureId());
            GlStateManager._glUniform1i(GL44.glGetUniformLocation(ModRenderTypes.normal.getId(), "Depth"), 0);
        }
        else {
            GlStateManager.glActiveTexture(GL44.GL_TEXTURE0);
            GlStateManager._bindTexture(Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
            GlStateManager._glUniform1i(GL44.glGetUniformLocation(ModRenderTypes.normal.getId(), "Depth"), 0);
        }
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();

        for(int i = 0; i < 6; i++) {

            poseStack.pushPose();

            poseStack.translate(0, 2,0);

            if (i == 0) {

                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 1) {

                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(-90));
            }

            if (i == 2) {

                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(90));
            }

            if (i == 3) {

                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            }

            if (i == 4) {

                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 5) {

                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder.vertex(matrix4f, -10, -10, -10).endVertex();
            bufferbuilder.vertex(matrix4f, -10, -10, 10).endVertex();
            bufferbuilder.vertex(matrix4f, 10, -10, 10).endVertex();
            bufferbuilder.vertex(matrix4f, 10,  -10, -10).endVertex();
            tesselator.end();


            poseStack.popPose();
        }
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        GL44.glMemoryBarrier(GL44.GL_FRAMEBUFFER_BARRIER_BIT);
        RenderLevelEventsForFog.positions.clear();

        GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);
    }


    public static void renderCubeSphere(PoseStack poseStack, int ticks, float partialTick, int alpha, float speed,
                                     ResourceLocation frontTexture, ResourceLocation backTexture, ResourceLocation leftTexture,
                                     ResourceLocation rightTexture, ResourceLocation topTexture, ResourceLocation bottomTexture) {

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        Vec3 pos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        float[] cameraPos = {(float) pos.x, (float) pos.y, (float) pos.z};

        RenderSystem.setShader(() -> ModRenderTypes.fog);
        GlStateManager._glUseProgram(ModRenderTypes.fog.getId());

        if (ModRenderTypes.fog.getUniform("CameraPos") != null) {
            ModRenderTypes.fog.getUniform("CameraPos").set(cameraPos);
        }
        Matrix4f matrix4f2 = poseStack.last().pose();
        if (ModRenderTypes.fog.getUniform("ModelView2") != null) {
            ModRenderTypes.fog.getUniform("ModelView2").set(matrix4f2);
        }

        if (Minecraft.getInstance().options.graphicsMode().get().getId() == GraphicsStatus.FABULOUS.getId()) {
            GlStateManager.glActiveTexture(GL44.GL_TEXTURE0);
            GlStateManager._bindTexture(Minecraft.getInstance().levelRenderer.getTranslucentTarget().getDepthTextureId());
            GlStateManager._glUniform1i(GL44.glGetUniformLocation(ModRenderTypes.fog.getId(), "Depth"), 0);
        }
        else {
            GlStateManager.glActiveTexture(GL44.GL_TEXTURE0);
            GlStateManager._bindTexture(Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
            GlStateManager._glUniform1i(GL44.glGetUniformLocation(ModRenderTypes.fog.getId(), "Depth"), 0);
        }
        RenderSystem.setShaderTexture(0, Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();

        for(int i = 0; i < 6; i++) {

            poseStack.pushPose();

            poseStack.translate(0, 2,0);

            if (i == 0) {

                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 1) {

                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(-90));
            }

            if (i == 2) {

                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(90));
            }

            if (i == 3) {

                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            }

            if (i == 4) {

                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 5) {

                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder.vertex(matrix4f, -10, -10, -10).endVertex();
            bufferbuilder.vertex(matrix4f, -10, -10, 10).endVertex();
            bufferbuilder.vertex(matrix4f, 10, -10, 10).endVertex();
            bufferbuilder.vertex(matrix4f, 10,  -10, -10).endVertex();
            tesselator.end();


            poseStack.popPose();
        }
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        GL44.glMemoryBarrier(GL44.GL_FRAMEBUFFER_BARRIER_BIT);
        RenderLevelEventsForFog.positions.clear();
    }



    public static void render(PoseStack poseStack, @Nullable ResourceLocation skyboxFolder) {
        if (skyboxFolder == null) {
            renderCube(poseStack, 0, 0, 255, 0,
                    null, null, null, null,
                    null, null);
            return;
        }

        renderCube(poseStack, 0, 0, 255, 0,
                skyboxFolder.withSuffix("/front.png"),
                skyboxFolder.withSuffix("/back.png"),
                skyboxFolder.withSuffix("/left.png"),
                skyboxFolder.withSuffix("/right.png"),
                skyboxFolder.withSuffix("/top.png"),
                skyboxFolder.withSuffix("/bottom.png")
        );
    }
    public static void renderSphere(PoseStack poseStack, @Nullable ResourceLocation skyboxFolder) {
        if (skyboxFolder == null) {
            renderCubeSphere(poseStack, 0, 0, 255, 0,
                    null, null, null, null,
                    null, null);
            return;
        }

        renderCubeSphere(poseStack, 0, 0, 255, 0,
                skyboxFolder.withSuffix("/front.png"),
                skyboxFolder.withSuffix("/back.png"),
                skyboxFolder.withSuffix("/left.png"),
                skyboxFolder.withSuffix("/right.png"),
                skyboxFolder.withSuffix("/top.png"),
                skyboxFolder.withSuffix("/bottom.png")
        );
    }


    public static void render(PoseStack poseStack, int ticks, float partialTick, int alpha, float speed, ResourceLocation skyboxFolder) {
        renderCube(poseStack, ticks, partialTick, alpha, speed,
                skyboxFolder.withSuffix("/front.png"),
                skyboxFolder.withSuffix("/back.png"),
                skyboxFolder.withSuffix("/left.png"),
                skyboxFolder.withSuffix("/right.png"),
                skyboxFolder.withSuffix("/top.png"),
                skyboxFolder.withSuffix("/bottom.png")
        );
    }
}
