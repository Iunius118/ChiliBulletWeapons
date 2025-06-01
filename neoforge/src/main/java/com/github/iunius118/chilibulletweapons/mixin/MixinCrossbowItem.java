package com.github.iunius118.chilibulletweapons.mixin;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunItem;
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
public abstract class MixinCrossbowItem {

    @Inject(method = "getChargeDuration", at = @At("HEAD"), cancellable = true)
    private static void onGetChargeDuration(ItemStack stack, LivingEntity shooter, CallbackInfoReturnable<Integer> cir) {
        if (stack.getItem() instanceof ChiliBulletGunItem chiliBulletGun) {
            // If the entity is holding a chili bullet gun,
            // return the reload duration of the gun
            int reloadDuration = chiliBulletGun.getReloadDuration(stack);
            cir.setReturnValue(reloadDuration);
        }
    }

    @Inject(method = "performShooting", at = @At("HEAD"), cancellable = true)
    private void onPerformShooting(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, float velocity, float inaccuracy, LivingEntity target, CallbackInfo ci) {
        //Constants.LOG.info("[CBGun] onPerformShooting with {} in {}", weapon, hand);

        if (weapon.getItem() instanceof ChiliBulletGunItem chiliBulletGun) {
            // If the entity is holding a chili bullet gun,
            // fire it instead of crossbow
            chiliBulletGun.performShootingByNonPlayer(level, shooter, hand, weapon);
            ci.cancel();
        }
    }
}
