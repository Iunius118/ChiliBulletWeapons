package com.github.iunius118.chilibulletweapons.tags;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> FORGE_CROPS = makeForgeTag("crops");
    public static final TagKey<Item> FORGE_CROPS_CHILI = makeForgeTag("crops/chili");
    public static final TagKey<Item> FORGE_SEEDS = makeForgeTag("seeds");
    public static final TagKey<Item> FORGE_SEEDS_CHILI = makeForgeTag("seeds/chili");

    public static final TagKey<Item> FOODS_COOKED_FISH = makeModTag("foods/cooked_fish");
    public static final TagKey<Item> FOODS_COOKED_MEAT = makeModTag("foods/cooked_meat");

    private static TagKey<Item> makeModTag(String id) {
        return ItemTags.create(new ResourceLocation(ChiliBulletWeapons.MOD_ID, id));
    }

    private static TagKey<Item> makeForgeTag(String path) {
        return ItemTags.create(new ResourceLocation("forge", path));
    }
}