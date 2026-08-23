package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.platform.Services;
import com.github.iunius118.chilibulletweapons.registry.ModRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class ModCreativeModeTabs {
    public static final CreativeModeTab MAIN = Services.PLATFORM.createCreativeModeTabBuilder()
            .title(Component.translatable(Constants.CreativeModeTabs.TITLE_MOD_MAIN))
            // Check whether the mod items exist
            .icon(() -> ModRegistries.iconMain.isBound() ?
                    new ItemStack(ModRegistries.iconMain.value()) : ItemStack.EMPTY)
            .displayItems((params, output) -> {
                // Check whether the mod items exist
                if (!ModRegistries.iconMain.isBound()) return;

                // Plants
                output.accept(ModItems.CHILI_SEEDS);
                output.accept(ModItems.CURVED_GREEN_CHILI);
                output.accept(ModItems.BULLET_CHILI);
                output.accept(ModItems.CURVED_CHILI);
                output.accept(ModItems.CURVED_CHILI_STRING);
                output.accept(ModItems.DRIED_CURVED_CHILI);
                output.accept(ModItems.BULLET_CHILI_SACK);
                output.accept(ModItems.CURVED_CHILI_SACK);
                output.accept(ModItems.POTTED_CHILI_PEPPER_FLOWERING);
                output.accept(ModItems.POTTED_CHILI_PEPPER_GREEN);
                output.accept(ModItems.POTTED_CHILI_PEPPER_RED);

                // Fuel
                output.accept(ModItems.CHILI_BIOFUEL);

                // Foods
                output.accept(ModItems.HOT_SAUCE);
                output.accept(ModItems.HOT_SAUCE_BARREL);
                output.accept(ModItems.GREEN_HOT_SAUCE);
                output.accept(ModItems.PICKLED_GREEN_CHILI);
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
                output.accept(ModItems.CHILI_CHOCOLATE);
                output.accept(ModItems.CHILI_CHOCOLATE_CHICKEN);

                // Weapons
                output.accept(ModItems.CHILI_ARROW);
                output.accept(ModItems.CHILI_BULLET);
                output.accept(ModItems.GUN);
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.MULTISHOT));
                output.accept(ModItems.BAYONETED_GUN);
                output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.PIERCING));
                output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.MULTISHOT));
                output.accept(ModItems.MACHINE_GUN);
                output.accept(ModItems.UPGRADE_GUN_BAYONET);
                output.accept(ModItems.UPGRADE_GUN_BARREL);
                output.accept(ModItems.UPGRADE_GUN_MECHANISM);
                // Guns with extra enchantments for debug
                //output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.MULTISHOT)); // For debug
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));
                //output.accept(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));  // For debug
                //output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.PIERCING, Enchantments.MULTISHOT)); // For debug
                output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.PIERCING, Enchantments.QUICK_CHARGE));
                output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));
                //output.accept(ChiliBulletGun.enchant(ModItems.BAYONETED_GUN, Enchantments.PIERCING, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE));  // For debug
                output.accept(ChiliBulletMachineGun.enchant(ModItems.MACHINE_GUN, Enchantments.UNBREAKING, Enchantments.MENDING));

                // Misc.
                //output.accept(ModItems.CAPSAICIN_POWDER);
            })
            .build();
}
