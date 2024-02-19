package com.github.iunius118.chilibulletweapons.mixin.client;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGun;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRenderer {
    @Shadow
    private void applyItemArmAttackTransform(PoseStack poseStack, HumanoidArm humanoidArm, float f) {}
    @Shadow
    private void applyItemArmTransform(PoseStack poseStack, HumanoidArm humanoidArm, float f) {}
    @Shadow
    public void renderItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {}

    @Inject(method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    private void onRenderArmWithItem(AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, CallbackInfo ci) {
        if (abstractClientPlayer.isScoping() || !(itemStack.getItem() instanceof ChiliBulletGun)) {
            return;
        }

        // Fix chili bullet gun animation in first person view
        boolean isInteractingWithMainHand = interactionHand == InteractionHand.MAIN_HAND;
        HumanoidArm interactingArm = isInteractingWithMainHand ? abstractClientPlayer.getMainArm() : abstractClientPlayer.getMainArm().getOpposite();
        boolean isLoaded = ChiliBulletGun.isLoaded(itemStack);
        boolean isInteractingArmRight = interactingArm == HumanoidArm.RIGHT;
        float armDirection = isInteractingArmRight ? 1.0F : -1.0F;
        int useItemRemainingTicks = abstractClientPlayer.getUseItemRemainingTicks();
        poseStack.pushPose();

        if (abstractClientPlayer.isUsingItem() && useItemRemainingTicks > 0 && abstractClientPlayer.getUsedItemHand() == interactionHand) {
            // Loading
            applyItemArmTransform(poseStack, interactingArm, i);
            poseStack.translate(armDirection * -0.4785682F, -0.094387F, 0.05731531F);
            poseStack.mulPose(Axis.XP.rotationDegrees(-11.935F));
            poseStack.mulPose(Axis.YP.rotationDegrees(armDirection * 65.3F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(armDirection * -9.785F));
            float loadingTicks = (float) itemStack.getUseDuration() - ((float) useItemRemainingTicks - f + 1.0F);
            float loadingProgress = Math.min(loadingTicks / (float) ChiliBulletGun.getChargeDuration(itemStack), 1.0F);

            if (loadingProgress > 0.1F) {
                float y = Mth.sin((loadingTicks - 0.1F) * 1.3F) * (loadingProgress - 0.1F);
                poseStack.translate(0.0F, y * 0.004F, 0.0F);
            }

            poseStack.translate(0.0F, 0.0F, loadingProgress * 0.04F);
            poseStack.scale(1.0F, 1.0F, 1.0F + loadingProgress * 0.2F);
            poseStack.mulPose(Axis.YN.rotationDegrees(armDirection * 45.0F));
        } else {
            // Loaded/Empty
            float x = -0.4F * Mth.sin(Mth.sqrt(h) * Mth.PI);
            float y = 0.2F * Mth.sin(Mth.sqrt(h) * Mth.TWO_PI);
            float z = -0.2F * Mth.sin(h * Mth.PI);
            poseStack.translate(armDirection * x, y, z);
            applyItemArmTransform(poseStack, interactingArm, i);
            applyItemArmAttackTransform(poseStack, interactingArm, h);

            if (isLoaded && h < 0.001F && isInteractingWithMainHand) {
                // Loaded
                poseStack.translate(armDirection * -0.641864F, 0.0F, 0.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(armDirection * 10.0F));
            }
        }

        renderItem(abstractClientPlayer, itemStack, isInteractingArmRight ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !isInteractingArmRight, poseStack, multiBufferSource, j);
        poseStack.popPose();
        ci.cancel();
    }
}
