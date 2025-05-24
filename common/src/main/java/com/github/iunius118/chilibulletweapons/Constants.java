package com.github.iunius118.chilibulletweapons;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "chilibulletweapons";
    public static final String MOD_NAME = "Chili Bullet Weapons";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static class Items {
        // Plants
        public static final ResourceLocation BULLET_CHILI = CommonClass.modLocation("bullet_chili");
        public static final ResourceLocation BULLET_CHILI_SACK = CommonClass.modLocation("bullet_chili_sack");

        // Weapons
        public static final ResourceLocation CHILI_ARROW = CommonClass.modLocation("chili_arrow");
        public static final ResourceLocation CHILI_BULLET = CommonClass.modLocation("chili_bullet");
        public static final ResourceLocation UPGRADE_GUN_BAYONET = CommonClass.modLocation("upgrade_gun_bayonet");
        public static final ResourceLocation UPGRADE_GUN_BARREL = CommonClass.modLocation("upgrade_gun_barrel");
        public static final ResourceLocation UPGRADE_GUN_MECHANISM = CommonClass.modLocation("upgrade_gun_mechanism");
        public static final ResourceLocation GUN = CommonClass.modLocation("gun");
        public static final ResourceLocation MACHINE_GUN = CommonClass.modLocation("machine_gun");
    }

    public static class EntityTypes {
        public static final ResourceLocation CHILI_ARROW = CommonClass.modLocation("chili_arrow");
        public static final ResourceLocation CHILI_BULLET = CommonClass.modLocation("chili_bullet");
    }

    public static class SoundEvents {
        public static final ResourceLocation GUN_SHOOT = CommonClass.modLocation("item_gun_shoot");
        public static final ResourceLocation GUN_ACTION_OPEN = CommonClass.modLocation("item_gun_action_open");
        public static final ResourceLocation GUN_ACTION_CLOSE = CommonClass.modLocation("item_gun_action_close");
        public static final ResourceLocation GUN_UPGRADE = CommonClass.modLocation("item_gun_upgrade");
    }

    public static class DataComponentTypes {
        public static final ResourceLocation LOADING = CommonClass.modLocation("loading");
        public static final ResourceLocation QUICK_LOADING = CommonClass.modLocation("quick_loading");
        public static final ResourceLocation MULTISHOT = CommonClass.modLocation("multishot");
        public static final ResourceLocation PIERCING = CommonClass.modLocation("piercing");
        public static final ResourceLocation BAYONETED = CommonClass.modLocation("bayoneted");
    }

    public static class CreativeModeTabs {
        public static final ResourceLocation MAIN = CommonClass.modLocation("main");
    }

    public static class ItemProperties {
        public static final ResourceLocation PROPERTY_LOADING = CommonClass.modLocation("loading");
        public static final ResourceLocation PROPERTY_MULTISHOT = CommonClass.modLocation("multishot");
        public static final ResourceLocation PROPERTY_PIERCING = CommonClass.modLocation("piercing");
        public static final ResourceLocation PROPERTY_BAYONETED = CommonClass.modLocation("bayoneted");
    }

    public static class ChiliArrow {
        public static final double FUSE_SPEED = 0.8D;
        public static final float DEFAULT_DAMAGE_MULTIPLIER = 1.0F;
        public static final float MAX_DAMAGE_MULTIPLIER = 8.0F;
    }

    public static class ChiliBullet {
        public static final double DEFAULT_BASE_DAMAGE = 0.85D;
        public static final float GRAVITY = 0.03F;
        public static final byte LIFETIME = 40;
    }

    public static class ChiliBulletGun {
        // Data components
        public static final int MAX_QUICK_LOADING = 3;
        public static final int MAX_PIERCING  = 5;
        public static final int BASIC_PIERCING  = 3;
        // Capacity
        public static final int CAPACITY_BASIC = 1;
        public static final int CAPACITY_MULTISHOT = 4;
        // Reload duration
        public static final int RELOAD_BASIC = 14;
        public static final int RELOAD_MULTISHOT = 22;
        public static final int RELOAD_MACHINE_GUN = 2;
        public static final int RELOAD_PER_QUICK_CHARGE = 2;
        public static final int RELOAD_BAYONETED_ADDITIONAL = 2;
        // Power
        public static final float POWER_BASIC = 3F;
        public static final float POWER_PIERCING = 4F;
        // Inaccuracy
        public static final float INACCURACY_BASIC = 1F;
        public static final float INACCURACY_PIERCING = 0.5F;
        public static final float INACCURACY_MULTISHOT_CORRECTION = 4F;
        // Bayonet specs
        public static final float BAYONET_ATTACK_DAMAGE = 5F;
        public static final float BAYONET_ATTACK_SPEED = -2.8F;
        // Enchantment
        public static final int ENCHANTMENT_VALUE = 15;
        // Description keys
        public static final String DESCRIPTION_PISTOL = "item.chilibulletweapons.gun.pistol";
        public static final String DESCRIPTION_RIFLE = "item.chilibulletweapons.gun.rifle";
        public static final String DESCRIPTION_VOLLEY_GUN = "item.chilibulletweapons.gun.volley_gun";
        public static final String DESCRIPTION_BAYONETED_PISTOL = "item.chilibulletweapons.gun.bayoneted.pistol";
        public static final String DESCRIPTION_BAYONETED_RIFLE = "item.chilibulletweapons.gun.bayoneted.rifle";
        public static final String DESCRIPTION_BAYONETED_VOLLEY_GUN = "item.chilibulletweapons.gun.bayoneted.volley_gun";
    }
}
