package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractUpgradeGunPart extends Item {
    public static final String TOOLTIP_UPGRADE_GUN_1 = "tooltip.chilibulletweapons.upgrade_gun_1";
    public static final String TOOLTIP_UPGRADE_GUN_2 = "tooltip.chilibulletweapons.upgrade_gun_2";
    public static final String TOOLTIP_UPGRADE_GUN_3 = "tooltip.chilibulletweapons.upgrade_gun_3";

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

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, tooltip, flag);
        tooltip.add(Component.translatable(TOOLTIP_UPGRADE_GUN_1).withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.translatable(TOOLTIP_UPGRADE_GUN_2).withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.translatable(TOOLTIP_UPGRADE_GUN_3).withStyle(ChatFormatting.YELLOW));
    }
}
