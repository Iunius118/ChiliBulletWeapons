package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunHelper;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/**
 * An item model property that represents the state of the gun item.
 * It encodes the gun's state in binary and returns it as a float value between 0 and 1.
 */
public class GunItemModelProperty implements RangeSelectItemModelProperty {
    public static final MapCodec<GunItemModelProperty> MAP_CODEC = MapCodec.unit(GunItemModelProperty::new);

    public static final float DYED_WEIGHT = 0.5F;
    public static final float LOADING_WEIGHT = 0.25F;
    public static final float BAYONET_WEIGHT = 0.125F;
    public static final float MULTISHOT_WEIGHT = 0.0625F;
    public static final float PIERCING_WEIGHT = 0.03125F;

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable ItemOwner itemOwner, int seed) {
        return getValue(
                itemStack.has(ModDataComponents.DYED_GUN_COLORS),
                ChiliBulletGunHelper.isLoading(itemStack),
                ChiliBulletGunHelper.hasBayonet(itemStack),
                ChiliBulletGunHelper.getBarrelCount(itemStack) > 1,
                ChiliBulletGunHelper.getPiercing(itemStack) > 0);
    }

    public static float getValue(boolean isDyed, boolean isLoading, boolean hasBayonet, boolean isMultiShot,
                                 boolean isPiercing) {
        float result = 0.0F;

        if (isDyed) {
            result += DYED_WEIGHT;
        }

        if (isLoading) {
            result += LOADING_WEIGHT;
        }

        if (hasBayonet) {
            result += BAYONET_WEIGHT;
        }

        if (isMultiShot) {
            result += MULTISHOT_WEIGHT;
        }

        if (isPiercing) {
            result += PIERCING_WEIGHT;
        }

        return result;
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return MAP_CODEC;
    }
}
