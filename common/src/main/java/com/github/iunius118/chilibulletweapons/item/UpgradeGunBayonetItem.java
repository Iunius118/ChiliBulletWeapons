package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import net.minecraft.world.item.ItemStack;

public class UpgradeGunBayonetItem extends UpgradeGunPartItem {

    public UpgradeGunBayonetItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canUpgrade(ItemStack itemStack) {
        return super.canUpgrade(itemStack) &&
                !ChiliBulletGunHelper.hasBayonet(itemStack);
    }

    @Override
    public ItemStack upgrade(ItemStack itemStack) {
        ItemStack result = itemStack.copy();
        ChiliBulletGunHelper.attachBayonet(result,
                Constants.ChiliBulletGun.BAYONET_ATTACK_DAMAGE, Constants.ChiliBulletGun.BAYONET_ATTACK_SPEED);
        return result;
    }
}
