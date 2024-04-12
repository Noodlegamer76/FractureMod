package com.noodlegamer76.fracture.datagen;

import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }


    @Override
    protected void generate() {

        //some examples for blocks that don't seem to work by normally using the methods
        //all blocks need to be set here due to how ModLootTableProvider is set up

        dropSelf(InitBlocks.FLESH_BLOCK.get());
        dropSelf(InitBlocks.FLESH_STAIRS.get());
        dropSelf(InitBlocks.FLESH_WALL.get());
        this.add(InitBlocks.FLESH_SLAB.get(),
                block -> createSlabItemTable(InitBlocks.FLESH_SLAB.get()));

        dropSelf(InitBlocks.FLESHY_DARKSTONE_BRICKS.get());
        dropSelf(InitBlocks.FLESHY_DARKSTONE_BRICK_STAIRS.get());
        dropSelf(InitBlocks.FLESHY_DARKSTONE_BRICK_WALL.get());
        this.add(InitBlocks.FLESHY_DARKSTONE_BRICK_SLAB.get(),
                block -> createSlabItemTable(InitBlocks.FLESHY_DARKSTONE_BRICK_SLAB.get()));

        dropSelf(InitBlocks.DARKSTONE_BRICKS.get());
        dropSelf(InitBlocks.DARKSTONE_BRICK_STAIRS.get());
        dropSelf(InitBlocks.DARKSTONE_BRICK_WALL.get());
        this.add(InitBlocks.DARKSTONE_BRICK_SLAB.get(),
                block -> createSlabItemTable(InitBlocks.DARKSTONE_BRICK_SLAB.get()));

        dropSelf(InitBlocks.DARKSTONE.get());
        dropSelf(InitBlocks.DARKSTONE_STAIRS.get());
        dropSelf(InitBlocks.DARKSTONE_WALL.get());
        this.add(InitBlocks.DARKSTONE_SLAB.get(),
                block -> createSlabItemTable(InitBlocks.DARKSTONE_SLAB.get()));

        dropSelf(InitBlocks.CRACKED_DARKSTONE_BRICKS.get());
        dropSelf(InitBlocks.CRACKED_DARKSTONE_BRICK_STAIRS.get());
        dropSelf(InitBlocks.CRACKED_DARKSTONE_BRICK_WALL.get());
        this.add(InitBlocks.CRACKED_DARKSTONE_BRICK_SLAB.get(),
                block -> createSlabItemTable(InitBlocks.CRACKED_DARKSTONE_BRICK_SLAB.get()));

        this.add(InitBlocks.FLESH_GROWTH.get(),
                block -> createShearsOnlyDrop(InitItems.FLESH_GROWTH.get()));
        this.add(InitBlocks.TENDRILS.get(),
                block -> createShearsOnlyDrop(InitItems.TENDRILS.get()));
        this.add(InitBlocks.HANGING_FLESH_PLANT.get(),
                block -> createShearsOnlyDrop(InitItems.TENDRILS.get()));
        this.add(InitBlocks.HANGING_FLESH.get(),
                block -> createShearsOnlyDrop(InitItems.TENDRILS.get()));

        dropSelf(InitBlocks.LARGE_FLESH_BULB.get());
        dropSelf(InitBlocks.SMALL_FLESH_BULB.get());
        dropSelf(InitBlocks.BLOODY_BOOKSHELF.get());
        dropSelf(InitBlocks.INTESTINE.get());
        dropSelf(InitBlocks.CHISELED_DARKSTONE_BRICKS.get());
        dropSelf(InitBlocks.DARKSTONE_PILLAR.get());

        dropOther(InitBlocks.FLESHY_GROUP_SPAWNER.get(), Blocks.AIR);

        dropOther(InitBlocks.BLOOD_BLOCK.get(), Blocks.BIRCH_DOOR);

        //this.add(InitBlocks.WHITE_COSMIC_LEAVES.get(), (block) ->
        //        createLeavesDrops(block, InitBlocks.WHITE_COSMIC_LEAVES.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        //dropSelf(BlockInit.CRYSTALLIZED_DIRT.get());

        //this.add(BlockInit.RAINBOW_SLAB.get(),
        //        block -> createSlabItemTable(BlockInit.RAINBOW_SLAB.get()));
        //this.add(BlockInit.RAINBOW_DOOR.get(),
        //        block -> createDoorTable(BlockInit.RAINBOW_DOOR.get()));

        //this.add(BlockInit.RAINBOW_LEAVES.get(), (block) ->
        //        createLeavesDrops(block, BlockInit.RAINBOW_LEAVES.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }



    @Override
    protected Iterable<Block> getKnownBlocks() {
        return InitBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
