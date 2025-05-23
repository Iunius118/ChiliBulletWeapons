package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.entity.ChiliArrow;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.Nullable;

public class ChiliArrowItem extends ArrowItem {

    public ChiliArrowItem(Properties properties) {
        super(properties);

        // Register dispense behavior
        DispenserBlock.registerProjectileBehavior(this);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new ChiliArrow(level, shooter, ammo.copyWithCount(1), weapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        var chiliArrow = new ChiliArrow(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null);
        chiliArrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return chiliArrow;
    }
}
