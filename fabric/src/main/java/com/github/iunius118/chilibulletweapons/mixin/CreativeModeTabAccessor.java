package com.github.iunius118.chilibulletweapons.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CreativeModeTab.class, remap = false)
public interface CreativeModeTabAccessor {

	@Accessor
	@Mutable
	void setDisplayName(Component displayName);
}
