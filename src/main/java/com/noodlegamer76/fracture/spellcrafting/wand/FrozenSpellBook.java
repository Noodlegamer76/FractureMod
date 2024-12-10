package com.noodlegamer76.fracture.spellcrafting.wand;

import com.noodlegamer76.fracture.client.renderers.item.wand.FrozenSpellbookRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.function.Consumer;

public class FrozenSpellBook extends Wand implements GeoItem {
    public FrozenSpellBook(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private FrozenSpellbookRenderer renderer = null;
            // Don't instantiate until ready. This prevents race conditions breaking things
            @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new FrozenSpellbookRenderer();

                return renderer;
            }
        });
    }
}
