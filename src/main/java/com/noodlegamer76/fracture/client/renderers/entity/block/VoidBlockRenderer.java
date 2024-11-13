package com.noodlegamer76.fracture.client.renderers.entity.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fracture.entity.block.VoidBlockEntity;
import com.noodlegamer76.fracture.event.RenderEventsForFbos;
import com.noodlegamer76.fracture.event.ShaderEvents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class VoidBlockRenderer<T extends VoidBlockEntity> implements BlockEntityRenderer<VoidBlockEntity> {
    public static final ResourceLocation SHADER_LOCATION = new ResourceLocation("denim", "shaders/core/skybox.json");
    public static BlockModel model;

    public VoidBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(VoidBlockEntity pBlockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        RenderEventsForFbos.positions.add(pBlockEntity.getBlockPos());
    }

    @Override
    public boolean shouldRender(VoidBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}

