package com.noodlegamer76.fracture.item;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.fluid.InitFluids;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FractureMod.MODID);


    //how to add a block item
    public static final RegistryObject<Item> FLESH_BLOCK = ITEMS.register("flesh_block",
            () -> new BlockItem(InitBlocks.FLESH_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESH_STAIRS = ITEMS.register("flesh_stairs",
            () -> new BlockItem(InitBlocks.FLESH_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESH_SLAB = ITEMS.register("flesh_slab",
            () -> new BlockItem(InitBlocks.FLESH_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESH_WALL = ITEMS.register("flesh_wall",
            () -> new BlockItem(InitBlocks.FLESH_WALL.get(), new Item.Properties()));


    public static final RegistryObject<Item> FLESHY_STONE_BRICKS = ITEMS.register("fleshy_stone_bricks",
            () -> new BlockItem(InitBlocks.FLESHY_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESHY_STONE_BRICK_STAIRS = ITEMS.register("fleshy_stone_brick_stairs",
            () -> new BlockItem(InitBlocks.FLESHY_STONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESHY_STONE_BRICK_SLAB = ITEMS.register("fleshy_stone_brick_slab",
            () -> new BlockItem(InitBlocks.FLESHY_STONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESHY_STONE_BRICK_WALL = ITEMS.register("fleshy_stone_brick_wall",
            () -> new BlockItem(InitBlocks.FLESHY_STONE_BRICK_WALL.get(), new Item.Properties()));


    public static final RegistryObject<Item> FLESH_GROWTH = ITEMS.register("flesh_growth",
            () -> new BlockItem(InitBlocks.FLESH_GROWTH.get(), new Item.Properties()));
    public static final RegistryObject<Item> TENDRILS = ITEMS.register("tendrils",
            () -> new BlockItem(InitBlocks.TENDRILS.get(), new Item.Properties()));


    public static final RegistryObject<Item> LARGE_FLESH_BULB = ITEMS.register("large_flesh_bulb",
            () -> new BlockItem(InitBlocks.LARGE_FLESH_BULB.get(), new Item.Properties()));
    public static final RegistryObject<Item> SMALL_FLESH_BULB = ITEMS.register("small_flesh_bulb",
            () -> new BlockItem(InitBlocks.SMALL_FLESH_BULB.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLOODY_BOOKSHELF = ITEMS.register("bloody_bookshelf",
            () -> new BlockItem(InitBlocks.BLOODY_BOOKSHELF.get(), new Item.Properties()));
    public static final RegistryObject<Item> INTESTINE = ITEMS.register("intestine",
            () -> new BlockItem(InitBlocks.INTESTINE.get(), new Item.Properties()));
    public static final RegistryObject<Item> HANGING_FLESH   = ITEMS.register("hanging_flesh",
            () -> new BlockItem(InitBlocks.HANGING_FLESH.get(), new Item.Properties()));


    public static final RegistryObject<Item> BLOOD_BUCKET = ITEMS.register("blood_bucket",
            () -> new BucketItem(InitFluids.SOURCE_BLOOD,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

}
