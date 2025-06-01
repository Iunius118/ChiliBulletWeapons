package com.github.iunius118.chilibulletweapons.mixin;

import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Projectile.class, remap = false)
public interface ProjectileAccessorForge {
    @Accessor
    boolean getLeftOwner();
    @Accessor
    void setLeftOwner(boolean leftOwner);
    @Accessor
    boolean getHasBeenShot();
    @Accessor
    void setHasBeenShot(boolean hasBeenShot);

    @Invoker
    boolean invokeCheckLeftOwner();
}
