package com.github.iunius118.chilibulletweapons.mixin;

import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AbstractArrow.class, remap = false)
public interface AbstractArrowAccessor {

	@Accessor
	double getBaseDamage();
}
