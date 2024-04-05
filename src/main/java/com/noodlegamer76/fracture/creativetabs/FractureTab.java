package com.noodlegamer76.fracture.creativetabs;

import com.noodlegamer76.fracture.item.InitItems;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FractureTab {
    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == InitCreativeTabs.FRACTURE_TAB.getKey()) {

            //example of how to add item to creative tab
            //event.accept(InitItems.TEST_ITEM);
        }
    }
}
