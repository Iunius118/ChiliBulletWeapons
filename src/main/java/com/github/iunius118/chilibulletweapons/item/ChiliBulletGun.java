package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ChiliBulletGun extends CrossbowItem {
    public static final Predicate<ItemStack> IS_CHILI_BULLET = itemStack -> itemStack.is(ModItems.CHILI_BULLET);
    public static final ResourceLocation PROPERTY_LOADING = new ResourceLocation(ChiliBulletWeapons.MOD_ID, "loading");
    public static final ResourceLocation PROPERTY_MULTISHOT = new ResourceLocation(ChiliBulletWeapons.MOD_ID, "multishot");
    public static final ResourceLocation PROPERTY_PIERCING = new ResourceLocation(ChiliBulletWeapons.MOD_ID, "piercing");
    public static final String TAG_LOADING = "Loading";
    public static final String TAG_LOADED_BULLETS = "LoadedBullets";
    public static final String DESCRIPTION_PISTOL = "item.chilibulletweapons.gun.pistol";
    public static final String DESCRIPTION_RIFLE = "item.chilibulletweapons.gun.rifle";
    public static final String DESCRIPTION_SHOTGUN = "item.chilibulletweapons.gun.shotgun";

    public static final int ENCHANTMENT_VALUE = 10;
    public static final int CAPACITY_BASIC = 1;
    public static final int CAPACITY_MULTISHOT = 4;
    public static final float POWER_BASIC = 3F;
    public static final float POWER_PIERCING = 4F;
    public static final float INACCURACY_BASIC = 1F;
    public static final float INACCURACY_PIERCING = 0.5F;
    public static final float INACCURACY_MULTISHOT_CORRECTION = 4F;
    public static final int RELOAD_BASIC = 14;
    public static final int RELOAD_MULTISHOT = 22;
    public static final int RELOAD_PER_QUICK_CHARGE = 2;

    public ChiliBulletGun(Properties properties) {
        super(properties);
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
            shootProjectile(level, player, hand, itemStack);
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

    public void shootProjectile(Level level, Player player, InteractionHand hand, ItemStack itemStack) {
        // Server side only
        if (level.isClientSide) {
            return;
        }

        final float shootingPower = getShootingPower(itemStack);
        final int piercingLevel = getPiercingLevel(itemStack);
        final int bullets = getBullets(itemStack);

        for (int i = 0; i < bullets; i++) {
            float inaccuracy = getInaccuracy(itemStack);

            if (i > 0 && getMultishotLevel(itemStack) != 0) {
                // Multishot is less accurate after the second shot
                inaccuracy += INACCURACY_MULTISHOT_CORRECTION;
            }

            // Shoot bullet entity
            ChiliBullet bullet = new ChiliBullet(player, level);
            bullet.shootFromRotation(player, shootingPower, inaccuracy);

            if (piercingLevel > 0) {
                bullet.setPierceLevel((byte) piercingLevel);
            }

            level.addFreshEntity(bullet);
            // Add firing effects
            addSmokeParticle(level, bullet);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.GUN_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

        // Wear out gun
        itemStack.hurtAndBreak(bullets, player, e -> e.broadcastBreakEvent(hand));
    }

    private void addSmokeParticle(Level level, ChiliBullet bullet) {
        Vec3 pos = bullet.position().add(bullet.getDeltaMovement().normalize());
        ((ServerLevel) level).sendParticles(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0, 0D, 0D, 0D, 2D);
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
        // Apply Multishot enchantment
        final int loadingBullets = (getMultishotLevel(itemStack) == 0) ? CAPACITY_BASIC : CAPACITY_MULTISHOT;

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

    public static int getQuickChargeLevel(ItemStack itemStack) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, itemStack);
    }

    public static int getMultishotLevel(ItemStack itemStack) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, itemStack);
    }

    public static int getPiercingLevel(ItemStack itemStack) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, itemStack);
    }

    public float getShootingPower(ItemStack itemStack) {
        // Apply Piercing enchantment
        return (getPiercingLevel(itemStack) <= 0) ? POWER_BASIC : POWER_PIERCING;
    }

    public float getInaccuracy(ItemStack itemStack) {
        // Apply Piercing enchantment
        return (getPiercingLevel(itemStack) <= 0) ? INACCURACY_BASIC : INACCURACY_PIERCING;
    }

    public int getReloadDuration(ItemStack itemStack) {
        // Apply Quick Charge and Multishot enchantments
        int basicDuration = (getMultishotLevel(itemStack) == 0) ? RELOAD_BASIC : RELOAD_MULTISHOT;
        int quickChargeLevel = getQuickChargeLevel(itemStack);
        return (quickChargeLevel == 0) ? basicDuration : basicDuration - RELOAD_PER_QUICK_CHARGE * quickChargeLevel;
    }

    public static ItemStack enchant(ItemLike item, Enchantment... enchantments) {
        ItemStack itemStack = new ItemStack(item);
        Arrays.asList(enchantments).forEach(e -> itemStack.enchant(e, e.getMaxLevel()));
        return itemStack;
    }

    @Override
    public int getEnchantmentValue() {
        return ENCHANTMENT_VALUE;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
    }

    @Override
    public String getDescriptionId(ItemStack itemStack) {
        // Change item display name by enchantment
        if (getMultishotLevel(itemStack) != 0) {
            return DESCRIPTION_SHOTGUN;
        } else if (getPiercingLevel(itemStack) > 0) {
            return DESCRIPTION_RIFLE;
        } else {
            return DESCRIPTION_PISTOL;
        }
    }
}
