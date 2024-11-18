package com.noodlegamer76.fracture.gui;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.gui.modificationstation.ModificationStationMenu;
import com.noodlegamer76.fracture.gui.skyboxgenerator.SkyboxGeneratorMenu;
import com.noodlegamer76.fracture.gui.wand.WandMenu;
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

    public static final RegistryObject<MenuType<WandMenu>> WAND_MENU =
            MENU_TYPES.register("wand_menu", () -> IForgeMenuType.create(WandMenu::new));

    public static final RegistryObject<MenuType<ModificationStationMenu>> MODIFICATION_STATION_MENU =
            MENU_TYPES.register("modification_station", () -> IForgeMenuType.create(ModificationStationMenu::new));
}
