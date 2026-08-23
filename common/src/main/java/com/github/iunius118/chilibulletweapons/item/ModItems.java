package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import net.minecraft.world.item.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final Map<Item, Float> COMPOSTABLES = new HashMap<>();
    public static final Map<Item, Integer> FURNACE_FUELS = new HashMap<>();

    // Plants
    public static final Item CHILI_SEEDS = add(new ItemNameBlockItem(ModBlocks.CHILI_PEPPER, new Item.Properties()));
    public static final Item CURVED_GREEN_CHILI = add(new Item(new Item.Properties()));
    public static final Item BULLET_CHILI = add(new Item(new Item.Properties()));
    public static final Item CURVED_CHILI = add(new Item(new Item.Properties()));
    public static final Item CURVED_CHILI_STRING = add(
            new BlockItem(ModBlocks.CURVED_CHILI_STRING, new Item.Properties()));
    public static final Item DRIED_CURVED_CHILI = add(new Item(new Item.Properties()));
    public static final Item BULLET_CHILI_SACK = add(new Item(new Item.Properties()));
    public static final Item CURVED_CHILI_SACK = add(new Item(new Item.Properties()));
    public static final Item POTTED_CHILI_PEPPER_FLOWERING = add(
            new BlockItem(ModBlocks.POTTED_CHILI_PEPPER_FLOWERING, new Item.Properties()));
    public static final Item POTTED_CHILI_PEPPER_GREEN = add(
            new BlockItem(ModBlocks.POTTED_CHILI_PEPPER_GREEN, new Item.Properties()));
    public static final Item POTTED_CHILI_PEPPER_RED = add(
            new BlockItem(ModBlocks.POTTED_CHILI_PEPPER_RED, new Item.Properties()));

    // Fuel
    public static final Item CHILI_BIOFUEL = add(new Item(new Item.Properties()));

    // Foods
    public static final Item HOT_SAUCE = add(
            new HotSauceItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE),
                    HotSauceItem.HOT_SAUCE_DURATION, HotSauceItem.RED_HOT_SAUCE_COLOR));
    public static final Item HOT_SAUCE_BARREL = add(new BlockItem(ModBlocks.HOT_SAUCE_BARREL, new Item.Properties()));
    public static final Item GREEN_HOT_SAUCE = add(
            new HotSauceItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE),
                    HotSauceItem.HOT_SAUCE_DURATION, HotSauceItem.GREEN_HOT_SAUCE_COLOR));
    public static final Item PICKLED_GREEN_CHILI = add(
            new Item(new Item.Properties().food(ModFoods.PICKLED_GREEN_CHILI)));
    public static final Item CHILI_CHICKEN_SANDWICH = add(
            new Item(new Item.Properties().food(ModFoods.CHILI_CHICKEN_SANDWICH)));
    public static final Item CHILI_FISH_SANDWICH = add(
            new Item(new Item.Properties().food(ModFoods.CHILI_FISH_SANDWICH)));
    public static final Item CHILI_MEAT_SANDWICH = add(
            new Item(new Item.Properties().food(ModFoods.CHILI_MEAT_SANDWICH)));
    public static final Item CHILI_POTATO_SANDWICH = add(
            new Item(new Item.Properties().food(ModFoods.CHILI_POTATO_SANDWICH)));
    public static final Item HALF_CHILI_CHICKEN_SANDWICH = add(
            new Item(new Item.Properties().food(ModFoods.HALF_CHILI_CHICKEN_SANDWICH)));
    public static final Item HALF_CHILI_FISH_SANDWICH = add(
            new Item(new Item.Properties().food(ModFoods.HALF_CHILI_FISH_SANDWICH)));
    public static final Item HALF_CHILI_MEAT_SANDWICH = add(
            new Item(new Item.Properties().food(ModFoods.HALF_CHILI_MEAT_SANDWICH)));
    public static final Item HALF_CHILI_POTATO_SANDWICH = add(
            new Item(new Item.Properties().food(ModFoods.HALF_CHILI_POTATO_SANDWICH)));
    public static final Item PASTA_OIL_AND_CHILI = add(
            new BowlFoodItem(new Item.Properties().stacksTo(1).food(ModFoods.PASTA_OIL_AND_CHILI)));
    public static final Item FRIED_CHILI_PEPPER = add(
            new Item(new Item.Properties().food(ModFoods.FRIED_CHILI_PEPPER)));
    public static final Item CHILI_CHOCOLATE = add(
            new Item(new Item.Properties().food(ModFoods.CHILI_CHOCOLATE)));
    public static final Item CHILI_CHOCOLATE_CHICKEN = add(
            new Item(new Item.Properties().food(ModFoods.CHILI_CHOCOLATE_CHICKEN)));

    // Weapons
    public static final Item CHILI_ARROW = add(new ChiliArrowItem(new Item.Properties()));
    public static final Item CHILI_BULLET = add(new Item(new Item.Properties()));
    public static final Item UPGRADE_GUN_BAYONET = add(new UpgradeGunBayonet(new Item.Properties()));
    public static final Item UPGRADE_GUN_BARREL = add(new UpgradeGunBarrel(new Item.Properties()));
    public static final Item UPGRADE_GUN_MECHANISM = add(new UpgradeGunMechanism(new Item.Properties()));
    public static final Item GUN = add(new ChiliBulletGun(new Item.Properties().stacksTo(1).durability(512)));
    public static final Item BAYONETED_GUN = add(
            new ChiliBulletGunBayoneted(new Item.Properties().stacksTo(1).durability(512)));
    public static final Item MACHINE_GUN = add(
            new ChiliBulletMachineGun(new Item.Properties().stacksTo(1).durability(64).fireResistant()));

    // Misc.
    public static final Item CAPSAICIN_POWDER = new Item(new Item.Properties());

    // Creative tab icon
    // This will not be added to creative tabs
    public static final Item ICON_MAIN = new Item(new Item.Properties());

    private static Item add(Item item) {
        ITEMS.add(item);
        return item;
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
