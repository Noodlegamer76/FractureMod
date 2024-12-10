package com.noodlegamer76.fracture.client.renderers.item.wand;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.spellcrafting.wand.FrozenSpellBook;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FrozenSpellbookRenderer extends GeoItemRenderer<FrozenSpellBook> {
    public FrozenSpellbookRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(FractureMod.MODID, "frozen_spellbook")));
    }
}
