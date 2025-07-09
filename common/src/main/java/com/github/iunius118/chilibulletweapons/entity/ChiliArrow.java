package com.github.iunius118.chilibulletweapons.entity;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.advancements.ModCriteriaTriggers;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.platform.Services;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
            explode(level, result.getLocation(), getExplosivePower(this));
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
            explode(level, result.getLocation(), getExplosivePower(result.getEntity()));
        }
    }

    private void explode(Level level, Vec3 pos, float power) {
        if (this.getOwner() instanceof ServerPlayer shooter) {
            // Trigger advancement
            ModCriteriaTriggers.EXPLODED_CHILI_ARROW.trigger(shooter);
        }

        level.explode(this, pos.x, pos.y, pos.z, power, Level.ExplosionInteraction.NONE);
    }

    private float getExplosivePower(Entity entity) {
        Entity owner = this.getOwner();
        var damagesource = this.damageSources().arrow(this, (owner != null ? owner : this));
        float damage = (float) this.getBaseDamage();

        // getWeaponItem() may return null if the arrow has not been shot from a weapon item
        if (this.getWeaponItem() != null && this.level() instanceof ServerLevel serverlevel) {
            // Apply weapon enchantments to the damage
            damage = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, damage);

            if (damage > this.getBaseDamage()) {
                damage += 0.5F;
            }
        }

        float damageMultiplier = Services.CONFIG.getChiliArrowDamageMultiplier();
        // Explosive power is 1.0-1.6 (Power 0-5 enchanted) * damageMultiplier
        return (damage / 5.0F + 0.6F) * damageMultiplier;
    }
}
