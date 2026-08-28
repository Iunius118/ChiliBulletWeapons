package com.github.iunius118.chilibulletweapons.mixin.client;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGun;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
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
    public void renderItem(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType,
                           boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {}

    @Inject(method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;" +
            "FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;" +
            "FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"), cancellable = true)
    private void onRenderArmWithItem(AbstractClientPlayer abstractClientPlayer, float partialTicks, float pitch,
                                     InteractionHand interactionHand, float swingProgress, ItemStack itemStack,
                                     float equippedProgress, PoseStack matrixStack, MultiBufferSource buffer,
                                     int combinedLight, CallbackInfo ci) {
        if (abstractClientPlayer.isScoping() || !(itemStack.getItem() instanceof ChiliBulletGun)) {
            return;
        }

        // Fix chili bullet gun animation in first person view
        boolean isInteractingWithMainHand = interactionHand == InteractionHand.MAIN_HAND;
        HumanoidArm interactingArm = isInteractingWithMainHand ?
                abstractClientPlayer.getMainArm() : abstractClientPlayer.getMainArm().getOpposite();
        boolean isLoaded = ChiliBulletGun.isLoaded(itemStack);
        boolean isInteractingArmRight = interactingArm == HumanoidArm.RIGHT;
        float armDirection = isInteractingArmRight ? 1.0F : -1.0F;
        int useItemRemainingTicks = abstractClientPlayer.getUseItemRemainingTicks();
        matrixStack.pushPose();

        if (abstractClientPlayer.isUsingItem() && useItemRemainingTicks > 0
                && abstractClientPlayer.getUsedItemHand() == interactionHand) {
            // Loading
            applyItemArmTransform(matrixStack, interactingArm, equippedProgress);
            matrixStack.translate(armDirection * -0.4785682F, -0.094387F, 0.05731531F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(-11.935F));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(armDirection * 65.3F));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(armDirection * -9.785F));
            float loadingTicks = itemStack.getUseDuration() - (useItemRemainingTicks - partialTicks + 1.0F);
            float loadingProgress = Math.min(loadingTicks / ChiliBulletGun.getChargeDuration(itemStack), 1.0F);

            if (loadingProgress > 0.1F) {
                float y = Mth.sin((loadingTicks - 0.1F) * 1.3F) * (loadingProgress - 0.1F);
                matrixStack.translate(0.0F, y * 0.004F, 0.0F);
            }

            matrixStack.translate(0.0F, 0.0F, loadingProgress * 0.04F);
            matrixStack.scale(1.0F, 1.0F, 1.0F + loadingProgress * 0.2F);
            matrixStack.mulPose(Vector3f.YN.rotationDegrees(armDirection * 45.0F));
        } else {
            // Loaded/Empty
            float x = -0.4F * Mth.sin(Mth.sqrt(swingProgress) * Mth.PI);
            float y = 0.2F * Mth.sin(Mth.sqrt(swingProgress) * Mth.TWO_PI);
            float z = -0.2F * Mth.sin(swingProgress * Mth.PI);
            matrixStack.translate(armDirection * x, y, z);
            applyItemArmTransform(matrixStack, interactingArm, equippedProgress);
            applyItemArmAttackTransform(matrixStack, interactingArm, swingProgress);

            if (isLoaded && swingProgress < 0.001F && isInteractingWithMainHand) {
                // Loaded
                matrixStack.translate(armDirection * -0.641864F, 0.0F, 0.0F);
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(armDirection * 10.0F));
            }
        }

        renderItem(abstractClientPlayer, itemStack, isInteractingArmRight ?
                ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND
                : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND,
                !isInteractingArmRight, matrixStack, buffer, combinedLight);
        matrixStack.popPose();
        ci.cancel();
    }
}
