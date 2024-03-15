package com.github.iunius118.chilibulletweapons.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class UpgradeGunBarrel extends AbstractUpgradeGunPart {
    public UpgradeGunBarrel(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canUpgrade(ItemStack itemStack) {
        int multishot = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, itemStack);
        int piercing = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, itemStack);
        return super.canUpgrade(itemStack) 
                && multishot == 0 
                && (piercing < 2 || piercing >= Enchantments.PIERCING.getMaxLevel());
    }

    @Override
    public ItemStack upgrade(ItemStack itemStack) {
        ItemStack result = itemStack.copy();
        final int piercing = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, itemStack);
        
        if (piercing < 2) {
            result.enchant(Enchantments.PIERCING, 2);
        } else {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(result);
            enchantments.remove(Enchantments.PIERCING);
            enchantments.put(Enchantments.MULTISHOT, 1);
            EnchantmentHelper.setEnchantments(enchantments, result);
        }

        return result;
    }
}
