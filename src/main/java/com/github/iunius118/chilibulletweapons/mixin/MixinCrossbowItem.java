package com.github.iunius118.chilibulletweapons.mixin;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGun;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class MixinCrossbowItem {
    @Inject(method = "getChargeDuration", at = @At("HEAD"), cancellable = true)
    private static void onGetChargeDuration(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        if (itemStack.getItem() instanceof ChiliBulletGun chiliBulletGun) {
            // If the entity is holding a chili bullet gun,
            // return the reload duration of the gun
            int reloadDuration = chiliBulletGun.getReloadDuration(itemStack);
            cir.setReturnValue(reloadDuration);
        }
    }

    @Inject(method = "performShooting", at = @At("HEAD"), cancellable = true)
    private static void onPerformShooting(Level level, LivingEntity livingEntity, InteractionHand hand, ItemStack itemStack, float velocity, float inaccuracy, CallbackInfo ci) {
        // ChiliBulletWeapons.LOGGER.info("[CBGun] onPerformShooting with {} in {}", itemStack, hand);
        if (itemStack.getItem() instanceof ChiliBulletGun chiliBulletGun) {
            // If the entity is holding a chili bullet gun,
            // fire it instead of crossbow
            chiliBulletGun.performShootingByNonPlayer(level, livingEntity, hand, itemStack);
            ci.cancel();
        }
    }
}
