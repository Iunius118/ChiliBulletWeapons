package com.github.iunius118.chilibulletweapons.tags;

import com.github.iunius118.chilibulletweapons.CommonClass;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> C_INGOTS_IRON = makeCTag("ingots/iron");
    public static final TagKey<Item> C_INGOTS_NETHERITE = makeCTag("ingots/netherite");

    public static final TagKey<Item> CHILI_BIOMASS = makeCBWTag("chili_biomass");
    public static final TagKey<Item> NON_FLAMMABLE_PLANKS = makeCBWTag("non_flammable_planks");

    private static TagKey<Item> makeCBWTag(String id) {
        return TagKey.create(Registries.ITEM, CommonClass.modLocation(id));
    }

    private static TagKey<Item> makeCTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
    }
}
