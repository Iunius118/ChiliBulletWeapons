package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.component.GunContents;
import net.minecraft.world.item.ItemStack;

public class UpgradeGunBarrelItem extends UpgradeGunPartItem {

    public UpgradeGunBarrelItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canUpgrade(ItemStack stack) {
        int barrelCount = ChiliBulletGunHelper.getBarrelCount(stack);
        return super.canUpgrade(stack)
                && barrelCount < Constants.ChiliBulletGun.CAPACITY_MULTISHOT;
    }

    @Override
    public ItemStack upgrade(ItemStack stack) {
        ItemStack result = stack.copy();
        var gunContents = GunContents.getOrDefault(stack);

        if (gunContents.piercing() < Constants.ChiliBulletGun.BASIC_PIERCING) {
            gunContents
                    .setPiercing(Constants.ChiliBulletGun.BASIC_PIERCING)
                    .setTo(result);
        } else {
            gunContents
                    .setPiercing(GunContents.DEFAULT_PIERCING)
                    .setBarrelCount(Constants.ChiliBulletGun.CAPACITY_MULTISHOT)
                    .setTo(result);
        }

        return result;
    }
}
