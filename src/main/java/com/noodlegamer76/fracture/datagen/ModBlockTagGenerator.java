package com.noodlegamer76.fracture.datagen;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.util.registryutils.BlockSet;
import com.noodlegamer76.fracture.util.registryutils.SimpleStoneSet;
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

        for (BlockSet blockSet: DataGenerators.BLOCKS) {
            if (blockSet instanceof SimpleStoneSet set) {
                this.tag(BlockTags.STAIRS)
                        .add(set.getStairs());
                this.tag(BlockTags.SLABS)
                        .add(set.getSlab());
                this.tag(BlockTags.WALLS)
                        .add(set.getWall());
            }
        }

        // How to add a custom tag, use BlockTags for normal tags
        this.tag(BlockTags.STAIRS)
                .add(
                        InitBlocks.FLESH_STAIRS.get(),
                        InitBlocks.FLESHY_DARKSTONE_BRICK_STAIRS.get(),
                        InitBlocks.DARKSTONE_BRICK_STAIRS.get(),
                        InitBlocks.DARKSTONE_STAIRS.get(),
                        InitBlocks.INKWOOD_STAIRS_BLOCK.get()
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
                        InitBlocks.DARKSTONE_SLAB.get(),
                        InitBlocks.INKWOOD_SLAB_BLOCK.get()
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
                        InitBlocks.FLESHY_DARKSTONE_BRICK_STAIRS.get(),
                        InitBlocks.SKYBOX_GENERATOR.get(),
                        InitBlocks.SMOKE_STACK.get(),
                        InitBlocks.VOID_BLOCK.get(),
                        InitBlocks.ICE_CRYSTAL_BLOCK.get(),
                        InitBlocks.RADIANT_ICE.get(),
                        InitBlocks.PRISON_BARS.get(),
                        InitBlocks.RUSTY_IRON_BARS.get(),
                        InitBlocks.METAL_GRATE.getBlock(),
                        InitBlocks.METAL_SHEET.getBlock(),
                        InitBlocks.METAL_SHEET.getStairs(),
                        InitBlocks.METAL_SHEET.getSlab(),
                        InitBlocks.METAL_SHEET.getWall(),
                        InitBlocks.SLAG.getBlock(),
                        InitBlocks.SLAG.getStairs(),
                        InitBlocks.SLAG.getSlab(),
                        InitBlocks.SLAG.getWall(),
                        InitBlocks.PLATED_BRICKS.getBlock(),
                        InitBlocks.PLATED_BRICKS.getStairs(),
                        InitBlocks.PLATED_BRICKS.getSlab(),
                        InitBlocks.PLATED_BRICKS.getWall(),
                        InitBlocks.LIGHTLY_CRACKED_PLATED_BRICKS.getBlock(),
                        InitBlocks.LIGHTLY_CRACKED_PLATED_BRICKS.getStairs(),
                        InitBlocks.LIGHTLY_CRACKED_PLATED_BRICKS.getSlab(),
                        InitBlocks.LIGHTLY_CRACKED_PLATED_BRICKS.getWall(),
                        InitBlocks.HEAVILY_CRACKED_PLATED_BRICKS.getBlock(),
                        InitBlocks.HEAVILY_CRACKED_PLATED_BRICKS.getStairs(),
                        InitBlocks.HEAVILY_CRACKED_PLATED_BRICKS.getSlab(),
                        InitBlocks.HEAVILY_CRACKED_PLATED_BRICKS.getWall()
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
                        InitBlocks.BLOODY_BOOKSHELF.get(),
                        InitBlocks.INKWOOD_LOG_BLOCK.get(),
                        InitBlocks.INKWOOD_STRIPPED_LOG_BLOCK.get(),
                        InitBlocks.INKWOOD_WOOD_BLOCK.get(),
                        InitBlocks.INKWOOD_STRIPPED_WOOD_BLOCK.get(),
                        InitBlocks.INKWOOD_PLANKS_BLOCK.get(),
                        InitBlocks.INKWOOD_STAIRS_BLOCK.get(),
                        InitBlocks.INKWOOD_SLAB_BLOCK.get(),
                        InitBlocks.INKWOOD_FENCE_BLOCK.get(),
                        InitBlocks.INKWOOD_FENCE_GATE_BLOCK.get(),
                        InitBlocks.INKWOOD_DOOR_BLOCK.get(),
                        InitBlocks.INKWOOD_TRAPDOOR_BLOCK.get(),
                        InitBlocks.INKWOOD_BUTTON_BLOCK.get(),
                        InitBlocks.INKWOOD_STANDING_SIGN_BLOCK.get(),
                        InitBlocks.INKWOOD_WALL_SIGN_BLOCK.get(),
                        InitBlocks.INKWOOD_WALL_HANGING_SIGN_BLOCK.get(),
                        InitBlocks.INKWOOD_CEILING_HANGING_SIGN_BLOCK.get()
                );

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(
                        InitBlocks.PERMAFROST.get(),
                        InitBlocks.SNOW_BRICK.getBlock(),
                        InitBlocks.SNOW_BRICK.getStairs(),
                        InitBlocks.SNOW_BRICK.getSlab(),
                        InitBlocks.SNOW_BRICK.getWall()
                );

        this.tag(Tags.Blocks.STONE)
                .add(
                        InitBlocks.DARKSTONE.get()
                );

        this.tag(Tags.Blocks.FENCES)
                .add(
                        InitBlocks.INKWOOD_FENCE_BLOCK.get()
                );

        this.tag(Tags.Blocks.FENCES_WOODEN)
                .add(
                        InitBlocks.INKWOOD_FENCE_BLOCK.get()
                );

        this.tag(Tags.Blocks.FENCE_GATES)
                .add(
                        InitBlocks.INKWOOD_FENCE_GATE_BLOCK.get()
                );

        this.tag(Tags.Blocks.FENCE_GATES_WOODEN)
                .add(
                        InitBlocks.INKWOOD_FENCE_GATE_BLOCK.get()
                );

        this.tag(BlockTags.FENCES)
                .add(
                        InitBlocks.INKWOOD_FENCE_BLOCK.get()
                );

        this.tag(BlockTags.WOODEN_FENCES)
                .add(
                        InitBlocks.INKWOOD_FENCE_BLOCK.get()
                );

        this.tag(BlockTags.FENCE_GATES)
                .add(
                        InitBlocks.INKWOOD_FENCE_GATE_BLOCK.get()
                );

        this.tag(BlockTags.LOGS)
                .add(
                        InitBlocks.INKWOOD_LOG_BLOCK.get(),
                        InitBlocks.INKWOOD_STRIPPED_LOG_BLOCK.get(),
                        InitBlocks.INKWOOD_WOOD_BLOCK.get(),
                        InitBlocks.INKWOOD_STRIPPED_WOOD_BLOCK.get()
                );

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(
                        InitBlocks.INKWOOD_LOG_BLOCK.get(),
                        InitBlocks.INKWOOD_STRIPPED_LOG_BLOCK.get(),
                        InitBlocks.INKWOOD_WOOD_BLOCK.get(),
                        InitBlocks.INKWOOD_STRIPPED_WOOD_BLOCK.get()
                );

        this.tag(BlockTags.PLANKS)
                .add(
                        InitBlocks.INKWOOD_PLANKS_BLOCK.get()
                );

        this.tag(BlockTags.WOODEN_DOORS)
                .add(
                        InitBlocks.INKWOOD_DOOR_BLOCK .get()
                );

        this.tag(BlockTags.DOORS)
                .add(
                        InitBlocks.INKWOOD_DOOR_BLOCK.get()
                );

        this.tag(BlockTags.BUTTONS)
                .add(
                        InitBlocks.INKWOOD_BUTTON_BLOCK.get()
                );

        this.tag(BlockTags.WOODEN_BUTTONS)
                .add(
                        InitBlocks.INKWOOD_BUTTON_BLOCK.get()
                );
        this.tag(BlockTags.PRESSURE_PLATES)
                .add(
                        InitBlocks.INKWOOD_PRESSURE_PLATE_BLOCK.get()
                );

        this.tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(
                        InitBlocks.INKWOOD_PRESSURE_PLATE_BLOCK.get()
                );

        this.tag(Tags.Blocks.BOOKSHELVES)
                .add(
                        InitBlocks.BLOODY_BOOKSHELF.get(),
                        InitBlocks.INKWOOD_BOOKSHELF.get()
                );

        this.tag(BlockTags.SIGNS)
                .add(
                        InitBlocks.INKWOOD_CEILING_HANGING_SIGN_BLOCK.get(),
                        InitBlocks.INKWOOD_WALL_HANGING_SIGN_BLOCK.get(),
                        InitBlocks.INKWOOD_WALL_SIGN_BLOCK.get(),
                        InitBlocks.INKWOOD_STANDING_SIGN_BLOCK.get()
                );

        this.tag(BlockTags.ALL_SIGNS)
                .add(
                        InitBlocks.INKWOOD_CEILING_HANGING_SIGN_BLOCK.get(),
                        InitBlocks.INKWOOD_WALL_HANGING_SIGN_BLOCK.get(),
                        InitBlocks.INKWOOD_WALL_SIGN_BLOCK.get(),
                        InitBlocks.INKWOOD_STANDING_SIGN_BLOCK.get()
                );

        this.tag(BlockTags.ALL_HANGING_SIGNS)
                .add(
                        InitBlocks.INKWOOD_WALL_HANGING_SIGN_BLOCK.get()
                );

        this.tag(BlockTags.CEILING_HANGING_SIGNS)
                .add(
                        InitBlocks.INKWOOD_CEILING_HANGING_SIGN_BLOCK.get()
                );

        this.tag(BlockTags.WALL_SIGNS)
                .add(
                        InitBlocks.INKWOOD_WALL_SIGN_BLOCK.get()
                );

        this.tag(BlockTags.STANDING_SIGNS)
                .add(
                        InitBlocks.INKWOOD_STANDING_SIGN_BLOCK.get()
                );

        this.tag(BlockTags.SAPLINGS)
                .add(
                        InitBlocks.INKWOOD_SAPLING.get()
                );

        this.tag(BlockTags.LEAVES)
                .add(
                        InitBlocks.INWKOOD_LEAVES.get()
                );

        this.tag(BlockTags.DIRT)
                .add(
                        InitBlocks.PERMAFROST.get()
                );
    }
}
