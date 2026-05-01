package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.component.GunContents;
import net.minecraft.world.item.ItemStack;

public class UpgradeGunMechanismItem extends UpgradeGunPartItem {

    public UpgradeGunMechanismItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canUpgrade(ItemStack itemStack) {
        int quickLoading = ChiliBulletGunHelper.getQuickLoading(itemStack);
        return super.canUpgrade(itemStack) &&
                quickLoading < Constants.ChiliBulletGun.MAX_QUICK_LOADING;
    }

    @Override
    public ItemStack upgrade(ItemStack itemStack) {
        ItemStack result = itemStack.copy();
        var gunContents = GunContents.getOrDefault(itemStack);
        gunContents.setQuickLoading(gunContents.quickLoading() + 1).setTo(result);
        return result;
    }
}
