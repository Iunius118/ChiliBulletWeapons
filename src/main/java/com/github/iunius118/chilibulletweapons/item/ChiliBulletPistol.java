package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class ChiliBulletPistol extends ProjectileWeaponItem {
    public static final float POWER_PISTOL = 3F;
    public static final float POWER_RIFLE = 4F;
    public static final float INACCURACY_PISTOL = 1F;
    public static final float INACCURACY_RIFLE = 0.5F;
    public static final int RELOAD_PISTOL = 10;
    public static final int RELOAD_RIFLE = 15;
    public static final Predicate<ItemStack> IS_CHILI_BULLET = itemStack -> itemStack.is(ModItems.CHILI_BULLET);
    public static final ResourceLocation PROPERTY_RELOAD = new ResourceLocation(ChiliBulletWeapons.MOD_ID, "reload");

    private static final String TAG_IS_SHOT = "IsShot";
    private static final String TAG_RELOADING = "Reload";

    private final float shootingPower;
    private final float inaccuracy;
    private final int reloadDuration;

    public ChiliBulletPistol(Properties properties, float shootingPower, float inaccuracy, int reloadDuration) {
        super(properties);
        this.shootingPower = shootingPower;
        this.inaccuracy = inaccuracy;
        this.reloadDuration = reloadDuration;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return IS_CHILI_BULLET;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (isReloaded(itemStack)) {
            // Shoot
            shootProjectile(level, player, hand, itemStack, getShootingPower(), getInaccuracy());
            setShot(itemStack, true);
            return InteractionResultHolder.consume(itemStack);
        } else if (!player.getProjectile(itemStack).isEmpty()) {
            // Start reloading
            player.startUsingItem(hand);
            setReloading(itemStack, true);
            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    public void shootProjectile(Level level, Player player, InteractionHand hand, ItemStack itemStack, float shootingPower, float inaccuracy) {
        // Server side only
        if (level.isClientSide) {
            return;
        }

        ChiliBullet bullet = new ChiliBullet(player, level);
        bullet.shootFromRotation(player, shootingPower, inaccuracy);
        level.addFreshEntity(bullet);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int ticks) {
        // Server side only
        if (level.isClientSide) {
            return;
        }

        if ((itemStack.getUseDuration() - ticks) >= getReloadDuration()
                && !isReloaded(itemStack) && tryLoadProjectile(entity, itemStack)) {
            setReloading(itemStack, false);
            setShot(itemStack, false);
        }
    }

    public int getUseDuration(ItemStack itemStack) {
        return getReloadDuration() + 3;
    }

    public boolean tryLoadProjectile(LivingEntity entity, ItemStack pistolStack) {
        if (entity instanceof Player player && player.getAbilities().instabuild) {
            // The player is creative mode.
            return true;
        }

        ItemStack bulletStack = entity.getProjectile(pistolStack);

        if (bulletStack.isEmpty()) {
            // The player had no ammo
            return false;
        }

        // Load ammo
        bulletStack.shrink(1);
        return true;
    }

    public static boolean isReloaded(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        return compoundtag == null || (!isShot(itemStack) && !isReloading(itemStack));
    }

    public static boolean isShot(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        return compoundtag != null && compoundtag.getBoolean(TAG_IS_SHOT);
    }

    public static void setShot(ItemStack itemStack, boolean isShot) {
        CompoundTag compoundtag = itemStack.getOrCreateTag();
        compoundtag.putBoolean(TAG_IS_SHOT, isShot);
    }

    public static boolean isReloading(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        return compoundtag != null && compoundtag.getBoolean(TAG_RELOADING);
    }

    public static void setReloading(ItemStack itemStack, boolean isReloading) {
        CompoundTag compoundtag = itemStack.getOrCreateTag();
        compoundtag.putBoolean(TAG_RELOADING, isReloading);
    }

    public float getShootingPower() {
        return shootingPower;
    }

    public float getInaccuracy() {
        return inaccuracy;
    }

    public int getReloadDuration() {
        return reloadDuration;
    }

    @Override
    public boolean useOnRelease(ItemStack itemStack) {
        return itemStack.is(this);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
}
