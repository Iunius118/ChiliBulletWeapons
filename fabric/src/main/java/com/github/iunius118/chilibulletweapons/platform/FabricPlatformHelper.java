package com.github.iunius118.chilibulletweapons.platform;

import com.github.iunius118.chilibulletweapons.platform.services.IPlatformHelper;
import com.github.iunius118.chilibulletweapons.registry.FabricModObjectRegistry;
import com.github.iunius118.chilibulletweapons.registry.ModObjectRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;

import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <V, T extends V> ModObjectRegistry<V, T> createModObjectRegistry(Registry<V> registry, String namespace) {
        return new FabricModObjectRegistry<>(registry, namespace);
    }

    @Override
    public CreativeModeTab createCreativeModeTab(ResourceLocation id, Supplier<ItemStack> iconSupplier) {
        return FabricItemGroupBuilder.build(id, iconSupplier);
    }

    @Override
    public boolean isHarvestingTool(ItemStack itemStack) {
        return itemStack.is(ConventionalItemTags.SHEARS);
    }

    @Override
    public boolean shouldCancelProjectileImpact(Projectile projectile, HitResult hitResult) {
        return false;
    }
}
