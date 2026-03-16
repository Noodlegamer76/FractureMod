package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FractureMod.MODID)
public class ItemTooltips {

    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().is(InitItems.INFESTED_CORE.get())) {
            event.getToolTip().add(1, Component.literal("§4Enhances the lifesteal of the Parasitic Sword"));
        }
    }
}
