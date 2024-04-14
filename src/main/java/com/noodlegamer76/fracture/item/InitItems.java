package com.noodlegamer76.fracture.item;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.fluid.InitFluids;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FractureMod.MODID);


    //test item for coding stuff
    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item",
            () -> new TestItem(new Item.Properties()));

    public static final RegistryObject<Item> BROOM = ITEMS.register("broom",
            () -> new Broom(new Item.Properties()));


    //how to add a block item
    public static final RegistryObject<Item> FLESH_BLOCK = ITEMS.register("flesh_block",
            () -> new BlockItem(InitBlocks.FLESH_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESH_STAIRS = ITEMS.register("flesh_stairs",
            () -> new BlockItem(InitBlocks.FLESH_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESH_SLAB = ITEMS.register("flesh_slab",
            () -> new BlockItem(InitBlocks.FLESH_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESH_WALL = ITEMS.register("flesh_wall",
            () -> new BlockItem(InitBlocks.FLESH_WALL.get(), new Item.Properties()));


    public static final RegistryObject<Item> DARKSTONE = ITEMS.register("darkstone",
            () -> new BlockItem(InitBlocks.DARKSTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARKSTONE_STAIRS = ITEMS.register("darkstone_stairs",
            () -> new BlockItem(InitBlocks.DARKSTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARKSTONE_SLAB = ITEMS.register("darkstone_slab",
            () -> new BlockItem(InitBlocks.DARKSTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARKSTONE_WALL = ITEMS.register("darkstone_wall",
            () -> new BlockItem(InitBlocks.DARKSTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARKSTONE_BRICKS = ITEMS.register("darkstone_bricks",
            () -> new BlockItem(InitBlocks.DARKSTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARKSTONE_BRICK_STAIRS = ITEMS.register("darkstone_brick_stairs",
            () -> new BlockItem(InitBlocks.DARKSTONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARKSTONE_BRICK_SLAB = ITEMS.register("darkstone_brick_slab",
            () -> new BlockItem(InitBlocks.DARKSTONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARKSTONE_BRICK_WALL = ITEMS.register("darkstone_brick_wall",
            () -> new BlockItem(InitBlocks.DARKSTONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRACKED_DARKSTONE_BRICKS = ITEMS.register("cracked_darkstone_bricks",
            () -> new BlockItem(InitBlocks.CRACKED_DARKSTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRACKED_DARKSTONE_BRICK_STAIRS = ITEMS.register("cracked_darkstone_brick_stairs",
            () -> new BlockItem(InitBlocks.CRACKED_DARKSTONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRACKED_DARKSTONE_BRICK_SLAB = ITEMS.register("cracked_darkstone_brick_slab",
            () -> new BlockItem(InitBlocks.CRACKED_DARKSTONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRACKED_DARKSTONE_BRICK_WALL = ITEMS.register("cracked_darkstone_brick_wall",
            () -> new BlockItem(InitBlocks.CRACKED_DARKSTONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESHY_DARKSTONE_BRICKS = ITEMS.register("fleshy_darkstone_bricks",
            () -> new BlockItem(InitBlocks.FLESHY_DARKSTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESHY_DARKSTONE_BRICK_STAIRS = ITEMS.register("fleshy_darkstone_brick_stairs",
            () -> new BlockItem(InitBlocks.FLESHY_DARKSTONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESHY_DARKSTONE_BRICK_SLAB = ITEMS.register("fleshy_darkstone_brick_slab",
            () -> new BlockItem(InitBlocks.FLESHY_DARKSTONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESHY_DARKSTONE_BRICK_WALL = ITEMS.register("fleshy_darkstone_brick_wall",
            () -> new BlockItem(InitBlocks.FLESHY_DARKSTONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_DARKSTONE_BRICKS = ITEMS.register("chiseled_darkstone_bricks",
            () -> new BlockItem(InitBlocks.CHISELED_DARKSTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARKSTONE_PILLAR = ITEMS.register("darkstone_pillar",
            () -> new BlockItem(InitBlocks.DARKSTONE_PILLAR.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESHY_CHISELED_DARKSTONE_BRICKS = ITEMS.register("fleshy_chiseled_darkstone_bricks",
            () -> new BlockItem(InitBlocks.FLESHY_CHISELED_DARKSTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLESHY_DARKSTONE_PILLAR = ITEMS.register("fleshy_darkstone_pillar",
            () -> new BlockItem(InitBlocks.FLESHY_DARKSTONE_PILLAR.get(), new Item.Properties()));


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

    public static final RegistryObject<Item> FLESHY_GROUP_SPAWNER   = ITEMS.register("fleshy_group_spawner",
            () -> new BlockItem(InitBlocks.FLESHY_GROUP_SPAWNER.get(), new Item.Properties()));


    public static final RegistryObject<Item> BLOOD_BUCKET = ITEMS.register("blood_bucket",
            () -> new BucketItem(InitFluids.SOURCE_BLOOD,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

}
