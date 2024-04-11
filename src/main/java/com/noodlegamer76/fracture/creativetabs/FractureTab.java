package com.noodlegamer76.fracture.creativetabs;

import com.noodlegamer76.fracture.item.InitItems;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FractureTab {
    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == InitCreativeTabs.FRACTURE_TAB.getKey()) {

            //example of how to add item to creative tab
            event.accept(InitItems.FLESH_BLOCK);
            event.accept(InitItems.FLESH_STAIRS);
            event.accept(InitItems.FLESH_SLAB);
            event.accept(InitItems.FLESH_WALL);
            event.accept(InitItems.FLESHY_STONE_BRICKS);
            event.accept(InitItems.FLESHY_STONE_BRICK_STAIRS);
            event.accept(InitItems.FLESHY_STONE_BRICK_SLAB);
            event.accept(InitItems.FLESHY_STONE_BRICK_WALL);
            event.accept(InitItems.FLESH_GROWTH);
            event.accept(InitItems.TENDRILS);
            event.accept(InitItems.LARGE_FLESH_BULB);
            event.accept(InitItems.BLOODY_BOOKSHELF);
            event.accept(InitItems.SMALL_FLESH_BULB);
            event.accept(InitItems.INTESTINE);
            event.accept(InitItems.HANGING_FLESH);
            event.accept(InitItems.BLOOD_BUCKET);
        }
    }
}
