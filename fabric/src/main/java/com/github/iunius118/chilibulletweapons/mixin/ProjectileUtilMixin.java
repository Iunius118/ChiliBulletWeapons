package com.github.iunius118.chilibulletweapons.mixin;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ProjectileUtil.class, remap = false)
public class ProjectileUtilMixin {

    @Inject(method = "getWeaponHoldingHand", at = @At("HEAD"), cancellable = true)
    private static void onGetWeaponHoldingHand(LivingEntity livingEntity, Item item, CallbackInfoReturnable<InteractionHand> cir) {
        // If the item is crossbow and the livingEntity is holding a chili bullet gun,
        // return the hand holding it to allow mobs to recognize gun items as crossbows
        if (livingEntity instanceof CrossbowAttackMob && item == Items.CROSSBOW) {
            if (livingEntity.getMainHandItem().getItem() instanceof ChiliBulletGunItem) {
                cir.setReturnValue(InteractionHand.MAIN_HAND);
            } else if (livingEntity.getOffhandItem().getItem() instanceof ChiliBulletGunItem) {
                cir.setReturnValue(InteractionHand.OFF_HAND);
            }
        }
    }
}
