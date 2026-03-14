package com.noodlegamer76.fracture.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.monster.boss.FleshObelisk;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FleshObeliskRenderer extends GeoEntityRenderer<FleshObelisk> {

    public FleshObeliskRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "flesh_obelisk"), false));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FleshObelisk entity) {
        return ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "textures/entity/flesh_obelisk/flesh_obelisk.png");
    }

    @Override
    public void actuallyRender(PoseStack poseStack,
                               FleshObelisk animatable,
                               BakedGeoModel model,
                               RenderType renderType,
                               MultiBufferSource bufferSource,
                               VertexConsumer buffer,
                               boolean isReRender,
                               float partialTick,
                               int packedLight,
                               int packedOverlay,
                               float red, float green, float blue, float alpha) {

        int clientTick = animatable.getClientSideAttackTime();
        int attack = animatable.getAttack();

        poseStack.pushPose();
        if (attack == 1) {
            int chargeUpEnd = FleshObelisk.LASER_BEAM_FREEZE_TIME + FleshObelisk.LASER_BEAM_CHARGE_UP;
            int fireEnd = chargeUpEnd + FleshObelisk.LASER_BEAM_FIRE_TIME;


            if (clientTick >= 0 && clientTick < fireEnd) {
                Vector3f randomOffset = new Vector3f((float) Math.random() - 0.5f, (float) Math.random() - 0.5f, (float) Math.random() - 0.5f);
                if (clientTick < chargeUpEnd) {
                    randomOffset.mul(0.05f);
                }
                else {
                    randomOffset.mul(0.1f);
                }
                poseStack.translate(randomOffset.x, randomOffset.y, randomOffset.z);
            }

        }
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}
