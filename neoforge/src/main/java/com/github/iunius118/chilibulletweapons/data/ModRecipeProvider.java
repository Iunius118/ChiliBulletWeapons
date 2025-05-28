package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider holderLookup) {
        //* Plants *//
        // Storage item
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BULLET_CHILI_SACK)
                .group(getItemId(ModItems.BULLET_CHILI_SACK).toString())
                .pattern("bbb")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', ModItems.BULLET_CHILI)
                .unlockedBy("has_bullet_chili", has(ModItems.BULLET_CHILI))
                .save(recipeOutput, getItemId(ModItems.BULLET_CHILI_SACK));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BULLET_CHILI, 9)
                .group(getItemId(ModItems.BULLET_CHILI).toString())
                .requires(ModItems.BULLET_CHILI_SACK)
                .unlockedBy("has_bullet_chili_sack", has(ModItems.BULLET_CHILI_SACK))
                .save(recipeOutput, getItemId(ModItems.BULLET_CHILI) + "_from_sack");

        //* Weapons *//
        // Arrow
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.CHILI_ARROW)
                .group(getItemId(ModItems.CHILI_ARROW).toString())
                .requires(Items.ARROW)
                .requires(ModItems.BULLET_CHILI)
                .unlockedBy("has_bullet_chili", has(ModItems.BULLET_CHILI))
                .save(recipeOutput, getItemId(ModItems.CHILI_ARROW));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.CHILI_ARROW)
                .group(getItemId(ModItems.CHILI_ARROW).toString())
                .requires(Items.ARROW)
                .requires(ModItems.CHILI_BULLET)
                .unlockedBy("has_chili_bullet", has(ModItems.CHILI_BULLET))
                .save(recipeOutput, getItemId(ModItems.CHILI_ARROW) + "_from_bullet");

        // Bullet
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.CHILI_BULLET)
                .group(getItemId(ModItems.CHILI_BULLET).toString())
                .requires(ModItems.BULLET_CHILI)
                .unlockedBy("has_bullet_chili", has(ModItems.BULLET_CHILI))
                .save(recipeOutput, getItemId(ModItems.CHILI_BULLET));

        // Guns
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.GUN)
                .group(getItemId(ModItems.GUN).toString())
                .pattern("i  ")
                .pattern(" i ")
                .pattern(" pb")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('p', ItemTags.PLANKS)
                .define('b', ModItems.CHILI_BULLET)
                .unlockedBy("has_chili_bullet", has(ModItems.CHILI_BULLET))
                .save(recipeOutput, getItemId(ModItems.GUN));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MACHINE_GUN)
                .group(getItemId(ModItems.MACHINE_GUN).toString())
                .pattern("n  ")
                .pattern(" n ")
                .pattern(" pb")
                .define('n', Tags.Items.INGOTS_NETHERITE)
                .define('p', ItemTags.NON_FLAMMABLE_WOOD)
                .define('b', ModItems.CHILI_BULLET)
                .unlockedBy("has_gun", has(ModItems.GUN))
                .save(recipeOutput, getItemId(ModItems.MACHINE_GUN));

        // Upgrade parts
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UPGRADE_GUN_BAYONET)
                .group(getItemId(ModItems.UPGRADE_GUN_BAYONET).toString())
                .pattern("ibg")
                .pattern("bib")
                .pattern("gbs")
                .define('b', ModItems.CHILI_BULLET)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_gun", has(ModItems.GUN))
                .save(recipeOutput, getItemId(ModItems.UPGRADE_GUN_BAYONET));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UPGRADE_GUN_BARREL)
                .group(getItemId(ModItems.UPGRADE_GUN_BARREL).toString())
                .pattern("ibg")
                .pattern("bib")
                .pattern("gbi")
                .define('b', ModItems.CHILI_BULLET)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_gun", has(ModItems.GUN))
                .save(recipeOutput, getItemId(ModItems.UPGRADE_GUN_BARREL));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UPGRADE_GUN_MECHANISM)
                .group(getItemId(ModItems.UPGRADE_GUN_MECHANISM).toString())
                .pattern("pbg")
                .pattern("bib")
                .pattern("gbi")
                .define('b', ModItems.CHILI_BULLET)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('p', ItemTags.PLANKS)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_gun", has(ModItems.GUN))
                .save(recipeOutput, getItemId(ModItems.UPGRADE_GUN_MECHANISM));
    }

    private ResourceLocation getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
