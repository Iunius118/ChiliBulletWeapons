package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider {

    public static void addProviders(boolean needsRun, DataGenerator gen) {
        var packOutput = gen.getPackOutput();
        gen.addProvider(needsRun, new ModEnglishLanguageProvider(packOutput, Constants.MOD_ID));
    }

    private static String getSubtitleKey(SoundEvent soundEvent) {
        return "subtitles." + soundEvent.getLocation().getPath();
    }

    public static class ModEnglishLanguageProvider extends LanguageProvider {

        public ModEnglishLanguageProvider(PackOutput output, String modId) {
            super(output, modId, "en_us");
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
        }
    }
}
