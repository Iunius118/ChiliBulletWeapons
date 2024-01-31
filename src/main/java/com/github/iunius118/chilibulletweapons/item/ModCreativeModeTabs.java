package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class ModCreativeModeTabs {
    public static final String KEY_MAIN = "itemGroup." + ChiliBulletWeapons.MOD_ID + ".main";
    public static final CreativeModeTab MAIN = CreativeModeTab.builder()
            .title(Component.translatable(KEY_MAIN))
            .icon(() -> new ItemStack(ModItems.BULLET_CHILI))
            .displayItems((params, output) -> {
                // Plants
                output.accept(ModItems.BULLET_CHILI);
                output.accept(ModItems.CURVED_CHILI);
                output.accept(ModItems.CHILI_SEEDS);
                output.accept(ModItems.CHILI_BULLET);
                // Weapons
                output.accept(ModItems.GUN);
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.MULTISHOT));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.MULTISHOT));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));
            })
            .build();
}
