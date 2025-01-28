package com.noodlegamer76.fracture.util.registryutils;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SimpleStoneSet extends BlockSet {
    public final BlockWithItem block;
    public final BlockWithItem stairs;
    public final BlockWithItem slab;
    public final BlockWithItem wall;

    public SimpleStoneSet(String name, BlockBehaviour.Properties properties) {
        block = new BlockWithItem(name, properties);
        stairs = new BlockWithItem(name + "_stairs", () -> new StairBlock(block.getBlock().defaultBlockState(), properties));
        slab = new BlockWithItem(name + "_slab", () -> new SlabBlock(properties));
        wall = new BlockWithItem(name + "_wall", () -> new WallBlock(properties));

        blockList.add(block);
        blockList.add(stairs);
        blockList.add(slab);
        blockList.add(wall);
    }

    public Block getBlock() {
        return block.getBlock();
    }

    public StairBlock getStairs() {
        return (StairBlock) stairs.getBlock();
    }

    public SlabBlock getSlab() {
        return (SlabBlock) slab.getBlock();
    }

    public WallBlock getWall() {
        return (WallBlock) wall.getBlock();
    }
}
