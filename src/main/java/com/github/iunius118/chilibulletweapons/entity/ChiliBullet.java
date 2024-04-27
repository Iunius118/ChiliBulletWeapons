package com.github.iunius118.chilibulletweapons.entity;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.integration.autoconfig.ChiliBulletWeaponsConfig;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ChiliBullet extends Projectile {
    public static final ResourceLocation ID = ChiliBulletWeapons.makeId("chili_bullet");
    public static final String TAG_AGE = "Age";
    public static final String TAG_PIERCE_LEVEL = "PierceLevel";
    public static final double DEFAULT_BASE_DAMAGE = 0.85D;
    public static final float GRAVITY = 0.03F;
    public static final byte LIFETIME = 40;

    private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(ChiliBullet.class, EntityDataSerializers.BYTE);
    private double baseDamage;
    private byte age = 0;
    @Nullable
    private IntOpenHashSet piercingIgnoreEntityIds;

    public ChiliBullet(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        setBaseDamage(ChiliBulletWeaponsConfig.getChiliBulletBaseDamage());
    }

    public ChiliBullet(double x, double y, double z, Level level) {
        this(ModEntityTypes.CHILI_BULLET, level);
        this.setPos(x, y, z);
    }

    public ChiliBullet(LivingEntity thrower, Level level) {
        this(thrower.getX(), thrower.getEyeY() - (double) 0.05F, thrower.getZ(), level);
        this.setOwner(thrower);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double sqrDistance) {
        double renderDistance = this.getBoundingBox().getSize() * 10.0D;

        if (Double.isNaN(renderDistance)) {
            renderDistance = 1.0D;
        }

        renderDistance *= 64.0D * getViewScale();
        return sqrDistance < renderDistance * renderDistance;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(PIERCE_LEVEL, (byte) 0);
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
    public void tick() {
        super.tick();
        Vec3 deltaV = this.getDeltaMovement();

        /* Collision Detection */

        Vec3 pos = this.position();
        Vec3 nextPos = pos.add(deltaV);
        HitResult hitResult = this.level().clip(new ClipContext(pos, nextPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

        if (hitResult.getType() != HitResult.Type.MISS) {
            nextPos = hitResult.getLocation();
        }

        while(!this.isRemoved()) {
            // Find hit entity that have not yet been hit
            EntityHitResult entityHitResult = findHitEntity(pos, nextPos);

            // Prioritize entity hit
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

        /* Bullet Movement */

        this.checkInsideBlocks();
        double nextX = this.getX() + deltaV.x;
        double nextY = this.getY() + deltaV.y;
        double nextZ = this.getZ() + deltaV.z;
        this.updateRotation();
        float decelerationRatio;

        if (this.isInWater()) {
            for(int i = 0; i < 4; ++i) {
                double offset = 0.25;
                this.level().addParticle(ParticleTypes.BUBBLE, nextX - deltaV.x * offset, nextY - deltaV.y * offset, nextZ - deltaV.z * offset, deltaV.x, deltaV.y, deltaV.z);
            }

            decelerationRatio = 0.8F;
        } else {
            decelerationRatio = 0.99F;
        }

        this.setDeltaMovement(deltaV.scale(decelerationRatio));

        if (!this.isNoGravity()) {
            this.setDeltaMovement(deltaV.x, deltaV.y - (double) this.getGravity(), deltaV.z);
        }

        this.setPos(nextX, nextY, nextZ);

        /* Lifespan Management */

        age++;

        if (!level().isClientSide) {
            tickDespawn();
        }
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 pos, Vec3 nextPos) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, pos, nextPos, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return super.canHitEntity(entity) && (piercingIgnoreEntityIds == null || !piercingIgnoreEntityIds.contains(entity.getId()));
    }

    private void tickDespawn() {
        if (age >= LIFETIME && !this.isRemoved()) {
            this.discard();
        }
    }

    public void setPierceLevel(byte level) {
        this.entityData.set(PIERCE_LEVEL, level);
    }

    public byte getPierceLevel() {
        return this.entityData.get(PIERCE_LEVEL);
    }

    protected float getGravity() {
        return GRAVITY;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        final Entity entity = result.getEntity();
        final Entity owner = this.getOwner();
        final byte pierceLevel = getPierceLevel();

        if (pierceLevel > 0) {
            // Apply Piercing enchantment
            if (piercingIgnoreEntityIds == null) {
                this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
            }

            if (piercingIgnoreEntityIds.size() >= pierceLevel + 1) {
                this.discard();
                return;
            }

            piercingIgnoreEntityIds.add(entity.getId());
        }

        if (!this.level().isClientSide && ChiliBulletWeaponsConfig.canShotgunMultiHit()) {
            entity.invulnerableTime = 0;
        }

        if (entity.hurt(this.damageSources().thrown(this, owner), (float) getDamage())) {
            if (entity != owner && entity instanceof Player && owner instanceof ServerPlayer ownerInServer && !this.isSilent()) {
                // Play a ding when the bullet hit a player
                ownerInServer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
            }
        }

        if (pierceLevel <= 0) {
            this.discard();
        }
    }

    private int getDamage() {
        double speedSqr = this.getDeltaMovement().lengthSqr();
        int force = Mth.ceil(Mth.clamp(speedSqr * getBaseDamage(), 0.0D, Integer.MAX_VALUE));
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

    public int getAge() {
        return age;
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putByte(TAG_AGE, age);
        compoundTag.putByte(TAG_PIERCE_LEVEL, getPierceLevel());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        age = compoundTag.getByte(TAG_AGE);
        setPierceLevel(compoundTag.getByte(TAG_PIERCE_LEVEL));
    }
}
