package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
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
        if (!isLoading(itemStack)) {
            boolean isLoaded = tryLoadProjectile(player, itemStack);
            setLoaded(itemStack, isLoaded);
        }

        return resultHolder;
    }

    @Override
    public int getReloadDuration(ItemStack itemStack) {
        // For handled by non-player
        return Constants.ChiliBulletGun.RELOAD_MACHINE_GUN;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public String getDescriptionId(ItemStack itemStack) {
        return this.getDescriptionId();
    }

    @Override
    public void fillItemCategory(CreativeModeTab category, NonNullList<ItemStack> items) {
        if (this.allowedIn(category)) {
            items.add(new ItemStack(ModItems.MACHINE_GUN));

            // Guns with extra enchantments for debug
            items.add(ChiliBulletMachineGun.enchant(ModItems.MACHINE_GUN,
                    Enchantments.UNBREAKING, Enchantments.MENDING));
        }
    }
}
