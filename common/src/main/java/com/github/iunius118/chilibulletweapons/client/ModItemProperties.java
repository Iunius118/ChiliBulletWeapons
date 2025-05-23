package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunHelper;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;

public class ModItemProperties {
    public static final ClampedItemPropertyFunction PROPERTY_LOADING = (s, l, e, i) -> ChiliBulletGunHelper.isLoading(s) ? 1.0F : 0.0F;
    public static final ClampedItemPropertyFunction PROPERTY_MULTISHOT = (s, l, e, i) -> ChiliBulletGunHelper.getBarrelCount(s) > 1 ? 1.0F : 0.0F;
    public static final ClampedItemPropertyFunction PROPERTY_PIERCING = (s, l, e, i) -> ChiliBulletGunHelper.getPiercing(s) > 0 ? 1.0F : 0.0F;
    public static final ClampedItemPropertyFunction PROPERTY_BAYONETED = (s, l, e, i) -> ChiliBulletGunHelper.isBayoneted(s) ? 1.0F : 0.0F;
}
