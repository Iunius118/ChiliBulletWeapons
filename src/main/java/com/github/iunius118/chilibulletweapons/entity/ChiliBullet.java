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
    public static final double BASE_DAMAGE = 2.0D;

    protected ChiliBullet(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    protected ChiliBullet(EntityType<? extends ThrowableProjectile> entityType, double x, double y, double z, Level level) {
        super(entityType, x, y, z, level);
    }

    protected ChiliBullet(EntityType<? extends ThrowableProjectile> entityType, LivingEntity thrower, Level level) {
        super(entityType, thrower, level);
    }

    @Override
    protected void defineSynchedData() {
    }

    public void shootFromRotation(Entity thrower, float velocity, float inaccuracy) {
        shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0.0F, velocity, inaccuracy);
    }

    @Override
    public void shootFromRotation(Entity thrower, float rotX, float rotY, float elevation, float velocity, float inaccuracy) {
        float deltaX = -Mth.sin(rotY * ((float)Math.PI / 180F)) * Mth.cos(rotX * ((float)Math.PI / 180F));
        float deltaY = -Mth.sin((rotX + elevation) * ((float)Math.PI / 180F));
        float deltaZ = Mth.cos(rotY * ((float)Math.PI / 180F)) * Mth.cos(rotX * ((float)Math.PI / 180F));
        this.shoot(deltaX, deltaY, deltaZ, velocity, inaccuracy);
    }

    @Override
    protected float getGravity() {
        return 0;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.invulnerableTime = 0;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), getDamage());
        this.discard();
    }

    private float getDamage() {
        double force = this.getDeltaMovement().length();
        long physicalDamage = Mth.ceil(Mth.clamp(force * BASE_DAMAGE, 0.0D, Integer.MAX_VALUE));
        long randomDamage = this.random.nextInt((int) Math.min(physicalDamage * 2 + 1, Integer.MAX_VALUE));
        return (int) Math.min(randomDamage + physicalDamage, Integer.MAX_VALUE);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }
}
