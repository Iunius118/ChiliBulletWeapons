package com.github.iunius118.chilibulletweapons.mixin.client;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunHelper;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AvatarRenderer.class, remap = false)
public abstract class AvatarRendererMixin {

    @Inject(method = "getArmPose(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/world/item/ItemStack;" +
            "Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;",
            at = @At("HEAD"), cancellable = true)
    private static void onGetArmPose(Avatar avatar, ItemStack itemInHand, InteractionHand hand,
                                     CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        // Return CROSSBOW_HOLD arm pose if the avatar is holding a loaded chili bullet gun in the hand
        if (!itemInHand.isEmpty() && !avatar.swinging &&
                itemInHand.getItem() instanceof ChiliBulletGunItem && ChiliBulletGunHelper.isLoaded(itemInHand)) {
            cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
