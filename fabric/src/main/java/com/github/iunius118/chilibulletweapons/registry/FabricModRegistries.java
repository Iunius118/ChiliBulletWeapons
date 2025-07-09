package com.github.iunius118.chilibulletweapons.registry;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.advancements.ModCriteriaTriggers;
import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class FabricModRegistries {

    public static void registerGameObjects() {
        registerItems();
        registerSoundEvents();
        registerEntityTypes();
        registerDataComponentTypes();
        registerCriterionTriggers();
        initModCreativeModeTabs();
    }

    private static void registerItems() {
        var itemRegister = ModObjectRegistry.create(BuiltInRegistries.ITEM, Constants.MOD_ID);

        // Plants
        itemRegister.register(Constants.Items.BULLET_CHILI.getPath(), ModItems.BULLET_CHILI);
        itemRegister.register(Constants.Items.BULLET_CHILI_SACK.getPath(), ModItems.BULLET_CHILI_SACK);
        // Weapons
        itemRegister.register(Constants.Items.CHILI_ARROW.getPath(), ModItems.CHILI_ARROW);
        itemRegister.register(Constants.Items.CHILI_BULLET.getPath(), ModItems.CHILI_BULLET);
        itemRegister.register(Constants.Items.GUN.getPath(), ModItems.GUN);
        itemRegister.register(Constants.Items.MACHINE_GUN.getPath(), ModItems.MACHINE_GUN);
        itemRegister.register(Constants.Items.UPGRADE_GUN_BAYONET.getPath(), ModItems.UPGRADE_GUN_BAYONET);
        itemRegister.register(Constants.Items.UPGRADE_GUN_BARREL.getPath(), ModItems.UPGRADE_GUN_BARREL);
        itemRegister.register(Constants.Items.UPGRADE_GUN_MECHANISM.getPath(), ModItems.UPGRADE_GUN_MECHANISM);
    }

    private static void registerSoundEvents() {
        var soundEventRegister = ModObjectRegistry.create(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

        soundEventRegister.register(Constants.SoundEvents.GUN_SHOOT.getPath(), ModSoundEvents.GUN_SHOOT);
        soundEventRegister.register(Constants.SoundEvents.GUN_ACTION_OPEN.getPath(), ModSoundEvents.GUN_ACTION_OPEN);
        soundEventRegister.register(Constants.SoundEvents.GUN_ACTION_CLOSE.getPath(), ModSoundEvents.GUN_ACTION_CLOSE);
        soundEventRegister.register(Constants.SoundEvents.GUN_UPGRADE.getPath(), ModSoundEvents.GUN_UPGRADE);
    }

    private static void registerEntityTypes() {
        var entityTypeRegister = ModObjectRegistry.create(BuiltInRegistries.ENTITY_TYPE, Constants.MOD_ID);

        entityTypeRegister.register(Constants.EntityTypes.CHILI_ARROW.getPath(), ModEntityTypes.CHILI_ARROW);
        entityTypeRegister.register(Constants.EntityTypes.CHILI_BULLET.getPath(), ModEntityTypes.CHILI_BULLET);
    }

    private static void registerDataComponentTypes() {
        var dataComponentTypeRegister = ModObjectRegistry.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

        dataComponentTypeRegister.register(Constants.DataComponentTypes.LOADING.getPath(), ModDataComponents.LOADING);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.GUN_CONTENTS.getPath(), ModDataComponents.GUN_CONTENTS);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.BAYONETED.getPath(), ModDataComponents.BAYONETED);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.FIXED.getPath(), ModDataComponents.FIXED);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.DYED_GUN_COLORS.getPath(), ModDataComponents.DYED_GUN_COLORS);

        // Deprecated
        dataComponentTypeRegister.register(Constants.DataComponentTypes.QUICK_LOADING.getPath(), ModDataComponents.QUICK_LOADING);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.PIERCING.getPath(), ModDataComponents.PIERCING);
        dataComponentTypeRegister.register(Constants.DataComponentTypes.MULTISHOT.getPath(), ModDataComponents.MULTISHOT);
    }

    private static void registerCriterionTriggers() {
        var criterionTriggerRegistry = ModObjectRegistry.create(BuiltInRegistries.TRIGGER_TYPES, Constants.MOD_ID);

        criterionTriggerRegistry.register(Constants.CriterionTriggers.EXPLODED_CHILI_ARROW.getPath(), ModCriteriaTriggers.EXPLODED_CHILI_ARROW);
        criterionTriggerRegistry.register(Constants.CriterionTriggers.SHOT_CHILI_BULLET_GUN.getPath(), ModCriteriaTriggers.SHOT_CHILI_BULLET_GUN);
        criterionTriggerRegistry.register(Constants.CriterionTriggers.UPGRADED_CHILI_BULLET_GUN.getPath(), ModCriteriaTriggers.UPGRADED_CHILI_BULLET_GUN);
        criterionTriggerRegistry.register(Constants.CriterionTriggers.KILLED_BY_CHILI_BULLET.getPath(), ModCriteriaTriggers.KILLED_BY_CHILI_BULLET);
    }

    public static void initModCreativeModeTabs() {
        var resourceKey = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Constants.CreativeModeTabs.MAIN);
        ItemGroupEvents.modifyEntriesEvent(resourceKey).register(entries -> {
            // Add items to the main mod creative mode tab
            ModItems.getCreativeModeTabItems().forEach(i -> {
                if (i != null) entries.accept(i);
            });
        });
    }

    private record ModObjectRegistry<V, T extends V>(Registry<V> registry, String modId) {

        public static <V, T extends V> ModObjectRegistry<V, T> create(Registry<V> registry, String modId) {
            return new ModObjectRegistry<>(registry, modId);
        }

        public void register(String id, T object) {
            Registry.register(registry, ResourceLocation.fromNamespaceAndPath(modId, id), object);
        }
    }
}
