package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;

public class ModItems {
    // Plants
    public static final Item BULLET_CHILI = new Item(new Item.Properties());
    public static final Item CURVED_CHILI = new Item(new Item.Properties());
    public static final Item CHILI_SEEDS = new ItemNameBlockItem(ModBlocks.CHILI_PEPPER, new Item.Properties());

    // Foods
    public static final Item CHILI_CHICKEN_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_CHICKEN_SANDWICH));
    public static final Item CHILI_FISH_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_FISH_SANDWICH));
    public static final Item CHILI_MEAT_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_MEAT_SANDWICH));
    public static final Item CHILI_POTATO_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_POTATO_SANDWICH));
    public static final Item HALF_CHILI_CHICKEN_SANDWICH = new Item(new Item.Properties().food(ModFoods.HALF_CHILI_CHICKEN_SANDWICH));
    public static final Item HALF_CHILI_FISH_SANDWICH = new Item(new Item.Properties().food(ModFoods.HALF_CHILI_FISH_SANDWICH));
    public static final Item HALF_CHILI_MEAT_SANDWICH = new Item(new Item.Properties().food(ModFoods.HALF_CHILI_MEAT_SANDWICH));
    public static final Item HALF_CHILI_POTATO_SANDWICH = new Item(new Item.Properties().food(ModFoods.HALF_CHILI_POTATO_SANDWICH));
    public static final Item FRIED_CHILI_PEPPER = new Item(new Item.Properties().food(ModFoods.FRIED_CHILI_PEPPER));

    // Weapons
    public static final Item CHILI_BULLET = new Item(new Item.Properties());
    public static final Item GUN = new ChiliBulletGun(new Item.Properties().stacksTo(1).durability(512));
    public static final Item MACHINE_GUN = new ChiliBulletMachineGun(new Item.Properties().stacksTo(1).durability(512).rarity(Rarity.RARE).fireResistant());
}
