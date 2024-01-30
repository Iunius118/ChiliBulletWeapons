package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModCreativeModeTabs;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.LanguageProvider;

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
        public final String bulletChiliName = "Bullet-like Chili Pepper";
        public final String curvedChiliName = "Curved Chili Pepper";
        public final String chiliSeedsName = "Chili Pepper Seeds";
        public final String chiliBulletName = "Chili Bullet";
        public final String pistolName = "Chili Bullet Pistol";
        public final String rifleName = "Chili Bullet Rifle";
        public final String shotgunName = "Chili Bullet Shotgun";

        public final String chiliBulletEntityName = "Chili Bullet";

        public final String subtitleGunShoot = "Chili Bullet gun fires";
        public final String subtitleGunActionClose = "Chili Bullet gun loads";
    }

    @Override
    protected void addTranslations() {
        // Creative mode tabs
        add(ModCreativeModeTabs.KEY_MAIN, ChiliBulletWeapons.MOD_NAME);

        // Items
        add(ModItems.BULLET_CHILI, translatedNameProvider.bulletChiliName);
        add(ModItems.CURVED_CHILI, translatedNameProvider.curvedChiliName);
        add(ModItems.CHILI_SEEDS, translatedNameProvider.chiliSeedsName);
        add(ModItems.CHILI_BULLET, translatedNameProvider.chiliBulletName);
        add(ModItems.PISTOL, translatedNameProvider.pistolName);
        add(ModItems.RIFLE, translatedNameProvider.rifleName);
        add(ModItems.SHOTGUN, translatedNameProvider.shotgunName);

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
