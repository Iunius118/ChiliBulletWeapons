package com.github.iunius118.chilibulletweapons.tags;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> COMMON_CROPS = makeCommonTag("crops");
    public static final TagKey<Item> COMMON_CROPS_CHILI = makeCommonTag("crops/chili");
    public static final TagKey<Item> COMMON_SEEDS = makeCommonTag("seeds");
    public static final TagKey<Item> COMMON_SEEDS_CHILI = makeCommonTag("seeds/chili");

    public static final TagKey<Item> FOODS_CHILI_PEPPER = makeModTag("foods/chili_pepper");
    public static final TagKey<Item> FOODS_CHILI_SEASONING = makeModTag("foods/chili_seasoning");
    public static final TagKey<Item> FOODS_COOKED_FISH = makeModTag("foods/cooked_fish");
    public static final TagKey<Item> FOODS_COOKED_MEAT = makeModTag("foods/cooked_meat");

    private static TagKey<Item> makeModTag(String id) {
        return TagKey.create(Registries.ITEM, ChiliBulletWeapons.makeId(id));
    }

    private static TagKey<Item> makeCommonTag(String path) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("c", path));
    }
}
