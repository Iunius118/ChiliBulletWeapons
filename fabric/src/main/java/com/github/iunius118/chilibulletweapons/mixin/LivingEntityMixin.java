package com.github.iunius118.chilibulletweapons.mixin;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, remap = false)
public abstract class LivingEntityMixin {

    @Inject(method = "isHolding(Lnet/minecraft/world/item/Item;)Z", at = @At("HEAD"), cancellable = true)
    private void onIsHolding(Item item, CallbackInfoReturnable<Boolean> cir) {
        var livingEntity = (LivingEntity)(Object) this;

        // If the item is crossbow and the livingEntity is holding a chili bullet gun,
        // return true to allow mobs to recognize gun items as crossbows
        if (livingEntity instanceof CrossbowAttackMob && item == Items.CROSSBOW &&
                livingEntity.isHolding(is -> is.getItem() instanceof ChiliBulletGunItem)) {
            // ChiliBulletWeapons.LOGGER.info("[CBGun] A living entity is holding a chili bullet gun");
            cir.setReturnValue(true);
        }
    }
}
