package com.github.iunius118.chilibulletweapons.mixin;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingTransformRecipe.class)
public abstract class MixinSmithingTransformRecipe implements SmithingRecipe {
    @Shadow @Final
    Ingredient template;
    @Shadow @Final
    Ingredient base;
    @Shadow @Final
    Ingredient addition;
    @Shadow @Final
    ItemStack result;

    @Inject(method = "matches(Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Z", at = @At("HEAD"), cancellable = true)
    private void onMatches(Container container, Level level, CallbackInfoReturnable<Boolean> cir) {
        if (!result.is(ModItems.BAYONETED_GUN)) {
            return;
        }

        // Allow template-less recipes for bayoneted guns
        if (template.isEmpty() && container.getItem(0).isEmpty()) {
            boolean hasMatched = base.test(container.getItem(1)) && addition.test(container.getItem(2));
            cir.setReturnValue(hasMatched);
            cir.cancel();
        }
    }
}
