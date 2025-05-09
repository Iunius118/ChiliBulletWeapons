package com.github.iunius118.chilibulletweapons.platform;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.platform.services.IChiliBulletWeaponsConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.FloatValue;
import org.apache.commons.lang3.tuple.Pair;

public class ForgeChiliBulletWeaponsConfig implements IChiliBulletWeaponsConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    /* Add config items */

    public static class CommonConfig {
        public final BooleanValue canShotgunMultiHit;
        public final FloatValue chiliArrowDamageMultiplier;
        public final DoubleValue chiliBulletBaseDamage;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("common");
            canShotgunMultiHit = builder.define("canShotgunMultiHit", true);
            chiliArrowDamageMultiplier = builder.defineInRange("chiliArrowDamageMultiplier",
                    Constants.ChiliArrow.DEFAULT_DAMAGE_MULTIPLIER, 0, Constants.ChiliArrow.MAX_DAMAGE_MULTIPLIER);
            chiliBulletBaseDamage = builder.defineInRange("chiliBulletBaseDamage",
                    Constants.ChiliBullet.DEFAULT_BASE_DAMAGE, 0, Double.MAX_VALUE);
            builder.pop();
        }
    }

    /* Getters */

    @Override
    public boolean canShotgunMultiHit() {
        return COMMON_SPEC.isLoaded() ? COMMON.canShotgunMultiHit.get() : COMMON.canShotgunMultiHit.getDefault();
    }

    @Override
    public float getChiliArrowDamageMultiplier() {
        return COMMON_SPEC.isLoaded() ? COMMON.chiliArrowDamageMultiplier.get() : COMMON.chiliArrowDamageMultiplier.getDefault();
    }

    @Override
    public double getChiliBulletBaseDamage() {
        return COMMON_SPEC.isLoaded() ? COMMON.chiliBulletBaseDamage.get() : COMMON.chiliBulletBaseDamage.getDefault();
    }
}
