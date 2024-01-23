package com.github.iunius118.chilibulletweapons.entity;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class ChiliBullet extends ThrowableProjectile {
    public static final ResourceLocation ID = new ResourceLocation(ChiliBulletWeapons.MOD_ID, "chili_bullet");
    public static final double BASE_DAMAGE = 0.85D;

    public ChiliBullet(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ChiliBullet(double x, double y, double z, Level level) {
        super(ModEntityTypes.CHILI_BULLET, x, y, z, level);
    }

    public ChiliBullet(LivingEntity thrower, Level level) {
        this(thrower.getX(), thrower.getEyeY() - (double) 0.05F, thrower.getZ(), level);
    }

    @Override
    protected void defineSynchedData() {
    }

    public void shootFromRotation(Entity thrower, float velocity, float inaccuracy) {
        shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0.0F, velocity, inaccuracy);
    }

    @Override
    public void shootFromRotation(Entity thrower, float rotX, float rotY, float elevation, float velocity, float inaccuracy) {
        final float pi = (float) Math.PI;
        float deltaX = -Mth.sin(rotY * (pi / 180F)) * Mth.cos(rotX * (pi / 180F));
        float deltaY = -Mth.sin((rotX + elevation) * (pi / 180F));
        float deltaZ = Mth.cos(rotY * (pi / 180F)) * Mth.cos(rotX * (pi / 180F));
        this.shoot(deltaX, deltaY, deltaZ, velocity, inaccuracy);
    }

    @Override
    protected float getGravity() {
        return super.getGravity();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.invulnerableTime = 0;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float) getDamage());
        this.discard();
    }

    private int getDamage() {
        double speedSqr = this.getDeltaMovement().lengthSqr();
        int force = Mth.ceil(Mth.clamp(speedSqr * BASE_DAMAGE, 0.0D, Integer.MAX_VALUE));
        long randomDamage = this.random.nextInt(force / 2 + 2);
        return (int) Math.min((long) force + randomDamage, Integer.MAX_VALUE);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }
}
