package com.github.iunius118.chilibulletweapons.registry;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.entity.ChiliArrow;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModCreativeModeTabs;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRegistries {
    public static void registerGameObjects(IEventBus modEventBus) {
        registerBlocks(modEventBus);
        registerItems(modEventBus);
        registerSoundEvents(modEventBus);
        registerEntityTypes(modEventBus);
        registerCreativeModeTabs(modEventBus);
    }

    private static void registerBlocks(IEventBus modEventBus) {
        var blockRegister = DeferredRegister.createBlocks(ChiliBulletWeapons.MOD_ID);

        blockRegister.register("chili_pepper", () -> ModBlocks.CHILI_PEPPER);
        blockRegister.register("curved_chili_string", () -> ModBlocks.CURVED_CHILI_STRING);
        blockRegister.register("hot_sauce_barrel", () -> ModBlocks.HOT_SAUCE_BARREL);

        blockRegister.register(modEventBus);
    }

    private static void registerItems(IEventBus modEventBus) {
        var itemRegister = DeferredRegister.createItems(ChiliBulletWeapons.MOD_ID);

        // Plants
        itemRegister.register("chili_seeds", () -> ModItems.CHILI_SEEDS);
        itemRegister.register("bullet_chili", () -> ModItems.BULLET_CHILI);
        itemRegister.register("curved_chili", () -> ModItems.CURVED_CHILI);
        itemRegister.register("curved_chili_string", () -> ModItems.CURVED_CHILI_STRING);
        itemRegister.register("dried_curved_chili", () -> ModItems.DRIED_CURVED_CHILI);
        itemRegister.register("bullet_chili_sack", () -> ModItems.BULLET_CHILI_SACK);
        itemRegister.register("curved_chili_sack", () -> ModItems.CURVED_CHILI_SACK);
        // Foods
        itemRegister.register("hot_sauce", () -> ModItems.HOT_SAUCE);
        itemRegister.register("hot_sauce_barrel", () -> ModItems.HOT_SAUCE_BARREL);
        itemRegister.register("chili_chicken_sandwich", () -> ModItems.CHILI_CHICKEN_SANDWICH);
        itemRegister.register("chili_fish_sandwich", () -> ModItems.CHILI_FISH_SANDWICH);
        itemRegister.register("chili_meat_sandwich", () -> ModItems.CHILI_MEAT_SANDWICH);
        itemRegister.register("chili_potato_sandwich", () -> ModItems.CHILI_POTATO_SANDWICH);
        itemRegister.register("half_chili_chicken_sandwich", () -> ModItems.HALF_CHILI_CHICKEN_SANDWICH);
        itemRegister.register("half_chili_fish_sandwich", () -> ModItems.HALF_CHILI_FISH_SANDWICH);
        itemRegister.register("half_chili_meat_sandwich", () -> ModItems.HALF_CHILI_MEAT_SANDWICH);
        itemRegister.register("half_chili_potato_sandwich", () -> ModItems.HALF_CHILI_POTATO_SANDWICH);
        itemRegister.register("pasta_oil_and_chili", () -> ModItems.PASTA_OIL_AND_CHILI);
        itemRegister.register("fried_chili_pepper", () -> ModItems.FRIED_CHILI_PEPPER);
        // Weapons
        itemRegister.register("chili_arrow",() -> ModItems.CHILI_ARROW);
        itemRegister.register("chili_bullet", () -> ModItems.CHILI_BULLET);
        itemRegister.register("upgrade_gun_bayonet", () -> ModItems.UPGRADE_GUN_BAYONET);
        itemRegister.register("upgrade_gun_barrel", () -> ModItems.UPGRADE_GUN_BARREL);
        itemRegister.register("upgrade_gun_mechanism", () -> ModItems.UPGRADE_GUN_MECHANISM);
        itemRegister.register("gun", () -> ModItems.GUN);
        itemRegister.register("bayoneted_gun", () -> ModItems.BAYONETED_GUN);
        itemRegister.register("machine_gun", () -> ModItems.MACHINE_GUN);
        // Misc.
        itemRegister.register("capsaicin_powder", () -> ModItems.CAPSAICIN_POWDER);

        itemRegister.register(modEventBus);
    }

    private static void registerSoundEvents(IEventBus modEventBus) {
        var soundEventRegister = DeferredRegister.create(Registries.SOUND_EVENT, ChiliBulletWeapons.MOD_ID);

        soundEventRegister.register("block_chili_pepper_pick_chili_peppers", () -> ModSoundEvents.CHILI_PEPPER_PICK_CHILI_PEPPERS);
        soundEventRegister.register("item_gun_shoot", () -> ModSoundEvents.GUN_SHOOT);
        soundEventRegister.register("item_gun_action_open", () -> ModSoundEvents.GUN_ACTION_OPEN);
        soundEventRegister.register("item_gun_action_close", () -> ModSoundEvents.GUN_ACTION_CLOSE);
        soundEventRegister.register("item_gun_upgrade", () -> ModSoundEvents.GUN_UPGRADE);

        soundEventRegister.register(modEventBus);
    }

    private static void registerEntityTypes(IEventBus modEventBus) {
        var entityTypeRegister = DeferredRegister.create(Registries.ENTITY_TYPE, ChiliBulletWeapons.MOD_ID);

        entityTypeRegister.register(ChiliArrow.ID.getPath(), () -> ModEntityTypes.CHILI_ARROW);
        entityTypeRegister.register(ChiliBullet.ID.getPath(), () -> ModEntityTypes.CHILI_BULLET);

        entityTypeRegister.register(modEventBus);
    }

    private static void registerCreativeModeTabs(IEventBus modEventBus) {
        var creativeModeTabRegister = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChiliBulletWeapons.MOD_ID);

        creativeModeTabRegister.register("main", () -> ModCreativeModeTabs.MAIN);

        creativeModeTabRegister.register(modEventBus);
    }
}
