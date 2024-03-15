package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class ModCreativeModeTabs {
    public static final String KEY_MAIN = "itemGroup." + ChiliBulletWeapons.MOD_ID + ".main";
    public static final CreativeModeTab MAIN = FabricItemGroup.builder()
            .title(Component.translatable(KEY_MAIN))
            .icon(() -> new ItemStack(ModItems.BULLET_CHILI))
            .displayItems((params, output) -> {
                // Plants
                output.accept(ModItems.BULLET_CHILI);
                output.accept(ModItems.CURVED_CHILI);
                output.accept(ModItems.CHILI_SEEDS);
                output.accept(ModItems.BULLET_CHILI_SACK);
                output.accept(ModItems.CURVED_CHILI_SACK);

                // Foods
                output.accept(ModItems.CHILI_CHICKEN_SANDWICH);
                output.accept(ModItems.CHILI_FISH_SANDWICH);
                output.accept(ModItems.CHILI_MEAT_SANDWICH);
                output.accept(ModItems.CHILI_POTATO_SANDWICH);
                output.accept(ModItems.HALF_CHILI_CHICKEN_SANDWICH);
                output.accept(ModItems.HALF_CHILI_FISH_SANDWICH);
                output.accept(ModItems.HALF_CHILI_MEAT_SANDWICH);
                output.accept(ModItems.HALF_CHILI_POTATO_SANDWICH);
                output.accept(ModItems.PASTA_OIL_AND_CHILI);
                output.accept(ModItems.FRIED_CHILI_PEPPER);

                // Weapons
                output.accept(ModItems.CHILI_BULLET);
                output.accept(ModItems.GUN);
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.MULTISHOT));
                // output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.MULTISHOT)); // For debug
                output.accept(ModItems.BAYONETED_GUN);
                output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.PIERCING));
                output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.MULTISHOT));
                // output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.PIERCING, Enchantments.MULTISHOT)); // For debug
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));
                // output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));  // For debug
                output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.PIERCING, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));
                // output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.PIERCING, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));  // For debug
                output.accept(ChiliBulletMachineGun.enchant(ModItems.MACHINE_GUN, Enchantments.PIERCING));
            })
            .build();
}
