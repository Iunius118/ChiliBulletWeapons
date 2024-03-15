package com.github.iunius118.chilibulletweapons.registry;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModCreativeModeTabs;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ModRegistries {
    public static void registerGameObjects() {
        registerBlocks();
        registerItems();
        registerSoundEvents();
        registerEntityTypes();
        registerCreativeModeTabs();
    }

    private static void registerBlocks() {
        var blockRegister = ModObjectRegistry.create(BuiltInRegistries.BLOCK, ChiliBulletWeapons.MOD_ID);

        blockRegister.register("chili_pepper", ModBlocks.CHILI_PEPPER);
    }

    private static void registerItems() {
        var itemRegister = ModObjectRegistry.create(BuiltInRegistries.ITEM, ChiliBulletWeapons.MOD_ID);

        // Plants
        itemRegister.register("bullet_chili", ModItems.BULLET_CHILI);
        itemRegister.register("curved_chili", ModItems.CURVED_CHILI);
        itemRegister.register("chili_seeds", ModItems.CHILI_SEEDS);
        itemRegister.register("bullet_chili_sack", ModItems.BULLET_CHILI_SACK);
        itemRegister.register("curved_chili_sack", ModItems.CURVED_CHILI_SACK);
        // Foods
        itemRegister.register("chili_chicken_sandwich", ModItems.CHILI_CHICKEN_SANDWICH);
        itemRegister.register("chili_fish_sandwich", ModItems.CHILI_FISH_SANDWICH);
        itemRegister.register("chili_meat_sandwich", ModItems.CHILI_MEAT_SANDWICH);
        itemRegister.register("chili_potato_sandwich", ModItems.CHILI_POTATO_SANDWICH);
        itemRegister.register("half_chili_chicken_sandwich", ModItems.HALF_CHILI_CHICKEN_SANDWICH);
        itemRegister.register("half_chili_fish_sandwich", ModItems.HALF_CHILI_FISH_SANDWICH);
        itemRegister.register("half_chili_meat_sandwich", ModItems.HALF_CHILI_MEAT_SANDWICH);
        itemRegister.register("half_chili_potato_sandwich", ModItems.HALF_CHILI_POTATO_SANDWICH);
        itemRegister.register("pasta_oil_and_chili", ModItems.PASTA_OIL_AND_CHILI);
        itemRegister.register("fried_chili_pepper", ModItems.FRIED_CHILI_PEPPER);
        // Weapons
        itemRegister.register("chili_bullet", ModItems.CHILI_BULLET);
        itemRegister.register("gun", ModItems.GUN);
        itemRegister.register("bayoneted_gun", ModItems.BAYONETED_GUN);
        itemRegister.register("machine_gun", ModItems.MACHINE_GUN);
    }

    private static void registerSoundEvents() {
        var soundEventRegister = ModObjectRegistry.create(BuiltInRegistries.SOUND_EVENT, ChiliBulletWeapons.MOD_ID);

        soundEventRegister.register("block_chili_pepper_pick_chili_peppers", ModSoundEvents.CHILI_PEPPER_PICK_CHILI_PEPPERS);
        soundEventRegister.register("item_gun_shoot", ModSoundEvents.GUN_SHOOT);
        soundEventRegister.register("item_gun_action_open", ModSoundEvents.GUN_ACTION_OPEN);
        soundEventRegister.register("item_gun_action_close", ModSoundEvents.GUN_ACTION_CLOSE);
    }

    private static void registerEntityTypes() {
        var entityTypeRegister = ModObjectRegistry.create(BuiltInRegistries.ENTITY_TYPE, ChiliBulletWeapons.MOD_ID);

        entityTypeRegister.register(ChiliBullet.ID.getPath(), ModEntityTypes.CHILI_BULLET);
    }

    private static void registerCreativeModeTabs() {
        var creativeModeTabRegister = ModObjectRegistry.create(BuiltInRegistries.CREATIVE_MODE_TAB, ChiliBulletWeapons.MOD_ID);

        creativeModeTabRegister.register("main", ModCreativeModeTabs.MAIN);
    }

    private record ModObjectRegistry<V, T extends V>(Registry<V> registry, String modId) {
        public static <V, T extends V> ModObjectRegistry<V, T> create(Registry<V> registry, String modId) {
            return new ModObjectRegistry<>(registry, modId);
        }

        public void register(String id, T object) {
            Registry.register(registry, new ResourceLocation(modId, id), object);
        }
    }
}
