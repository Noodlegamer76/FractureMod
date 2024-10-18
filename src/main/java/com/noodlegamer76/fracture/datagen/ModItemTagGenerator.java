package com.noodlegamer76.fracture.datagen;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture,
                               CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, providerCompletableFuture, tagLookupCompletableFuture, FractureMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {


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
    }
}
