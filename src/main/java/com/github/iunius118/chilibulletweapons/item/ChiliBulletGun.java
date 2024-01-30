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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class ChiliBulletGun extends CrossbowItem {
    public static final Predicate<ItemStack> IS_CHILI_BULLET = itemStack -> itemStack.is(ModItems.CHILI_BULLET);
    public static final ResourceLocation PROPERTY_LOADING = new ResourceLocation(ChiliBulletWeapons.MOD_ID, "loading");
    public static final String TAG_LOADING = "Loading";
    public static final String TAG_LOADED_BULLETS = "LoadedBullets";

    private final int maxLoadedBullets;
    private final float shootingPower;
    private final float inaccuracy;
    private final int reloadDuration;

    public ChiliBulletGun(Properties properties, int maxLoadedBullets, float shootingPower, float inaccuracy, int reloadDuration) {
        super(properties);
        this.maxLoadedBullets = maxLoadedBullets;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (isLoaded(itemStack) && !isLoading(itemStack)) {
            // Shoot
            shootProjectile(level, player, hand, itemStack, getShootingPower(), getInaccuracy());
            setLoaded(itemStack, false);
            setBullets(itemStack, 0);
            return InteractionResultHolder.consume(itemStack);
        } else if (!player.getProjectile(itemStack).isEmpty()) {
            // Begin loading
            if (!isLoading(itemStack)) {
                setLoading(itemStack, true);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.GUN_ACTION_OPEN, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
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

        int bullets = getBullets(itemStack);

        for (int i = 0; i < bullets; i++) {
            // Shoot bullet entity
            ChiliBullet bullet = new ChiliBullet(player, level);
            bullet.shootFromRotation(player, shootingPower, inaccuracy);
            level.addFreshEntity(bullet);
            // Play firing sound
            level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.GUN_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

        // Wear out gun
        itemStack.hurtAndBreak(bullets, player, e -> e.broadcastBreakEvent(hand));
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int ticks) {
        // ChiliBulletWeapons.LOGGER.debug("Release {}/{}", itemStack.getUseDuration() - ticks, getReloadDuration());
        if ((itemStack.getUseDuration() - ticks) >= getReloadDuration(itemStack) && !isLoaded(itemStack)) {
            // Ready to fire
            setLoaded(itemStack, true);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int ticks) {
        // Server side only
        if (level.isClientSide) {
            return;
        }

        // ChiliBulletWeapons.LOGGER.debug("Using {}/{}", itemStack.getUseDuration() - ticks, getReloadDuration());

        if ((itemStack.getUseDuration() - ticks) >= getReloadDuration(itemStack)
                && isLoading(itemStack) && tryLoadProjectile(entity, itemStack)) {
            // Finish loading
            setLoading(itemStack, false);
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSoundEvents.GUN_ACTION_CLOSE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    public int getUseDuration(ItemStack itemStack) {
        return getReloadDuration(itemStack) + 3;
    }

    public boolean tryLoadProjectile(LivingEntity entity, ItemStack itemStack) {
        // Apply Multi-shot enchantment
        int multiShotLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, itemStack);
        int loadingBullets = (multiShotLevel == 0) ? maxLoadedBullets : maxLoadedBullets * 2;

        if (entity instanceof Player player && player.getAbilities().instabuild) {
            // For creative mode player
            setBullets(itemStack, loadingBullets);
            return true;
        }

        ItemStack bulletStack = entity.getProjectile(itemStack);

        if (bulletStack.isEmpty()) {
            // Player had no ammo
            return false;
        }

        // Load ammo
        int bullets = Math.min(bulletStack.getCount(), loadingBullets);
        setBullets(itemStack, bullets);
        bulletStack.shrink(bullets);
        return true;
    }

    public static boolean isLoaded(ItemStack itemStack) {
        return isCharged(itemStack);
    }

    public static void setLoaded(ItemStack itemStack, boolean isLoaded) {
        setCharged(itemStack, isLoaded);
    }

    public static boolean isLoading(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        return compoundTag != null && compoundTag.getBoolean(TAG_LOADING);
    }

    public static void setLoading(ItemStack itemStack, boolean isReloading) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        compoundTag.putBoolean(TAG_LOADING, isReloading);
    }

    public static int getBullets(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        return compoundTag.getInt(TAG_LOADED_BULLETS);
    }

    public static void setBullets(ItemStack itemStack, int count) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        compoundTag.putInt(TAG_LOADED_BULLETS, count);
    }

    public float getShootingPower() {
        return shootingPower;
    }

    public float getInaccuracy() {
        return inaccuracy;
    }

    public int getReloadDuration(ItemStack itemStack) {
        // Apply Quick Charge enchantment
        int quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, itemStack);
        return quickChargeLevel == 0 ? reloadDuration : reloadDuration - 4 * quickChargeLevel;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
    }
}
