package com.noodlegamer76.fracture.item;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.vehicle.ModBoatEntity;
import com.noodlegamer76.fracture.fluid.InitFluids;
import com.noodlegamer76.fracture.item.modifiable.Broom;
import com.noodlegamer76.fracture.util.ArmorTiers;
import com.noodlegamer76.fracture.util.ToolTiers;
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

    public static final RegistryObject<Item> BOREAS_KEY = ITEMS.register("boreas_key",
            () -> new BoreasKey(new Item.Properties()));
    public static final RegistryObject<Item> BOREAS_PORTAL_LOCK = ITEMS.register("boreas_portal_lock",
            () -> new BlockItem(InitBlocks.BOREAS_PORTAL_LOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> BOREAS_PORTAL_FRAME = ITEMS.register("boreas_portal_frame",
            () -> new BlockItem(InitBlocks.BOREAS_PORTAL_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> PERMAFROST = ITEMS.register("permafrost",
            () -> new BlockItem(InitBlocks.PERMAFROST.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICE_CRYSTAL_BLOCK = ITEMS.register("ice_crystal_block",
            () -> new BlockItem(InitBlocks.ICE_CRYSTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> RADIANT_ICE = ITEMS.register("radiant_ice",
            () -> new BlockItem(InitBlocks.RADIANT_ICE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PRISON_BARS = ITEMS.register("prison_bars",
            () -> new BlockItem(InitBlocks.PRISON_BARS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUSTY_IRON_BARS = ITEMS.register("rusty_iron_bars",
            () -> new BlockItem(InitBlocks.RUSTY_IRON_BARS.get(), new Item.Properties()));


    public static final RegistryObject<Item> PARASITIC_SWORD = ITEMS.register("parasitic_sword",
            () -> new ParasiticSword(ToolTiers.ModItemTier.BLOOD, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> PARASITIC_PICKAXE = ITEMS.register("parasitic_pickaxe",
            () -> new ParasiticPickaxe(ToolTiers.ModItemTier.BLOOD, 2, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> PARASITIC_AXE = ITEMS.register("parasitic_axe",
            () -> new ParasiticAxe(ToolTiers.ModItemTier.BLOOD, 6, -1.2F, new Item.Properties()));
    public static final RegistryObject<Item> PARASITIC_SHOVEL = ITEMS.register("parasitic_shovel",
            () -> new ParasiticShovel(ToolTiers.ModItemTier.BLOOD, 1, -2.4F, new Item.Properties()));

    public static final RegistryObject<Item> PERMAFROST_SWORD = ITEMS.register("permafrost_sword",
            () -> new PermafrostSword(ToolTiers.ModItemTier.PERMAFROST, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> PERMAFROST_PICKAXE = ITEMS.register("permafrost_pickaxe",
            () -> new PermafrostPickaxe(ToolTiers.ModItemTier.PERMAFROST, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> PERMAFROST_AXE = ITEMS.register("permafrost_axe",
            () -> new PermafrostAxe(ToolTiers.ModItemTier.PERMAFROST, 5.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> PERMAFROST_SHOVEL = ITEMS.register("permafrost_shovel",
            () -> new PermafrostShovel(ToolTiers.ModItemTier.PERMAFROST, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> PERMAFROST_HOE = ITEMS.register("permafrost_hoe",
            () -> new HoeItem(ToolTiers.ModItemTier.PERMAFROST, -3, 0.0F, new Item.Properties()));


    public static final RegistryObject<Item> FROZEN_GRASS = ITEMS.register("frozen_grass",
            () -> new BlockItem(InitBlocks.FROZEN_GRASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> VOID_BLOCK = ITEMS.register("void_block",
            () -> new VoidBlockItem(InitBlocks.VOID_BLOCK.get(), new Item.Properties()));


    public static final RegistryObject<Item> DOUBLE_CAST_SPELL = ITEMS.register("double_cast_spell",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DOUBLE_CAST_INNACURATE_SPELL = ITEMS.register("double_cast_inaccurate_spell",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EXPLOSION_SPELL = ITEMS.register("explosion_spell",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GIANT_SNOWBALL_SPELL = ITEMS.register("giant_snowball_spell",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GIANT_SNOWBALL_WITH_TRIGGER_SPELL = ITEMS.register("giant_snowball_with_trigger_spell",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRIPLE_CAST_SPELL = ITEMS.register("triple_cast_spell",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> BLOOD_SLIME_BALL = ITEMS.register("blood_slime_ball",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLESHY_BONE = ITEMS.register("fleshy_bone",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PERMAFROST_CORE = ITEMS.register("permafrost_core",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PERMAFROST_SHARD = ITEMS.register("permafrost_shard",
            () -> new Item(new Item.Properties()));

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


    public static final RegistryObject<Item> ICE_CREAM = ITEMS.register("ice_cream",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(6).build()).stacksTo(16)));

    public static final RegistryObject<Item> ANKLE_BITER_SPAWN_EGG = ITEMS.register("ankle_biter_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.ANKLE_BITER, new Color(74, 10, 3).getRGB(), new Color(74, 3, 3).getRGB(), new Item.Properties()));
    public static final RegistryObject<Item> FLESH_WALKER_SPAWN_EGG = ITEMS.register("flesh_walker_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.FLESH_WALKER, new Color(74, 10, 3).getRGB(), new Color(207, 95, 110).getRGB(), new Item.Properties()));
    public static final RegistryObject<Item> FLESH_SLIME_SPAWN_EGG = ITEMS.register("flesh_slime_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.FLESH_SLIME, new Color(74, 10, 3).getRGB(), new Color(15, 50, 15).getRGB(), new Item.Properties()));
    public static final RegistryObject<Item> ABDOMINAL_SNOWMAN_SPAWN_EGG = ITEMS.register("abdominal_snowman_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.ABDOMINAL_SNOWMAN, new Color(Color.WHITE.getRGB()).getRGB(), new Color(Color.LIGHT_GRAY.getRGB()).getRGB(), new Item.Properties()));
    public static final RegistryObject<Item> KNOWLEDGEABLE_SNOWMAN_SPAWN_EGG = ITEMS.register("knowledgeable_snowman_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.KNOWLEDGEABLE_SNOWMAN, new Color(Color.WHITE.getRGB()).getRGB(), new Color(Color.gray.getRGB()).getRGB(), new Item.Properties()));
    public static final RegistryObject<Item> MOOSICLE = ITEMS.register("moosicle_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.MOOSICLE, new Color(0, 80, 203).getRGB(), new Color(42, 8, 3).getRGB(), new Item.Properties()));
    public static final RegistryObject<Item> COMPARABLE_SNOWMAN_SPAWN_EGG = ITEMS.register("comparable_snowman_spawn_egg",
            () -> new ForgeSpawnEggItem(InitEntities.COMPARABLE_SNOWMAN, new Color(0, 80, 203).getRGB(), new Color(127, 127, 199).getRGB(), new Item.Properties()));

    public static final RegistryObject<Item> BLOODY_SKULL = ITEMS.register("bloody_skull",
            () -> new ArmorItem(ArmorTiers.BLOOD_HELMET, ArmorItem.Type.HELMET, new Item.Properties()));


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
    public static final RegistryObject<Item> HANGING_FLESH = ITEMS.register("hanging_flesh",
            () -> new BlockItem(InitBlocks.HANGING_FLESH.get(), new Item.Properties()));


    public static final RegistryObject<Item> BLOOD_BUCKET = ITEMS.register("blood_bucket",
            () -> new BucketItem(InitFluids.SOURCE_BLOOD,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> INKWOOD_LOG_ITEM = ITEMS.register("inkwood_log",
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
            () -> new ModBoatItem(false, ModBoatEntity.Type.INKWOOD, new Item.Properties()));
    public static final RegistryObject<Item> INKWOOD_CHEST_BOAT_ITEM = ITEMS.register("inkwood_chest_boat",
            () -> new ModBoatItem(true, ModBoatEntity.Type.INKWOOD, new Item.Properties()));

}
