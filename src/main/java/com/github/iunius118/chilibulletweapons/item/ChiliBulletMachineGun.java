package com.github.iunius118.chilibulletweapons.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChiliBulletMachineGun extends ChiliBulletGun {
    public ChiliBulletMachineGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        final var resultHolder = super.use(level, player, hand);
        var itemStack = player.getItemInHand(hand);
        // Feed bullets automatically
        boolean isLoaded = tryLoadProjectile(player, itemStack);
        setLoaded(itemStack, isLoaded);
        return resultHolder;
    }

    @Override
    public String getDescriptionId(ItemStack itemStack) {
        return this.getDescriptionId();
    }
}
