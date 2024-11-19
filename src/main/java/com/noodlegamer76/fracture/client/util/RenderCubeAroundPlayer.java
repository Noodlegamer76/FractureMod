package com.noodlegamer76.fracture.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.fracture.event.RenderLevelEventsForFog;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterShadersEvent;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

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

    public static void renderCubeFog(PoseStack poseStack, int ticks, float partialTick, int alpha, float speed,
                                     ResourceLocation frontTexture, ResourceLocation backTexture, ResourceLocation leftTexture,
                                     ResourceLocation rightTexture, ResourceLocation topTexture, ResourceLocation bottomTexture) {

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        VertexConsumer buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(ModRenderTypes.FOG_RENDERTYPE);


        RenderSystem.setShader(() -> ModRenderTypes.fog);
        RenderSystem.disableDepthTest();


        poseStack.pushPose();

        poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

        Matrix4f matrix4f = poseStack.last().pose();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        Camera.NearPlane near = Minecraft.getInstance().gameRenderer.getMainCamera().getNearPlane();
        bufferbuilder.vertex(matrix4f, (float) near.getTopLeft().x() + -10, (float) near.getTopLeft().y() + -1, (float) near.getTopLeft().z() + -10).endVertex();
        bufferbuilder.vertex(matrix4f, (float) near.getTopRight().x() + -10, (float) near.getTopRight().y() + -1, (float) near.getTopRight().z() + 10).endVertex();
        bufferbuilder.vertex(matrix4f, (float) near.getBottomRight().x() + 10, (float) near.getBottomRight().y() + -1, (float) near.getBottomRight().z() + 10).endVertex();
        bufferbuilder.vertex(matrix4f, (float) near.getBottomLeft().x() + 10, (float) near.getBottomLeft().y() + -1, (float) near.getBottomLeft().z() + -10).endVertex();
        tesselator.end();

        poseStack.popPose();

        RenderSystem.enableDepthTest();
        RenderLevelEventsForFog.positions.clear();
    }


    public static void renderFullscreenQuad(PoseStack poseStack, int ticks, float partialTick, int alpha, float speed,
                                            ResourceLocation frontTexture, ResourceLocation backTexture,
                                            ResourceLocation leftTexture, ResourceLocation rightTexture,
                                            ResourceLocation topTexture, ResourceLocation bottomTexture) {

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        // Disable depth test and depth mask for full-screen rendering
        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();

        // Set shader for fog rendering
        RenderSystem.setShader(() -> ModRenderTypes.fog);

        // Push transformation matrix
        poseStack.pushPose();

        // Apply any necessary rotations or transformations to ensure full-screen coverage
        // The following just applies a general view matrix transformation
        poseStack.mulPose(Axis.YN.rotationDegrees((ticks + partialTick) * speed));

        // Set the matrix for transformation
        Matrix4f matrix4f = poseStack.last().pose();

        // Begin rendering the quad
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        // Coordinates for fullscreen quad (you may adjust the near/far if needed)
        float near = -1.0f;  // Near plane position
        float far = 1.0f;    // Far plane position

        // Vertex coordinates and texture coordinates for fullscreen quad
        bufferbuilder.vertex(matrix4f, -1.0f, -1.0f, near).uv(0.0F, 0.0F).color(255, 255, 255, alpha).endVertex();
        bufferbuilder.vertex(matrix4f, -1.0f,  1.0f, near).uv(0.0F, 1.0F).color(255, 255, 255, alpha).endVertex();
        bufferbuilder.vertex(matrix4f,  1.0f,  1.0f, near).uv(1.0F, 1.0F).color(255, 255, 255, alpha).endVertex();
        bufferbuilder.vertex(matrix4f,  1.0f, -1.0f, near).uv(1.0F, 0.0F).color(255, 255, 255, alpha).endVertex();

        // End the rendering
        tesselator.end();

        // Pop transformation matrix
        poseStack.popPose();

        // Re-enable depth testing and depth mask
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);

        // Clear the positions (if you have this as part of your post-processing setup)
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

    public static void renderFog(PoseStack poseStack, @Nullable ResourceLocation skyboxFolder) {
        if (skyboxFolder == null) {
            renderCubeFog(poseStack, 0, 0, 255, 0,
                    null, null, null, null,
                    null, null);
            return;
        }

        renderCubeFog(poseStack, 0, 0, 255, 0,
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
