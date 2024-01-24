package com.github.iunius118.chilibulletweapons.registry;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModCreativeModeTabs;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRegistries {
    public static void registerGameObjects(IEventBus modEventBus) {
        registerBlocks(modEventBus);
        registerItems(modEventBus);
        registerSoundEvents(modEventBus);
        registerEntityTypes(modEventBus);
        registerCreativeModeTabs(modEventBus);
    }

    private static void registerBlocks(IEventBus modEventBus) {
        var blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, ChiliBulletWeapons.MOD_ID);

        blockRegister.register(modEventBus);
    }

    private static void registerItems(IEventBus modEventBus) {
        var itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, ChiliBulletWeapons.MOD_ID);

        itemRegister.register("bullet_chili", () -> ModItems.BULLET_CHILI);
        itemRegister.register("curved_chili", () -> ModItems.CURVED_CHILI);
        itemRegister.register("chili_seeds", () -> ModItems.CHILI_SEEDS);
        itemRegister.register("chili_bullet", () -> ModItems.CHILI_BULLET);
        itemRegister.register("pistol", () -> ModItems.PISTOL);
        itemRegister.register("rifle", () -> ModItems.RIFLE);

        itemRegister.register(modEventBus);
    }

    private static void registerSoundEvents(IEventBus modEventBus) {
        var soundEventRegister = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ChiliBulletWeapons.MOD_ID);

        soundEventRegister.register("item_pistol_shoot", () -> ModSoundEvents.PISTOL_SHOOT);
        soundEventRegister.register("item_pistol_action_open", () -> ModSoundEvents.PISTOL_ACTION_OPEN);
        soundEventRegister.register("item_pistol_action_close", () -> ModSoundEvents.PISTOL_ACTION_CLOSE);

        soundEventRegister.register(modEventBus);
    }

    private static void registerEntityTypes(IEventBus modEventBus) {
        var entityTypeRegister = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ChiliBulletWeapons.MOD_ID);

        entityTypeRegister.register(ChiliBullet.ID.getPath(), () -> ModEntityTypes.CHILI_BULLET);

        entityTypeRegister.register(modEventBus);
    }

    private static void registerCreativeModeTabs(IEventBus modEventBus) {
        var creativeModeTabRegister = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChiliBulletWeapons.MOD_ID);

        creativeModeTabRegister.register("main", () -> ModCreativeModeTabs.MAIN);

        creativeModeTabRegister.register(modEventBus);
    }
}
