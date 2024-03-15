package com.github.iunius118.chilibulletweapons.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class UpgradeGunMechanism extends AbstractUpgradeGunPart {
    public UpgradeGunMechanism(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canUpgrade(ItemStack itemStack) {
        int quickCharge = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, itemStack);
        return super.canUpgrade(itemStack) && quickCharge == 0;
    }

    @Override
    public ItemStack upgrade(ItemStack itemStack) {
        ItemStack result = itemStack.copy();
        result.enchant(Enchantments.QUICK_CHARGE, 1);
        return result;
    }
}
