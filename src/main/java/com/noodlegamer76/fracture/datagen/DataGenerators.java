package com.noodlegamer76.fracture.datagen;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.util.registryutils.BlockSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    //For my registry stuff
    public static final ArrayList<BlockSet> BLOCKS = new ArrayList<>();

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModBlockTagGenerator modBlockTagGenerator = new ModBlockTagGenerator(packOutput, lookupProvider, FractureMod.MODID, existingFileHelper);


        generator.addProvider(true, new ModRecipeProvider(packOutput));
        generator.addProvider(true, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
        generator.addProvider(true, modBlockTagGenerator);
        generator.addProvider(true, new ModItemTagGenerator(packOutput, lookupProvider, modBlockTagGenerator.contentsGetter() , existingFileHelper));
        generator.addProvider(true, ModLootTableProvider.create(packOutput));
    }

    public static void registerBaseBlocksWithItems() {
        BLOCKS.add(InitBlocks.SNOW_BRICK);
        BLOCKS.add(InitBlocks.METAL_SHEET);
        BLOCKS.add(InitBlocks.SLAG);
        BLOCKS.add(InitBlocks.PLATED_BRICKS);
        BLOCKS.add(InitBlocks.LIGHTLY_CRACKED_PLATED_BRICKS);
        BLOCKS.add(InitBlocks.HEAVILY_CRACKED_PLATED_BRICKS);
    }
}
