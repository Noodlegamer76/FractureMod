package com.noodlegamer76.fracture.client.renderers.entity.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class EntityDebugRenderers {

    public static void renderHeadLine(LivingEntity entity, float partialTicks, MultiBufferSource bufferSource, PoseStack poseStack, float length) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.LINES);

        Vec3 startPos = entity.getEyePosition(partialTicks).subtract(entity.getPosition(partialTicks));
        Vec3 endPos = entity.getLookAngle().scale(length);

        poseStack.pushPose();
        PoseStack.Pose pose = poseStack.last();

        vertexConsumer.vertex(pose.pose(), (float) startPos.x, (float) startPos.y, (float) startPos.z)
                .color(255, 0, 0, 255).normal(0, 1, 0).endVertex();
        vertexConsumer.vertex(pose.pose(), (float) endPos.x, (float) endPos.y, (float) endPos.z)
                .color(255, 0, 0, 255).normal(0, 1, 0).endVertex();

        poseStack.popPose();
    }
}
