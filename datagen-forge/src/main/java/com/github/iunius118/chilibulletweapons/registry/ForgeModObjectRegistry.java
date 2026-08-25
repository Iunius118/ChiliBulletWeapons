package com.github.iunius118.chilibulletweapons.registry;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public record ForgeModObjectRegistry<V, T extends V>(DeferredRegister<V> deferredRegister, String namespace)
        implements ModObjectRegistry<V, T> {

    @Override
    public ModRegistryObject<V> register(String name, Supplier<T> object) {
        return new ModRegistryObjectImpl<>(deferredRegister.register(name, object));
    }

    @Override
    public void register() {
        if (ChiliBulletWeapons.modEventBus != null) {
            deferredRegister.register(ChiliBulletWeapons.modEventBus);
        }
    }

    private record ModRegistryObjectImpl<T>(RegistryObject<T> object) implements ModRegistryObject<T> {

        @Override
        public Holder<T> getHolder() {
            return object.getHolder().orElseThrow();
        }

        @Override
        public ResourceKey<T> getKey() {
            return object.getKey();
        }

        @Override
        public boolean isBound() {
            return object.isPresent();
        }

        @Override
        public T value() {
            return object.get();
        }
    }
}
