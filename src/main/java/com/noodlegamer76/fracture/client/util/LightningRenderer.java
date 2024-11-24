package com.noodlegamer76.fracture.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;

public class LightningRenderer {
    public static ArrayList<BlockPos> positions = new ArrayList<>();

    public static void renderLightning() {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        for (BlockPos pos: positions) {
            PoseStack poseStack = new PoseStack();

            RenderSystem.setShader(GameRenderer::getPositionColorShader);

            renderSegment(pos.getCenter().toVector3f(), pos.above().above().getCenter().toVector3f(), 0.2f, builder, poseStack);
        }
        tesselator.end();
        positions.clear();
    }

    public static void renderSegment(Vector3f first, Vector3f last, float width, BufferBuilder builder, PoseStack poseStack) {

        for (int i = 0; i < 4; i++) {
            poseStack.pushPose();
            Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
            poseStack.translate(first.x, first.y(), first.z());

            Matrix4f matrix4f = poseStack.last().pose();

            switch (i) {
                case 0: break;
                case 1: poseStack.mulPose(Axis.YP.rotationDegrees(90)); break;
                case 2: poseStack.mulPose(Axis.YP.rotationDegrees(180)); break;
                case 3: poseStack.mulPose(Axis.YP.rotationDegrees(-90)); break;
            }
            poseStack.translate(0, 0, -width / 2);
            builder.vertex(matrix4f, -width / 2, 0, 0.0f).color(255, 255, 255, 255).endVertex();
            builder.vertex(matrix4f, -width / 2, first.distance(last), 0.0f).color(255, 255, 255, 255).endVertex();
            builder.vertex(matrix4f, width / 2, first.distance(last), 0.0f).color(255, 255, 255, 255).endVertex();
            builder.vertex(matrix4f, width / 2, 0, 0.0f).color(255, 255, 255, 255).endVertex();

            poseStack.popPose();
        }
    }
}
