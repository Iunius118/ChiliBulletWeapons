package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider {

    public static void addProviders(boolean needsRun, DataGenerator generator) {
        generator.addProvider(needsRun, new ModEnglishLanguageProvider(generator));
    }

    public static class ModEnglishLanguageProvider extends LanguageProvider {

        public ModEnglishLanguageProvider(DataGenerator generator) {
            super(generator, Constants.MOD_ID, "en_us");
        }

        @Override
        protected void addTranslations() {
            // Creative mode tabs
            add(Constants.CreativeModeTabs.TITLE_MOD_MAIN, "Chili Bullet Weapons");

            // Block
            add(ModBlocks.CHILI_PEPPER, "Chili Pepper Crops (CBW)");
            add(ModBlocks.CURVED_CHILI_STRING, "String of Curved Chili Peppers");
            add(ModBlocks.POTTED_CHILI_PEPPER_FLOWERING, "Potted Flowering Chili Pepper");
            add(ModBlocks.POTTED_CHILI_PEPPER_GREEN, "Potted Green Chili Pepper");
            add(ModBlocks.POTTED_CHILI_PEPPER_RED, "Potted Red Chili Pepper");
            add(ModBlocks.HOT_SAUCE_BARREL, "Barrel of Hot Chili Sauce");

            // Item.Plants
            add(ModItems.CHILI_SEEDS, "Chili Pepper Seeds (CBW)");
            add(ModItems.CURVED_GREEN_CHILI, "Curved Green Chili Pepper");
            add(ModItems.BULLET_CHILI, "Bullet-like Chili Pepper");
            add(ModItems.CURVED_CHILI, "Curved Chili Pepper");
            add(ModItems.DRIED_CURVED_CHILI, "Dried Curved Chili Pepper");
            add(ModItems.BULLET_CHILI_SACK, "Sack of Bullet-like Chili Peppers");
            add(ModItems.CURVED_CHILI_SACK, "Sack of Curved Chili Peppers");
            // Item.Fuel
            add(ModItems.CHILI_BIOFUEL, "Chili Plant Biofuel");
            // Item.Foods
            add(ModItems.HOT_SAUCE, "Hot Chili Sauce");
            add(ModItems.GREEN_HOT_SAUCE, "Green Hot Chili Sauce");
            add(ModItems.PICKLED_GREEN_CHILI, "Pickled Green Chili Pepper");
            add(ModItems.CHILI_CHICKEN_SANDWICH, "Chili Chicken Sandwich");
            add(ModItems.CHILI_FISH_SANDWICH, "Chili Fish Sandwich");
            add(ModItems.CHILI_MEAT_SANDWICH, "Chili Meat Sandwich");
            add(ModItems.CHILI_POTATO_SANDWICH, "Chili Potato Sandwich");
            add(ModItems.HALF_CHILI_CHICKEN_SANDWICH, "Half-sized Chili Chicken Sandwich");
            add(ModItems.HALF_CHILI_FISH_SANDWICH, "Half-sized Chili Fish Sandwich");
            add(ModItems.HALF_CHILI_MEAT_SANDWICH, "Half-sized Chili Meat Sandwich");
            add(ModItems.HALF_CHILI_POTATO_SANDWICH, "Half-sized Chili Potato Sandwich");
            add(ModItems.PASTA_OIL_AND_CHILI, "Pasta Olio e Peperoncino");
            add(ModItems.FRIED_CHILI_PEPPER, "Fried Chili Pepper");
            add(ModItems.CHILI_CHOCOLATE, "Chili Chocolate");
            add(ModItems.CHILI_CHOCOLATE_CHICKEN, "Chicken with Chili Chocolate Sauce");
            // Item.Weapons
            add(ModItems.CHILI_ARROW, "Bullet Chili Arrow");
            add(ModItems.CHILI_BULLET, "Chili Bullet");
            add(ModItems.UPGRADE_GUN_BAYONET, "Bayonet Upgrade");
            add(ModItems.UPGRADE_GUN_BARREL, "Gun Barrel Upgrade");
            add(ModItems.UPGRADE_GUN_MECHANISM, "Gun Mechanism Upgrade");
            add(ModItems.GUN, "Chili Bullet Gun");
            add(Constants.ChiliBulletGun.DESCRIPTION_PISTOL, "Chili Bullet Pistol");
            add(Constants.ChiliBulletGun.DESCRIPTION_RIFLE, "Chili Bullet Rifle");
            add(Constants.ChiliBulletGun.DESCRIPTION_VOLLEY_GUN, "Chili Bullet Volley Gun");
            add(ModItems.BAYONETED_GUN, "Chili Bullet Gun with Bayonet");
            add(Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_PISTOL, "Chili Bullet Pistol with Bayonet");
            add(Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_RIFLE, "Chili Bullet Rifle with Bayonet");
            add(Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_VOLLEY_GUN, "Chili Bullet Volley Gun with Bayonet");
            add(ModItems.MACHINE_GUN, "Chili Bullet Machine Gun");
            // Item.Misc.
            add(ModItems.CAPSAICIN_POWDER, "Capsaicin Powder");

            // Tooltips
            add(Constants.UpgradeGunPart.TOOLTIP_UPGRADE_GUN_1, "Hold gun in off-hand and");
            add(Constants.UpgradeGunPart.TOOLTIP_UPGRADE_GUN_2, "use this item in main hand");
            add(Constants.UpgradeGunPart.TOOLTIP_UPGRADE_GUN_3, "to upgrade gun.");

            // Entity
            add(ModEntityTypes.CHILI_ARROW, "Bullet Chili Arrow");
            add(ModEntityTypes.CHILI_BULLET, "Chili Bullet");

            // Subtitles
            add(getSubtitleKey(ModSoundEvents.GUN_SHOOT), "Chili Bullet Gun fires");
            add(getSubtitleKey(ModSoundEvents.GUN_ACTION_CLOSE), "Chili Bullet Gun loads");
            add(getSubtitleKey(ModSoundEvents.GUN_UPGRADE), "Chili Bullet Gun upgraded");

            // Advancements
            addModAdvancement("main", "root",
                    "Chili Bullet Weapons", "Chili peppers, foods and weapons");
            addModAdvancement("main", "curved_chili",
                    "Hot Topic", "Obtain a Curved Chili Pepper");
            addModAdvancement("main", "harvested_chili_pepper_with_shears",
                    "Be Gentle", "Harvest Curved Chili Peppers using shears");
            addModAdvancement("main", "half_sandwich",
                    "Let's Go Halves", "Obtain half-sized sandwiches");
            addModAdvancement("main", "threw_hot_sauce",
                    "Non-Lethal?", "Throw hot sauce at enemies");
            addModAdvancement("main", "bullet_chili",
                    "Like a Bullet?", "Obtain a Bullet-like Chili Pepper");
            addModAdvancement("main", "exploded_chili_arrow",
                    "Boom!", "Shoot a Bullet Chili Arrow and make it explode");
            addModAdvancement("main", "shot_gun",
                    "Bang!", "Shoot a Chili Bullet Gun");
            addModAdvancement("main", "upgraded_gun",
                    "Master Gunsmith", "Upgrade a gun with an upgrade item");
            addModAdvancement("main", "killed_by_chili_bullet",
                    "Quad-Sharp Shooter", "Defeat 4 mobs with one Chili Bullet");
            addModAdvancement("main", "shot_machine_gun",
                    "Handle With Care", "Shoot a Chili Bullet Machine Gun");
            addModAdvancement("main", "machine_gun_mending_1",
                    "Battle Has Changed", "Obtain a Mending enchanted Chili Bullet Machine Gun");
        }

        private String getSubtitleKey(SoundEvent soundEvent) {
            return "subtitles." + soundEvent.getLocation().getPath();
        }

        private void addModAdvancement(String tab, String name, String title, String description) {
            add("advancements.%s.%s.%s.title".formatted(Constants.MOD_ID, tab, name), title);
            add("advancements.%s.%s.%s.description".formatted(Constants.MOD_ID, tab, name), description);
        }
    }
}
