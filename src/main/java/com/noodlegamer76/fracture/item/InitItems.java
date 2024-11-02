package com.noodlegamer76.fracture.item;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.ModBoatEntity;
import com.noodlegamer76.fracture.fluid.InitFluids;
import com.noodlegamer76.fracture.item.armor.InvertedGlasses;
import com.noodlegamer76.fracture.util.ArmorTiers;
import com.noodlegamer76.fracture.util.ToolTiers;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FractureMod.MODID);


    //test item for coding stuff
    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item",
            () -> new TestItem(new Item.Properties()));

    public static final RegistryObject<Item> HANGING_CHAIN = ITEMS.register("hanging_chain",
            () -> new HangingEntityItem(InitEntities.HANGING_CHAIN.get(), new Item.Properties()));

    public static final RegistryObject<Item> PARASITIC_SWORD = ITEMS.register("parasitic_sword",
            () -> new ParasiticSword(ToolTiers.ModItemTier.BLOOD, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> PARASITIC_PICKAXE = ITEMS.register("parasitic_pickaxe",
            () -> new ParasiticPickaxe(ToolTiers.ModItemTier.BLOOD, 2, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> PARASITIC_AXE = ITEMS.register("parasitic_axe",
            () -> new ParasiticAxe(ToolTiers.ModItemTier.BLOOD, 6, -1.2F, new Item.Properties()));
    public static final RegistryObject<Item> PARASITIC_SHOVEL = ITEMS.register("parasitic_shovel",
            () -> new ParasiticShovel(ToolTiers.ModItemTier.BLOOD, 1, -2.4F, new Item.Properties()));

    public static final RegistryObject<Item> FOG_EMITTER = ITEMS.register("fog_emitter",
            () -> new BlockItem(InitBlocks.FOG_EMITTER.get(), new Item.Properties()));

    public static final RegistryObject<Item> BLOOD_SLIME_BALL = ITEMS.register("blood_slime_ball",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLESHY_BONE = ITEMS.register("fleshy_bone",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SKYBOX_GENERATOR = ITEMS.register("skybox_generator",
            () -> new BlockItem(InitBlocks.SKYBOX_GENERATOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> INKWOOD_LEAVES = ITEMS.register("inkwood_leaves",
            () -> new BlockItem(InitBlocks.INWKOOD_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_SAPLING = ITEMS.register("inkwood_sapling",
            () -> new BlockItem(InitBlocks.INKWOOD_SAPLING.get(), new Item.Properties()));

    public static final RegistryObject<Item> SMOKE_STACK = ITEMS.register("smoke_stack",
            () -> new BlockItem(InitBlocks.SMOKE_STACK.get(), new Item.Properties()));

    public static final RegistryObject<Item> COMPACT_TNT = ITEMS.register("compact_tnt",
            () -> new BlockItem(InitBlocks.COMPACT_TNT.get(), new Item.Properties()));


    public static final RegistryObject<Item> BROOM = ITEMS.register("broom",
            () -> new Broom(new Item.Properties()));
    public static final RegistryObject<Item> LIVING_FLESH = ITEMS.register("living_flesh",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationMod(6)
                            .effect(new MobEffectInstance(MobEffects.POISON, 100, 2), 1).build())));
    public static final RegistryObject<Item> ANKLE_BITER_SPAWN_EGG = ITEMS.register("ankle_biter_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.ANKLE_BITER, new Color(74, 10, 3).getRGB(), new Color(74, 3, 3).getRGB(), new Item.Properties()));
    public static final RegistryObject<Item> FLESH_WALKER_SPAWN_EGG = ITEMS.register("flesh_walker_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.FLESH_WALKER, new Color(74, 10, 3).getRGB(), new Color(207, 95, 110).getRGB(), new Item.Properties()));
    public static final RegistryObject<Item> FLESH_SLIME_SPAWN_EGG = ITEMS.register("flesh_slime_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.FLESH_SLIME, new Color(74, 10, 3).getRGB(), new Color(15, 50, 15).getRGB(), new Item.Properties()));
    public static final RegistryObject<Item> BLOOD_SLIME_SPAWN_EGG = ITEMS.register("blood_slime_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.BLOOD_SLIME, new Color(74, 10, 3).getRGB(), new Color(255, 0, 0).getRGB(), new Item.Properties()));

    public static final RegistryObject<Item> INVERTED_GLASSES = ITEMS.register("inverted_glasses",
            () -> new InvertedGlasses(ArmorTiers.TRINKETS, ArmorItem.Type.HELMET, new Item.Properties()));


    public static final RegistryObject<Item> BLOOD_SLIME_BLOCK = ITEMS.register("blood_slime_block",
            () -> new BlockItem(InitBlocks.BLOOD_SLIME_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> INKWOOD_BOOKSHELF = ITEMS.register("inkwood_bookshelf",
            () -> new BlockItem(InitBlocks.INKWOOD_BOOKSHELF.get(), new Item.Properties()));

    public static final RegistryObject<Item> FLESH_SPRAYER = ITEMS.register("flesh_sprayer",
            () -> new BlockItem(InitBlocks.FLESH_SPRAYER.get(), new Item.Properties()));

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
    public static final RegistryObject<Item> HANGING_FLESH  = ITEMS.register("hanging_flesh",
            () -> new BlockItem(InitBlocks.HANGING_FLESH.get(), new Item.Properties()));


    public static final RegistryObject<Item> BLOOD_BUCKET = ITEMS.register("blood_bucket",
            () -> new BucketItem(InitFluids.SOURCE_BLOOD,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> INKWOOD_LOG_ITEM = ITEMS.register( "inkwood_log",
            () -> new BlockItem(InitBlocks.INKWOOD_LOG_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_WOOD_ITEM = ITEMS.register("inkwood_wood",
            () -> new BlockItem(InitBlocks.INKWOOD_WOOD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_STRIPPED_LOG_ITEM = ITEMS.register("inkwood_stripped_log",
            () -> new BlockItem(InitBlocks.INKWOOD_STRIPPED_LOG_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> INKWOOD_STRIPPED_WOOD_ITEM = ITEMS.register("inkwood_stripped_wood",
            () -> new BlockItem(InitBlocks.INKWOOD_STRIPPED_WOOD_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> INKWOOD_PLANKS_ITEM = ITEMS.register("inkwood_planks",
            () -> new BlockItem(InitBlocks.INKWOOD_PLANKS_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> INKWOOD_SLAB_ITEM = ITEMS.register("inkwood_slab",
            () -> new BlockItem(InitBlocks.INKWOOD_SLAB_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_STAIRS_ITEM = ITEMS.register("inkwood_stairs",
            () -> new BlockItem(InitBlocks.INKWOOD_STAIRS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_TRAPDOOR_ITEM = ITEMS.register("inkwood_trapdoor",
            () -> new BlockItem(InitBlocks.INKWOOD_TRAPDOOR_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_DOOR_ITEM = ITEMS.register("inkwood_door",
            () -> new BlockItem(InitBlocks.INKWOOD_DOOR_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_FENCE_GATE_ITEM = ITEMS.register("inkwood_fence_gate",
            () -> new BlockItem(InitBlocks.INKWOOD_FENCE_GATE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_FENCE_ITEM = ITEMS.register("inkwood_fence",
            () -> new BlockItem(InitBlocks.INKWOOD_FENCE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_HANGING_SIGN_ITEM = ITEMS.register("inkwood_hanging_sign",
            () -> new HangingSignItem(InitBlocks.INKWOOD_CEILING_HANGING_SIGN_BLOCK.get(), InitBlocks.INKWOOD_WALL_HANGING_SIGN_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_SIGN_ITEM = ITEMS.register("inkwood_sign",
            () -> new SignItem(new Item.Properties(), InitBlocks.INKWOOD_STANDING_SIGN_BLOCK.get(), InitBlocks.INKWOOD_WALL_SIGN_BLOCK.get()));
    public static final RegistryObject<Item> INKWOOD_BUTTON_ITEM = ITEMS.register("inkwood_button",
            () -> new BlockItem(InitBlocks.INKWOOD_BUTTON_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_PRESSURE_PLATE_ITEM = ITEMS.register("inkwood_pressure_plate",
            () -> new BlockItem(InitBlocks.INKWOOD_PRESSURE_PLATE_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> INKWOOD_BOAT_ITEM = ITEMS.register("inkwood_boat",
            () -> new ModBoatItem(false, ModBoatEntity.Type.INKWOOD , new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_CHEST_BOAT_ITEM = ITEMS.register("inkwood_chest_boat",
            () -> new ModBoatItem(true, ModBoatEntity.Type.INKWOOD, new Item.Properties()));

}
