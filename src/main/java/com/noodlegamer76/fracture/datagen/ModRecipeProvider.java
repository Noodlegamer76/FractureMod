package com.noodlegamer76.fracture.datagen;

import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        //example for making a recipe
        // planksFromLogs(pWriter, ItemInit.RAINBOW_PLANKS.get(), ModTags.Items.RAINBOW_WOOD, 4);

        //Darkstone
        surroundWith8(pWriter, Items.COAL, Items.STONE, InitItems.DARKSTONE.get());
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.DARKSTONE_SLAB.get(), InitItems.DARKSTONE.get());
        stairs(pWriter, InitItems.DARKSTONE_STAIRS.get(), InitItems.DARKSTONE.get());
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.DARKSTONE_WALL.get(), InitItems.DARKSTONE.get());

        //Darkstone Bricks
        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.DARKSTONE_BRICKS.get(), InitItems.DARKSTONE.get());
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.DARKSTONE_BRICK_SLAB.get(), InitItems.DARKSTONE_BRICKS.get());
        stairs(pWriter, InitItems.DARKSTONE_BRICK_STAIRS.get(), InitItems.DARKSTONE_BRICKS.get());
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.DARKSTONE_BRICK_WALL.get(), InitItems.DARKSTONE_BRICKS.get());
        chiseled(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.CHISELED_DARKSTONE_BRICKS.get(), InitItems.DARKSTONE_BRICKS.get());
        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.DARKSTONE_PILLAR.get(), InitItems.DARKSTONE_BRICK_SLAB.get());


        //Fleshy Darkstone Bricks
        surroundWith8(pWriter, InitItems.FLESH_BLOCK.get(), InitItems.DARKSTONE_BRICKS.get(), InitItems.FLESHY_DARKSTONE_BRICKS.get());
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.FLESHY_DARKSTONE_BRICK_SLAB.get(), InitItems.FLESHY_DARKSTONE_BRICKS.get());
        stairs(pWriter, InitItems.FLESHY_DARKSTONE_BRICK_STAIRS.get(), InitItems.FLESHY_DARKSTONE_BRICKS.get());
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.FLESHY_DARKSTONE_BRICK_WALL.get(), InitItems.FLESHY_DARKSTONE_BRICKS.get());
        chiseled(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.FLESHY_CHISELED_DARKSTONE_BRICKS.get(), InitItems.FLESHY_DARKSTONE_BRICKS.get());
        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.FLESHY_DARKSTONE_PILLAR.get(), InitItems.FLESHY_DARKSTONE_BRICK_SLAB.get());

        //Flesh
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.FLESH_SLAB.get(), InitItems.FLESH_BLOCK.get());
        stairs(pWriter, InitItems.FLESH_STAIRS.get(), InitItems.FLESH_BLOCK.get());
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.FLESH_WALL.get(), InitItems.FLESH_BLOCK.get());

        //Cracked Darkstone Bricks
        smeltingResultFromBase(pWriter, InitItems.CRACKED_DARKSTONE_BRICKS.get(), InitItems.DARKSTONE_BRICKS.get());
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.CRACKED_DARKSTONE_BRICK_SLAB.get(), InitItems.CRACKED_DARKSTONE_BRICKS.get());
        stairs(pWriter, InitItems.CRACKED_DARKSTONE_BRICK_STAIRS.get(), InitItems.CRACKED_DARKSTONE_BRICKS.get());
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.CRACKED_DARKSTONE_BRICK_WALL.get(), InitItems.CRACKED_DARKSTONE_BRICKS.get());

        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.FLESH_BLOCK.get(), Items.ROTTEN_FLESH);
    }

    protected static void surroundWith8(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike middle, ItemLike surround, ItemLike result) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 8)
                .define('#', Ingredient.of(surround))
                .define('-', Ingredient.of(middle))
                .pattern("###")
                .pattern("#-#")
                .pattern("###")
                .unlockedBy(getHasName(surround), has(surround))
                .save(pFinishedRecipeConsumer);
    }

    protected static void stairs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike stairs, ItemLike ingredient) {
        stairBuilder(stairs, Ingredient.of(ingredient))
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(pFinishedRecipeConsumer);
    }
}
