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

        simpleItem(InitItems.BLOOD_BUCKET);

        handHeldItem(InitItems.BROOM);

        spawnEggItem(InitItems.ANKLE_BITER_SPAWN_EGG);
        spawnEggItem(InitItems.FLESH_WALKER_SPAWN_EGG);



        cubeColumn("bloody_bookshelf", BLOODY_BOOKSHELF, BLOODY_WOOD);
        cubeColumn("darkstone_pillar", DARKSTONE_PILLAR_SIDE, DARKSTONE_PILLAR_TOP);
        cubeColumn("fleshy_darkstone_pillar", FLESHY_DARKSTONE_PILLAR_SIDE, FLESHY_DARKSTONE_PILLAR_TOP);

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
                mcLoc("item/generated"))
                .texture("layer0",
                        new ResourceLocation(FractureMod.MODID, "item/" + block.getId().getPath()));
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
