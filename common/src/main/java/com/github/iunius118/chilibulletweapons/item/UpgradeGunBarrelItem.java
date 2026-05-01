package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.component.GunContents;
import net.minecraft.world.item.ItemStack;

public class UpgradeGunBarrelItem extends UpgradeGunPartItem {

    public UpgradeGunBarrelItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canUpgrade(ItemStack itemStack) {
        int barrelCount = ChiliBulletGunHelper.getBarrelCount(itemStack);
        return super.canUpgrade(itemStack) &&
                barrelCount < Constants.ChiliBulletGun.CAPACITY_MULTISHOT;
    }

    @Override
    public ItemStack upgrade(ItemStack itemStack) {
        ItemStack result = itemStack.copy();
        var gunContents = GunContents.getOrDefault(itemStack);

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
