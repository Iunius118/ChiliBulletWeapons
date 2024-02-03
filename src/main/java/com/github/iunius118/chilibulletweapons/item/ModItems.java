package com.github.iunius118.chilibulletweapons.item;

import net.minecraft.world.item.Item;

public class ModItems {
    // Plants
    public static final Item BULLET_CHILI = new Item(new Item.Properties());
    public static final Item CURVED_CHILI = new Item(new Item.Properties());
    public static final Item CHILI_SEEDS = new Item(new Item.Properties());

    // Foods
    public static final Item CHILI_CHICKEN_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_CHICKEN_SANDWICH));
    public static final Item CHILI_FISH_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_FISH_SANDWICH));
    public static final Item CHILI_MEAT_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_MEAT_SANDWICH));
    public static final Item CHILI_POTATO_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_POTATO_SANDWICH));

    // Weapons
    public static final Item CHILI_BULLET = new Item(new Item.Properties());
    public static final Item GUN = new ChiliBulletGun(new Item.Properties().stacksTo(1).durability(512));
}
