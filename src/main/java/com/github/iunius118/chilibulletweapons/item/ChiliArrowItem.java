package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.entity.ChiliArrow;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class ChiliArrowItem extends ArrowItem {
    public ChiliArrowItem(Properties properties) {
        super(properties);

        // Register dispense behavior
        DispenserBlock.registerBehavior(this, new DispenseBehavior());
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity livingEntity) {
        return new ChiliArrow(livingEntity, level);
    }

    public static class DispenseBehavior extends AbstractProjectileDispenseBehavior {
        @Override
        protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
            ChiliArrow chiliArrow = new ChiliArrow(position.x(), position.y(), position.z(), level);
            chiliArrow.pickup = AbstractArrow.Pickup.ALLOWED;
            return chiliArrow;
        }
    }
}
