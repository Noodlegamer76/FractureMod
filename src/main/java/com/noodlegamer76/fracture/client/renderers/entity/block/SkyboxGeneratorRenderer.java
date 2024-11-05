package com.noodlegamer76.fracture.client.renderers.entity.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.block.SkyboxGeneratorEntity;
import com.noodlegamer76.fracture.event.RenderLevelEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.ArrayList;

public class SkyboxGeneratorRenderer extends GeoBlockRenderer<SkyboxGeneratorEntity> {

    public SkyboxGeneratorRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(FractureMod.MODID, "skybox_generator")));
    }

    @Override
    public boolean shouldRender(SkyboxGeneratorEntity pBlockEntity, Vec3 pCameraPos) {
        float renderDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();
        return Vec3.atCenterOf(pBlockEntity.getBlockPos()).closerThan(pCameraPos, renderDistance);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, SkyboxGeneratorEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        ArrayList<Integer> array = new ArrayList<>();
        array.add(animatable.skybox);
        array.add(animatable.rotationSpeed);
        array.add(animatable.transparency);
        array.add(animatable.minRenderDistance);
        array.add(animatable.maxRenderDistance);
        array.add(animatable.renderPriority);

        for(int i = 0; i < RenderLevelEvent.positions.size(); i++) {
            int current = RenderLevelEvent.positions.get(i).get(5);
            if (current <= animatable.renderPriority) {
                RenderLevelEvent.positions.add(i, array);
                return;
            }
        }
        RenderLevelEvent.positions.add(array);
    }

    @Override
    public boolean shouldRenderOffScreen(SkyboxGeneratorEntity pBlockEntity) {
        return true;
    }
}
