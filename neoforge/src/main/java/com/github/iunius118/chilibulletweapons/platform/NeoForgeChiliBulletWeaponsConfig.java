package com.github.iunius118.chilibulletweapons.platform;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.platform.services.IChiliBulletWeaponsConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import org.apache.commons.lang3.tuple.Pair;


public class NeoForgeChiliBulletWeaponsConfig implements IChiliBulletWeaponsConfig {

    public static final ModConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;

    static {
        final Pair<CommonConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    /* Add config items */

    public static class CommonConfig {
        public final BooleanValue canMultishotMultiHit;
        public final DoubleValue chiliArrowDamageMultiplier;
        public final DoubleValue chiliBulletBaseDamage;

        public CommonConfig(ModConfigSpec.Builder builder) {
            builder.push("common");
            canMultishotMultiHit = builder.define("canMultishotMultiHit", true);
            chiliArrowDamageMultiplier = builder.defineInRange("chiliArrowDamageMultiplier",
                    Constants.ChiliArrow.DEFAULT_DAMAGE_MULTIPLIER, 0, Constants.ChiliArrow.MAX_DAMAGE_MULTIPLIER);
            chiliBulletBaseDamage = builder.defineInRange("chiliBulletBaseDamage",
                    Constants.ChiliBullet.DEFAULT_BASE_DAMAGE, 0, Double.MAX_VALUE);
            builder.pop();
        }
    }

    /* Getters */

    @Override
    public boolean canMultishotMultiHit() {
        return COMMON_SPEC.isLoaded() ? COMMON.canMultishotMultiHit.get() : COMMON.canMultishotMultiHit.getDefault();
    }

    @Override
    public float getChiliArrowDamageMultiplier() {
        Double d = COMMON_SPEC.isLoaded() ? COMMON.chiliArrowDamageMultiplier.get() : COMMON.chiliArrowDamageMultiplier.getDefault();
        return d.floatValue();
    }

    @Override
    public double getChiliBulletBaseDamage() {
        return COMMON_SPEC.isLoaded() ? COMMON.chiliBulletBaseDamage.get() : COMMON.chiliBulletBaseDamage.getDefault();
    }
}
