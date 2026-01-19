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
        if (event.getItemStack().is(InitItems.PARASITIC_SWORD.get())) {
            event.getToolTip().add(1, Component.literal("§4Absorbs health when you hit mobs"));
        }
        else if (event.getItemStack().is(InitItems.BLOODY_SKULL.get())) {
            event.getToolTip().add(1, Component.literal("§4Enhances the lifesteal of the Parasitic Sword"));
        }
        else if (event.getItemStack().is(InitItems.PARASITIC_AXE.get()) ||
                event.getItemStack().is(InitItems.PARASITIC_PICKAXE.get()) ||
                event.getItemStack().is(InitItems.PARASITIC_SHOVEL.get())) {
            event.getToolTip().add(1, Component.literal("§4Mines quickly at the cost of your health"));
        }
        else if (event.getItemStack().is(InitItems.PERMAFROST_SWORD.get())) {
            event.getToolTip().add(1, Component.literal("§bFreezes enemies for a short amount of time"));
        }
        else if (event.getItemStack().is(InitItems.PERMAFROST_PICKAXE.get())) {
            event.getToolTip().add(1, Component.literal("§bRight click to place blue ice"));
        }
        else if (event.getItemStack().is(InitItems.PERMAFROST_AXE.get())) {
            event.getToolTip().add(1, Component.literal("§bSlows enemies for a short time"));
        }
        else if (event.getItemStack().is(InitItems.PERMAFROST_SHOVEL.get())) {
            event.getToolTip().add(1, Component.literal("§btba"));
        }
        else if (event.getItemStack().is(InitItems.PERMAFROST_HOE.get())) {
            event.getToolTip().add(1, Component.literal("§btba"));
        }

    }
}
