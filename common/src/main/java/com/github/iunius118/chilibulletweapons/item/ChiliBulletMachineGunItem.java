package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChiliBulletMachineGunItem extends ChiliBulletGunItem {

    public ChiliBulletMachineGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        final var resultHolder = super.use(level, player, hand);
        var itemStack = player.getItemInHand(hand);

        if (!ChiliBulletGunHelper.isLoading(itemStack)) {
            ChiliBulletGunItem.tryLoadProjectiles(player, itemStack);
        }

        return resultHolder;
    }

    @Override
    public int getReloadDuration(ItemStack itemStack) {
        return Constants.ChiliBulletGun.RELOAD_MACHINE_GUN;
    }

    @Override
    public Component getName(ItemStack itemStack) {
        return itemStack.getComponents().getOrDefault(DataComponents.ITEM_NAME, CommonComponents.EMPTY);
    }

    @Override
    public boolean isUpgradable(ItemStack itemStack) {
        return false;
    }
}
