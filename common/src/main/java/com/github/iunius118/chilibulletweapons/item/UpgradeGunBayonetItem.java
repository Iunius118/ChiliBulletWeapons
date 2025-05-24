package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import net.minecraft.world.item.ItemStack;

public class UpgradeGunBayonetItem extends UpgradeGunPartItem {

    public UpgradeGunBayonetItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canUpgrade(ItemStack stack) {
        return super.canUpgrade(stack)
                && !ChiliBulletGunHelper.isBayoneted(stack);
    }

    @Override
    public ItemStack upgrade(ItemStack stack) {
        ItemStack result = stack.copy();
        result.set(ModDataComponents.BAYONETED, Constants.ChiliBulletGun.BAYONET_ATTACK_DAMAGE);
        return result;
    }
}
