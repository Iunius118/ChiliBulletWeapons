package com.github.iunius118.chilibulletweapons.registry;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.advancements.ModCriteriaTriggers;
import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeModRegistries {

    public static void registerGameObjects(IEventBus modEventBus) {
        registerItems(modEventBus);
        registerSoundEvents(modEventBus);
        registerEntityTypes(modEventBus);
        registerDataComponentTypes(modEventBus);
        registerCriterionTriggers(modEventBus);
        modEventBus.addListener(NeoForgeModRegistries::onCreativeModeTabBuildContents);
    }

    private static void registerItems(IEventBus modEventBus) {
        var items = DeferredRegister.createItems(Constants.MOD_ID);

        // Plants
        items.register(Constants.Items.BULLET_CHILI.getPath(), () -> ModItems.BULLET_CHILI);
        items.register(Constants.Items.BULLET_CHILI_SACK.getPath(), () -> ModItems.BULLET_CHILI_SACK);
        // Weapons
        items.register(Constants.Items.CHILI_ARROW.getPath(), () -> ModItems.CHILI_ARROW);
        items.register(Constants.Items.CHILI_BULLET.getPath(), () -> ModItems.CHILI_BULLET);
        items.register(Constants.Items.GUN.getPath(), () -> ModItems.GUN);
        items.register(Constants.Items.MACHINE_GUN.getPath(), () -> ModItems.MACHINE_GUN);
        items.register(Constants.Items.UPGRADE_GUN_BAYONET.getPath(), () -> ModItems.UPGRADE_GUN_BAYONET);
        items.register(Constants.Items.UPGRADE_GUN_BARREL.getPath(), () -> ModItems.UPGRADE_GUN_BARREL);
        items.register(Constants.Items.UPGRADE_GUN_MECHANISM.getPath(), () -> ModItems.UPGRADE_GUN_MECHANISM);

        items.register(modEventBus);
    }

    private static void registerSoundEvents(IEventBus modEventBus) {
        var soundEvents = DeferredRegister.create(Registries.SOUND_EVENT, Constants.MOD_ID);

        soundEvents.register(Constants.SoundEvents.GUN_SHOOT.getPath(), () -> ModSoundEvents.GUN_SHOOT);
        soundEvents.register(Constants.SoundEvents.GUN_ACTION_OPEN.getPath(), () -> ModSoundEvents.GUN_ACTION_OPEN);
        soundEvents.register(Constants.SoundEvents.GUN_ACTION_CLOSE.getPath(), () -> ModSoundEvents.GUN_ACTION_CLOSE);
        soundEvents.register(Constants.SoundEvents.GUN_UPGRADE.getPath(), () -> ModSoundEvents.GUN_UPGRADE);

        soundEvents.register(modEventBus);
    }

    private static void registerEntityTypes(IEventBus modEventBus) {
        var entityTypes = DeferredRegister.create(Registries.ENTITY_TYPE, Constants.MOD_ID);

        entityTypes.register(Constants.EntityTypes.CHILI_ARROW.getPath(), () -> ModEntityTypes.CHILI_ARROW);
        entityTypes.register(Constants.EntityTypes.CHILI_BULLET.getPath(), () -> ModEntityTypes.CHILI_BULLET);

        entityTypes.register(modEventBus);
    }

    private static void registerDataComponentTypes(IEventBus modEventBus) {
        var dataComponentTypes = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

        dataComponentTypes.register(Constants.DataComponentTypes.LOADING.getPath(), () -> ModDataComponents.LOADING);
        dataComponentTypes.register(Constants.DataComponentTypes.GUN_CONTENTS.getPath(),
                () -> ModDataComponents.GUN_CONTENTS);
        dataComponentTypes.register(Constants.DataComponentTypes.FIXED.getPath(), () -> ModDataComponents.FIXED);
        dataComponentTypes.register(Constants.DataComponentTypes.DYED_GUN_COLORS.getPath(),
                () -> ModDataComponents.DYED_GUN_COLORS);

        dataComponentTypes.register(modEventBus);
    }

    private static void registerCriterionTriggers(IEventBus modEventBus) {
        var criterionTriggers = DeferredRegister.create(Registries.TRIGGER_TYPE, Constants.MOD_ID);

        criterionTriggers.register(Constants.CriterionTriggers.EXPLODED_CHILI_ARROW.getPath(),
                () -> ModCriteriaTriggers.EXPLODED_CHILI_ARROW);
        criterionTriggers.register(Constants.CriterionTriggers.SHOT_CHILI_BULLET_GUN.getPath(),
                () -> ModCriteriaTriggers.SHOT_CHILI_BULLET_GUN);
        criterionTriggers.register(Constants.CriterionTriggers.UPGRADED_CHILI_BULLET_GUN.getPath(),
                () -> ModCriteriaTriggers.UPGRADED_CHILI_BULLET_GUN);
        criterionTriggers.register(Constants.CriterionTriggers.KILLED_BY_CHILI_BULLET.getPath(),
                () -> ModCriteriaTriggers.KILLED_BY_CHILI_BULLET);

        criterionTriggers.register(modEventBus);
    }

    private static void onCreativeModeTabBuildContents(BuildCreativeModeTabContentsEvent event) {
        var resourceKey = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Constants.CreativeModeTabs.MAIN);

        if (event.getTabKey().equals(resourceKey)) {
            // Add items to the main mod creative mode tab
            ModItems.getCreativeModeTabItems().forEach(i -> {
                if (i != null) event.accept(i);
            });
        }
    }
}
