package com.noodlegamer76.fracture.creativetabs;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FractureMod.MODID);

    //if you want to add an item to a creative tab, check out FractureTab
    public static RegistryObject<CreativeModeTab> FRACTURE_TAB = CREATIVE_TABS.register("fracture_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("fracture.creative_tab"))
            .icon(() -> new ItemStack(InitItems.FLESH_BLOCK.get()))
            .build());
}
