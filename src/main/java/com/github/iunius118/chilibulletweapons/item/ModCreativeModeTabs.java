package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public final static String KEY_MAIN = "itemGroup." + ChiliBulletWeapons.MOD_ID + ".main";
    public static final CreativeModeTab MAIN = CreativeModeTab.builder()
            .title(Component.translatable(KEY_MAIN))
            .icon(() -> new ItemStack(ModItems.BULLET_CHILI))
            .displayItems((params, output) -> {
                output.accept(ModItems.BULLET_CHILI);
                output.accept(ModItems.CURVED_CHILI);
                output.accept(ModItems.CHILI_SEEDS);
                output.accept(ModItems.CHILI_BULLET);
            })
            .build();
}
