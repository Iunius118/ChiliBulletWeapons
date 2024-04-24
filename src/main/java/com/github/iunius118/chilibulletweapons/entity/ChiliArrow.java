package com.github.iunius118.chilibulletweapons.entity;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.integration.autoconfig.ModConfig;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ChiliArrow extends Arrow {
    public static final ResourceLocation ID = ChiliBulletWeapons.makeId("chili_arrow");
    public static final double FUSE_SPEED = 0.8;
    public static final float DEFAULT_DAMAGE_MULTIPLIER = 1.0F;

    public ChiliArrow(EntityType<? extends ChiliArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ChiliArrow(double x, double y, double z, Level level) {
        this(ModEntityTypes.CHILI_ARROW, level);
        this.setPos(x, y, z);
    }

    public ChiliArrow(LivingEntity shooter, Level level) {
        this(shooter.getX(), shooter.getEyeY() - (double) 0.1F, shooter.getZ(), level);
        this.setOwner(shooter);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.CHILI_ARROW);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (this.getDeltaMovement().length() < FUSE_SPEED) {
            // Does not explode when speed is low
            super.onHitBlock(blockHitResult);
            return;
        }

        Level level = this.level();
        BlockState blockState = level.getBlockState(blockHitResult.getBlockPos());
        blockState.onProjectileHit(level, blockState, blockHitResult, this);
        this.inGround = true;
        this.discard();

        if (!this.level().isClientSide) {
            explode(blockHitResult.getLocation(), getExplosivePower());
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (this.getDeltaMovement().length() < FUSE_SPEED) {
            // Does not explode when speed is low
            super.onHitEntity(entityHitResult);
            return;
        }

        this.discard();

        if (!this.level().isClientSide) {
            explode(entityHitResult.getLocation(), getExplosivePower());
        }
    }

    private void explode(Vec3 pos, float power) {
        this.level().explode(this, pos.x, pos.y, pos.z, power, Level.ExplosionInteraction.NONE);
    }

    private float getExplosivePower() {
        // Explosive power is 1.0-1.6 (Power 0-5 enchanted)
        return ((float) this.getBaseDamage() / 5.0F + 0.6F) * ModConfig.getChiliArrowDamageMultiplier();
    }
}
