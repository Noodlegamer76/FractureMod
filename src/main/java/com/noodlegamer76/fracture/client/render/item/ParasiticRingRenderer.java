package com.noodlegamer76.fracture.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.model.SimpleRingModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class ParasiticRingRenderer implements ICurioRenderer {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "textures/block/flesh_block.png");
    private final SimpleRingModel<LivingEntity> model;

    public ParasiticRingRenderer() {
        this.model = new SimpleRingModel<>(
                Minecraft.getInstance()
                        .getEntityModels()
                        .bakeLayer(SimpleRingModel.LAYER_LOCATION)
        );
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            RenderLayerParent<T, M> parent,
            MultiBufferSource buffer,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        poseStack.pushPose();

        if (parent.getModel() instanceof HumanoidModel<?> humanoidModel) {

            ICurioRenderer.translateIfSneaking(poseStack, slotContext.entity());

            if (slotContext.index() == 0) {
                humanoidModel.rightArm.translateAndRotate(poseStack);
            }
            else {
                humanoidModel.leftArm.translateAndRotate(poseStack);
            }

            poseStack.scale(0.8f, 0.8f, 0.8f);
            poseStack.translate(0.0D, -1.05D, 0D);

            VertexConsumer vertexConsumer =
                    ItemRenderer.getArmorFoilBuffer(
                            buffer,
                            RenderType.entityCutoutNoCull(getTextureLocation()),
                            false,
                            stack.hasFoil()
                    );

            model.renderToBuffer(
                    poseStack,
                    vertexConsumer,
                    light,
                    OverlayTexture.NO_OVERLAY,
                    1F, 1F, 1F, 1F
            );
        }

        poseStack.popPose();
    }

    public ResourceLocation getTextureLocation() {
        return TEXTURE;
    }
}