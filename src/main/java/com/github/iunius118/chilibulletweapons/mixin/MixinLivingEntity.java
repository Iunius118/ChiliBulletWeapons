package com.github.iunius118.chilibulletweapons.mixin;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGun;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @Inject(method = "isHolding(Lnet/minecraft/world/item/Item;)Z", at = @At("HEAD"), cancellable = true)
    private void onIsHolding(Item item, CallbackInfoReturnable<Boolean> cir) {
        if (item == Items.CROSSBOW
                && LivingEntity.class.cast(this).isHolding(is -> is.getItem() instanceof ChiliBulletGun)) {
            // If item is Items.CROSSBOW and livingEntity is holding a chili bullet gun,
            // return true
            // ChiliBulletWeapons.LOGGER.info("[CBGun] A living entity is holding a chili bullet gun");
            cir.setReturnValue(true);
        }
    }
}
