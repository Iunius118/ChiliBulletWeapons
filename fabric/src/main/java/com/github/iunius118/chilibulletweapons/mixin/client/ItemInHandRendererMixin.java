package com.github.iunius118.chilibulletweapons.mixin.client;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunHelper;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
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

@Mixin(value = ItemInHandRenderer.class, remap = false)
public abstract class ItemInHandRendererMixin {

    @Shadow
	protected abstract void applyItemArmAttackTransform(PoseStack poseStack, HumanoidArm humanoidArm,
                                                        float attackValue);
    @Shadow
    protected abstract void applyItemArmTransform(PoseStack poseStack, HumanoidArm humanoidArm, float inverseArmHeight);
    @Shadow
    protected abstract void renderItem(final LivingEntity mob, final ItemStack itemStack, final ItemDisplayContext type,
                                       final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector,
                                       final int lightCoords);

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void onRenderArmWithItem(AbstractClientPlayer player, float frameInterp, float xRot, InteractionHand hand,
                                     float attack, ItemStack itemStack, float inverseArmHeight, PoseStack poseStack,
                                     SubmitNodeCollector submitNodeCollector, int lightCoords, CallbackInfo ci) {
        if (player.isScoping() || !(itemStack.getItem() instanceof ChiliBulletGunItem)) {
            return;
        }

        // Fix chili bullet gun animation in first person view
        boolean isMainHand = hand == InteractionHand.MAIN_HAND;
		HumanoidArm arm = isMainHand ? player.getMainArm() : player.getMainArm().getOpposite();
        boolean isLoaded = ChiliBulletGunHelper.isLoaded(itemStack);
        boolean isRightArm = arm == HumanoidArm.RIGHT;
        float armDirection = isRightArm ? 1.0F : -1.0F;
        int useItemRemainingTicks = player.getUseItemRemainingTicks();
        // Push pose stack
        poseStack.pushPose();
        applyItemArmTransform(poseStack, arm, inverseArmHeight);

        if (player.isUsingItem() && useItemRemainingTicks > 0 && player.getUsedItemHand() == hand && !isLoaded) {
            // Loading
            poseStack.translate(armDirection * -0.4785682F, -0.094387F, 0.05731531F);
            poseStack.mulPose(Axis.XP.rotationDegrees(-11.935F));
            poseStack.mulPose(Axis.YP.rotationDegrees(armDirection * 65.3F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(armDirection * -9.785F));
            float timeHeld =
                    (float) itemStack.getUseDuration(player) - ((float) useItemRemainingTicks - frameInterp + 1.0F);
            float loadingProgress = ChiliBulletGunItem.getLoadingProgress(timeHeld, itemStack, player);

            if (loadingProgress > 0.1F) {
                float shake = Mth.sin((timeHeld - 0.1F) * 1.3F) * (loadingProgress - 0.1F);
                poseStack.translate(0.0F, shake * 0.004F, 0.0F);
            }

            poseStack.translate(0.0F, 0.0F, loadingProgress * 0.04F);
            poseStack.scale(1.0F, 1.0F, 1.0F + loadingProgress * 0.2F);
            poseStack.mulPose(Axis.YN.rotationDegrees(armDirection * 45.0F));
        } else {
            // Loaded/Empty
            float x = -0.4F * Mth.sin(Mth.sqrt(attack) * Mth.PI);
            float y = 0.2F * Mth.sin(Mth.sqrt(attack) * Mth.TWO_PI);
            float z = -0.2F * Mth.sin(attack * Mth.PI);
            poseStack.translate(armDirection * x, y, z);
            applyItemArmAttackTransform(poseStack, arm, attack);

            if (isLoaded && attack < 0.001F && isMainHand) {
                // Loaded
                poseStack.translate(armDirection * -0.641864F, 0.0F, 0.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(armDirection * 10.0F));
            }
        }

        renderItem(player, itemStack,
                isRightArm ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                poseStack, submitNodeCollector, lightCoords);
        // Pop pose stack
        poseStack.popPose();
        ci.cancel();
    }
}
