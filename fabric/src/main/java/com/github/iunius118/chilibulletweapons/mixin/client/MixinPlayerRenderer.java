package com.github.iunius118.chilibulletweapons.mixin.client;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunHelper;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer {

    @Inject(method = "setModelProperties(Lnet/minecraft/client/player/AbstractClientPlayer;)V", at = @At("TAIL"))
    private void onSetModelProperties(AbstractClientPlayer abstractClientPlayer, CallbackInfo ci) {
        if (abstractClientPlayer.isSpectator()) {
            return;
        }

        // Fix chili bullet gun animation in third person view
        boolean isMainArmRight = (abstractClientPlayer.getMainArm() == HumanoidArm.RIGHT);
        PlayerModel<AbstractClientPlayer> playerModel = PlayerRenderer.class.cast(this).getModel();
        var mainArmPose = isMainArmRight ? playerModel.rightArmPose : playerModel.leftArmPose;
        var offArmPose = isMainArmRight ? playerModel.leftArmPose : playerModel.rightArmPose;
        ItemStack mainHandStack = abstractClientPlayer.getMainHandItem();
        ItemStack offhandStack = abstractClientPlayer.getOffhandItem();

        if (isChiliBulletGunLoaded(abstractClientPlayer, mainArmPose, mainHandStack)) {
            // Main hand
            if (isMainArmRight) {
                playerModel.rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
                playerModel.leftArmPose = abstractClientPlayer.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
            } else {
                playerModel.leftArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
                playerModel.rightArmPose = abstractClientPlayer.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
            }
        } else if (!mainArmPose.isTwoHanded() && isChiliBulletGunLoaded(abstractClientPlayer, offArmPose, offhandStack)) {
            // Offhand
            if (!isMainArmRight) {
                playerModel.rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            } else {
                playerModel.leftArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
        }
    }

    @Unique
    private boolean isChiliBulletGunLoaded(AbstractClientPlayer abstractClientPlayer, HumanoidModel.ArmPose armPose, ItemStack itemStack) {
        return !abstractClientPlayer.swinging && armPose == HumanoidModel.ArmPose.ITEM
                && itemStack.getItem() instanceof ChiliBulletGunItem && ChiliBulletGunHelper.isLoaded(itemStack);
    }
}
