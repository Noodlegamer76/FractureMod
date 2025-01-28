package com.noodlegamer76.fracture.util.registryutils;

import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.datagen.DataGenerators;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockWithItem {

    public final RegistryObject<Block> block;
    public final RegistryObject<Item> item;

    public BlockWithItem(String name, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        block = InitBlocks.BLOCKS.register(name,
                () -> new Block(blockProperties));
        item = InitItems.ITEMS.register(name,
                () -> new BlockItem(block.get(), itemProperties));
    }

    public BlockWithItem(String name, BlockBehaviour.Properties blockProperties) {
        block = InitBlocks.BLOCKS.register(name,
                () -> new Block(blockProperties));
        item = InitItems.ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public BlockWithItem(String name, Supplier<? extends Block> blockSup, Supplier<? extends Item> itemSup) {
        block = InitBlocks.BLOCKS.register(name, blockSup);
        item = InitItems.ITEMS.register(name, itemSup);
    }

    public BlockWithItem(String name, Supplier<? extends Block> blockSup) {
        block = InitBlocks.BLOCKS.register(name, blockSup);
        item = InitItems.ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public Block getBlock() {
        return block.get();
    }

    public Item getItem() {
        return item.get();
    }
}
