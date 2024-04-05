package com.noodlegamer76.fracture.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider  {
    //public static final TagKey<Block> VOID_REPLACEABLE = BlockTags.create(new ResourceLocation(FractureMod.MODID, "void_replaceable"));
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {


        // How to add a custom tag, use BlockTags for normal tags
        //this.tag(VOID_REPLACEABLE)
        //        .addTags(
        //                BlockTags.MINEABLE_WITH_AXE,
        //                BlockTags.MINEABLE_WITH_HOE,
        //                BlockTags.MINEABLE_WITH_PICKAXE,
        //                BlockTags.MINEABLE_WITH_SHOVEL
        //        );
    }
}
