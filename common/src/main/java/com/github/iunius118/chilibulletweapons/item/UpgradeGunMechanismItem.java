package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import net.minecraft.world.item.ItemStack;

public class UpgradeGunMechanismItem extends UpgradeGunPartItem {

    public UpgradeGunMechanismItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canUpgrade(ItemStack stack) {
        int quickLoading = ChiliBulletGunHelper.getQuickLoading(stack);
        return super.canUpgrade(stack)
                && quickLoading < Constants.ChiliBulletGun.MAX_QUICK_LOADING;
    }

    @Override
    public ItemStack upgrade(ItemStack stack) {
        ItemStack result = stack.copy();
        int quickLoading = ChiliBulletGunHelper.getQuickLoading(stack);
        result.set(ModDataComponents.QUICK_LOADING, quickLoading + 1);
        return result;
    }
}
