package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGun;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunBayoneted;
import com.github.iunius118.chilibulletweapons.item.ModCreativeModeTabs;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public final TranslatedNameProvider translatedNameProvider;

    public ModLanguageProvider(PackOutput output, String modId, String locale, TranslatedNameProvider translatedNameProvider) {
        super(output, modId, locale);
        this.translatedNameProvider = translatedNameProvider;
    }

    public static void addProviders(boolean needsRun, String modId, DataGenerator gen) {
        var packOutput = gen.getPackOutput();
        gen.addProvider(needsRun, new ModLanguageProvider(packOutput, modId, "en_us", new TranslatedNameProvider()));
    }

    public static class TranslatedNameProvider {
        // Block
        public final String chiliPepperCropName = "Chili Pepper Crops (CBW)";

        // Item.Plants
        public final String bulletChiliName = "Bullet-like Chili Pepper";
        public final String curvedChiliName = "Curved Chili Pepper";
        public final String chiliSeedsName = "Chili Pepper Seeds (CBW)";
        public final String bulletChiliSackName = "Sack of Bullet-like Chili Peppers";
        public final String curvedChiliSackName = "Sack of Curved Chili Peppers";
        // Item.Foods
        public final String chiliChickenSandwichName = "Chili Chicken Sandwich";
        public final String chiliFishSandwichName = "Chili Fish Sandwich";
        public final String chiliMeatSandwichName = "Chili Meat Sandwich";
        public final String chiliPotatoSandwichName = "Chili Potato Sandwich";
        public final String halfChiliChickenSandwichName = "Half-sized Chili Chicken Sandwich";
        public final String halfChiliFishSandwichName = "Half-sized Chili Fish Sandwich";
        public final String halfChiliMeatSandwichName = "Half-sized Chili Meat Sandwich";
        public final String halfChiliPotatoSandwichName = "Half-sized Chili Potato Sandwich";
        public final String friedChiliPepperName = "Fried Chili Pepper";
        // Item.Weapons
        public final String chiliBulletName = "Chili Bullet";
        public final String gunName = "Chili Bullet Gun";
        public final String pistolName = "Chili Bullet Pistol";
        public final String rifleName = "Chili Bullet Rifle";
        public final String shotgunName = "Chili Bullet Shotgun";
        public final String bayonetedGunName = "Chili Bullet Gun with Bayonet";
        public final String bayonetedPistolName = "Chili Bullet Pistol with Bayonet";
        public final String bayonetedRifleName = "Chili Bullet Rifle with Bayonet";
        public final String bayonetedShotgunName = "Chili Bullet Shotgun with Bayonet";
        public final String machineGunName = "Chili Bullet Machine Gun";

        // Entity
        public final String chiliBulletEntityName = "Chili Bullet";

        // Subtitles
        public final String subtitleGunShoot = "Chili Bullet Gun fires";
        public final String subtitleGunActionClose = "Chili Bullet Gun loads";
    }

    @Override
    protected void addTranslations() {
        // Creative mode tabs
        add(ModCreativeModeTabs.KEY_MAIN, ChiliBulletWeapons.MOD_NAME);

        // Block
        add(ModBlocks.CHILI_PEPPER, translatedNameProvider.chiliPepperCropName);

        // Item.Plants
        add(ModItems.BULLET_CHILI, translatedNameProvider.bulletChiliName);
        add(ModItems.CURVED_CHILI, translatedNameProvider.curvedChiliName);
        add(ModItems.CHILI_SEEDS, translatedNameProvider.chiliSeedsName);
        add(ModItems.BULLET_CHILI_SACK, translatedNameProvider.bulletChiliSackName);
        add(ModItems.CURVED_CHILI_SACK, translatedNameProvider.curvedChiliSackName);
        // Item.Foods
        add(ModItems.CHILI_CHICKEN_SANDWICH, translatedNameProvider.chiliChickenSandwichName);
        add(ModItems.CHILI_FISH_SANDWICH, translatedNameProvider.chiliFishSandwichName);
        add(ModItems.CHILI_MEAT_SANDWICH, translatedNameProvider.chiliMeatSandwichName);
        add(ModItems.CHILI_POTATO_SANDWICH, translatedNameProvider.chiliPotatoSandwichName);
        add(ModItems.HALF_CHILI_CHICKEN_SANDWICH, translatedNameProvider.halfChiliChickenSandwichName);
        add(ModItems.HALF_CHILI_FISH_SANDWICH, translatedNameProvider.halfChiliFishSandwichName);
        add(ModItems.HALF_CHILI_MEAT_SANDWICH, translatedNameProvider.halfChiliMeatSandwichName);
        add(ModItems.HALF_CHILI_POTATO_SANDWICH, translatedNameProvider.halfChiliPotatoSandwichName);
        add(ModItems.FRIED_CHILI_PEPPER, translatedNameProvider.friedChiliPepperName);
        // Item.Weapons
        add(ModItems.CHILI_BULLET, translatedNameProvider.chiliBulletName);
        add(ModItems.GUN, translatedNameProvider.gunName);
        add(ChiliBulletGun.DESCRIPTION_PISTOL, translatedNameProvider.pistolName);
        add(ChiliBulletGun.DESCRIPTION_RIFLE, translatedNameProvider.rifleName);
        add(ChiliBulletGun.DESCRIPTION_SHOTGUN, translatedNameProvider.shotgunName);
        add(ModItems.BAYONETED_GUN, translatedNameProvider.bayonetedGunName);
        add(ChiliBulletGunBayoneted.DESCRIPTION_PISTOL, translatedNameProvider.bayonetedPistolName);
        add(ChiliBulletGunBayoneted.DESCRIPTION_RIFLE, translatedNameProvider.bayonetedRifleName);
        add(ChiliBulletGunBayoneted.DESCRIPTION_SHOTGUN, translatedNameProvider.bayonetedShotgunName);
        add(ModItems.MACHINE_GUN, translatedNameProvider.machineGunName);

        // Entity
        add(ModEntityTypes.CHILI_BULLET, translatedNameProvider.chiliBulletEntityName);

        // Subtitles
        add(ModSoundEvents.GUN_SHOOT, translatedNameProvider.subtitleGunShoot);
        add(ModSoundEvents.GUN_ACTION_CLOSE, translatedNameProvider.subtitleGunActionClose);
    }

    private void add(SoundEvent soundEvent, String name) {
        add("subtitles." + soundEvent.getLocation().getPath(), name);
    }
}
