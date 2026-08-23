package com.github.iunius118.chilibulletweapons.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public record FabricModObjectRegistry<V, T extends V>(Registry<V> registry, String namespace)
        implements ModObjectRegistry<V, T> {

    @Override
    public ModRegistryObject<V> register(String name, Supplier<T> object) {
        return ModRegistryObject.of(
                Registry.registerForHolder(registry, new ResourceLocation(namespace, name), object.get())
        );
    }

    @Override
    public void register() {
        // Do nothing
    }
}
