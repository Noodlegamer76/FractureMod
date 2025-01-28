package com.noodlegamer76.fracture.datagen;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.util.registryutils.BlockSet;
import com.noodlegamer76.fracture.util.registryutils.SimpleStoneSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public static final TagKey<Item> INKWOOD_LOGS = ItemTags.create(new ResourceLocation(FractureMod.MODID, "inkwood_logs"));
    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture,
                               CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, providerCompletableFuture, tagLookupCompletableFuture, FractureMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        for (BlockSet blockSet: DataGenerators.BLOCKS) {
            if (blockSet instanceof SimpleStoneSet set) {
                this.tag(ItemTags.STAIRS)
                        .add(set.stairs.getItem());
                this.tag(ItemTags.SLABS)
                        .add(set.slab.getItem());
                this.tag(ItemTags.WALLS)
                        .add(set.wall.getItem());
            }
        }

        //example for adding items to tags, check ModItemTagGenerator to see how to make your own tag
        this.tag(ItemTags.WALLS)
                .add(InitItems.FLESH_WALL.get())
                .add(InitItems.FLESHY_DARKSTONE_BRICK_WALL.get())
                .add(InitItems.DARKSTONE_BRICK_WALL.get())
                .add(InitItems.DARKSTONE_WALL.get());

        this.tag(ItemTags.STAIRS)
                .add(InitItems.FLESH_STAIRS.get())
                .add(InitItems.FLESHY_DARKSTONE_BRICK_STAIRS.get())
                .add(InitItems.DARKSTONE_BRICK_STAIRS.get())
                .add(InitItems.DARKSTONE_STAIRS.get())
                .add(InitItems.INKWOOD_STAIRS_ITEM.get());

        this.tag(ItemTags.SLABS)
                .add(InitItems.FLESH_SLAB.get())
                .add(InitItems.FLESHY_DARKSTONE_BRICK_SLAB.get())
                .add(InitItems.DARKSTONE_BRICK_SLAB.get())
                .add(InitItems.DARKSTONE_SLAB.get())
                .add(InitItems.INKWOOD_SLAB_ITEM.get());

        this.tag(ItemTags.STONE_BRICKS)
                .add(InitItems.DARKSTONE_BRICKS.get())
                .add(InitItems.CHISELED_DARKSTONE_BRICKS.get())
                .add(InitItems.FLESHY_DARKSTONE_BRICKS.get())
                .add(InitItems.CRACKED_DARKSTONE_BRICKS.get());

        this.tag(Tags.Items.STONE)
                .add(InitItems.DARKSTONE.get());


        this.tag(Tags.Items.FENCES)
                .add(InitItems.INKWOOD_FENCE_ITEM.get());

        this.tag(Tags.Items.FENCES_WOODEN)
                .add(InitItems.INKWOOD_FENCE_ITEM.get());

        this.tag(Tags.Items.FENCE_GATES)
                .add(InitItems.INKWOOD_FENCE_GATE_ITEM.get());

        this.tag(Tags.Items.FENCE_GATES_WOODEN)
                .add(InitItems.INKWOOD_FENCE_GATE_ITEM.get());

        this.tag(ItemTags.LOGS)
                .add(
                        InitItems.INKWOOD_LOG_ITEM.get(),
                        InitItems.INKWOOD_STRIPPED_LOG_ITEM.get(),
                        InitItems.INKWOOD_WOOD_ITEM.get(),
                        InitItems.INKWOOD_STRIPPED_WOOD_ITEM.get()
                );

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(
                        InitItems.INKWOOD_LOG_ITEM.get(),
                        InitItems.INKWOOD_STRIPPED_LOG_ITEM.get(),
                        InitItems.INKWOOD_WOOD_ITEM.get(),
                        InitItems.INKWOOD_STRIPPED_WOOD_ITEM.get()
                );

        this.tag(ItemTags.PLANKS)
                .add(
                        InitItems.INKWOOD_PLANKS_ITEM.get()
                );

        this.tag(ItemTags.WOODEN_DOORS)
                .add(
                        InitItems.INKWOOD_DOOR_ITEM .get()
                );

        this.tag(ItemTags.DOORS)
                .add(
                        InitItems.INKWOOD_DOOR_ITEM.get()
                );

        this.tag(ItemTags.BUTTONS)
                .add(
                        InitItems.INKWOOD_BUTTON_ITEM.get()
                );

        this.tag(ItemTags.WOODEN_BUTTONS)
                .add(
                        InitItems.INKWOOD_BUTTON_ITEM.get()
                );

        this.tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(
                        InitItems.INKWOOD_PRESSURE_PLATE_ITEM.get()
                );

        this.tag(Tags.Items.BOOKSHELVES)
                .add(
                        InitItems.BLOODY_BOOKSHELF.get(),
                        InitItems.INKWOOD_BOOKSHELF.get()
                );

        this.tag(INKWOOD_LOGS)
                .add(
                        InitItems.INKWOOD_LOG_ITEM.get(),
                        InitItems.INKWOOD_STRIPPED_LOG_ITEM.get(),
                        InitItems.INKWOOD_WOOD_ITEM.get(),
                        InitItems.INKWOOD_STRIPPED_WOOD_ITEM.get()
                );

        this.tag(ItemTags.SAPLINGS)
                .add(
                        InitItems.INKWOOD_SAPLING.get()
                );

        this.tag(ItemTags.LEAVES)
                .add(
                        InitItems.INKWOOD_LEAVES.get()
                );

    }
}
