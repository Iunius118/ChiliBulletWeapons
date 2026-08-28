package com.github.iunius118.chilibulletweapons.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public record FabricModObjectRegistry<V, T extends V>(Registry<V> registry, String namespace)
        implements ModObjectRegistry<V, T> {

    @Override
    public ModRegistryObject<V> register(String name, Supplier<T> object) {
        T registered = Registry.register(registry, new ResourceLocation(namespace, name), object.get());
        return ModRegistryObject.of(Holder.direct(registered));
    }

    @Override
    public void register() {
        // Do nothing
    }
}
