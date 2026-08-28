package com.github.iunius118.chilibulletweapons.platform.services;

import com.github.iunius118.chilibulletweapons.registry.ModObjectRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;

import java.util.function.Supplier;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }


    /**
     * Creates a registrar for registering objects to given registry.
     *
     * @param registry  The registry to register to
     * @param namespace The namespace for all objects registered to the register
     * @return A registrar for registering objects to a registry
     */
    <V, T extends V> ModObjectRegistry<V, T> createModObjectRegistry(Registry<V> registry, String namespace);

    /**
     * Creates a new creative mode tab.
     *
     * @param id           The ID of the creative mode tab
     * @param iconSupplier The item stack supplier for the creative mode tab icon
     * @return A creative mode tab
     */
    CreativeModeTab createCreativeModeTab(ResourceLocation id, Supplier<ItemStack> iconSupplier);

    /**
     * Checks whether the given item stack is a tool that can harvest crops.
     *
     * @param itemStack The item stack to check.
     * @return True if the item stack is a harvesting tool, false otherwise.
     */
    boolean isHarvestingTool(ItemStack itemStack);

    /**
     * Determines if a projectile impact event should be canceled.
     *
     * @param projectile The projectile that impacted a target.
     * @param hitResult The result of the impact.
     * @return True if the impact event should be canceled, false otherwise.
     */
    boolean shouldCancelProjectileImpact(Projectile projectile, HitResult hitResult);
}