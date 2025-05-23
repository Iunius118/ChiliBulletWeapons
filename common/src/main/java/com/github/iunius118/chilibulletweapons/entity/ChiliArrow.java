package com.github.iunius118.chilibulletweapons.entity;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.platform.Services;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ChiliArrow extends AbstractArrow {

    public ChiliArrow(EntityType<? extends ChiliArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ChiliArrow(Level level, double x, double y, double z, ItemStack pickupItemStack, ItemStack firedFromWeapon) {
        super(ModEntityTypes.CHILI_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    public ChiliArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, ItemStack firedFromWeapon) {
        super(ModEntityTypes.CHILI_ARROW, owner, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.CHILI_ARROW);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (this.getDeltaMovement().length() < Constants.ChiliArrow.FUSE_SPEED) {
            // Does not explode when the speed is low
            super.onHitBlock(result);
            return;
        }

        var level = this.level();
        var blockState = level.getBlockState(result.getBlockPos());
        blockState.onProjectileHit(level, blockState, result, this);
        this.inGround = true;
        this.discard();

        if (!level.isClientSide) {
            explode(level, result.getLocation(), getExplosivePower());
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (this.getDeltaMovement().length() < Constants.ChiliArrow.FUSE_SPEED) {
            // Does not explode when the speed is low
            super.onHitEntity(result);
            return;
        }

        var level = this.level();
        this.discard();

        if (!level.isClientSide) {
            explode(level, result.getLocation(), getExplosivePower());
        }
    }

    private void explode(Level level, Vec3 pos, float power) {
        level.explode(this, pos.x, pos.y, pos.z, power, Level.ExplosionInteraction.NONE);
    }

    private float getExplosivePower() {
        float damageMultiplier = Services.CONFIG.getChiliArrowDamageMultiplier();
        // Explosive power is 1.0-1.6 (Power 0-5 enchanted) * damageMultiplier
        return ((float) this.getBaseDamage() / 5.0F + 0.6F) * damageMultiplier;
    }
}
