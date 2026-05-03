package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.component.DyedGunColors;
import com.github.iunius118.chilibulletweapons.component.GunContents;
import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;


public class ModRecipeProvider extends VanillaRecipeProvider {

    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        HolderLookup.RegistryLookup<Item> items = this.registries.lookupOrThrow(Registries.ITEM);

        //* Plants *//
        // Storage item
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.BULLET_CHILI_SACK)
                .group(getItemId(ModItems.BULLET_CHILI_SACK).toString())
                .pattern("bbb")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', ModItems.BULLET_CHILI)
                .unlockedBy("has_bullet_chili", has(ModItems.BULLET_CHILI))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.BULLET_CHILI, 9)
                .group(getItemId(ModItems.BULLET_CHILI).toString())
                .requires(ModItems.BULLET_CHILI_SACK)
                .unlockedBy("has_bullet_chili_sack", has(ModItems.BULLET_CHILI_SACK))
                .save(this.output, getItemId(ModItems.BULLET_CHILI) + "_from_sack");

        //* Weapons *//
        // Arrow
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.COMBAT, ModItems.CHILI_ARROW)
                .group(getItemId(ModItems.CHILI_ARROW).toString())
                .requires(Items.ARROW)
                .requires(ModItems.BULLET_CHILI)
                .unlockedBy("has_bullet_chili", has(ModItems.BULLET_CHILI))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.COMBAT, ModItems.CHILI_ARROW)
                .group(getItemId(ModItems.CHILI_ARROW).toString())
                .requires(Items.ARROW)
                .requires(ModItems.CHILI_BULLET)
                .unlockedBy("has_chili_bullet", has(ModItems.CHILI_BULLET))
                .save(this.output, getItemId(ModItems.CHILI_ARROW) + "_from_bullet");

        // Bullet
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.COMBAT, ModItems.CHILI_BULLET)
                .group(getItemId(ModItems.CHILI_BULLET).toString())
                .requires(ModItems.BULLET_CHILI)
                .unlockedBy("has_bullet_chili", has(ModItems.BULLET_CHILI))
                .save(this.output);

        // Guns
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ModItems.GUN)
                .group(getItemId(ModItems.GUN).toString())
                .pattern("i  ")
                .pattern(" i ")
                .pattern(" pb")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('p', ItemTags.PLANKS)
                .define('b', ModItems.CHILI_BULLET)
                .unlockedBy("has_chili_bullet", has(ModItems.CHILI_BULLET))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ModItems.MACHINE_GUN)
                .group(getItemId(ModItems.MACHINE_GUN).toString())
                .pattern("n  ")
                .pattern(" n ")
                .pattern(" pb")
                .define('n', Tags.Items.INGOTS_NETHERITE)
                .define('p', ModItemTags.NON_FLAMMABLE_PLANKS)
                .define('b', ModItems.CHILI_BULLET)
                .unlockedBy("has_gun", has(ModItems.GUN))
                .save(this.output);

        // Upgrade parts
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.UPGRADE_GUN_BAYONET)
                .group(getItemId(ModItems.UPGRADE_GUN_BAYONET).toString())
                .pattern("ibg")
                .pattern("bib")
                .pattern("gbs")
                .define('b', ModItems.CHILI_BULLET)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_gun", has(ModItems.GUN))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.UPGRADE_GUN_BARREL)
                .group(getItemId(ModItems.UPGRADE_GUN_BARREL).toString())
                .pattern("ibg")
                .pattern("bib")
                .pattern("gbi")
                .define('b', ModItems.CHILI_BULLET)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_gun", has(ModItems.GUN))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.UPGRADE_GUN_MECHANISM)
                .group(getItemId(ModItems.UPGRADE_GUN_MECHANISM).toString())
                .pattern("pbg")
                .pattern("bib")
                .pattern("gbi")
                .define('b', ModItems.CHILI_BULLET)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('p', ItemTags.PLANKS)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_gun", has(ModItems.GUN))
                .save(this.output);

        // Sample of customized gun
        var iconItem = getItem(Constants.Items.ICON_MAIN);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, getSampleGunTemplate())
                .pattern("i  ")
                .pattern(" # ")
                .pattern(" pb")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('#', iconItem)
                .define('p', ItemTags.PLANKS)
                .define('b', ModItems.CHILI_BULLET)
                .unlockedBy("has_cbw_icon", has(iconItem))
                .save(this.output, Constants.MOD_ID + ":sample/" + getItemId(ModItems.GUN).getPath() + "_customized");
    }

    private ItemStackTemplate getSampleGunTemplate() {
        var itemAttributeModifiers = ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 5, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, -2.8, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
        var enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        var itemEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        itemEnchantments.set(enchantments.getOrThrow(Enchantments.VANISHING_CURSE), 1);
        var dataComponentPatch = DataComponentPatch.builder()
                .set(ModDataComponents.GUN_CONTENTS, new GunContents(5, 1, 2, true))
                .set(ModDataComponents.FIXED, Unit.INSTANCE)
                .set(ModDataComponents.DYED_GUN_COLORS, new DyedGunColors(3355443, 10027008, 16764108, true))
                .set(DataComponents.WEAPON, new Weapon(1))
                .set(DataComponents.ATTRIBUTE_MODIFIERS, itemAttributeModifiers)
                .set(DataComponents.CUSTOM_NAME, Component.literal("Unguis Accipitris"))
                .set(DataComponents.ENCHANTMENTS, itemEnchantments.toImmutable())
                .build();
        return new ItemStackTemplate(ModItems.GUN, dataComponentPatch);
    }

    private Item getItem(Identifier id) {
        return BuiltInRegistries.ITEM.getValue(id);
    }

    private Identifier getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput output) {
            return new ModRecipeProvider(registryLookup, output);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }
}
