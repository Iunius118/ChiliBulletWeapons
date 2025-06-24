package com.github.iunius118.chilibulletweapons.registry;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeModRegistries {

    public static void registerGameObjects(IEventBus modEventBus) {
        registerItems(modEventBus);
        registerSoundEvents(modEventBus);
        registerEntityTypes(modEventBus);
        registerDataComponentTypes(modEventBus);
        modEventBus.addListener(ForgeModRegistries::onCreativeModeTabBuildContents);
    }

    private static void registerItems(IEventBus modEventBus) {
        var itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

        // Plants
        itemRegister.register(Constants.Items.BULLET_CHILI.getPath(), () -> ModItems.BULLET_CHILI);
        itemRegister.register(Constants.Items.BULLET_CHILI_SACK.getPath(), () -> ModItems.BULLET_CHILI_SACK);
        // Weapons
        itemRegister.register(Constants.Items.CHILI_ARROW.getPath(), () -> ModItems.CHILI_ARROW);
        itemRegister.register(Constants.Items.CHILI_BULLET.getPath(), () -> ModItems.CHILI_BULLET);
        itemRegister.register(Constants.Items.GUN.getPath(), () -> ModItems.GUN);
        itemRegister.register(Constants.Items.MACHINE_GUN.getPath(), () -> ModItems.MACHINE_GUN);
        itemRegister.register(Constants.Items.UPGRADE_GUN_BAYONET.getPath(), () -> ModItems.UPGRADE_GUN_BAYONET);
        itemRegister.register(Constants.Items.UPGRADE_GUN_BARREL.getPath(), () -> ModItems.UPGRADE_GUN_BARREL);
        itemRegister.register(Constants.Items.UPGRADE_GUN_MECHANISM.getPath(), () -> ModItems.UPGRADE_GUN_MECHANISM);

        itemRegister.register(modEventBus);
    }

    private static void registerSoundEvents(IEventBus modEventBus) {
        var soundEventRegister = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Constants.MOD_ID);

        soundEventRegister.register(Constants.SoundEvents.GUN_SHOOT.getPath(), () -> ModSoundEvents.GUN_SHOOT);
        soundEventRegister.register(Constants.SoundEvents.GUN_ACTION_OPEN.getPath(), () -> ModSoundEvents.GUN_ACTION_OPEN);
        soundEventRegister.register(Constants.SoundEvents.GUN_ACTION_CLOSE.getPath(), () -> ModSoundEvents.GUN_ACTION_CLOSE);
        soundEventRegister.register(Constants.SoundEvents.GUN_UPGRADE.getPath(), () -> ModSoundEvents.GUN_UPGRADE);

        soundEventRegister.register(modEventBus);
    }

    private static void registerEntityTypes(IEventBus modEventBus) {
        var entityTypeRegister = DeferredRegister.create(Registries.ENTITY_TYPE, Constants.MOD_ID);

        entityTypeRegister.register(Constants.EntityTypes.CHILI_ARROW.getPath(), () -> ModEntityTypes.CHILI_ARROW);
        entityTypeRegister.register(Constants.EntityTypes.CHILI_BULLET.getPath(), () -> ModEntityTypes.CHILI_BULLET);

        entityTypeRegister.register(modEventBus);
    }

    private static void registerDataComponentTypes(IEventBus modEventBus) {
        var dataComponentTypeRegister = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

        dataComponentTypeRegister.register(Constants.DataComponentTypes.LOADING.getPath(), () -> ModDataComponents.LOADING);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.GUN_CONTENTS.getPath(), () -> ModDataComponents.GUN_CONTENTS);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.BAYONETED.getPath(), () -> ModDataComponents.BAYONETED);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.FIXED.getPath(), () -> ModDataComponents.FIXED);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.DYED_GUN_COLORS.getPath(), () -> ModDataComponents.DYED_GUN_COLORS);

        // Deprecated
        dataComponentTypeRegister.register(Constants.DataComponentTypes.QUICK_LOADING.getPath(), () -> ModDataComponents.QUICK_LOADING);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.PIERCING.getPath(), () -> ModDataComponents.PIERCING);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.MULTISHOT.getPath(), () -> ModDataComponents.MULTISHOT);

        dataComponentTypeRegister.register(modEventBus);
    }

    private static void onCreativeModeTabBuildContents(BuildCreativeModeTabContentsEvent event) {
        var creativeModeTab = BuiltInRegistries.CREATIVE_MODE_TAB.get(Constants.CreativeModeTabs.MAIN);

        if (event.getTab().equals(creativeModeTab)) {
            // Add items to the main mod creative mode tab
            ModItems.getCreativeModeTabItems().forEach(i -> {
                if (i != null) event.accept(i);
            });
        }
    }
}
