package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Creative mode tabs
        add("itemGroup.%s.main".formatted(Constants.MOD_ID), Constants.MOD_NAME);

        // Item.Plants
        add(ModItems.BULLET_CHILI, "Bullet-like Chili Pepper");
        add(ModItems.BULLET_CHILI_SACK, "Sack of Bullet-like Chili Peppers");
        // Item.Weapons
        add(ModItems.CHILI_ARROW, "Bullet Chili Arrow");
        add(ModItems.CHILI_BULLET, "Chili Bullet");
        add(ModItems.GUN, "Chili Bullet Gun");
        add(Constants.ChiliBulletGun.DESCRIPTION_PISTOL, "Chili Bullet Pistol");
        add(Constants.ChiliBulletGun.DESCRIPTION_RIFLE, "Chili Bullet Rifle");
        add(Constants.ChiliBulletGun.DESCRIPTION_VOLLEY_GUN, "Chili Bullet Volley Gun");
        add(Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_PISTOL, "Chili Bullet Pistol with Bayonet");
        add(Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_RIFLE, "Chili Bullet Rifle with Bayonet");
        add(Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_VOLLEY_GUN, "Chili Bullet Volley Gun with Bayonet");
        add(ModItems.MACHINE_GUN, "Chili Bullet Machine Gun");
        add(ModItems.UPGRADE_GUN_BAYONET, "Bayonet Upgrade");
        add(ModItems.UPGRADE_GUN_BARREL, "Gun Barrel Upgrade");
        add(ModItems.UPGRADE_GUN_MECHANISM, "Gun Mechanism Upgrade");

        // Tooltips
        add(Constants.ChiliBulletGun.TOOLTIP_BARREL_INFO, "MuzzleV:%s, Piercing:%s, Barrel:%s");
        add(Constants.ChiliBulletGun.TOOLTIP_QUICK_LOAD, "Quick Load +%s");
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
        add("advancements.chilibulletweapons.main.root.title", "Chili Bullet Weapons");
        add("advancements.chilibulletweapons.main.root.description", "Chili peppers, foods and weapons");
        add("advancements.chilibulletweapons.main.bullet_chili.title", "Like a Bullet?");
        add("advancements.chilibulletweapons.main.bullet_chili.description", "Obtain a Bullet-like Chili Pepper");
        add("advancements.chilibulletweapons.main.exploded_chili_arrow.title", "Boom!");
        add("advancements.chilibulletweapons.main.exploded_chili_arrow.description", "Shoot a Bullet Chili Arrow and make it explode");
        add("advancements.chilibulletweapons.main.shot_gun.title", "Bang!");
        add("advancements.chilibulletweapons.main.shot_gun.description", "Shoot a Chili Bullet Gun");
        add("advancements.chilibulletweapons.main.upgraded_gun.title", "Master Gunsmith");
        add("advancements.chilibulletweapons.main.upgraded_gun.description", "Upgrade a gun with an upgrade item");
        add("advancements.chilibulletweapons.main.killed_by_chili_bullet.title", "Quad-Sharp Shooter");
        add("advancements.chilibulletweapons.main.killed_by_chili_bullet.description", "Defeat 4 mobs with one Chili Bullet");
        add("advancements.chilibulletweapons.main.shot_machine_gun.title", "Handle With Care");
        add("advancements.chilibulletweapons.main.shot_machine_gun.description", "Shoot a Chili Bullet Machine Gun");
        add("advancements.chilibulletweapons.main.machine_gun_mending_1.title", "Battle Has Changed");
        add("advancements.chilibulletweapons.main.machine_gun_mending_1.description", "Obtain a Mending enchanted Chili Bullet Machine Gun");
    }

    private static String getSubtitleKey(SoundEvent soundEvent) {
        return "subtitles." + soundEvent.getLocation().getPath();
    }
}
