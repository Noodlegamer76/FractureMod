package com.noodlegamer76.fracture.datagen;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider  {
    public static final TagKey<Block> FLESH_SOLID = BlockTags.create(new ResourceLocation(FractureMod.MODID, "flesh_solid"));
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {


        // How to add a custom tag, use BlockTags for normal tags
        this.tag(BlockTags.STAIRS)
                .add(
                        InitBlocks.FLESH_STAIRS.get(),
                        InitBlocks.FLESHY_DARKSTONE_BRICK_STAIRS.get(),
                        InitBlocks.DARKSTONE_BRICK_STAIRS.get(),
                        InitBlocks.DARKSTONE_STAIRS.get()
                );

        this.tag(BlockTags.WALLS)
                .add(
                        InitBlocks.FLESH_WALL.get(),
                        InitBlocks.FLESHY_DARKSTONE_BRICK_WALL.get(),
                        InitBlocks.DARKSTONE_BRICK_WALL.get(),
                        InitBlocks.DARKSTONE_WALL.get()
                );

        this.tag(BlockTags.SLABS)
                .add(
                        InitBlocks.FLESH_SLAB.get(),
                        InitBlocks.FLESHY_DARKSTONE_BRICK_SLAB.get(),
                        InitBlocks.DARKSTONE_BRICK_SLAB.get(),
                        InitBlocks.DARKSTONE_SLAB.get()
                );

        this.tag(FLESH_SOLID)
                .add(
                        InitBlocks.FLESH_BLOCK.get(),
                        InitBlocks.FLESHY_DARKSTONE_BRICKS.get(),
                        InitBlocks.LARGE_FLESH_BULB.get(),
                        InitBlocks.BLOODY_BOOKSHELF.get()
                );

        this.tag(BlockTags.STONE_BRICKS)
                .add(
                        InitBlocks.CHISELED_DARKSTONE_BRICKS.get(),
                        InitBlocks.FLESHY_CHISELED_DARKSTONE_BRICKS.get(),
                        InitBlocks.CRACKED_DARKSTONE_BRICKS.get(),
                        InitBlocks.DARKSTONE_BRICKS.get(),
                        InitBlocks.FLESHY_DARKSTONE_BRICKS.get()
                );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        InitBlocks.DARKSTONE.get(),
                        InitBlocks.DARKSTONE_PILLAR.get(),
                        InitBlocks.DARKSTONE_BRICKS.get(),
                        InitBlocks.DARKSTONE_SLAB.get(),
                        InitBlocks.DARKSTONE_STAIRS.get(),
                        InitBlocks.DARKSTONE_WALL.get(),
                        InitBlocks.DARKSTONE_BRICK_SLAB.get(),
                        InitBlocks.DARKSTONE_BRICK_STAIRS.get(),
                        InitBlocks.DARKSTONE_BRICK_WALL.get(),
                        InitBlocks.CHISELED_DARKSTONE_BRICKS.get(),
                        InitBlocks.FLESHY_CHISELED_DARKSTONE_BRICKS.get(),
                        InitBlocks.FLESHY_DARKSTONE_PILLAR.get(),
                        InitBlocks.FLESHY_DARKSTONE_BRICKS.get(),
                        InitBlocks.FLESHY_DARKSTONE_BRICK_SLAB.get(),
                        InitBlocks.FLESHY_DARKSTONE_BRICK_WALL.get(),
                        InitBlocks.FLESHY_DARKSTONE_BRICK_STAIRS.get()
                        );

        this.tag(BlockTags.MINEABLE_WITH_HOE)
                .add(
                        InitBlocks.FLESH_BLOCK.get(),
                        InitBlocks.FLESH_SLAB.get(),
                        InitBlocks.FLESH_WALL.get(),
                        InitBlocks.FLESH_STAIRS.get(),
                        InitBlocks.FLESH_SPRAYER.get(),
                        InitBlocks.LARGE_FLESH_BULB.get(),
                        InitBlocks.SMALL_FLESH_BULB.get()
                );

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(
                        InitBlocks.BLOODY_BOOKSHELF.get()
                );

        this.tag(Tags.Blocks.STONE)
                .add(
                        InitBlocks.DARKSTONE.get()
                );
    }
}
