package com.github.iunius118.chilibulletweapons;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final String MOD_ID = "chilibulletweapons";
    public static final String MOD_NAME = "Chili Bullet Weapons";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static class ChiliArrow {

        public static final ResourceLocation ID = CommonClass.modLocation("chili_arrow");
        public static final double FUSE_SPEED = 0.8D;
        public static final float DEFAULT_DAMAGE_MULTIPLIER = 1.0F;
        public static final float MAX_DAMAGE_MULTIPLIER = 8.0F;
    }

    public static class ChiliBullet {

        public static final ResourceLocation ID = CommonClass.modLocation("chili_bullet");
        public static final double DEFAULT_BASE_DAMAGE = 0.85D;
        public static final float GRAVITY = 0.03F;
        public static final byte LIFETIME = 40;
    }
}
