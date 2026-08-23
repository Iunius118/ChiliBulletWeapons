package com.github.iunius118.chilibulletweapons.platform;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.platform.services.IChiliBulletWeaponsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.util.Mth;

@Config(name = Constants.MOD_ID)
public class FabricChiliBulletWeaponsConfig implements ConfigData, IChiliBulletWeaponsConfig {

    /* Add config items */

    CommonConfig common = new CommonConfig();

    static class CommonConfig {
        boolean canMultishotMultiHit = true;
        float chiliArrowDamageMultiplier = Constants.ChiliArrow.DEFAULT_DAMAGE_MULTIPLIER;
        double chiliBulletBaseDamage = Constants.ChiliBullet.DEFAULT_BASE_DAMAGE;
    }

    /* Getters */

    @Override
    public boolean canMultishotMultiHit() {
        return getConfig().common.canMultishotMultiHit;
    }

    @Override
    public float getChiliArrowDamageMultiplier() {
        return Mth.clamp(getConfig().common.chiliArrowDamageMultiplier, 0, Constants.ChiliArrow.MAX_DAMAGE_MULTIPLIER);
    }

    @Override
    public double getChiliBulletBaseDamage() {
        return Math.max(getConfig().common.chiliBulletBaseDamage, 0);
    }

    private static FabricChiliBulletWeaponsConfig getConfig() {
        return AutoConfig.getConfigHolder(FabricChiliBulletWeaponsConfig.class).getConfig();
    }
}
