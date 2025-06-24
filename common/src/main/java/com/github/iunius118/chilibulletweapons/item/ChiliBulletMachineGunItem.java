package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChiliBulletMachineGunItem extends ChiliBulletGunItem {

    public ChiliBulletMachineGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        final var resultHolder = super.use(level, player, hand);
        var itemStack = player.getItemInHand(hand);

        if (!ChiliBulletGunHelper.isLoading(itemStack)) {
            ChiliBulletGunItem.tryLoadProjectiles(player, itemStack);
        }

        return resultHolder;
    }

    @Override
    public int getReloadDuration(ItemStack stack) {
        return Constants.ChiliBulletGun.RELOAD_MACHINE_GUN;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return this.getDescriptionId();
    }

    @Override
    public boolean isUpgradable(ItemStack stack) {
        return false;
    }
}
