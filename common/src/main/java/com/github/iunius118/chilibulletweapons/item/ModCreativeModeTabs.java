package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.platform.Services;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static final CreativeModeTab MAIN = Services.PLATFORM.createCreativeModeTab(
            Constants.CreativeModeTabs.MAIN,
            () -> new ItemStack(ModItems.ICON_MAIN));
}
