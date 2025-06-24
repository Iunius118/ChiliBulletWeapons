package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunHelper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GunItemPropertyFunction implements ClampedItemPropertyFunction {
    public static final float DYED_WEIGHT = 0.5F;
    public static final float LOADING_WEIGHT = 0.25F;
    public static final float BAYONETED_WEIGHT = 0.125F;
    public static final float MULTISHOT_WEIGHT = 0.0625F;
    public static final float PIERCING_WEIGHT = 0.03125F;

    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        return getValue(stack, level, entity, seed);
    }

    public static float getValue(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        return getValue(
                stack.has(ModDataComponents.DYED_GUN_COLORS),
                ChiliBulletGunHelper.isLoading(stack),
                ChiliBulletGunHelper.isBayoneted(stack),
                ChiliBulletGunHelper.getBarrelCount(stack) > 1,
                ChiliBulletGunHelper.getPiercing(stack) > 0);
    }

    public static float getValue(boolean isDyed, boolean isLoading, boolean isBayoneted, boolean isMultiShot, boolean isPiercing) {
        float result = 0.0F;

        if (isDyed) {
            result += DYED_WEIGHT;
        }

        if (isLoading) {
            result += LOADING_WEIGHT;
        }

        if (isBayoneted) {
            result += BAYONETED_WEIGHT;
        }

        if (isMultiShot) {
            result += MULTISHOT_WEIGHT;
        }

        if (isPiercing) {
            result += PIERCING_WEIGHT;
        }

        return result;
    }
}
