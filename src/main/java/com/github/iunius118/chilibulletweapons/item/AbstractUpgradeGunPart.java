package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractUpgradeGunPart extends Item {
    public AbstractUpgradeGunPart(Properties properties) {
        super(properties);
    }

    public boolean canUpgrade(ItemStack itemStack) {
        return (itemStack.getItem() instanceof ChiliBulletGun gun) && gun.isUpgradable();
    }

    public abstract ItemStack upgrade(ItemStack itemStack);

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);

        if (hand != InteractionHand.MAIN_HAND || !canUpgrade(offHandStack)) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        if (!level.isClientSide()) {
            ItemStack upgradedGunStack = upgrade(offHandStack);
            player.setItemInHand(InteractionHand.OFF_HAND, upgradedGunStack);

            if (!player.getAbilities().instabuild) {
                player.getItemInHand(hand).shrink(1);
            }
        }

        player.playSound(ModSoundEvents.GUN_UPGRADE, 0.5F, 1.1F + level.getRandom().nextFloat() * 0.1F);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
}
