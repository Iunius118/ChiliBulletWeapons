package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.tags.ModItemTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // Plants
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHILI_SEEDS)
                .requires(ModItems.CURVED_CHILI)
                .unlockedBy("has_curved_chili", has(ModItems.CURVED_CHILI))
                .save(recipeOutput, getItemId(ModItems.CHILI_SEEDS));

        // Foods
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHILI_CHICKEN_SANDWICH, 2)
                .requires(ModItems.CURVED_CHILI)
                .requires(Items.COOKED_CHICKEN)
                .requires(Items.BREAD)
                .unlockedBy("has_curved_chili", has(ModItems.CURVED_CHILI))
                .save(recipeOutput, getItemId(ModItems.CHILI_CHICKEN_SANDWICH));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHILI_FISH_SANDWICH, 2)
                .requires(ModItems.CURVED_CHILI)
                .requires(ModItemTags.FOODS_COOKED_FISH)
                .requires(Items.BREAD)
                .unlockedBy("has_curved_chili", has(ModItems.CURVED_CHILI))
                .save(recipeOutput, getItemId(ModItems.CHILI_FISH_SANDWICH));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHILI_MEAT_SANDWICH, 2)
                .requires(ModItems.CURVED_CHILI)
                .requires(ModItemTags.FOODS_COOKED_MEAT)
                .requires(Items.BREAD)
                .unlockedBy("has_curved_chili", has(ModItems.CURVED_CHILI))
                .save(recipeOutput, getItemId(ModItems.CHILI_MEAT_SANDWICH));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHILI_POTATO_SANDWICH, 2)
                .requires(ModItems.CURVED_CHILI)
                .requires(Items.BAKED_POTATO)
                .requires(Items.BREAD)
                .unlockedBy("has_curved_chili", has(ModItems.CURVED_CHILI))
                .save(recipeOutput, getItemId(ModItems.CHILI_POTATO_SANDWICH));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.HALF_CHILI_CHICKEN_SANDWICH, 2)
                .requires(ModItems.CHILI_CHICKEN_SANDWICH)
                .unlockedBy("has_full_sandwich", has(ModItems.CHILI_CHICKEN_SANDWICH))
                .save(recipeOutput, getItemId(ModItems.HALF_CHILI_CHICKEN_SANDWICH));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.HALF_CHILI_FISH_SANDWICH, 2)
                .requires(ModItems.CHILI_FISH_SANDWICH)
                .unlockedBy("has_full_sandwich", has(ModItems.CHILI_FISH_SANDWICH))
                .save(recipeOutput, getItemId(ModItems.HALF_CHILI_FISH_SANDWICH));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.HALF_CHILI_MEAT_SANDWICH, 2)
                .requires(ModItems.CHILI_MEAT_SANDWICH)
                .unlockedBy("has_full_sandwich", has(ModItems.CHILI_MEAT_SANDWICH))
                .save(recipeOutput, getItemId(ModItems.HALF_CHILI_MEAT_SANDWICH));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.HALF_CHILI_POTATO_SANDWICH, 2)
                .requires(ModItems.CHILI_POTATO_SANDWICH)
                .unlockedBy("has_curved_chili", has(ModItems.CHILI_POTATO_SANDWICH))
                .save(recipeOutput, getItemId(ModItems.HALF_CHILI_POTATO_SANDWICH));

        // Weapons
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.CHILI_BULLET)
                .requires(ModItems.BULLET_CHILI)
                .unlockedBy("has_bullet_chili", has(ModItems.BULLET_CHILI))
                .save(recipeOutput, getItemId(ModItems.CHILI_BULLET));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.GUN)
                .pattern("i  ")
                .pattern(" i ")
                .pattern(" pb")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('p', ItemTags.PLANKS)
                .define('b', ModItems.CHILI_BULLET)
                .unlockedBy("has_chili_bullet", has(ModItems.CHILI_BULLET))
                .save(recipeOutput, getItemId(ModItems.GUN));
    }

    private ResourceLocation getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }
}
