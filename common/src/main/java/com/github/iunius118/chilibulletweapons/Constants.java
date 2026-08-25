package com.github.iunius118.chilibulletweapons;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "chilibulletweapons";
    public static final String MOD_NAME = "Chili Bullet Weapons";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static class Blocks {
        public static final ResourceKey<Block> CHILI_PEPPER = createKey("chili_pepper");
        public static final ResourceKey<Block> CURVED_CHILI_STRING = createKey("curved_chili_string");
        public static final ResourceKey<Block> HOT_SAUCE_BARREL = createKey("hot_sauce_barrel");
        public static final ResourceKey<Block> POTTED_CHILI_PEPPER_FLOWERING =
                createKey("potted_chili_pepper_flowering");
        public static final ResourceKey<Block> POTTED_CHILI_PEPPER_GREEN = createKey("potted_chili_pepper_green");
        public static final ResourceKey<Block> POTTED_CHILI_PEPPER_RED = createKey("potted_chili_pepper_red");

        private static ResourceKey<Block> createKey(String path) {
            return ResourceKey.create(Registries.BLOCK, CommonClass.modLocation(path));
        }
    }

    public static class Items {
        // Plants
        public static final ResourceKey<Item> CHILI_SEEDS = createKey("chili_seeds");
        public static final ResourceKey<Item> CURVED_GREEN_CHILI = createKey("curved_green_chili");
        public static final ResourceKey<Item> BULLET_CHILI = createKey("bullet_chili");
        public static final ResourceKey<Item> CURVED_CHILI = createKey("curved_chili");
        public static final ResourceKey<Item> CURVED_CHILI_STRING = createKey("curved_chili_string");
        public static final ResourceKey<Item> DRIED_CURVED_CHILI = createKey("dried_curved_chili");
        public static final ResourceKey<Item> BULLET_CHILI_SACK = createKey("bullet_chili_sack");
        public static final ResourceKey<Item> CURVED_CHILI_SACK = createKey("curved_chili_sack");
        public static final ResourceKey<Item> POTTED_CHILI_PEPPER_FLOWERING =
                createKey("potted_chili_pepper_flowering");
        public static final ResourceKey<Item> POTTED_CHILI_PEPPER_GREEN = createKey("potted_chili_pepper_green");
        public static final ResourceKey<Item> POTTED_CHILI_PEPPER_RED = createKey("potted_chili_pepper_red");

        // Fuel
        public static final ResourceKey<Item> CHILI_BIOFUEL = createKey("chili_biofuel");

        // Foods
        public static final ResourceKey<Item> HOT_SAUCE = createKey("hot_sauce");
        public static final ResourceKey<Item> HOT_SAUCE_BARREL = createKey("hot_sauce_barrel");
        public static final ResourceKey<Item> GREEN_HOT_SAUCE = createKey("green_hot_sauce");
        public static final ResourceKey<Item> PICKLED_GREEN_CHILI = createKey("pickled_green_chili");
        public static final ResourceKey<Item> CHILI_CHICKEN_SANDWICH = createKey("chili_chicken_sandwich");
        public static final ResourceKey<Item> CHILI_FISH_SANDWICH = createKey("chili_fish_sandwich");
        public static final ResourceKey<Item> CHILI_MEAT_SANDWICH = createKey("chili_meat_sandwich");
        public static final ResourceKey<Item> CHILI_POTATO_SANDWICH = createKey("chili_potato_sandwich");
        public static final ResourceKey<Item> HALF_CHILI_CHICKEN_SANDWICH = createKey("half_chili_chicken_sandwich");
        public static final ResourceKey<Item> HALF_CHILI_FISH_SANDWICH = createKey("half_chili_fish_sandwich");
        public static final ResourceKey<Item> HALF_CHILI_MEAT_SANDWICH = createKey("half_chili_meat_sandwich");
        public static final ResourceKey<Item> HALF_CHILI_POTATO_SANDWICH = createKey("half_chili_potato_sandwich");
        public static final ResourceKey<Item> PASTA_OIL_AND_CHILI = createKey("pasta_oil_and_chili");
        public static final ResourceKey<Item> FRIED_CHILI_PEPPER = createKey("fried_chili_pepper");
        public static final ResourceKey<Item> CHILI_CHOCOLATE = createKey("chili_chocolate");
        public static final ResourceKey<Item> CHILI_CHOCOLATE_CHICKEN = createKey("chili_chocolate_chicken");

        // Weapons
        public static final ResourceKey<Item> CHILI_ARROW = createKey("chili_arrow");
        public static final ResourceKey<Item> CHILI_BULLET = createKey("chili_bullet");
        public static final ResourceKey<Item> UPGRADE_GUN_BAYONET = createKey("upgrade_gun_bayonet");
        public static final ResourceKey<Item> UPGRADE_GUN_BARREL = createKey("upgrade_gun_barrel");
        public static final ResourceKey<Item> UPGRADE_GUN_MECHANISM = createKey("upgrade_gun_mechanism");
        public static final ResourceKey<Item> GUN = createKey("gun");
        public static final ResourceKey<Item> BAYONETED_GUN = createKey("bayoneted_gun");
        public static final ResourceKey<Item> MACHINE_GUN = createKey("machine_gun");

        // Misc.
        public static final ResourceKey<Item> CAPSAICIN_POWDER = createKey("capsaicin_powder");

        // Creative tab icon
        public static final ResourceKey<Item> ICON_MAIN = createKey("icon_main");

        private static ResourceKey<Item> createKey(String path) {
            return ResourceKey.create(Registries.ITEM, CommonClass.modLocation(path));
        }
    }

    public static class SoundEvents {
        public static final ResourceKey<SoundEvent> CHILI_PEPPER_PICK_CHILI_PEPPERS =
                createKey("block_chili_pepper_pick_chili_peppers");
        public static final ResourceKey<SoundEvent> GUN_SHOOT = createKey("item_gun_shoot");
        public static final ResourceKey<SoundEvent> GUN_ACTION_OPEN = createKey("item_gun_action_open");
        public static final ResourceKey<SoundEvent> GUN_ACTION_CLOSE = createKey("item_gun_action_close");
        public static final ResourceKey<SoundEvent> GUN_UPGRADE = createKey("item_gun_upgrade");

        private static ResourceKey<SoundEvent> createKey(String path) {
            return ResourceKey.create(Registries.SOUND_EVENT, CommonClass.modLocation(path));
        }
    }

    public static class EntityTypes{
        public static final ResourceKey<EntityType<?>> CHILI_ARROW = createKey(ChiliArrow.ID);
        public static final ResourceKey<EntityType<?>> CHILI_BULLET = createKey(ChiliBullet.ID);

        private static ResourceKey<EntityType<?>> createKey(String path) {
            return ResourceKey.create(Registries.ENTITY_TYPE, CommonClass.modLocation(path));
        }

        private static ResourceKey<EntityType<?>> createKey(ResourceLocation id) {
            return ResourceKey.create(Registries.ENTITY_TYPE, id);
        }
    }

    public static class CriterionTriggers {
        public static final ResourceLocation HARVESTED_CHILI_PEPPER_WITH_SHEARS =
                createId("harvested_chili_pepper_with_shears");
        public static final ResourceLocation THREW_HOT_SAUCE = createId("threw_hot_sauce");
        public static final ResourceLocation EXPLODED_CHILI_ARROW = createId("exploded_chili_arrow");
        public static final ResourceLocation SHOT_CHILI_BULLET_GUN = createId("shot_chili_bullet_gun");
        public static final ResourceLocation UPGRADED_CHILI_BULLET_GUN = createId("upgraded_chili_bullet_gun");
        public static final ResourceLocation KILLED_BY_CHILI_BULLET = createId("killed_by_chili_bullet");

        private static ResourceLocation createId(String path) {
            return CommonClass.modLocation(path);
        }
    }

    public static class CreativeModeTabs {
        public static final ResourceKey<CreativeModeTab> MAIN = createKey("main");

        // Translation keys
        public static final String TITLE_MOD_MAIN = "itemGroup.%s.main".formatted(Constants.MOD_ID);

        private static ResourceKey<CreativeModeTab> createKey(String path) {
            return ResourceKey.create(Registries.CREATIVE_MODE_TAB, CommonClass.modLocation(path));
        }
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
        public static final String DESCRIPTION_BAYONETED_PISTOL = "item.chilibulletweapons.bayoneted_gun.pistol";
        public static final String DESCRIPTION_BAYONETED_RIFLE = "item.chilibulletweapons.bayoneted_gun.rifle";
        public static final String DESCRIPTION_BAYONETED_VOLLEY_GUN = "item.chilibulletweapons.bayoneted_gun.volley_gun";
        // Item tooltip keys
        public static final String TOOLTIP_BARREL_INFO = "tooltip.chilibulletweapons.barrel_info";
        public static final String TOOLTIP_QUICK_LOAD = "tooltip.chilibulletweapons.quick_load";
    }

    public static class UpgradeGunPart {
        // Item tooltip keys
        public static final String TOOLTIP_UPGRADE_GUN_1 = "tooltip.chilibulletweapons.upgrade_gun_1";
        public static final String TOOLTIP_UPGRADE_GUN_2 = "tooltip.chilibulletweapons.upgrade_gun_2";
        public static final String TOOLTIP_UPGRADE_GUN_3 = "tooltip.chilibulletweapons.upgrade_gun_3";
    }

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
        public static final float CRIT_RATE = 0.1F;
        public static final double CRIT_DAMAGE_MULTIPLIER =
                ChiliBulletGun.POWER_PIERCING * ChiliBulletGun.POWER_PIERCING;
    }
}