package com.github.iunius118.chilibulletweapons.integration.autoconfig;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.entity.ChiliArrow;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = ChiliBulletWeapons.MOD_ID)
public class ModConfig implements ConfigData {
    /* Add config items */
    CommonConfig common = new CommonConfig();

    public ModConfig() {
    }

    static class CommonConfig {
        boolean canShotgunMultiHit = true;
        float chiliArrowDamageMultiplier = ChiliArrow.DEFAULT_DAMAGE_MULTIPLIER;
        double chiliBulletBaseDamage = ChiliBullet.DEFAULT_BASE_DAMAGE;
    }

    /* Getters */
    public static boolean canShotgunMultiHit() {
        return getConfig().common.canShotgunMultiHit;
    }

    public static float getChiliArrowDamageMultiplier() {
        return Math.max(getConfig().common.chiliArrowDamageMultiplier, 0);
    }

    public static double getChiliBulletBaseDamage() {
        return Math.max(getConfig().common.chiliBulletBaseDamage, 0);
    }

    private static ModConfig getConfig() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
