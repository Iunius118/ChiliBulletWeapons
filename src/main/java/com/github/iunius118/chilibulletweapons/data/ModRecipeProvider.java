package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHILI_SEEDS)
                .requires(ModItems.CURVED_CHILI)
                .unlockedBy("has_curved_chili", has(ModItems.CURVED_CHILI))
                .save(recipeOutput, getItemId(ModItems.CHILI_SEEDS));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.CHILI_BULLET)
                .requires(ModItems.BULLET_CHILI)
                .unlockedBy("has_bullet_chili", has(ModItems.BULLET_CHILI))
                .save(recipeOutput, getItemId(ModItems.CHILI_BULLET));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.PISTOL)
                .pattern("i  ")
                .pattern(" i ")
                .pattern(" pb")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('p', ItemTags.PLANKS)
                .define('b', ModItems.CHILI_BULLET)
                .unlockedBy("has_chili_bullet", has(ModItems.CHILI_BULLET))
                .save(recipeOutput, getItemId(ModItems.PISTOL));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.RIFLE)
                .pattern("i  ")
                .pattern(" i ")
                .pattern(" pg")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('p', ItemTags.PLANKS)
                .define('g', ModItems.PISTOL)
                .unlockedBy("has_pistol", has(ModItems.PISTOL))
                .save(recipeOutput, getItemId(ModItems.RIFLE));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SHOTGUN)
                .pattern("ii ")
                .pattern(" ii")
                .pattern(" pg")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('p', ItemTags.PLANKS)
                .define('g', ModItems.PISTOL)
                .unlockedBy("has_pistol", has(ModItems.PISTOL))
                .save(recipeOutput, getItemId(ModItems.SHOTGUN));
    }

    private ResourceLocation getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }
}
