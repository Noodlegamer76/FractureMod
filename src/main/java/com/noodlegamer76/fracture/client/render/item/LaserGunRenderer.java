package com.noodlegamer76.fracture.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.monster.boss.FleshObelisk;
import com.noodlegamer76.fracture.item.LaserGunItem;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.item.SkyblockHolderBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class LaserGunRenderer extends GeoItemRenderer<LaserGunItem> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "textures/item/laser_gun.png");

    public LaserGunRenderer() {
        super(
                new DefaultedItemGeoModel<>(
                        ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "laser_gun")
                )
        );
    }

    @Override
    public void actuallyRender(PoseStack poseStack, LaserGunItem animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        CompoundTag tag = stack.getOrCreateTag();
        poseStack.pushPose();
        if (tag.contains("laserData") && (transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)) {
            poseStack.translate(0.0f, -0.6f, -0.75f);
        }

        super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);

        poseStack.popPose();
    }

    @Override
    public void renderRecursively(
            PoseStack poseStack,
            LaserGunItem animatable,
            GeoBone bone,
            RenderType renderType,
            MultiBufferSource bufferSource,
            VertexConsumer buffer,
            boolean isReRender,
            float partialTick,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        if (bone.getName().equals("bone9")) {
            CompoundTag tag = getCurrentItemStack().getTag();

            float t = 0.0f;

            if (tag != null && tag.contains("laserData")) {
                CompoundTag laserData = tag.getCompound("laserData");

                int totalTime = laserData.getInt("time");
                int chargeTime = FleshObelisk.LASER_BEAM_CHARGE_UP + FleshObelisk.LASER_BEAM_FREEZE_TIME;

                LivingEntity player = Minecraft.getInstance().player;

                if (player != null && player.isUsingItem()) {
                    int remaining = player.getUseItemRemainingTicks();
                    int totalUse = getCurrentItemStack().getUseDuration();

                    int elapsed = totalUse - remaining;

                    t = Mth.clamp(elapsed / (float) chargeTime, 0.0f, 1.0f);
                }
            }

            red = Mth.lerp(t, 1.0f, 0.6f);
            green = Mth.lerp(t, 1.0f, 0.0f);
            blue = Mth.lerp(t, 1.0f, 1.0f);
        }

        super.renderRecursively(
                poseStack,
                animatable,
                bone,
                renderType,
                bufferSource,
                buffer,
                isReRender,
                partialTick,
                packedLight,
                packedOverlay,
                red,
                green,
                blue,
                alpha
        );
    }

    @Override
    public ResourceLocation getTextureLocation(LaserGunItem animatable) {
        return TEXTURE;
    }

    @Override
    public RenderType getRenderType(LaserGunItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
