package com.noodlegamer76.fracture.client.renderers.entity.block;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import com.noodlegamer76.fracture.entity.block.FrostedIceCrystalsEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FrostedIceCrystalsRenderer extends GeoBlockRenderer<FrostedIceCrystalsEntity> {
    public FrostedIceCrystalsRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(FractureMod.MODID, "inverted_cube")));
    }

    @Override
    public RenderType getRenderType(FrostedIceCrystalsEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return ModRenderTypes.FROSTED_GLASS;
    }
}
