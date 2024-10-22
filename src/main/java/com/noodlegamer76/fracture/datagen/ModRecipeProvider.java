package com.noodlegamer76.fracture.datagen;

import com.noodlegamer76.fracture.block.InitBlocks;
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

import static com.noodlegamer76.fracture.datagen.ModItemTagGenerator.INKWOOD_LOGS;

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

        planksFromLogs(pWriter, InitItems.INKWOOD_PLANKS_ITEM.get(), INKWOOD_LOGS, 4);
        woodFromLogs(pWriter, InitItems.INKWOOD_WOOD_ITEM.get(), InitItems.INKWOOD_LOG_ITEM.get());
        stairs(pWriter, InitItems.INKWOOD_STAIRS_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, InitItems.INKWOOD_SLAB_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        fence(pWriter, InitItems.INKWOOD_FENCE_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        fenceGate(pWriter, InitItems.INKWOOD_FENCE_GATE_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        door(pWriter, InitItems.INKWOOD_DOOR_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        trapdoor(pWriter, InitItems.INKWOOD_TRAPDOOR_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        pressurePlate(pWriter, InitItems.INKWOOD_PRESSURE_PLATE_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        button(pWriter, InitItems.INKWOOD_BUTTON_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        hangingSign(pWriter, InitItems.INKWOOD_HANGING_SIGN_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        sign(pWriter, InitItems.INKWOOD_SIGN_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        woodenBoat(pWriter, InitItems.INKWOOD_BOAT_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        chestBoat(pWriter, InitItems.INKWOOD_CHEST_BOAT_ITEM.get(), InitItems.INKWOOD_PLANKS_ITEM.get());
        bookshelf(pWriter, Items.BOOK, InitItems.INKWOOD_PLANKS_ITEM.get(), InitItems.INKWOOD_BOOKSHELF.get());
        surroundWith8(pWriter, InitItems.BLOOD_SLIME_BALL.get(), Items.BOOKSHELF, InitItems.BLOODY_BOOKSHELF.get());

        twoByTwoPacker(pWriter, RecipeCategory.REDSTONE, InitItems.BLOOD_SLIME_BLOCK.get(), InitItems.BLOOD_SLIME_BALL.get());
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

    protected static void bookshelf(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike book, ItemLike wood, ItemLike result) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 8)
                .define('#', Ingredient.of(wood))
                .define('-', Ingredient.of(book))
                .pattern("###")
                .pattern("---")
                .pattern("###")
                .unlockedBy(getHasName(book), has(book))
                .save(pFinishedRecipeConsumer);
    }

    protected static void stairs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike stairs, ItemLike ingredient) {
        stairBuilder(stairs, Ingredient.of(ingredient))
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(pFinishedRecipeConsumer);
    }

    protected static void fence(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike fence, ItemLike ingredient) {
        fenceBuilder(fence, Ingredient.of(ingredient))
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(pFinishedRecipeConsumer);
    }

    protected static void fenceGate(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike gate, ItemLike ingredient) {
        fenceGateBuilder(gate, Ingredient.of(ingredient))
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(pFinishedRecipeConsumer);
    }

    protected static void door(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike door, ItemLike ingredient) {
        doorBuilder(door, Ingredient.of(ingredient))
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(pFinishedRecipeConsumer);
    }

    protected static void trapdoor(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike trapdoor, ItemLike ingredient) {
        trapdoorBuilder(trapdoor, Ingredient.of(ingredient))
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(pFinishedRecipeConsumer);
    }

    protected static void button(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike button, ItemLike ingredient) {
        buttonBuilder(button, Ingredient.of(ingredient))
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(pFinishedRecipeConsumer);
    }

    protected static void sign(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike sign, ItemLike ingredient) {
        signBuilder(sign, Ingredient.of(ingredient))
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(pFinishedRecipeConsumer);
    }
}
