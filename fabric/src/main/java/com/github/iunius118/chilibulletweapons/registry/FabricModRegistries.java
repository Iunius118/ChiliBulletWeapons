package com.github.iunius118.chilibulletweapons.registry;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.advancements.ModCriteriaTriggers;
import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.mixin.CreativeModeTabAccessor;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

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
        var items = ModObjectRegistry.create(BuiltInRegistries.ITEM, Constants.MOD_ID);

        // Plants
        items.register(Constants.Items.BULLET_CHILI.getPath(), ModItems.BULLET_CHILI);
        items.register(Constants.Items.BULLET_CHILI_SACK.getPath(), ModItems.BULLET_CHILI_SACK);
        // Weapons
        items.register(Constants.Items.CHILI_ARROW.getPath(), ModItems.CHILI_ARROW);
        items.register(Constants.Items.CHILI_BULLET.getPath(), ModItems.CHILI_BULLET);
        items.register(Constants.Items.GUN.getPath(), ModItems.GUN);
        items.register(Constants.Items.MACHINE_GUN.getPath(), ModItems.MACHINE_GUN);
        items.register(Constants.Items.UPGRADE_GUN_BAYONET.getPath(), ModItems.UPGRADE_GUN_BAYONET);
        items.register(Constants.Items.UPGRADE_GUN_BARREL.getPath(), ModItems.UPGRADE_GUN_BARREL);
        items.register(Constants.Items.UPGRADE_GUN_MECHANISM.getPath(), ModItems.UPGRADE_GUN_MECHANISM);
    }

    private static void registerSoundEvents() {
        var soundEvents = ModObjectRegistry.create(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

        soundEvents.register(Constants.SoundEvents.GUN_SHOOT.getPath(), ModSoundEvents.GUN_SHOOT);
        soundEvents.register(Constants.SoundEvents.GUN_ACTION_OPEN.getPath(), ModSoundEvents.GUN_ACTION_OPEN);
        soundEvents.register(Constants.SoundEvents.GUN_ACTION_CLOSE.getPath(), ModSoundEvents.GUN_ACTION_CLOSE);
        soundEvents.register(Constants.SoundEvents.GUN_UPGRADE.getPath(), ModSoundEvents.GUN_UPGRADE);
    }

    private static void registerEntityTypes() {
        var entityTypes = ModObjectRegistry.create(BuiltInRegistries.ENTITY_TYPE, Constants.MOD_ID);

        entityTypes.register(Constants.EntityTypes.CHILI_ARROW.getPath(), ModEntityTypes.CHILI_ARROW);
        entityTypes.register(Constants.EntityTypes.CHILI_BULLET.getPath(), ModEntityTypes.CHILI_BULLET);
    }

    private static void registerDataComponentTypes() {
        var dataComponentTypes = ModObjectRegistry.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

        dataComponentTypes.register(Constants.DataComponentTypes.LOADING.getPath(), ModDataComponents.LOADING);
        dataComponentTypes.register(Constants.DataComponentTypes.GUN_CONTENTS.getPath(),
                ModDataComponents.GUN_CONTENTS);
        dataComponentTypes.register(Constants.DataComponentTypes.FIXED.getPath(), ModDataComponents.FIXED);
        dataComponentTypes.register(Constants.DataComponentTypes.DYED_GUN_COLORS.getPath(),
                ModDataComponents.DYED_GUN_COLORS);
    }

    private static void registerCriterionTriggers() {
        var criterionTriggers = ModObjectRegistry.create(BuiltInRegistries.TRIGGER_TYPES, Constants.MOD_ID);

        criterionTriggers.register(Constants.CriterionTriggers.EXPLODED_CHILI_ARROW.getPath(),
                ModCriteriaTriggers.EXPLODED_CHILI_ARROW);
        criterionTriggers.register(Constants.CriterionTriggers.SHOT_CHILI_BULLET_GUN.getPath(),
                ModCriteriaTriggers.SHOT_CHILI_BULLET_GUN);
        criterionTriggers.register(Constants.CriterionTriggers.UPGRADED_CHILI_BULLET_GUN.getPath(),
                ModCriteriaTriggers.UPGRADED_CHILI_BULLET_GUN);
        criterionTriggers.register(Constants.CriterionTriggers.KILLED_BY_CHILI_BULLET.getPath(),
                ModCriteriaTriggers.KILLED_BY_CHILI_BULLET);
    }

    public static void initModCreativeModeTabs() {
        var resourceKey = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Constants.CreativeModeTabs.MAIN);

        CreativeModeTabEvents.modifyOutputEvent(resourceKey).register(entries -> {
            // ** Only on Fabric, change the title to CBW **
            BuiltInRegistries.CREATIVE_MODE_TAB.getOptional(resourceKey).ifPresent(tab ->
                    ((CreativeModeTabAccessor) tab)
                            .setDisplayName(Component.translatable(Constants.CreativeModeTabs.TITLE_CBW_MAIN)));

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
            Registry.register(registry, Identifier.fromNamespaceAndPath(modId, id), object);
        }
    }
}
