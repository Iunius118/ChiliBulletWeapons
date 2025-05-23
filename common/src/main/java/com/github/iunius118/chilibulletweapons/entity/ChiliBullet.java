package com.github.iunius118.chilibulletweapons.entity;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunHelper;
import com.github.iunius118.chilibulletweapons.mixin.ProjectileAccessor;
import com.github.iunius118.chilibulletweapons.platform.Services;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ChiliBullet extends ThrowableProjectile {
    public static final String TAG_AGE = "Age";
    public static final String TAG_PIERCE_LEVEL = "PierceLevel";

    private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(ChiliBullet.class, EntityDataSerializers.BYTE);
    private double baseDamage;
    private byte age = 0;
    private IntOpenHashSet piercingIgnoreEntityIds;

    public ChiliBullet(EntityType<ChiliBullet> entityType, Level level) {
        super(entityType, level);
        setBaseDamage(Services.CONFIG.getChiliBulletBaseDamage());
    }

    public ChiliBullet(Level level, double x, double y, double z, ItemStack weapon) {
        this(ModEntityTypes.CHILI_BULLET, level);
        this.setPos(x, y, z);

        if(weapon != null && !weapon.isEmpty() && !level.isClientSide) {
            // Apply piercing level from the weapon
            int pierceLaval = ChiliBulletGunHelper.getPiercing(weapon);

            if (pierceLaval > 0) {
                setPierceLevel((byte) pierceLaval);
            }
        }
    }

    public ChiliBullet(Level level, LivingEntity thrower, ItemStack weapon) {
        this(level, thrower.getX(), thrower.getEyeY() - (double) 0.05F, thrower.getZ(), weapon);
        this.setOwner(thrower);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double renderDistance = this.getBoundingBox().getSize() * 10.0D;

        if (Double.isNaN(renderDistance)) {
            renderDistance = 1.0D;
        }

        renderDistance *= 64.0D * getViewScale();
        return distance < renderDistance * renderDistance;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(PIERCE_LEVEL, (byte) 0);
    }

    @Override
    public void tick() {
        //** Projectile.tick() **//
        // Do not use ThrowableProjectile.tick()

        var projectileAccessor = (ProjectileAccessor) this;

        if (!projectileAccessor.getHasBeenShot()) {
            this.gameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner());
            projectileAccessor.setHasBeenShot(true);
        }

        if (!projectileAccessor.getLeftOwner()) {
            projectileAccessor.setLeftOwner(projectileAccessor.invokeCheckLeftOwner());
        }

        //** Entity.tick() **//

        this.baseTick();

        //** Collision Detection **//

        final Vec3 startVec = this.position();
        final Vec3 delta = this.getDeltaMovement();
        Vec3 endVec = startVec.add(delta);
        HitResult hitResult = this.level().clip(new ClipContext(startVec, endVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

        if (hitResult.getType() != HitResult.Type.MISS) {
            endVec = hitResult.getLocation();
        }

        while(!this.isRemoved()) {
            // Find hit entity that have not yet been hit
            EntityHitResult entityHitResult = findHitEntity(startVec, endVec);

            // Prioritize entity hits over others
            if (entityHitResult != null) {
                hitResult = entityHitResult;
            }

            if (hitResult instanceof EntityHitResult entityHitResult1) {
                Entity entity = entityHitResult1.getEntity();

                // Exclude player that attacker cannot harm
                if (entity instanceof Player player && this.getOwner() instanceof Player owner && !owner.canHarmPlayer(player)) {
                    hitResult = null;
                    entityHitResult = null;
                }
            }

            if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
                // Handling of a hit
                this.onHit(hitResult);
                this.hasImpulse = true;
            }

            if (entityHitResult == null || this.getPierceLevel() <= 0) {
                break;
            }

            hitResult = null;
        }

        if (this.isRemoved()) {
            return;
        }

        //** Movement **//

        this.checkInsideBlocks();
        Vec3 next = startVec.add(delta);
        this.updateRotation();
        float decelerationRatio = 0.99F;

        if (this.isInWater()) {
            for (int i = 0; i < 4; i++) {
                float offset = 0.25F;
                this.level().addParticle(ParticleTypes.BUBBLE,
                        next.x - delta.x * offset, next.y - delta.y * offset, next.z - delta.z * offset, delta.x, delta.y, delta.z);
            }

            decelerationRatio = 0.8F;
        }

        this.setDeltaMovement(delta.scale(decelerationRatio));
        this.applyGravity();
        this.setPos(next);

        //** Lifespan Management **//

        tickDespawn();
    }

    protected void tickDespawn() {
        age++;

        if (age >= Constants.ChiliBullet.LIFETIME) {
            this.discard();
        }
    }

    @Override
    protected double getDefaultGravity() {
        return Constants.ChiliBullet.GRAVITY;
    }

    protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, startVec, endVec,
                this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), this::canHitEntity);
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return super.canHitEntity(entity) && (piercingIgnoreEntityIds == null || !piercingIgnoreEntityIds.contains(entity.getId()));
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        final Entity entity = result.getEntity();
        final Entity owner = this.getOwner();
        final byte pierceLevel = getPierceLevel();

        if (pierceLevel > 0) {
            // Apply piercing
            if (piercingIgnoreEntityIds == null) {
                piercingIgnoreEntityIds = new IntOpenHashSet(pierceLevel);
            }

            if (piercingIgnoreEntityIds.size() >= pierceLevel + 1) {
                this.discard();
                return;
            }

            piercingIgnoreEntityIds.add(entity.getId());
        }

        if (owner instanceof LivingEntity livingEntity) {
            livingEntity.setLastHurtMob(entity);
        }

        // Multi-hit
        if (!this.level().isClientSide && Services.CONFIG.canMultishotMultiHit()) {
            entity.invulnerableTime = 0;
        }

        var damageSource = this.damageSources().thrown(this, owner != null ? owner : this);

        // Deal damage to the entity
        if (entity.hurt(damageSource, (float) getDamage())) {
            if (entity != owner && entity instanceof Player && owner instanceof ServerPlayer ownerInServer && !this.isSilent()) {
                // Play a ding when the bullet hit a player
                ownerInServer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
            }
        }

        if (pierceLevel <= 0) {
            this.discard();
        }
    }

    public byte getPierceLevel() {
        return this.entityData.get(PIERCE_LEVEL);
    }

    public void setPierceLevel(byte level) {
        this.entityData.set(PIERCE_LEVEL, level);
    }

    private int getDamage() {
        if (baseDamage <= 0) {
            return 0;
        }

        double speedSqr = this.getDeltaMovement().lengthSqr();
        int force = Mth.ceil(Mth.clamp(speedSqr * baseDamage, 0.0D, Integer.MAX_VALUE));
        long randomDamage = this.random.nextInt(force / 2 + 2);
        return (int) Math.min((long) force + randomDamage, Integer.MAX_VALUE);
    }

    public double getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(double damage) {
        baseDamage = damage;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        setPierceLevel((byte) 0);
        this.discard();
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte(TAG_AGE, age);
        compound.putByte(TAG_PIERCE_LEVEL, getPierceLevel());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setAge(compound.getByte(TAG_AGE));
        setPierceLevel(compound.getByte(TAG_PIERCE_LEVEL));
    }
}
