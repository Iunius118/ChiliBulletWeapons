package com.github.iunius118.chilibulletweapons.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
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
        if (item == Items.CROSSBOW) {
            // Change item comparison from 'is' to 'instanceof' when item is crossbow
            var livingEntity = LivingEntity.class.cast(this);
            boolean b = livingEntity.isHolding(is -> is.getItem() instanceof CrossbowItem);
            // ChiliBulletWeapons.LOGGER.info("[CBGun] Compare with CrossbowItem: {}", b);
            cir.setReturnValue(b);
        }
    }
}
