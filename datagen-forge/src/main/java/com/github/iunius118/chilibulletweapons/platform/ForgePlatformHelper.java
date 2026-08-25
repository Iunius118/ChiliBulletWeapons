package com.github.iunius118.chilibulletweapons.platform;

import com.github.iunius118.chilibulletweapons.platform.services.IPlatformHelper;
import com.github.iunius118.chilibulletweapons.registry.ForgeModObjectRegistry;
import com.github.iunius118.chilibulletweapons.registry.ModObjectRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.DeferredRegister;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public <V, T extends V> ModObjectRegistry<V, T> createModObjectRegistry(Registry<V> registry, String namespace) {
        return new ForgeModObjectRegistry<>(DeferredRegister.create(registry.key(), namespace), namespace);
    }

    @Override
    public CreativeModeTab.Builder createCreativeModeTabBuilder() {
        return CreativeModeTab.builder();
    }

    @Override
    public boolean isHarvestingTool(ItemStack itemStack) {
        return itemStack.canPerformAction(ToolActions.SHEARS_HARVEST);
    }

    @Override
    public boolean shouldCancelProjectileImpact(Projectile projectile, HitResult hitResult) {
        return ForgeEventFactory.onProjectileImpactResult(projectile, hitResult)
                == ProjectileImpactEvent.ImpactResult.SKIP_ENTITY;
    }
}