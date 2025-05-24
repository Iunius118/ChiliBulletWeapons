package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
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
        int piercing = ChiliBulletGunHelper.getPiercing(stack);

        if (piercing < Constants.ChiliBulletGun.BASIC_PIERCING) {
            result.set(ModDataComponents.PIERCING, Constants.ChiliBulletGun.BASIC_PIERCING);
        } else {
            result.remove(ModDataComponents.PIERCING);
            result.set(ModDataComponents.MULTISHOT, Constants.ChiliBulletGun.CAPACITY_MULTISHOT);
        }

        return result;
    }
}
