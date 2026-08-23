package com.github.iunius118.chilibulletweapons.registry;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModCreativeModeTabs;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.platform.Services;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class ModRegistries {
    public static ModRegistryObject<Item> iconMain;

    public static void registerGameObjects() {
        // Blocks
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.BLOCK, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.Blocks.CHILI_PEPPER, () -> ModBlocks.CHILI_PEPPER);
                    r.register(Constants.Blocks.CURVED_CHILI_STRING, () -> ModBlocks.CURVED_CHILI_STRING);
                    r.register(Constants.Blocks.POTTED_CHILI_PEPPER_FLOWERING,
                            () -> ModBlocks.POTTED_CHILI_PEPPER_FLOWERING);
                    r.register(Constants.Blocks.POTTED_CHILI_PEPPER_GREEN, () -> ModBlocks.POTTED_CHILI_PEPPER_GREEN);
                    r.register(Constants.Blocks.POTTED_CHILI_PEPPER_RED, () -> ModBlocks.POTTED_CHILI_PEPPER_RED);
                    r.register(Constants.Blocks.HOT_SAUCE_BARREL, () -> ModBlocks.HOT_SAUCE_BARREL);
                });
        // Items
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.ITEM, Constants.MOD_ID)
                .registerObjects(r -> {
                    // Plants
                    r.register(Constants.Items.CHILI_SEEDS, () -> ModItems.CHILI_SEEDS);
                    r.register(Constants.Items.CURVED_GREEN_CHILI, () -> ModItems.CURVED_GREEN_CHILI);
                    r.register(Constants.Items.BULLET_CHILI, () -> ModItems.BULLET_CHILI);
                    r.register(Constants.Items.CURVED_CHILI, () -> ModItems.CURVED_CHILI);
                    r.register(Constants.Items.CURVED_CHILI_STRING, () -> ModItems.CURVED_CHILI_STRING);
                    r.register(Constants.Items.DRIED_CURVED_CHILI, () -> ModItems.DRIED_CURVED_CHILI);
                    r.register(Constants.Items.BULLET_CHILI_SACK, () -> ModItems.BULLET_CHILI_SACK);
                    r.register(Constants.Items.CURVED_CHILI_SACK, () -> ModItems.CURVED_CHILI_SACK);
                    r.register(Constants.Items.POTTED_CHILI_PEPPER_FLOWERING,
                            () -> ModItems.POTTED_CHILI_PEPPER_FLOWERING);
                    r.register(Constants.Items.POTTED_CHILI_PEPPER_GREEN, () -> ModItems.POTTED_CHILI_PEPPER_GREEN);
                    r.register(Constants.Items.POTTED_CHILI_PEPPER_RED, () -> ModItems.POTTED_CHILI_PEPPER_RED);
                    // Fuel
                    r.register(Constants.Items.CHILI_BIOFUEL, () -> ModItems.CHILI_BIOFUEL);
                    // Foods
                    r.register(Constants.Items.HOT_SAUCE, () -> ModItems.HOT_SAUCE);
                    r.register(Constants.Items.HOT_SAUCE_BARREL, () -> ModItems.HOT_SAUCE_BARREL);
                    r.register(Constants.Items.GREEN_HOT_SAUCE, () -> ModItems.GREEN_HOT_SAUCE);
                    r.register(Constants.Items.PICKLED_GREEN_CHILI, () -> ModItems.PICKLED_GREEN_CHILI);
                    r.register(Constants.Items.CHILI_CHICKEN_SANDWICH, () -> ModItems.CHILI_CHICKEN_SANDWICH);
                    r.register(Constants.Items.CHILI_FISH_SANDWICH, () -> ModItems.CHILI_FISH_SANDWICH);
                    r.register(Constants.Items.CHILI_MEAT_SANDWICH, () -> ModItems.CHILI_MEAT_SANDWICH);
                    r.register(Constants.Items.CHILI_POTATO_SANDWICH, () -> ModItems.CHILI_POTATO_SANDWICH);
                    r.register(Constants.Items.HALF_CHILI_CHICKEN_SANDWICH, () -> ModItems.HALF_CHILI_CHICKEN_SANDWICH);
                    r.register(Constants.Items.HALF_CHILI_FISH_SANDWICH, () -> ModItems.HALF_CHILI_FISH_SANDWICH);
                    r.register(Constants.Items.HALF_CHILI_MEAT_SANDWICH, () -> ModItems.HALF_CHILI_MEAT_SANDWICH);
                    r.register(Constants.Items.HALF_CHILI_POTATO_SANDWICH, () -> ModItems.HALF_CHILI_POTATO_SANDWICH);
                    r.register(Constants.Items.PASTA_OIL_AND_CHILI, () -> ModItems.PASTA_OIL_AND_CHILI);
                    r.register(Constants.Items.FRIED_CHILI_PEPPER, () -> ModItems.FRIED_CHILI_PEPPER);
                    r.register(Constants.Items.CHILI_CHOCOLATE, () -> ModItems.CHILI_CHOCOLATE);
                    r.register(Constants.Items.CHILI_CHOCOLATE_CHICKEN, () -> ModItems.CHILI_CHOCOLATE_CHICKEN);
                    // Weapons
                    r.register(Constants.Items.CHILI_ARROW, () -> ModItems.CHILI_ARROW);
                    r.register(Constants.Items.CHILI_BULLET, () -> ModItems.CHILI_BULLET);
                    r.register(Constants.Items.UPGRADE_GUN_BAYONET, () -> ModItems.UPGRADE_GUN_BAYONET);
                    r.register(Constants.Items.UPGRADE_GUN_BARREL, () -> ModItems.UPGRADE_GUN_BARREL);
                    r.register(Constants.Items.UPGRADE_GUN_MECHANISM, () -> ModItems.UPGRADE_GUN_MECHANISM);
                    r.register(Constants.Items.GUN, () -> ModItems.GUN);
                    r.register(Constants.Items.BAYONETED_GUN, () -> ModItems.BAYONETED_GUN);
                    r.register(Constants.Items.MACHINE_GUN, () -> ModItems.MACHINE_GUN);
                    // Misc.
                    r.register(Constants.Items.CAPSAICIN_POWDER, () -> ModItems.CAPSAICIN_POWDER);
                    // Creative tab icon
                    iconMain = r.register(Constants.Items.ICON_MAIN, () -> ModItems.ICON_MAIN);
                });
        // Sound events
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.SoundEvents.CHILI_PEPPER_PICK_CHILI_PEPPERS,
                            () -> ModSoundEvents.CHILI_PEPPER_PICK_CHILI_PEPPERS);
                    r.register(Constants.SoundEvents.GUN_SHOOT, () -> ModSoundEvents.GUN_SHOOT);
                    r.register(Constants.SoundEvents.GUN_ACTION_OPEN, () -> ModSoundEvents.GUN_ACTION_OPEN);
                    r.register(Constants.SoundEvents.GUN_ACTION_CLOSE, () -> ModSoundEvents.GUN_ACTION_CLOSE);
                    r.register(Constants.SoundEvents.GUN_UPGRADE, () -> ModSoundEvents.GUN_UPGRADE);
                });
        // Entity types
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.ENTITY_TYPE, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.EntityTypes.CHILI_ARROW, () -> ModEntityTypes.CHILI_ARROW);
                    r.register(Constants.EntityTypes.CHILI_BULLET, () -> ModEntityTypes.CHILI_BULLET);
                });
        // Creative mode tabs
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.CreativeModeTabs.MAIN, () -> ModCreativeModeTabs.MAIN);
                });
    }
}
