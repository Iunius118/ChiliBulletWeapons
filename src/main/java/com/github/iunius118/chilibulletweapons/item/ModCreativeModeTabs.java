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

                // Foods
                output.accept(ModItems.CHILI_CHICKEN_SANDWICH);
                output.accept(ModItems.CHILI_FISH_SANDWICH);
                output.accept(ModItems.CHILI_MEAT_SANDWICH);
                output.accept(ModItems.CHILI_POTATO_SANDWICH);

                // Weapons
                output.accept(ModItems.CHILI_BULLET);
                output.accept(ModItems.GUN);
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.MULTISHOT));
                // output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.MULTISHOT)); // For debug
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));
                // output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));  // For debug
            })
            .build();
}