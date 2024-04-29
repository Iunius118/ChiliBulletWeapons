package com.github.iunius118.chilibulletweapons.integration.autoconfig;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.entity.ChiliArrow;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.util.Mth;

@Config(name = ChiliBulletWeapons.MOD_ID)
public class ChiliBulletWeaponsConfig implements ConfigData {
    /* Add config items */
    CommonConfig common = new CommonConfig();

    public ChiliBulletWeaponsConfig() {
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
        return Mth.clamp(getConfig().common.chiliArrowDamageMultiplier, 0, 8);
    }

    public static double getChiliBulletBaseDamage() {
        return Math.max(getConfig().common.chiliBulletBaseDamage, 0);
    }

    private static ChiliBulletWeaponsConfig getConfig() {
        return AutoConfig.getConfigHolder(ChiliBulletWeaponsConfig.class).getConfig();
    }
}
