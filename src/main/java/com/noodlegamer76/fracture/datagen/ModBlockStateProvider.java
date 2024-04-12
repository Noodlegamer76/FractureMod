package com.noodlegamer76.fracture.datagen;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public static final ResourceLocation FLESH_BLOCK = new ResourceLocation(FractureMod.MODID, "block/flesh_block");
    public static final ResourceLocation FLESHY_DARKSTONE_BRICKS = new ResourceLocation(FractureMod.MODID, "block/fleshy_darkstone_bricks");
    public static final ResourceLocation DARKSTONE_BRICKS = new ResourceLocation(FractureMod.MODID, "block/darkstone_bricks");
    public static final ResourceLocation CRACKED_DARKSTONE_BRICKS = new ResourceLocation(FractureMod.MODID, "block/cracked_darkstone_bricks");
    public static final ResourceLocation DARKSTONE = new ResourceLocation(FractureMod.MODID, "block/darkstone");
    public static final ResourceLocation BLOODY_WOOD = new ResourceLocation(FractureMod.MODID, "block/bloody_wood");
    public static final ResourceLocation BLOODY_BOOKSHELF = new ResourceLocation(FractureMod.MODID, "block/bloody_bookshelf");
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FractureMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //write here to generate models

        blockWithItem(InitBlocks.FLESH_BLOCK);
        stairsBlock((StairBlock) InitBlocks.FLESH_STAIRS.get(), FLESH_BLOCK);
        slabBlock((SlabBlock) InitBlocks.FLESH_SLAB.get(), FLESH_BLOCK, FLESH_BLOCK);
        wallBlock((WallBlock) InitBlocks.FLESH_WALL.get(), FLESH_BLOCK);

        blockWithItem(InitBlocks.FLESHY_DARKSTONE_BRICKS);
        stairsBlock((StairBlock) InitBlocks.FLESHY_DARKSTONE_BRICK_STAIRS.get(), FLESHY_DARKSTONE_BRICKS);
        slabBlock((SlabBlock) InitBlocks.FLESHY_DARKSTONE_BRICK_SLAB.get(), FLESHY_DARKSTONE_BRICKS, FLESHY_DARKSTONE_BRICKS);
        wallBlock((WallBlock) InitBlocks.FLESHY_DARKSTONE_BRICK_WALL.get(), FLESHY_DARKSTONE_BRICKS);

        blockWithItem(InitBlocks.DARKSTONE_BRICKS);
        stairsBlock((StairBlock) InitBlocks.DARKSTONE_BRICK_STAIRS.get(), DARKSTONE_BRICKS);
        slabBlock((SlabBlock) InitBlocks.DARKSTONE_BRICK_SLAB.get(), DARKSTONE_BRICKS, DARKSTONE_BRICKS);
        wallBlock((WallBlock) InitBlocks.DARKSTONE_BRICK_WALL.get(), DARKSTONE_BRICKS);

        blockWithItem(InitBlocks.DARKSTONE);
        stairsBlock((StairBlock) InitBlocks.DARKSTONE_STAIRS.get(), DARKSTONE);
        slabBlock((SlabBlock) InitBlocks.DARKSTONE_SLAB.get(), DARKSTONE, DARKSTONE);
        wallBlock((WallBlock) InitBlocks.DARKSTONE_WALL.get(), DARKSTONE);

        blockWithItem(InitBlocks.CRACKED_DARKSTONE_BRICKS);
        stairsBlock((StairBlock) InitBlocks.CRACKED_DARKSTONE_BRICK_STAIRS.get(), CRACKED_DARKSTONE_BRICKS);
        slabBlock((SlabBlock) InitBlocks.CRACKED_DARKSTONE_BRICK_SLAB.get(), CRACKED_DARKSTONE_BRICKS, CRACKED_DARKSTONE_BRICKS);
        wallBlock((WallBlock) InitBlocks.CRACKED_DARKSTONE_BRICK_WALL.get(), CRACKED_DARKSTONE_BRICKS);

        cross(InitBlocks.FLESH_GROWTH);
        cross(InitBlocks.TENDRILS);
        cross(InitBlocks.HANGING_FLESH_PLANT);
        cross(InitBlocks.HANGING_FLESH);

        translucentBlockWithItem(InitBlocks.LARGE_FLESH_BULB);

        blockWithItem(InitBlocks.CHISELED_DARKSTONE_BRICKS);

        cubeColumn(InitBlocks.BLOODY_BOOKSHELF, BLOODY_BOOKSHELF, BLOODY_WOOD);

    }
    private void cutout(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cubeAll(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
    private void cubeColumn(RegistryObject<Block> blockRegistryObject, ResourceLocation side, ResourceLocation top) {
        simpleBlock(blockRegistryObject.get(),
                models().cubeColumn(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), side, top).renderType("cutout"));
    }
    private void translucent(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cubeAll(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("translucent"));
    }
    private void cutoutBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), models().cubeAll(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
    private void translucentBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), models().cubeAll(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("translucent"));
    }
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void cross(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
    private void crop(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().crop(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
    private void carpet(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().carpet(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())));
    }
}
