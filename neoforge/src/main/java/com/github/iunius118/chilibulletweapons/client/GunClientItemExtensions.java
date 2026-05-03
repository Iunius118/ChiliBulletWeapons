package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunHelper;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jspecify.annotations.Nullable;

public class GunClientItemExtensions implements IClientItemExtensions {
	@Override
	public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
		if (!entityLiving.swinging &&
				itemStack.getItem() instanceof ChiliBulletGunItem && ChiliBulletGunHelper.isLoaded(itemStack)) {
			// Return CROSSBOW_HOLD arm pose if the avatar is holding a loaded chili bullet gun in the hand
			return HumanoidModel.ArmPose.CROSSBOW_HOLD;
		} else {
			return IClientItemExtensions.super.getArmPose(entityLiving, hand, itemStack);
		}
	}
}
