package com.noodlegamer76.fracture.creativetabs;

import com.noodlegamer76.fracture.item.InitItems;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FractureTab {
    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == InitCreativeTabs.FRACTURE_TAB.getKey()) {

            //example of how to add item to creative tab
            event.accept(InitItems.BLOODY_BOOKSHELF);
            event.accept(InitItems.BLOOD_BUCKET);
            event.accept(InitItems.BROOM);
            event.accept(InitItems.CHISELED_DARKSTONE_BRICKS);
            event.accept(InitItems.CRACKED_DARKSTONE_BRICKS);
            event.accept(InitItems.CRACKED_DARKSTONE_BRICK_SLAB);
            event.accept(InitItems.CRACKED_DARKSTONE_BRICK_STAIRS);
            event.accept(InitItems.CRACKED_DARKSTONE_BRICK_WALL);
            event.accept(InitItems.DARKSTONE);
            event.accept(InitItems.DARKSTONE_BRICKS);
            event.accept(InitItems.DARKSTONE_BRICK_SLAB);
            event.accept(InitItems.DARKSTONE_BRICK_STAIRS);
            event.accept(InitItems.DARKSTONE_BRICK_WALL);
            event.accept(InitItems.DARKSTONE_PILLAR);
            event.accept(InitItems.DARKSTONE_SLAB);
            event.accept(InitItems.DARKSTONE_STAIRS);
            event.accept(InitItems.DARKSTONE_WALL);
            event.accept(InitItems.FLESHY_CHISELED_DARKSTONE_BRICKS);
            event.accept(InitItems.FLESHY_DARKSTONE_BRICKS);
            event.accept(InitItems.FLESHY_DARKSTONE_BRICK_SLAB);
            event.accept(InitItems.FLESHY_DARKSTONE_BRICK_STAIRS);
            event.accept(InitItems.FLESHY_DARKSTONE_BRICK_WALL);
            event.accept(InitItems.FLESHY_DARKSTONE_PILLAR);
            event.accept(InitItems.FLESH_BLOCK);
            event.accept(InitItems.FLESH_GROWTH);
            event.accept(InitItems.FLESH_SLAB);
            event.accept(InitItems.FLESH_SPRAYER);
            event.accept(InitItems.FLESH_STAIRS);
            event.accept(InitItems.FLESH_WALL);
            event.accept(InitItems.BLOOD_SLIME_BALL);
            event.accept(InitItems.HANGING_FLESH);
            event.accept(InitItems.INTESTINE);
            event.accept(InitItems.INVERTED_GLASSES);
            event.accept(InitItems.LARGE_FLESH_BULB);
            event.accept(InitItems.LIVING_FLESH);
            event.accept(InitItems.SMALL_FLESH_BULB);
            event.accept(InitItems.TENDRILS);
            event.accept(InitItems.BLOOD_SLIME_BLOCK);
            event.accept(InitItems.FLESHY_BONE);
            event.accept(InitItems.PARASITIC_SWORD);
            event.accept(InitItems.PARASITIC_PICKAXE);
            event.accept(InitItems.PARASITIC_AXE);
            event.accept(InitItems.PARASITIC_SHOVEL);
            event.accept(InitItems.BLOODY_SKULL);





            event.accept(InitItems.SKYBOX_GENERATOR);
            event.accept(InitItems.COMPACT_TNT);
            event.accept(InitItems.SMOKE_STACK);
            event.accept(InitItems.VOID_BLOCK);
            event.accept(InitItems.BOREAS_KEY);
            event.accept(InitItems.BOREAS_PORTAL_LOCK);
            event.accept(InitItems.BOREAS_PORTAL_FRAME);
            event.accept(InitItems.MODIFICATION_STATION);

            event.accept(InitItems.INKWOOD_LOG_ITEM);
            event.accept(InitItems.INKWOOD_WOOD_ITEM);
            event.accept(InitItems.INKWOOD_STRIPPED_LOG_ITEM);
            event.accept(InitItems.INKWOOD_STRIPPED_WOOD_ITEM);
            event.accept(InitItems.INKWOOD_PLANKS_ITEM);
            event.accept(InitItems.INKWOOD_STAIRS_ITEM);
            event.accept(InitItems.INKWOOD_SLAB_ITEM);
            event.accept(InitItems.INKWOOD_FENCE_ITEM);
            event.accept(InitItems.INKWOOD_FENCE_GATE_ITEM);
            event.accept(InitItems.INKWOOD_DOOR_ITEM);
            event.accept(InitItems.INKWOOD_TRAPDOOR_ITEM);
            event.accept(InitItems.INKWOOD_PRESSURE_PLATE_ITEM);
            event.accept(InitItems.INKWOOD_BUTTON_ITEM);
            event.accept(InitItems.INKWOOD_SIGN_ITEM);
            event.accept(InitItems.INKWOOD_HANGING_SIGN_ITEM);
            event.accept(InitItems.INKWOOD_BOAT_ITEM);
            event.accept(InitItems.INKWOOD_CHEST_BOAT_ITEM);
            event.accept(InitItems.INKWOOD_BOOKSHELF);
            event.accept(InitItems.INKWOOD_SAPLING);
            event.accept(InitItems.INKWOOD_LEAVES);

            event.accept(InitItems.PERMAFROST);
            event.accept(InitItems.ICE_CRYSTAL_BLOCK);
            event.accept(InitItems.RADIANT_ICE);
            event.accept(InitItems.FROZEN_GRASS);


            event.accept(InitItems.WAND);
            event.accept(InitItems.VOID_BALL_SPELL_ITEM);
            event.accept(InitItems.GIANT_SNOWBALL_SPELL_ITEM);
            event.accept(InitItems.GIANT_SNOWBALL_WITH_TRIGGER_SPELL_ITEM);
            event.accept(InitItems.DOUBLE_CAST_SPELL_ITEM);
            event.accept(InitItems.DOUBLE_CAST_INACCURATE_SPELL_ITEM);
            event.accept(InitItems.TRIPLE_CAST_SPELL_ITEM);
            event.accept(InitItems.RANDOM_SHOT_SPELL_ITEM);
            event.accept(InitItems.ICE_SPIKE_SCATTER_SPELL_ITEM);

            event.accept(InitItems.ANKLE_BITER_SPAWN_EGG);
            event.accept(InitItems.BLOOD_SLIME_SPAWN_EGG);
            event.accept(InitItems.FLESH_SLIME_SPAWN_EGG);
            event.accept(InitItems.FLESH_WALKER_SPAWN_EGG);
            event.accept(InitItems.ABDOMINAL_SNOWMAN_SPAWN_EGG);
            event.accept(InitItems.KNOWLEDGEABLE_SNOWMAN_SPAWN_EGG);
        }
    }
}
