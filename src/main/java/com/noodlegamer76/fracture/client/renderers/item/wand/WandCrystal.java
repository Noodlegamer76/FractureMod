package com.noodlegamer76.fracture.client.renderers.item.wand;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import com.noodlegamer76.fracture.spellcrafting.Wand;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.renderer.layer.BoneFilterGeoLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class WandCrystal extends AutoGlowingGeoLayer<Wand> {
    public WandCrystal(GeoRenderer<Wand> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    protected RenderType getRenderType(Wand animatable) {
        return ModRenderTypes.SKYBOX;
    }

    @Override
    public GeoModel<Wand> getGeoModel() {
        return new DefaultedItemGeoModel<>(new ResourceLocation(FractureMod.MODID, "wand_crystal"));
    }

    @Override
    protected ResourceLocation getTextureResource(Wand animatable) {
        return null;
    }
}
