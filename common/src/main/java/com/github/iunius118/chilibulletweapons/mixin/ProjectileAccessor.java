package com.github.iunius118.chilibulletweapons.mixin;

import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Projectile.class)
public interface ProjectileAccessor {
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
