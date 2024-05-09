package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import net.minecraft.world.item.*;

public class ModItems {
    // Plants
    public static final Item CHILI_SEEDS = new ItemNameBlockItem(ModBlocks.CHILI_PEPPER, new Item.Properties());
    public static final Item BULLET_CHILI = new Item(new Item.Properties());
    public static final Item CURVED_CHILI = new Item(new Item.Properties());
    public static final Item CURVED_CHILI_STRING = new BlockItem(ModBlocks.CURVED_CHILI_STRING, new Item.Properties());
    public static final Item DRIED_CURVED_CHILI = new Item(new Item.Properties());
    public static final Item BULLET_CHILI_SACK = new Item(new Item.Properties());
    public static final Item CURVED_CHILI_SACK = new Item(new Item.Properties());

    // Foods
    public static final Item HOT_SAUCE = new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE));
    public static final Item HOT_SAUCE_BARREL = new BlockItem(ModBlocks.HOT_SAUCE_BARREL, new Item.Properties());
    public static final Item CHILI_CHICKEN_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_CHICKEN_SANDWICH));
    public static final Item CHILI_FISH_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_FISH_SANDWICH));
    public static final Item CHILI_MEAT_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_MEAT_SANDWICH));
    public static final Item CHILI_POTATO_SANDWICH = new Item(new Item.Properties().food(ModFoods.CHILI_POTATO_SANDWICH));
    public static final Item HALF_CHILI_CHICKEN_SANDWICH = new Item(new Item.Properties().food(ModFoods.HALF_CHILI_CHICKEN_SANDWICH));
    public static final Item HALF_CHILI_FISH_SANDWICH = new Item(new Item.Properties().food(ModFoods.HALF_CHILI_FISH_SANDWICH));
    public static final Item HALF_CHILI_MEAT_SANDWICH = new Item(new Item.Properties().food(ModFoods.HALF_CHILI_MEAT_SANDWICH));
    public static final Item HALF_CHILI_POTATO_SANDWICH = new Item(new Item.Properties().food(ModFoods.HALF_CHILI_POTATO_SANDWICH));
    public static final Item PASTA_OIL_AND_CHILI = new BowlFoodItem(new Item.Properties().stacksTo(1).food(ModFoods.PASTA_OIL_AND_CHILI));
    public static final Item FRIED_CHILI_PEPPER = new Item(new Item.Properties().food(ModFoods.FRIED_CHILI_PEPPER));

    // Weapons
    public static final Item CHILI_ARROW = new ChiliArrowItem(new Item.Properties());
    public static final Item CHILI_BULLET = new Item(new Item.Properties());
    public static final Item UPGRADE_GUN_BAYONET = new UpgradeGunBayonet(new Item.Properties());
    public static final Item UPGRADE_GUN_BARREL = new UpgradeGunBarrel(new Item.Properties());
    public static final Item UPGRADE_GUN_MECHANISM = new UpgradeGunMechanism(new Item.Properties());
    public static final Item GUN = new ChiliBulletGun(new Item.Properties().stacksTo(1).durability(512));
    public static final Item BAYONETED_GUN = new ChiliBulletGunBayoneted(new Item.Properties().stacksTo(1).durability(512));
    public static final Item MACHINE_GUN = new ChiliBulletMachineGun(new Item.Properties().stacksTo(1).durability(512).rarity(Rarity.RARE).fireResistant());
}
