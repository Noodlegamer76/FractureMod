package com.noodlegamer76.fracture.client.renderers.item;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.item.SkyboxGeneratorItem;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SkyboxGeneratorItemRenderer extends GeoItemRenderer<SkyboxGeneratorItem> {
    public SkyboxGeneratorItemRenderer() {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(FractureMod.MODID, "skybox_generator")));
    }
}
