package com.noodlegamer76.fracture;

import com.mojang.logging.LogUtils;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.creativetabs.FractureTab;
import com.noodlegamer76.fracture.creativetabs.InitCreativeTabs;
import com.noodlegamer76.fracture.datagen.DataGenerators;
import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.ai.memory.InitMemoryModuleTypes;
import com.noodlegamer76.fracture.entity.block.InitBlockEntities;
import com.noodlegamer76.fracture.fluid.InitFluidTypes;
import com.noodlegamer76.fracture.fluid.InitFluids;
import com.noodlegamer76.fracture.gui.InitMenus;
import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.particles.InitParticles;
import com.noodlegamer76.fracture.worldgen.features.InitFeatures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(FractureMod.MODID)
public class FractureMod {
    public static final String MODID = "fracture";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FractureMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GeckoLib.initialize();

        InitEntities.ENTITIES.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitBlocks.BLOCKS.register(modEventBus);
        InitParticles.PARTICLE_TYPES.register(modEventBus);
        InitFluids.FLUIDS.register(modEventBus);
        InitFluidTypes.FLUID_TYPES.register(modEventBus);
        InitBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        InitMenus.MENU_TYPES.register(modEventBus);
        InitFeatures.FEATURES.register(modEventBus);
        InitMemoryModuleTypes.MEMORY_MODULE_TYPES.register(modEventBus);


        InitCreativeTabs.CREATIVE_TABS.register(modEventBus);
        modEventBus.register(new FractureTab());

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        DataGenerators.registerBaseBlocksWithItems();
    }
}
