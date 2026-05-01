package com.github.iunius118.chilibulletweapons.mixin.client;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunHelper;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunItem;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AvatarRenderer.class, remap = false)
public abstract class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> {

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

/*
    @Inject(method = "extractRenderState" +
            "(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V",
            at = @At("TAIL"))*/
    @Unique
    private void onExtractRenderState(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks,
                                      CallbackInfo ci) {
        if (entity.isSpectator()) {
            return;
        }

        // Fix chili bullet gun animation in third person view
        boolean isMainArmRight = (entity.getMainArm() == HumanoidArm.RIGHT);
        var mainArmPose = isMainArmRight ? state.rightArmPose : state.leftArmPose;
        var offArmPose = isMainArmRight ? state.leftArmPose : state.rightArmPose;
        ItemStack mainHandStack = entity.getMainHandItem();
        ItemStack offhandStack = entity.getOffhandItem();

        if (isChiliBulletGunLoaded(entity, mainArmPose, mainHandStack)) {
            // Main hand
            if (isMainArmRight) {
                state.rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
                state.leftArmPose = entity.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
            } else {
                state.leftArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
                state.rightArmPose = entity.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
            }
        } else if (!mainArmPose.isTwoHanded() && isChiliBulletGunLoaded(entity, offArmPose, offhandStack)) {
            // Offhand
            if (!isMainArmRight) {
                state.rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            } else {
                state.leftArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
        }
    }

    @Unique
    private boolean isChiliBulletGunLoaded(Avatar avatar, HumanoidModel.ArmPose armPose,
                                           ItemStack itemStack) {
        return !avatar.swinging && armPose == HumanoidModel.ArmPose.ITEM &&
                itemStack.getItem() instanceof ChiliBulletGunItem && ChiliBulletGunHelper.isLoaded(itemStack);
    }
}
