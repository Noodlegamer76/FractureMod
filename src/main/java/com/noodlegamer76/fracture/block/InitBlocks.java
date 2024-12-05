package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.signs.ModHangingSignBlock;
import com.noodlegamer76.fracture.block.signs.ModStandingSignBlock;
import com.noodlegamer76.fracture.block.signs.ModWallHangingSignBlock;
import com.noodlegamer76.fracture.block.signs.ModWallSignBlock;
import com.noodlegamer76.fracture.util.ModWoodTypes;
import com.noodlegamer76.fracture.fluid.InitFluids;
import com.noodlegamer76.fracture.worldgen.tree.InkwoodTreeGrower;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FractureMod.MODID);

    public static final RegistryObject<Block> FROZEN_GRASS = BLOCKS.register("frozen_grass",
            () -> new FrozenGrass(BlockBehaviour.Properties.copy(Blocks.GRASS)));
    public static final RegistryObject<Block> FROSTED_ICE_CRYSTALS = BLOCKS.register("frosted_ice_crystals",
            () -> new FrostedIceCrystals(BlockBehaviour.Properties.copy(Blocks.GLASS)));
    public static final RegistryObject<Block> ICE_CRYSTAL_BLOCK = BLOCKS.register("ice_crystal_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> RADIANT_ICE = BLOCKS.register("radiant_ice",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).lightLevel((l) -> 10)));

    public static final RegistryObject<Block> FOG_EMITTER = BLOCKS.register("fog_emitter",
            () -> new FogEmitterBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> MODIFICATION_STATION = BLOCKS.register("modification_station",
            () -> new ModificationStationBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

    public static final RegistryObject<Block> BOREAS_PORTAL_LOCK = BLOCKS.register("boreas_portal_lock",
            () -> new BoreasPortalLock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));
    public static final RegistryObject<Block> BOREAS_PORTAL_FRAME = BLOCKS.register("boreas_portal_frame",
            () -> new BoreasPortalFrame(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));
    public static final RegistryObject<Block> PERMAFROST = BLOCKS.register("permafrost",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)));

    public static final RegistryObject<Block> VOID_BLOCK = BLOCKS.register("void_block",
            () -> new VoidBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK).noOcclusion()));

    public static final RegistryObject<Block> COMPACT_TNT = BLOCKS.register("compact_tnt",
            () -> new CompactTntBlock(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final RegistryObject<Block> INWKOOD_LEAVES = BLOCKS.register("inkwood_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> INKWOOD_SAPLING = BLOCKS.register("inkwood_sapling",
            () -> new InkwoodSapling(new InkwoodTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> SMOKE_STACK = BLOCKS.register("smoke_stack",
            () -> new SmokeStack(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> FLESH_SPRAYER = BLOCKS.register("flesh_sprayer",
            () -> new FleshParticleSpawner(BlockBehaviour.Properties.of().mapColor(DyeColor.RED).strength(0.4F)));

    public static final RegistryObject<Block> SKYBOX_GENERATOR = BLOCKS.register("skybox_generator",
            () -> new SkyboxGenerator(BlockBehaviour.Properties.of().noOcclusion()));


    public static final RegistryObject<Block> BLOOD_SLIME_BLOCK = BLOCKS.register("blood_slime_block",
            () -> new BloodSlimeBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).noCollission()));

    public static final RegistryObject<Block> INKWOOD_BOOKSHELF = BLOCKS.register("inkwood_bookshelf",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF)));

    public static final RegistryObject<Block> FLESH_BLOCK = BLOCKS.register("flesh_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).mapColor(DyeColor.RED).strength(0.4F)));
    public static final RegistryObject<Block> FLESH_STAIRS = BLOCKS.register("flesh_stairs",
            () -> new StairBlock(FLESH_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).mapColor(DyeColor.RED).strength(0.4F)));
    public static final RegistryObject<Block> FLESH_SLAB = BLOCKS.register("flesh_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).mapColor(DyeColor.RED)));
    public static final RegistryObject<Block> FLESH_WALL = BLOCKS.register("flesh_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).mapColor(DyeColor.RED)));

    //Darkstone
    public static final RegistryObject<Block> DARKSTONE = BLOCKS.register("darkstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> DARKSTONE_STAIRS = BLOCKS.register("darkstone_stairs",
            () -> new StairBlock(DARKSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> DARKSTONE_SLAB = BLOCKS.register("darkstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> DARKSTONE_WALL = BLOCKS.register("darkstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    //Darkstone Bricks
    public static final RegistryObject<Block> DARKSTONE_BRICKS = BLOCKS.register("darkstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> DARKSTONE_BRICK_STAIRS = BLOCKS.register("darkstone_brick_stairs",
            () -> new StairBlock(DARKSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> DARKSTONE_BRICK_SLAB = BLOCKS.register("darkstone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> DARKSTONE_BRICK_WALL = BLOCKS.register("darkstone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static final RegistryObject<Block> CHISELED_DARKSTONE_BRICKS = BLOCKS.register("chiseled_darkstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> DARKSTONE_PILLAR = BLOCKS.register("darkstone_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));



    public static final RegistryObject<Block> FLESHY_CHISELED_DARKSTONE_BRICKS = BLOCKS.register("fleshy_chiseled_darkstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> FLESHY_DARKSTONE_PILLAR = BLOCKS.register("fleshy_darkstone_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    //Cracked Darkstone Bricks
    public static final RegistryObject<Block> CRACKED_DARKSTONE_BRICKS = BLOCKS.register("cracked_darkstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_DARKSTONE_BRICK_STAIRS = BLOCKS.register("cracked_darkstone_brick_stairs",
            () -> new StairBlock(CRACKED_DARKSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_DARKSTONE_BRICK_SLAB = BLOCKS.register("cracked_darkstone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_DARKSTONE_BRICK_WALL = BLOCKS.register("cracked_darkstone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    //Fleshy Darkstone bricks
    public static final RegistryObject<Block> FLESHY_DARKSTONE_BRICKS = BLOCKS.register("fleshy_darkstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> FLESHY_DARKSTONE_BRICK_STAIRS = BLOCKS.register("fleshy_darkstone_brick_stairs",
            () -> new StairBlock(FLESHY_DARKSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> FLESHY_DARKSTONE_BRICK_SLAB = BLOCKS.register("fleshy_darkstone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> FLESHY_DARKSTONE_BRICK_WALL = BLOCKS.register("fleshy_darkstone_brick_wall",
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
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block> INTESTINE = BLOCKS.register("intestine",
            () -> new ChainBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.RED).strength(0.1F)));


    public static final RegistryObject<Block> INKWOOD_LOG_BLOCK = BLOCKS.register("inkwood_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> INKWOOD_WOOD_BLOCK = BLOCKS.register( "inkwood_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> INKWOOD_STRIPPED_LOG_BLOCK = BLOCKS.register( "inkwood_stripped_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> INKWOOD_STRIPPED_WOOD_BLOCK = BLOCKS.register( "inkwood_stripped_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> INKWOOD_PLANKS_BLOCK = BLOCKS.register( "inkwood_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> INKWOOD_STAIRS_BLOCK = BLOCKS.register( "inkwood_stairs",
            () -> new StairBlock(INKWOOD_PLANKS_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> INKWOOD_SLAB_BLOCK = BLOCKS.register( "inkwood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> INKWOOD_FENCE_BLOCK = BLOCKS.register("inkwood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> INKWOOD_FENCE_GATE_BLOCK = BLOCKS.register("inkwood_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> INKWOOD_DOOR_BLOCK = BLOCKS.register("inkwood_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), BlockSetType.OAK));
    public static final RegistryObject<Block> INKWOOD_TRAPDOOR_BLOCK = BLOCKS.register("inkwood_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistryObject<Block> INKWOOD_PRESSURE_PLATE_BLOCK = BLOCKS.register("inkwood_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> INKWOOD_BUTTON_BLOCK = BLOCKS.register("inkwood_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), BlockSetType.OAK, 100, true));
    public static final RegistryObject<Block> INKWOOD_STANDING_SIGN_BLOCK = BLOCKS.register("inkwood_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), ModWoodTypes.INKWOOD));
    public static final RegistryObject<Block> INKWOOD_WALL_SIGN_BLOCK = BLOCKS.register("inkwood_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), ModWoodTypes.INKWOOD));
    public static final RegistryObject<Block> INKWOOD_WALL_HANGING_SIGN_BLOCK = BLOCKS.register("inkwood_wall_hanging_sign",
            () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), ModWoodTypes.INKWOOD));
    public static final RegistryObject<Block> INKWOOD_CEILING_HANGING_SIGN_BLOCK = BLOCKS.register("inkwood_hanging_sign",
            () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), ModWoodTypes.INKWOOD));



    public static final RegistryObject<LiquidBlock> BLOOD_BLOCK = BLOCKS.register("blood_block",
            () -> new LiquidBlock(InitFluids.SOURCE_BLOOD, BlockBehaviour.Properties.copy(Blocks.WATER).mapColor(MapColor.COLOR_RED)));

}
