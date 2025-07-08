package com.github.iunius118.chilibulletweapons;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "chilibulletweapons";
    public static final String MOD_NAME = "Chili Bullet Weapons";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static class Blocks {
        // CBW Chili Peppers And Foods
        public static final ResourceLocation CHILI_PEPPER = CommonClass.modLocation("chili_pepper");
    }

    public static class Items {
        // CBW Chili Peppers And Foods
        public static final ResourceLocation CHILI_SEEDS = CommonClass.modLocation("chili_seeds");
        public static final ResourceLocation CURVED_GREEN_CHILI = CommonClass.modLocation("curved_green_chili");
        public static final ResourceLocation CURVED_CHILI = CommonClass.modLocation("curved_chili");
        public static final ResourceLocation FERROCAPSICUMIUM_INGOT = CommonClass.modLocation("ferrocapsicumium_ingot");
        public static final ResourceLocation FERROCAPSICUMIUM_BLOCK = CommonClass.modLocation("ferrocapsicumium_block");

        // Plants
        public static final ResourceLocation BULLET_CHILI = CommonClass.modLocation("bullet_chili");
        public static final ResourceLocation BULLET_CHILI_SACK = CommonClass.modLocation("bullet_chili_sack");

        // Weapons
        public static final ResourceLocation CHILI_ARROW = CommonClass.modLocation("chili_arrow");
        public static final ResourceLocation CHILI_BULLET = CommonClass.modLocation("chili_bullet");
        public static final ResourceLocation GUN = CommonClass.modLocation("gun");
        public static final ResourceLocation MACHINE_GUN = CommonClass.modLocation("machine_gun");
        public static final ResourceLocation UPGRADE_GUN_BAYONET = CommonClass.modLocation("upgrade_gun_bayonet");
        public static final ResourceLocation UPGRADE_GUN_BARREL = CommonClass.modLocation("upgrade_gun_barrel");
        public static final ResourceLocation UPGRADE_GUN_MECHANISM = CommonClass.modLocation("upgrade_gun_mechanism");

        // Creative tab icon
        public static final ResourceLocation ICON_MAIN = CommonClass.modLocation("icon_main");
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
        public static final ResourceLocation GUN_CONTENTS = CommonClass.modLocation("gun_contents");
        public static final ResourceLocation BAYONETED = CommonClass.modLocation("bayoneted");
        public static final ResourceLocation FIXED = CommonClass.modLocation("fixed");
        public static final ResourceLocation DYED_GUN_COLORS = CommonClass.modLocation("dyed_gun_colors");

        // Deprecated
        public static final ResourceLocation QUICK_LOADING = CommonClass.modLocation("quick_loading");
        public static final ResourceLocation PIERCING = CommonClass.modLocation("piercing");
        public static final ResourceLocation MULTISHOT = CommonClass.modLocation("multishot");
    }

    public static class CriterionTriggers {
        public static final ResourceLocation SHOT_CHILI_BULLET_GUN = CommonClass.modLocation("shot_chili_bullet_gun");
        public static final ResourceLocation UPGRADED_CHILI_BULLET_GUN = CommonClass.modLocation("upgraded_chili_bullet_gun");
        public static final ResourceLocation KILLED_BY_CHILI_BULLET = CommonClass.modLocation("killed_by_chili_bullet");
    }

    public static class CreativeModeTabs {
        public static final ResourceLocation MAIN = CommonClass.modLocation("main");
    }

    public static class ItemProperties {
        public static final ResourceLocation PROPERTY_GUN = CommonClass.modLocation("gun");
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
        // Item tooltip keys
        public static final String TOOLTIP_BARREL_COUNT = "tooltip.chilibulletweapons.barrel_count";
        public static final String TOOLTIP_QUICK_LOAD = "tooltip.chilibulletweapons.quick_load";
    }

    public static class UpgradeGunPart {
        // Item tooltip keys
        public static final String TOOLTIP_UPGRADE_GUN_1 = "tooltip.chilibulletweapons.upgrade_gun_1";
        public static final String TOOLTIP_UPGRADE_GUN_2 = "tooltip.chilibulletweapons.upgrade_gun_2";
        public static final String TOOLTIP_UPGRADE_GUN_3 = "tooltip.chilibulletweapons.upgrade_gun_3";
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
        public static final float CRIT_RATE = 0.1F;
        public static final double CRIT_DAMAGE_MULTIPLIER = ChiliBulletGun.POWER_PIERCING * ChiliBulletGun.POWER_PIERCING;
    }
}
