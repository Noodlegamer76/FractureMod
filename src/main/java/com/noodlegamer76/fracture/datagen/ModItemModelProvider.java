package com.noodlegamer76.fracture.datagen;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public static final ResourceLocation FLESH_BLOCK = new ResourceLocation(FractureMod.MODID, "block/flesh_block");
    public static final ResourceLocation FLESHY_DARKSTONE_BRICKS = new ResourceLocation(FractureMod.MODID, "block/fleshy_darkstone_bricks");
    public static final ResourceLocation DARKSTONE_BRICKS = new ResourceLocation(FractureMod.MODID, "block/darkstone_bricks");
    public static final ResourceLocation CRACKED_DARKSTONE_BRICKS = new ResourceLocation(FractureMod.MODID, "block/cracked_darkstone_bricks");
    public static final ResourceLocation DARKSTONE_PILLAR_TOP = new ResourceLocation(FractureMod.MODID, "block/darkstone_pillar_top");
    public static final ResourceLocation DARKSTONE_PILLAR_SIDE = new ResourceLocation(FractureMod.MODID, "block/darkstone_pillar_side");
    public static final ResourceLocation FLESHY_DARKSTONE_PILLAR_TOP = new ResourceLocation(FractureMod.MODID, "block/fleshy_darkstone_pillar_top");
    public static final ResourceLocation FLESHY_DARKSTONE_PILLAR_SIDE = new ResourceLocation(FractureMod.MODID, "block/fleshy_darkstone_pillar_side");
    public static final ResourceLocation DARKSTONE = new ResourceLocation(FractureMod.MODID, "block/darkstone");
    public static final ResourceLocation BLOODY_WOOD = new ResourceLocation(FractureMod.MODID, "block/bloody_wood");
    public static final ResourceLocation BLOODY_BOOKSHELF = new ResourceLocation(FractureMod.MODID, "block/bloody_bookshelf");
    public static final ResourceLocation INKWOOD_PLANKS = new ResourceLocation(FractureMod.MODID, "block/inkwood_planks");
    final ResourceLocation INKWOOD_LOG_TEXTURE = new ResourceLocation(FractureMod.MODID, "block/inkwood_log");
    final ResourceLocation INKWOOD_LOG_TOP = new ResourceLocation(FractureMod.MODID, "block/inkwood_log_top");
    final ResourceLocation STRIPPED_INKWOOD_WOOD = new ResourceLocation(FractureMod.MODID, "block/inkwood_stripped_log");
    final ResourceLocation INKWOOD_BOOKSHELF = new ResourceLocation(FractureMod.MODID, "block/inkwood_bookshelf");
    public static final ResourceLocation FLESH_SPRAYER = new ResourceLocation(FractureMod.MODID, "block/flesh_sprayer");
    public static final ResourceLocation SMOKE_STACK_SIDE = new ResourceLocation(FractureMod.MODID, "block/smoke_stack_side");
    public static final ResourceLocation SMOKE_STACK_TOP = new ResourceLocation(FractureMod.MODID, "block/smoke_stack_top");
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FractureMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        stairsItem(InitBlocks.FLESH_STAIRS, FLESH_BLOCK);
        slabItem(InitBlocks.FLESH_SLAB, FLESH_BLOCK);
        wallItem(InitBlocks.FLESH_WALL, FLESH_BLOCK);

        stairsItem(InitBlocks.FLESHY_DARKSTONE_BRICK_STAIRS, FLESHY_DARKSTONE_BRICKS);
        slabItem(InitBlocks.FLESHY_DARKSTONE_BRICK_SLAB, FLESHY_DARKSTONE_BRICKS);
        wallItem(InitBlocks.FLESHY_DARKSTONE_BRICK_WALL, FLESHY_DARKSTONE_BRICKS);

        stairsItem(InitBlocks.DARKSTONE_BRICK_STAIRS, DARKSTONE_BRICKS);
        slabItem(InitBlocks.DARKSTONE_BRICK_SLAB, DARKSTONE_BRICKS);
        wallItem(InitBlocks.DARKSTONE_BRICK_WALL, DARKSTONE_BRICKS);

        stairsItem(InitBlocks.DARKSTONE_STAIRS, DARKSTONE);
        slabItem(InitBlocks.DARKSTONE_SLAB, DARKSTONE);
        wallItem(InitBlocks.DARKSTONE_WALL, DARKSTONE);

        stairsItem(InitBlocks.CRACKED_DARKSTONE_BRICK_STAIRS, CRACKED_DARKSTONE_BRICKS);
        slabItem(InitBlocks.CRACKED_DARKSTONE_BRICK_SLAB, CRACKED_DARKSTONE_BRICKS);
        wallItem(InitBlocks.CRACKED_DARKSTONE_BRICK_WALL, CRACKED_DARKSTONE_BRICKS);

        simpleItem(InitItems.FLESH_GROWTH);
        simpleItem(InitItems.TENDRILS);
        simpleItem(InitItems.SMALL_FLESH_BULB);
        simpleItem(InitItems.INTESTINE);
        simpleItem(InitItems.HANGING_FLESH);
        simpleItem(InitItems.LIVING_FLESH);
        simpleItem(InitItems.FLESHY_BONE);
        simpleItem(InitItems.BOREAS_KEY);

        simpleItem(InitItems.BLOOD_BUCKET);

        simpleBlockItem(InitBlocks.BOREAS_PORTAL_FRAME);
        simpleBlockItem(InitBlocks.BOREAS_PORTAL_LOCK);

        handHeldItem(InitItems.BROOM);
        handHeldItem(InitItems.PARASITIC_SWORD);
        handHeldItem(InitItems.PARASITIC_PICKAXE);
        handHeldItem(InitItems.PARASITIC_AXE);
        handHeldItem(InitItems.PARASITIC_SHOVEL);

        handHeldItem(InitItems.DOUBLE_CAST_SPELL_ITEM);
        handHeldItem(InitItems.TRIPLE_CAST_SPELL_ITEM);
        handHeldItem(InitItems.GIANT_SNOWBALL_SPELL_ITEM);
        handHeldItem(InitItems.DOUBLE_CAST_INACCURATE_SPELL_ITEM);
        handHeldItem(InitItems.RANDOM_SHOT_SPELL_ITEM);
        handHeldItem(InitItems.GIANT_SNOWBALL_WITH_TRIGGER_SPELL_ITEM);

        spawnEggItem(InitItems.ANKLE_BITER_SPAWN_EGG);
        spawnEggItem(InitItems.FLESH_WALKER_SPAWN_EGG);
        spawnEggItem(InitItems.FLESH_SLIME_SPAWN_EGG);
        spawnEggItem(InitItems.BLOOD_SLIME_SPAWN_EGG);
        spawnEggItem(InitItems.ABDOMINAL_SNOWMAN_SPAWN_EGG);
        spawnEggItem(InitItems.KNOWLEDGEABLE_SNOWMAN_SPAWN_EGG);
        spawnEggItem(InitItems.MOOSICLE);

        cubeColumn("bloody_bookshelf", BLOODY_BOOKSHELF, BLOODY_WOOD);
        cubeColumn("darkstone_pillar", DARKSTONE_PILLAR_SIDE, DARKSTONE_PILLAR_TOP);
        cubeColumn("fleshy_darkstone_pillar", FLESHY_DARKSTONE_PILLAR_SIDE, FLESHY_DARKSTONE_PILLAR_TOP);

        cubeColumn("inkwood_log", INKWOOD_LOG_TEXTURE, INKWOOD_LOG_TOP);
        cubeColumn("inkwood_wood", INKWOOD_LOG_TEXTURE, INKWOOD_LOG_TEXTURE);
        cubeColumn("inkwood_stripped_log", STRIPPED_INKWOOD_WOOD, INKWOOD_LOG_TOP);
        cubeColumn("inkwood_stripped_wood", STRIPPED_INKWOOD_WOOD, STRIPPED_INKWOOD_WOOD);
        cubeColumn("inkwood_bookshelf", INKWOOD_BOOKSHELF, STRIPPED_INKWOOD_WOOD);
        stairsItem(InitBlocks.INKWOOD_STAIRS_BLOCK, INKWOOD_PLANKS);
        slabItem(InitBlocks.INKWOOD_SLAB_BLOCK, INKWOOD_PLANKS);
        fenceItem(InitBlocks.INKWOOD_FENCE_BLOCK, InitBlocks.INKWOOD_PLANKS_BLOCK);
        fenceGate("inkwood_fence_gate", INKWOOD_PLANKS);
        simpleItem(InitItems.INKWOOD_DOOR_ITEM);
        trapdoorItem(InitBlocks.INKWOOD_TRAPDOOR_BLOCK);
        pressurePlate("inkwood_pressure_plate", INKWOOD_PLANKS);
        buttonItem(InitBlocks.INKWOOD_BUTTON_BLOCK, InitBlocks.INKWOOD_PLANKS_BLOCK);
        simpleItem(InitItems.INKWOOD_SIGN_ITEM);
        simpleItem(InitItems.INKWOOD_HANGING_SIGN_ITEM);
        simpleItem(InitItems.INKWOOD_BOAT_ITEM);
        simpleItem(InitItems.INKWOOD_CHEST_BOAT_ITEM);
        simpleItem(InitItems.INVERTED_GLASSES);

        simpleItem(InitItems.BLOOD_SLIME_BALL);
        simpleItem(InitItems.FROZEN_GRASS);

        cubeColumn("flesh_sprayer", FLESH_BLOCK, FLESH_SPRAYER);

        saplingItem(InitBlocks.INKWOOD_SAPLING);

        cubeTop("smoke_stack", SMOKE_STACK_SIDE, SMOKE_STACK_TOP);


        //some examples
        //withExistingParent(InitItems.WARDLING_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(FractureMod.MODID, "block/" + item.getId().getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                        mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(FractureMod.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                        mcLoc("block/button_inventory"))
                .texture("texture",
                        new ResourceLocation(FractureMod.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, ResourceLocation texture) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                        mcLoc("block/wall_inventory"))
                .texture("wall",  texture);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                mcLoc("item/generated"))
                .texture("layer0",
                        new ResourceLocation(FractureMod.MODID, "item/" + item.getId().getPath()));
    }
    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> block) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                mcLoc("fracture:block/" + block.getId().getPath()))
                .texture("layer0",
                        new ResourceLocation(FractureMod.MODID, "block/" + block.getId().getPath()));
    }

    private ItemModelBuilder cubeBlockItem(RegistryObject<Block> block) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                mcLoc("minecraft:block/cube_all" + block.getId().getPath()))
                .texture("layer0",
                        new ResourceLocation(FractureMod.MODID, "block/" + block.getId().getPath()));
    }
    private ItemModelBuilder slabItem(RegistryObject<Block> block, ResourceLocation texture) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                new ResourceLocation(FractureMod.MODID, "item/slab"))
                .texture("slab", texture);
    }
    private ItemModelBuilder stairsItem(RegistryObject<Block> block, ResourceLocation texture) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                new ResourceLocation(FractureMod.MODID, "item/stairs"))
                .texture("stairs", texture);
    }

    private ItemModelBuilder handHeldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                mcLoc("item/handheld")).texture("layer0",
                new ResourceLocation(FractureMod.MODID, "item/" + item.getId().getPath()));
    }
    private ItemModelBuilder spawnEggItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                mcLoc("item/template_spawn_egg"));
    }
}
