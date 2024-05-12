package com.github.iunius118.chilibulletweapons.config;

import com.github.iunius118.chilibulletweapons.entity.ChiliArrow;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import org.apache.commons.lang3.tuple.Pair;

public class ChiliBulletWeaponsConfig {
    /* Add config items */
    public static class CommonConfig {
        public final BooleanValue canShotgunMultiHit;
        public final DoubleValue chiliArrowDamageMultiplier;
        public final DoubleValue chiliBulletBaseDamage;

        public CommonConfig(ModConfigSpec.Builder builder) {
            builder.push("common");
            canShotgunMultiHit = builder.define("canShotgunMultiHit", true);
            chiliArrowDamageMultiplier = builder.defineInRange("chiliArrowDamageMultiplier", ChiliArrow.DEFAULT_DAMAGE_MULTIPLIER, 0, 8);
            chiliBulletBaseDamage = builder.defineInRange("chiliBulletBaseDamage", ChiliBullet.DEFAULT_BASE_DAMAGE, 0, Double.MAX_VALUE);
            builder.pop();
        }
    }


    public static final ModConfigSpec commonSpec;
    public static final CommonConfig COMMON;

    static {
        final Pair<CommonConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(CommonConfig::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    /* Getters */
    public static boolean canShotgunMultiHit() {
        return COMMON.canShotgunMultiHit.get();
    }

    public static float getChiliArrowDamageMultiplier() {
        return COMMON.chiliArrowDamageMultiplier.get().floatValue();
    }

    public static double getChiliBulletBaseDamage() {
        return COMMON.chiliBulletBaseDamage.get();
    }
}
