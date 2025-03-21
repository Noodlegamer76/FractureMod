package com.noodlegamer76.fracture.client.renderers.item.wand;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.renderers.item.DynamicGeoItemRenderer;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import com.noodlegamer76.fracture.spellcrafting.wand.CreativeWand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

import java.util.Objects;

public class CreativeWandRenderer extends DynamicGeoItemRenderer<CreativeWand> {
    public CreativeWandRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(FractureMod.MODID, "wand")));
        //addRenderLayer(new WandCrystal(this));
    }

    @Override
    public void renderRecursively(PoseStack poseStack, CreativeWand animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (Objects.equals(bone.getName(), "layer")) {
            poseStack.pushPose();
            float rotationAngle = (float) ((Minecraft.getInstance().getPartialTick() + Minecraft.getInstance().level.getGameTime()));
            rotationAngle -= (float) (animatable.spinTime) + 1;
            poseStack.translate(0, 1.0625, 0);
            //poseStack.mulPose(Axis.XP.rotationDegrees(rotationAngle * 10f));
            poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle * 7.5f));
            //poseStack.mulPose(Axis.ZP.rotationDegrees(rotationAngle * 12.5f));
            poseStack.translate(0, -1.0625, 0);
            super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
        }
        else {
            super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }


    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
    }

    @Override
    protected @Nullable RenderType getRenderTypeOverrideForBone(GeoBone bone, CreativeWand animatable, ResourceLocation texturePath, MultiBufferSource bufferSource, float partialTick) {
        if (Objects.equals(bone.getName(), "layer")) {
            return ModRenderTypes.SKYBOX;
        } else {
            return RenderType.entityCutoutNoCull(getTextureLocation(animatable));

        }
    }
}
