package com.noodlegamer76.fracture.client.renderers.entity.block;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import com.noodlegamer76.fracture.entity.block.BoreasPortalEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BoreasPortalRenderer extends GeoBlockRenderer<BoreasPortalEntity> {
    public BoreasPortalRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(FractureMod.MODID, "boreas_portal")));
    }

    @Override
    public RenderType getRenderType(BoreasPortalEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return ModRenderTypes.SKYBOX;
    }
}
