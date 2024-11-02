package com.noodlegamer76.fracture.gui;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.gui.skyboxgenerator.SkyboxGeneratorMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitMenus {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, FractureMod.MODID);

    public static final RegistryObject<MenuType<SkyboxGeneratorMenu>> SKYBOX_GENERATOR =
            MENU_TYPES.register("skybox_generator", () -> IForgeMenuType.create(SkyboxGeneratorMenu::new));
}
