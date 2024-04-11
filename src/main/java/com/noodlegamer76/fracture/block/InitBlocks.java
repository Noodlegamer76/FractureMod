package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.fluid.InitFluids;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class InitBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FractureMod.MODID);

    public static final RegistryObject<Block> FLESH_BLOCK = BLOCKS.register("flesh_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.RED).strength(0.4F)));
    public static final RegistryObject<Block> FLESH_STAIRS = BLOCKS.register("flesh_stairs",
            () -> new StairBlock(FLESH_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.RED).strength(0.4F)));
    public static final RegistryObject<Block> FLESH_SLAB = BLOCKS.register("flesh_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.RED).strength(0.4F)));
    public static final RegistryObject<Block> FLESH_WALL = BLOCKS.register("flesh_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.RED).strength(0.4F)));

    //Fleshy Stone Bricks
    public static final RegistryObject<Block> FLESHY_STONE_BRICKS = BLOCKS.register("fleshy_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> FLESHY_STONE_BRICK_STAIRS = BLOCKS.register("fleshy_stone_brick_stairs",
            () -> new StairBlock(FLESHY_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> FLESHY_STONE_BRICK_SLAB = BLOCKS.register("fleshy_stone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> FLESHY_STONE_BRICK_WALL = BLOCKS.register("fleshy_stone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));


    public static final RegistryObject<Block> FLESH_GROWTH = BLOCKS.register("flesh_growth",
            () -> new FleshRootsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().noOcclusion().instabreak()
                    .replaceable().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> TENDRILS = BLOCKS.register("tendrils",
            () -> new FleshRootsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().noOcclusion().instabreak()
                    .replaceable().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> HANGING_FLESH = BLOCKS.register("hanging_flesh",
            () -> new HangingFlesh(BlockBehaviour.Properties.copy(Blocks.WEEPING_VINES).mapColor(MapColor.COLOR_RED).noCollission().noOcclusion().instabreak()
                    .replaceable().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> HANGING_FLESH_PLANT = BLOCKS.register("hanging_flesh_plant",
            () -> new HangingFleshPlant(BlockBehaviour.Properties.copy(Blocks.WEEPING_VINES_PLANT).mapColor(MapColor.COLOR_RED).noCollission().noOcclusion().instabreak()
                    .replaceable().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));


    public static final RegistryObject<Block> LARGE_FLESH_BULB = BLOCKS.register("large_flesh_bulb",
            () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().lightLevel((blockState) -> 10).strength(0.1F)));
    public static final RegistryObject<Block> SMALL_FLESH_BULB = BLOCKS.register("small_flesh_bulb",
            () -> new LanternBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().lightLevel((blockState) -> 15).strength(0.1F)));
    public static final RegistryObject<Block> BLOODY_BOOKSHELF = BLOCKS.register("bloody_bookshelf",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF).mapColor(MapColor.COLOR_ORANGE)));
    public static final RegistryObject<Block> INTESTINE = BLOCKS.register("intestine",
            () -> new ChainBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.RED).strength(0.4F)));


    public static final RegistryObject<LiquidBlock> BLOOD_BLOCK = BLOCKS.register("blood_block",
            () -> new LiquidBlock(InitFluids.SOURCE_BLOOD, BlockBehaviour.Properties.copy(Blocks.WATER)));


}
