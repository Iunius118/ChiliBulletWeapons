package com.github.iunius118.chilibulletweapons.tags;

import com.github.iunius118.chilibulletweapons.CommonClass;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> FORGE_CROPS = makeForgeTag("crops");
    public static final TagKey<Item> FORGE_CROPS_CHILI = makeForgeTag("crops/chili");
    public static final TagKey<Item> FORGE_SEEDS = makeForgeTag("seeds");
    public static final TagKey<Item> FORGE_SEEDS_CHILI = makeForgeTag("seeds/chili");

    public static final TagKey<Item> COMMON_CROPS = makeCommonTag("crops");
    public static final TagKey<Item> COMMON_CROPS_CHILI = makeCommonTag("crops/chili");
    public static final TagKey<Item> COMMON_SEEDS = makeCommonTag("seeds");
    public static final TagKey<Item> COMMON_SEEDS_CHILI = makeCommonTag("seeds/chili");

    public static final TagKey<Item> FOODS_CHILI_PEPPERS = makeModTag("foods/chili_peppers");
    public static final TagKey<Item> FOODS_RED_CHILI_PEPPER = makeModTag("foods/chili_peppers/red");
    public static final TagKey<Item> FOODS_GREEN_CHILI_PEPPER = makeModTag("foods/chili_peppers/green");
    public static final TagKey<Item> FOODS_CHILI_SEASONING = makeModTag("foods/chili_seasoning");
    public static final TagKey<Item> FOODS_COOKED_FISH = makeModTag("foods/cooked_fish");
    public static final TagKey<Item> FOODS_COOKED_MEAT = makeModTag("foods/cooked_meat");
    public static final TagKey<Item> CHILI_BIOMASS = makeModTag("chili_biomass");
    public static final TagKey<Item> NON_FLAMMABLE_PLANKS = makeModTag("non_flammable_planks");

    private static TagKey<Item> makeModTag(String id) {
        return TagKey.create(Registry.ITEM_REGISTRY, CommonClass.modLocation(id));
    }

    private static TagKey<Item> makeCommonTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", path));
    }

    private static TagKey<Item> makeForgeTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", path));
    }
}
