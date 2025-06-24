package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class DyedGunItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        var dyedGunColors = stack.get(ModDataComponents.DYED_GUN_COLORS);

        if (dyedGunColors == null) {
            return 0xFFFFFFFF;
        }

        return switch (tintIndex) {
            case 0 -> dyedGunColors.metalColor();
            case 1 -> dyedGunColors.woodColor();
            case 2 -> dyedGunColors.bladeColor();
            default -> 0xFFFFFFFF;
        };
    }
}
