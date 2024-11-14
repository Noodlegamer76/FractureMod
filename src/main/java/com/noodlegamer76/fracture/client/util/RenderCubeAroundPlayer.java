package com.noodlegamer76.fracture.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
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

    public static void renderCubeFog(PoseStack poseSteack, int ticks, float partialTick, int alpha, float speed,
                                     ResourceLocation frontTexture, ResourceLocation backTexture, ResourceLocation leftTexture,
                                     ResourceLocation rightTexture, ResourceLocation topTexture, ResourceLocation bottomTexture) {

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        RenderSystem.depthMask(false);

        PoseStack poseStack = new PoseStack();


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
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
            float near = (float) 100;
            bufferbuilder.vertex(matrix4f, -near, -near, -near).uv(0.0F, 0.0F).color(255, 255, 255, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, -near, -near, near).uv(0.0F, 1.0F).color(255, 255, 255, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, near, -near, near).uv(1.0F, 1.0F).color(255, 255, 255, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, near, -near, -near).uv(1.0F, 0.0F).color(255, 255, 255, alpha).endVertex();
            tesselator.end();
            poseStack.popPose();
        }
        RenderSystem.depthMask(true);
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
