package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import net.minecraft.world.item.*;

public class ModItems {
    // Plants
    public static final Item CHILI_SEEDS = new ItemNameBlockItem(ModBlocks.CHILI_PEPPER, new Item.Properties());
    public static final Item CURVED_GREEN_CHILI = new Item(new Item.Properties());
    public static final Item BULLET_CHILI = new Item(new Item.Properties());
    public static final Item CURVED_CHILI = new Item(new Item.Properties());
    public static final Item CURVED_CHILI_STRING = new BlockItem(ModBlocks.CURVED_CHILI_STRING, new Item.Properties());
    public static final Item DRIED_CURVED_CHILI = new Item(new Item.Properties());
    public static final Item BULLET_CHILI_SACK = new Item(new Item.Properties());
    public static final Item CURVED_CHILI_SACK = new Item(new Item.Properties());
    public static final Item POTTED_CHILI_PEPPER_FLOWERING = new BlockItem(ModBlocks.POTTED_CHILI_PEPPER_FLOWERING, new Item.Properties());
    public static final Item POTTED_CHILI_PEPPER_GREEN = new BlockItem(ModBlocks.POTTED_CHILI_PEPPER_GREEN, new Item.Properties());
    public static final Item POTTED_CHILI_PEPPER_RED = new BlockItem(ModBlocks.POTTED_CHILI_PEPPER_RED, new Item.Properties());

    // Fuel
    public static final Item CHILI_BIOFUEL = new Item(new Item.Properties());

    // Foods
    public static final Item HOT_SAUCE = new HotSauce(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE), HotSauce.HOT_SAUCE_DURATION, HotSauce.RED_HOT_SAUCE_COLOR);
    public static final Item HOT_SAUCE_BARREL = new BlockItem(ModBlocks.HOT_SAUCE_BARREL, new Item.Properties());
    public static final Item GREEN_HOT_SAUCE = new HotSauce(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE), HotSauce.HOT_SAUCE_DURATION, HotSauce.GREEN_HOT_SAUCE_COLOR);
    public static final Item PICKLED_GREEN_CHILI = new Item(new Item.Properties().food(ModFoods.PICKLED_GREEN_CHILI));
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
    public static final Item CHILI_CHOCOLATE = new Item(new Item.Properties().food(ModFoods.CHILI_CHOCOLATE));
    public static final Item CHILI_CHOCOLATE_CHICKEN = new Item(new Item.Properties().food(ModFoods.CHILI_CHOCOLATE_CHICKEN));

    // Weapons
    public static final Item CHILI_ARROW = new ChiliArrowItem(new Item.Properties());
    public static final Item CHILI_BULLET = new Item(new Item.Properties());
    public static final Item UPGRADE_GUN_BAYONET = new UpgradeGunBayonet(new Item.Properties());
    public static final Item UPGRADE_GUN_BARREL = new UpgradeGunBarrel(new Item.Properties());
    public static final Item UPGRADE_GUN_MECHANISM = new UpgradeGunMechanism(new Item.Properties());
    public static final Item GUN = new ChiliBulletGun(new Item.Properties().stacksTo(1).durability(512));
    public static final Item BAYONETED_GUN = new ChiliBulletGunBayoneted(new Item.Properties().stacksTo(1).durability(512));
    public static final Item MACHINE_GUN = new ChiliBulletMachineGun(new Item.Properties().stacksTo(1).durability(512).rarity(Rarity.RARE).fireResistant());

    // Misc.
    public static final Item CAPSAICIN_POWDER = new Item(new Item.Properties());

    // Creative tab icon
    // This will not be added to creative tabs
    public static final Item ICON_MAIN = new Item(new Item.Properties());
}
