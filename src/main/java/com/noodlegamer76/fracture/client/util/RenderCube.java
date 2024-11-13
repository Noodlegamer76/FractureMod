package com.noodlegamer76.fracture.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.fracture.event.RenderEventsForFbos;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterShadersEvent;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL44;

import java.util.ArrayList;

public class RenderCube {

    public static void createCubeWithShader(PoseStack poseStack, ArrayList<BlockPos> positions, float partialTicks) {
        // Get a reference to the Tesselator and BufferBuilder for batch rendering
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        // Enable necessary OpenGL states
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();

        // Set the shader once for the entire batch
        RenderSystem.setShader(() -> ModRenderTypes.skybox);

        // Begin batching by defining the geometry in a single buffer
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for (int j = 0; j < positions.size(); j++) {
            BlockPos pos = positions.get(j);

            // Loop through all 6 faces of the cube
            for (int i = 0; i < 6; i++) {
                poseStack.pushPose();
                poseStack.translate(0.5, 0.5, 0.5);

                // Translate the position to the correct location
                poseStack.translate(-camera.x, -camera.y, -camera.z);
                poseStack.translate(pos.getX(), pos.getY(), pos.getZ());

                // Apply the appropriate rotation for each face of the cube
                switch (i) {
                    case 0: break;  // Default orientation, no rotation
                    case 1: poseStack.mulPose(Axis.XP.rotationDegrees(90)); break;
                    case 2: poseStack.mulPose(Axis.XP.rotationDegrees(180)); break;
                    case 3: poseStack.mulPose(Axis.XP.rotationDegrees(-90)); break;
                    case 4: poseStack.mulPose(Axis.ZP.rotationDegrees(-90)); break;
                    case 5: poseStack.mulPose(Axis.ZN.rotationDegrees(-90)); break;
                }

                poseStack.translate(0, -0.5, 0);  // Adjust for quad position
                poseStack.scale(0.5f, 0.5f, 0.5f);

                // Get the transformation matrix for the current face
                Matrix4f matrix4f = poseStack.last().pose();

                // Add the 4 vertices for this quad to the buffer
                bufferBuilder.vertex(matrix4f, -1, 0, -1).color(255, 255, 255, 100).uv(0, 0).uv2(0, 0).normal(0, 0, 0).endVertex();
                bufferBuilder.vertex(matrix4f, 1, 0, -1).color(255, 255, 255, 100).uv(1, 0).uv2(1, 0).normal(0, 0, 0).endVertex();
                bufferBuilder.vertex(matrix4f, 1, 0, 1).color(255, 255, 255, 100).uv(1, 1).uv2(1, 1).normal(0, 0, 0).endVertex();
                bufferBuilder.vertex(matrix4f, -1, 0, 1).color(255, 255, 255, 100).uv(0, 1).uv2(0, 1).normal(0, 0, 0).endVertex();

                poseStack.popPose();  // Restore the original pose
            }
        }

        // After all quads are added to the buffer, draw them in a single call
        tesselator.end();

        // Disable unnecessary OpenGL states after the batch is rendered
        RenderSystem.disableBlend();
        //GL44.glDisable(GL44.GL_STENCIL_TEST);
        positions.clear();
    }

}
