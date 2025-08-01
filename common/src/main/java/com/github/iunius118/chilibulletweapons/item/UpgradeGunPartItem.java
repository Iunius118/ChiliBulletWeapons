package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.advancements.ModCriteriaTriggers;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class UpgradeGunPartItem extends Item {

    public UpgradeGunPartItem(Properties properties) {
        super(properties);
    }

    public boolean canUpgrade(ItemStack stack) {
        return (stack.getItem() instanceof ChiliBulletGunItem gun) && gun.isUpgradable(stack);
    }

    public abstract ItemStack upgrade(ItemStack stack);

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack usedStack = player.getItemInHand(hand);
        ItemStack offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);

        if (hand != InteractionHand.MAIN_HAND || !canUpgrade(offHandStack)) {
            // If the items cannot be upgraded, do nothing
            return InteractionResultHolder.pass(usedStack);
        }

        if (!level.isClientSide()) {
            // In server-side, upgrade the gun in the off-hand
            ItemStack upgradedGunStack = upgrade(offHandStack);
            player.setItemInHand(InteractionHand.OFF_HAND, upgradedGunStack);

            if (player instanceof ServerPlayer serverplayer) {
                // Trigger advancement
                ModCriteriaTriggers.UPGRADED_CHILI_BULLET_GUN.trigger(serverplayer, usedStack);
                serverplayer.awardStat(Stats.ITEM_USED.get(this));
            }

            if (!player.getAbilities().instabuild) {
                usedStack.shrink(1);
            }
        }

        // Play upgrade sound effect
        player.playSound(ModSoundEvents.GUN_UPGRADE, 0.5F, 1.1F + level.getRandom().nextFloat() * 0.1F);
        return InteractionResultHolder.sidedSuccess(usedStack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable(Constants.UpgradeGunPart.TOOLTIP_UPGRADE_GUN_1).withStyle(ChatFormatting.YELLOW));
        tooltipComponents.add(Component.translatable(Constants.UpgradeGunPart.TOOLTIP_UPGRADE_GUN_2).withStyle(ChatFormatting.YELLOW));
        tooltipComponents.add(Component.translatable(Constants.UpgradeGunPart.TOOLTIP_UPGRADE_GUN_3).withStyle(ChatFormatting.YELLOW));
    }
}
