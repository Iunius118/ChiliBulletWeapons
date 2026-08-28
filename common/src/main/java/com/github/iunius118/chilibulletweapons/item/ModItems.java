package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import net.minecraft.world.item.*;

import java.util.HashMap;
import java.util.Map;

public class ModItems {
    public static final Map<Item, Float> COMPOSTABLES = new HashMap<>();
    public static final Map<Item, Integer> FURNACE_FUELS = new HashMap<>();

    // Plants
    public static final Item CHILI_SEEDS = new ItemNameBlockItem(ModBlocks.CHILI_PEPPER, createMainItemProperties());
    public static final Item CURVED_GREEN_CHILI = new Item(createMainItemProperties());
    public static final Item BULLET_CHILI = new Item(createMainItemProperties());
    public static final Item CURVED_CHILI = new Item(createMainItemProperties());
    public static final Item CURVED_CHILI_STRING = 
            new BlockItem(ModBlocks.CURVED_CHILI_STRING, createMainItemProperties());
    public static final Item DRIED_CURVED_CHILI = new Item(createMainItemProperties());
    public static final Item BULLET_CHILI_SACK = new Item(createMainItemProperties());
    public static final Item CURVED_CHILI_SACK = new Item(createMainItemProperties());
    public static final Item POTTED_CHILI_PEPPER_FLOWERING = 
            new BlockItem(ModBlocks.POTTED_CHILI_PEPPER_FLOWERING, createMainItemProperties());
    public static final Item POTTED_CHILI_PEPPER_GREEN = 
            new BlockItem(ModBlocks.POTTED_CHILI_PEPPER_GREEN, createMainItemProperties());
    public static final Item POTTED_CHILI_PEPPER_RED = 
            new BlockItem(ModBlocks.POTTED_CHILI_PEPPER_RED, createMainItemProperties());

    // Fuel
    public static final Item CHILI_BIOFUEL = new Item(createMainItemProperties());

    // Foods
    public static final Item HOT_SAUCE = 
            new HotSauceItem(createMainItemProperties().craftRemainder(Items.GLASS_BOTTLE),
                    HotSauceItem.HOT_SAUCE_DURATION, HotSauceItem.RED_HOT_SAUCE_COLOR);
    public static final Item HOT_SAUCE_BARREL = new BlockItem(ModBlocks.HOT_SAUCE_BARREL, createMainItemProperties());
    public static final Item GREEN_HOT_SAUCE = 
            new HotSauceItem(createMainItemProperties().craftRemainder(Items.GLASS_BOTTLE),
                    HotSauceItem.HOT_SAUCE_DURATION, HotSauceItem.GREEN_HOT_SAUCE_COLOR);
    public static final Item PICKLED_GREEN_CHILI = 
            new Item(createMainItemProperties().food(ModFoods.PICKLED_GREEN_CHILI));
    public static final Item CHILI_CHICKEN_SANDWICH = 
            new Item(createMainItemProperties().food(ModFoods.CHILI_CHICKEN_SANDWICH));
    public static final Item CHILI_FISH_SANDWICH = 
            new Item(createMainItemProperties().food(ModFoods.CHILI_FISH_SANDWICH));
    public static final Item CHILI_MEAT_SANDWICH = 
            new Item(createMainItemProperties().food(ModFoods.CHILI_MEAT_SANDWICH));
    public static final Item CHILI_POTATO_SANDWICH = 
            new Item(createMainItemProperties().food(ModFoods.CHILI_POTATO_SANDWICH));
    public static final Item HALF_CHILI_CHICKEN_SANDWICH = 
            new Item(createMainItemProperties().food(ModFoods.HALF_CHILI_CHICKEN_SANDWICH));
    public static final Item HALF_CHILI_FISH_SANDWICH = 
            new Item(createMainItemProperties().food(ModFoods.HALF_CHILI_FISH_SANDWICH));
    public static final Item HALF_CHILI_MEAT_SANDWICH = 
            new Item(createMainItemProperties().food(ModFoods.HALF_CHILI_MEAT_SANDWICH));
    public static final Item HALF_CHILI_POTATO_SANDWICH = 
            new Item(createMainItemProperties().food(ModFoods.HALF_CHILI_POTATO_SANDWICH));
    public static final Item PASTA_OIL_AND_CHILI = 
            new BowlFoodItem(createMainItemProperties().stacksTo(1).food(ModFoods.PASTA_OIL_AND_CHILI));
    public static final Item FRIED_CHILI_PEPPER = 
            new Item(createMainItemProperties().food(ModFoods.FRIED_CHILI_PEPPER));
    public static final Item CHILI_CHOCOLATE = 
            new Item(createMainItemProperties().food(ModFoods.CHILI_CHOCOLATE));
    public static final Item CHILI_CHOCOLATE_CHICKEN = 
            new Item(createMainItemProperties().food(ModFoods.CHILI_CHOCOLATE_CHICKEN));

    // Weapons
    public static final Item CHILI_ARROW = new ChiliArrowItem(createMainItemProperties());
    public static final Item CHILI_BULLET = new Item(createMainItemProperties());
    public static final Item UPGRADE_GUN_BAYONET = new UpgradeGunBayonet(createMainItemProperties());
    public static final Item UPGRADE_GUN_BARREL = new UpgradeGunBarrel(createMainItemProperties());
    public static final Item UPGRADE_GUN_MECHANISM = new UpgradeGunMechanism(createMainItemProperties());
    public static final Item GUN = new ChiliBulletGun(createMainItemProperties().stacksTo(1).durability(512));
    public static final Item BAYONETED_GUN = 
            new ChiliBulletGunBayoneted(createMainItemProperties().stacksTo(1).durability(512));
    public static final Item MACHINE_GUN = 
            new ChiliBulletMachineGun(createMainItemProperties().stacksTo(1).durability(64).fireResistant());

    // Misc.
    public static final Item CAPSAICIN_POWDER = new Item(new Item.Properties());

    // Creative tab icon
    // This will not be added to creative tabs
    public static final Item ICON_MAIN = new Item(new Item.Properties());

    private static Item.Properties createMainItemProperties() {
        return new Item.Properties().tab(ModCreativeModeTabs.MAIN);
    }

    static {
        // Add compostable items
        COMPOSTABLES.put(ModItems.CHILI_SEEDS, 0.3F);
        COMPOSTABLES.put(ModItems.CURVED_GREEN_CHILI, 0.3F);
        COMPOSTABLES.put(ModItems.BULLET_CHILI, 0.3F);
        COMPOSTABLES.put(ModItems.CURVED_CHILI, 0.3F);
        COMPOSTABLES.put(ModItems.CURVED_CHILI_STRING, 0.85F);
        COMPOSTABLES.put(ModItems.DRIED_CURVED_CHILI, 0.3F);
        COMPOSTABLES.put(ModItems.PICKLED_GREEN_CHILI, 0.3F);
        COMPOSTABLES.put(ModItems.CHILI_POTATO_SANDWICH, 0.85F);
        COMPOSTABLES.put(ModItems.HALF_CHILI_POTATO_SANDWICH, 0.5F);
        COMPOSTABLES.put(ModItems.FRIED_CHILI_PEPPER, 0.3F);
        COMPOSTABLES.put(ModItems.CHILI_CHOCOLATE, 0.3F);

        // Add fuel items
        FURNACE_FUELS.put(CHILI_BIOFUEL, 1600);
    }
}
