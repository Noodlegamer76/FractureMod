package com.noodlegamer76.fracture.gui;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitMenus {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, FractureMod.MODID);

   //public static final RegistryObject<MenuType<GuiBuilderMenu>> GUI_BUILDER =
   //        MENU_TYPES.register("gui_builder", () -> IForgeMenuType.create(GuiBuilderMenu::new));
}
