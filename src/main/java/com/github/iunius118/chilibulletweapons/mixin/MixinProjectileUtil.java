package com.github.iunius118.chilibulletweapons.mixin;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGun;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileUtil.class)
public class MixinProjectileUtil {
    @Inject(method = "getWeaponHoldingHand", at = @At("HEAD"), cancellable = true)
    private static void onGetWeaponHoldingHand(LivingEntity livingEntity, Item item, CallbackInfoReturnable<InteractionHand> cir) {
        if (item == Items.CROSSBOW) {
            // If item is Items.CROSSBOW and livingEntity is holding a chili bullet gun,
            // return the hand holding it
            if (livingEntity.getMainHandItem().getItem() instanceof ChiliBulletGun) {
                cir.setReturnValue(InteractionHand.MAIN_HAND);
            } else if (livingEntity.getOffhandItem().getItem() instanceof ChiliBulletGun) {
                cir.setReturnValue(InteractionHand.OFF_HAND);
            }
        }
    }
}
