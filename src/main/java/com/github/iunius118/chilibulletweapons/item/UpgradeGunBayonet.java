package com.github.iunius118.chilibulletweapons.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class UpgradeGunBayonet extends AbstractUpgradeGunPart {
    public UpgradeGunBayonet(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canUpgrade(ItemStack itemStack) {
        return super.canUpgrade(itemStack) && !itemStack.is(ModItems.BAYONETED_GUN);
    }

    @Override
    public ItemStack upgrade(ItemStack itemStack) {
        ItemStack result = new ItemStack(ModItems.BAYONETED_GUN);
        CompoundTag tag = itemStack.getTag();

        if (tag != null) {
            result.setTag(tag.copy());
        }

        return result;
    }
}
