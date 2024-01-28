package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class ChiliBulletPistol extends CrossbowItem {
    public static final float POWER_PISTOL = 3F;
    public static final float POWER_RIFLE = 4F;
    public static final float INACCURACY_PISTOL = 1F;
    public static final float INACCURACY_RIFLE = 0.5F;
    public static final int RELOAD_PISTOL = 10;
    public static final int RELOAD_RIFLE = 15;
    public static final Predicate<ItemStack> IS_CHILI_BULLET = itemStack -> itemStack.is(ModItems.CHILI_BULLET);
    public static final ResourceLocation PROPERTY_LOADING = new ResourceLocation(ChiliBulletWeapons.MOD_ID, "loading");

    private static final String TAG_IS_SHOT = "IsShot";
    private static final String TAG_LOADING = "Loading";

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
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return getAllSupportedProjectiles();
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean b) {
        if (!itemStack.hasTag()) {
            // For using crossbow animation
            setCharged(itemStack, true);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (isLoaded(itemStack)) {
            // Shoot
            shootProjectile(level, player, hand, itemStack, getShootingPower(), getInaccuracy());
            setShot(itemStack, true);
            // For using crossbow animation
            setCharged(itemStack, false);
            return InteractionResultHolder.consume(itemStack);
        } else if (!player.getProjectile(itemStack).isEmpty()) {
            // Begin loading
            if (!isLoading(itemStack)) {
                setLoading(itemStack, true);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.PISTOL_ACTION_OPEN, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
            }

            player.startUsingItem(hand);
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

        // Shoot bullet entity
        ChiliBullet bullet = new ChiliBullet(player, level);
        bullet.shootFromRotation(player, shootingPower, inaccuracy);
        level.addFreshEntity(bullet);
        // Wear out gun
        itemStack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));
        // Play firing sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.PISTOL_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int ticks) {
        if ((itemStack.getUseDuration() - ticks) >= getReloadDuration() && !isCharged(itemStack) && !isShot(itemStack)) {
            // For using crossbow animation
            setCharged(itemStack, true);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int ticks) {
        // Server side only
        if (level.isClientSide) {
            return;
        }

        if ((itemStack.getUseDuration() - ticks) >= getReloadDuration()
                && isLoading(itemStack) && tryLoadProjectile(entity, itemStack)) {
            // Finish loading
            setLoading(itemStack, false);
            setShot(itemStack, false);
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSoundEvents.PISTOL_ACTION_CLOSE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    public int getUseDuration(ItemStack itemStack) {
        return getReloadDuration() + 3;
    }

    public boolean tryLoadProjectile(LivingEntity entity, ItemStack pistolStack) {
        if (entity instanceof Player player && player.getAbilities().instabuild) {
            // The player is creative mode
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

    public static boolean isLoaded(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        return compoundTag == null || (!isShot(itemStack) && !isLoading(itemStack));
    }

    public static boolean isShot(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        return compoundTag != null && compoundTag.getBoolean(TAG_IS_SHOT);
    }

    public static void setShot(ItemStack itemStack, boolean isShot) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        compoundTag.putBoolean(TAG_IS_SHOT, isShot);
    }

    public static boolean isLoading(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        return compoundTag != null && compoundTag.getBoolean(TAG_LOADING);
    }

    public static void setLoading(ItemStack itemStack, boolean isReloading) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        compoundTag.putBoolean(TAG_LOADING, isReloading);
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
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
    }
}
