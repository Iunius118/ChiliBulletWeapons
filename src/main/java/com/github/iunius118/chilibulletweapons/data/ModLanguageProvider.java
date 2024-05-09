package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.*;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.sounds.SoundEvent;

public class ModLanguageProvider {
    public static void addProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(ModEnglishLanguageProvider::new);
    }

    public static class ModEnglishLanguageProvider extends FabricLanguageProvider {
        protected ModEnglishLanguageProvider(FabricDataOutput dataOutput) {
            super(dataOutput, "en_us");
        }

        @Override
        public void generateTranslations(TranslationBuilder translationBuilder) {
            // Creative mode tabs
            translationBuilder.add(ModCreativeModeTabs.KEY_MAIN, "Chili Bullet Weapons");

            // Block
            translationBuilder.add(ModBlocks.CHILI_PEPPER, "Chili Pepper Crops (CBW)");
            translationBuilder.add(ModBlocks.CURVED_CHILI_STRING, "String of Curved Chili Peppers");
            translationBuilder.add(ModBlocks.HOT_SAUCE_BARREL, "Barrel of Hot Chili Sauce");

            // Item.Plants
            translationBuilder.add(ModItems.CHILI_SEEDS, "Chili Pepper Seeds (CBW)");
            translationBuilder.add(ModItems.BULLET_CHILI, "Bullet-like Chili Pepper");
            translationBuilder.add(ModItems.CURVED_CHILI, "Curved Chili Pepper");
            translationBuilder.add(ModItems.DRIED_CURVED_CHILI, "Dried Curved Chili Pepper");
            translationBuilder.add(ModItems.BULLET_CHILI_SACK, "Sack of Bullet-like Chili Peppers");
            translationBuilder.add(ModItems.CURVED_CHILI_SACK, "Sack of Curved Chili Peppers");
            // Item.Foods
            translationBuilder.add(ModItems.HOT_SAUCE, "Hot Chili Sauce");
            translationBuilder.add(ModItems.CHILI_CHICKEN_SANDWICH, "Chili Chicken Sandwich");
            translationBuilder.add(ModItems.CHILI_FISH_SANDWICH, "Chili Fish Sandwich");
            translationBuilder.add(ModItems.CHILI_MEAT_SANDWICH, "Chili Meat Sandwich");
            translationBuilder.add(ModItems.CHILI_POTATO_SANDWICH, "Chili Potato Sandwich");
            translationBuilder.add(ModItems.HALF_CHILI_CHICKEN_SANDWICH, "Half-sized Chili Chicken Sandwich");
            translationBuilder.add(ModItems.HALF_CHILI_FISH_SANDWICH, "Half-sized Chili Fish Sandwich");
            translationBuilder.add(ModItems.HALF_CHILI_MEAT_SANDWICH, "Half-sized Chili Meat Sandwich");
            translationBuilder.add(ModItems.HALF_CHILI_POTATO_SANDWICH, "Half-sized Chili Potato Sandwich");
            translationBuilder.add(ModItems.PASTA_OIL_AND_CHILI, "Pasta Olio e Peperoncino");
            translationBuilder.add(ModItems.FRIED_CHILI_PEPPER, "Fried Chili Pepper");
            // Item.Weapons
            translationBuilder.add(ModItems.CHILI_ARROW, "Bullet Chili Arrow");
            translationBuilder.add(ModItems.CHILI_BULLET, "Chili Bullet");
            translationBuilder.add(ModItems.UPGRADE_GUN_BAYONET, "Bayonet Upgrade");
            translationBuilder.add(ModItems.UPGRADE_GUN_BARREL, "Gun Barrel Upgrade");
            translationBuilder.add(ModItems.UPGRADE_GUN_MECHANISM, "Gun Mechanism Upgrade");
            translationBuilder.add(ModItems.GUN, "Chili Bullet Gun");
            translationBuilder.add(ChiliBulletGun.DESCRIPTION_PISTOL, "Chili Bullet Pistol");
            translationBuilder.add(ChiliBulletGun.DESCRIPTION_RIFLE, "Chili Bullet Rifle");
            translationBuilder.add(ChiliBulletGun.DESCRIPTION_SHOTGUN, "Chili Bullet Shotgun");
            translationBuilder.add(ModItems.BAYONETED_GUN, "Chili Bullet Gun with Bayonet");
            translationBuilder.add(ChiliBulletGunBayoneted.DESCRIPTION_PISTOL, "Chili Bullet Pistol with Bayonet");
            translationBuilder.add(ChiliBulletGunBayoneted.DESCRIPTION_RIFLE, "Chili Bullet Rifle with Bayonet");
            translationBuilder.add(ChiliBulletGunBayoneted.DESCRIPTION_SHOTGUN, "Chili Bullet Shotgun with Bayonet");
            translationBuilder.add(ModItems.MACHINE_GUN, "Chili Bullet Machine Gun");

            // Tooltips
            translationBuilder.add(AbstractUpgradeGunPart.TOOLTIP_UPGRADE_GUN_1, "Hold gun in off-hand and");
            translationBuilder.add(AbstractUpgradeGunPart.TOOLTIP_UPGRADE_GUN_2, "use this item in main hand");
            translationBuilder.add(AbstractUpgradeGunPart.TOOLTIP_UPGRADE_GUN_3, "to upgrade gun.");

            // Entity
            translationBuilder.add(ModEntityTypes.CHILI_ARROW, "Bullet Chili Arrow");
            translationBuilder.add(ModEntityTypes.CHILI_BULLET, "Chili Bullet");

            // Subtitles
            translationBuilder.add(getSubtitleKey(ModSoundEvents.GUN_SHOOT), "Chili Bullet Gun fires");
            translationBuilder.add(getSubtitleKey(ModSoundEvents.GUN_ACTION_CLOSE), "Chili Bullet Gun loads");
            translationBuilder.add(getSubtitleKey(ModSoundEvents.GUN_UPGRADE), "Chili Bullet Gun upgraded");
        }

        private String getSubtitleKey(SoundEvent soundEvent) {
            return "subtitles." + soundEvent.getLocation().getPath();
        }
    }
}
