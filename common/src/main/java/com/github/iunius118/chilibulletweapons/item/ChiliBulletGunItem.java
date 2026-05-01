package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.advancements.ModCriteriaTriggers;
import com.github.iunius118.chilibulletweapons.component.DyedGunColors;
import com.github.iunius118.chilibulletweapons.component.GunContents;
import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ChiliBulletGunItem extends CrossbowItem {
    public static final Predicate<ItemStack> IS_CHILI_BULLET = itemStack -> itemStack.is(ModItems.CHILI_BULLET);

    public ChiliBulletGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return getAllSupportedProjectiles();
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return IS_CHILI_BULLET;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);

        if (ChiliBulletGunHelper.isLoaded(itemStack) && !ChiliBulletGunHelper.isLoading(itemStack)) {
            // When the gun is loaded, shoot bullets
            performShooting(level, player, hand, itemStack, ChiliBulletGunHelper.getShootingPower(itemStack),
                    ChiliBulletGunHelper.getInaccuracy(itemStack), null);
            return InteractionResult.CONSUME;
        } else if (!player.getProjectile(itemStack).isEmpty()) {
            // When the gun is unloaded, start loading
            if (!ChiliBulletGunHelper.isLoading(itemStack)) {
                openAction(level, player, itemStack);
            }

            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        } else {
            // When the gun is unloaded and the player has no ammo
            if (ChiliBulletGunHelper.isLoading(itemStack)) {
                // Close action without loading
                closeAction(level, player, itemStack);
            }

            return InteractionResult.FAIL;
        }
    }

    public void performShooting(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon,
                                float velocity, float inaccuracy, LivingEntity target) {
        if (level instanceof ServerLevel serverlevel) {
            // Server side only
            // Pull out loaded bullets from gun
            var chargedprojectiles = weapon.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);

            if (chargedprojectiles != null && !chargedprojectiles.isEmpty()) {
                // Shoot all loaded bullets
                shoot(serverlevel, shooter, hand, weapon, chargedprojectiles.itemCopies(),
                        velocity, inaccuracy, false, target);

                if (shooter instanceof ServerPlayer serverplayer) {
                    // Trigger advancement
                    ModCriteriaTriggers.SHOT_CHILI_BULLET_GUN.trigger(serverplayer, weapon);
                    serverplayer.awardStat(Stats.ITEM_USED.get(weapon.getItem()));
                }
            }
        }
    }

    public void performShootingByNonPlayer(Level level, LivingEntity livingEntity, InteractionHand hand,
                                           ItemStack stack) {
        performShooting(level, livingEntity, hand, stack, ChiliBulletGunHelper.getShootingPower(stack),
                ChiliBulletGunHelper.getInaccuracy(stack), null);
    }

    @Override
    protected void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon,
                         List<ItemStack> projectileItems, float velocity, float inaccuracy, boolean isCrit,
                         LivingEntity target) {
        int abrasion = 0;

        // Shoot projectiles
        for (int i = 0; i < projectileItems.size(); i++) {
            ItemStack projectileItem = projectileItems.get(i);

            if (!projectileItem.isEmpty()) {
                // Shoot each projectile
                Projectile projectile = createProjectile(level, shooter, weapon, projectileItem, isCrit);
                shootProjectile(shooter, projectile, i, velocity, inaccuracy, 0, target);
                level.addFreshEntity(projectile);
                abrasion += getDurabilityUse(projectileItem);
            }
        }

        // Wear out gun
        hurtAndBreak(weapon, abrasion, shooter, hand.asEquipmentSlot());
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo,
                                          boolean isCrit) {
        return new ChiliBullet(level, shooter, weapon);
    }

    @Override
    protected int getDurabilityUse(ItemStack stack) {
        return 1;
    }

    public void hurtAndBreak(ItemStack itemStack, int amount, LivingEntity entityLiving, EquipmentSlot slot) {
        if (itemStack.has(DataComponents.CUSTOM_NAME)
                && entityLiving.getRandom().nextInt(2) == 0) {
            // If the item has a custom name, there is a 50% chance to not wear out the gun
            return;
        }

        // Wear out gun
        itemStack.hurtAndBreak(amount, entityLiving, slot);
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index,
                                   float velocity, float inaccuracy, float angle, LivingEntity target) {
        float fixedInaccuracy = inaccuracy;

        if (index > 0) {
            // Multishot is less accurate after the second shot
            fixedInaccuracy += Constants.ChiliBulletGun.INACCURACY_MULTISHOT_CORRECTION;
        }

        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, velocity, fixedInaccuracy);
        // Add firing effects
        var level = shooter.level();
        addSmokeParticle(level, projectile);
        level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(),
                ModSoundEvents.GUN_SHOOT, shooter.getSoundSource(), 1.0F, getRandomPitch(shooter.getRandom()));
    }

    private static void addSmokeParticle(Level level, Projectile projectile) {
        Vec3 pos = projectile.position().add(projectile.getDeltaMovement().normalize());
        ((ServerLevel) level).sendParticles(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0, 0D, 0D, 0D, 2D);
    }

    private static float getRandomPitch(RandomSource random) {
        return 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F;
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int remainingTime) {
        int timeHeld = getUseDuration(itemStack, entity) - remainingTime;
        if (timeHeld >= getReloadDuration(itemStack)
                && !ChiliBulletGunHelper.isLoaded(itemStack)) {
            // Ready to fire
            ChiliBulletGunHelper.changeLoading(itemStack, false);
        }

        /* For debug
        if (!level.isClientSide()) Constants.LOG.info("[CBGun] Release {}/{}", timeHeld, getReloadDuration(itemStack));
        //*/

        return getLoadingProgress((float) timeHeld, itemStack, entity) >= 1.0F && isCharged(itemStack);
    }


    public static float getLoadingProgress(float timeHeld, ItemStack itemStack, LivingEntity holder) {
        return Math.min(timeHeld / (float) CrossbowItem.getChargeDuration(itemStack, holder), 1.0F);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int count) {
        int usingCount = itemStack.getUseDuration(livingEntity) - count;
        boolean isLoading = ChiliBulletGunHelper.isLoading(itemStack);

        if (usingCount == 0 && !isLoading) {
            // For handled by non-player
            openAction(level, livingEntity, itemStack);
        }

        if (level.isClientSide()) {
            return;
        }

        // Server side only
        //Constants.LOG.info("[CBGun] Using {}/{}", usingCount, getReloadDuration(itemStack));

        if (usingCount >= getReloadDuration(itemStack)
                && isLoading && tryLoadProjectiles(livingEntity, itemStack)) {
            // Finish loading
            closeAction(level, livingEntity, itemStack);
            //Constants.LOG.info("[CBGun] loaded");
        }
    }

    private void openAction(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        ChiliBulletGunHelper.changeLoading(itemStack, true);
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                ModSoundEvents.GUN_ACTION_OPEN, livingEntity.getSoundSource(), 1.0F,
                getRandomPitch(livingEntity.getRandom()));
    }

    private void closeAction(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        ChiliBulletGunHelper.changeLoading(itemStack, false);
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                ModSoundEvents.GUN_ACTION_CLOSE, livingEntity.getSoundSource(), 1.0F,
                getRandomPitch(livingEntity.getRandom()));
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity entity) {
        return getReloadDuration(itemStack) + 3;
    }

    /* CrossbowItem.getChargeDuration() will be hooked using Mixin */

    public int getReloadDuration(ItemStack stack) {
        return ChiliBulletGunHelper.getReloadDuration(stack);
    }

    public static boolean tryLoadProjectiles(LivingEntity shooter, ItemStack stack) {
        //Constants.LOG.info("[CBGun] Try loading");
        // Get ammo stack from shooter inventory
        ItemStack ammo = shooter.getProjectile(stack);
        // If ammo stack is arrow, replace it with chili bullet
        ammo = ammo.is(Items.ARROW) ? new ItemStack(ModItems.CHILI_BULLET) : ammo;
        // Draw ammo to load from ammo stack
        List<ItemStack> list = draw(stack, ammo, shooter);

        if (!list.isEmpty()) {
            // Set loaded ammo to gun
            stack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.ofNonEmpty(list));
            return true;
        } else {
            // No ammo found
            return false;
        }
    }

    protected static List<ItemStack> draw(ItemStack weapon, ItemStack ammo, LivingEntity shooter) {
        int barrelCount = ChiliBulletGunHelper.getBarrelCount(weapon);

        if (shooter.hasInfiniteMaterials()) {
            // For creative mode
            ItemStack infAmmo = ammo.isEmpty() ? new ItemStack(ModItems.CHILI_BULLET) : ammo.copy();
            return Stream.generate(() -> infAmmo.copyWithCount(1)).limit(barrelCount).toList();
        } else if (ammo.isEmpty()) {
            return List.of();
        } else {
            // For survival mode
            // Number of drawable ammo
            int ammoCount = Math.min(ammo.getCount(), barrelCount);
            // Draw ammo
            List<ItemStack> list = Stream.generate(() -> ammo.split(1)).limit(ammoCount).toList();

            if (ammo.isEmpty() && shooter instanceof Player player) {
                // Remove empty ammo stack from player inventory
                player.getInventory().removeItem(ammo);
            }

            return list;
        }
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay display,
                                Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);

        GunContents.getOrDefault(itemStack).addToTooltip(builder, tooltipFlag);

        var dyedGunColors = itemStack.get(ModDataComponents.DYED_GUN_COLORS);

        if (dyedGunColors != null) {
            DyedGunColors.getOrDefault(itemStack).addToTooltip(builder, tooltipFlag);
        }
    }

    @Override
    public Component getName(ItemStack itemStack) {
        boolean hasBayonet = ChiliBulletGunHelper.hasBayonet(itemStack);
        boolean canMultishot = ChiliBulletGunHelper.canMultishot(itemStack);
        boolean hasPiercing = ChiliBulletGunHelper.getPiercing(itemStack) > 0;
        String nameKey;

        // Change item display name by upgrading
        if (hasBayonet) {
            if (canMultishot) {
                nameKey = Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_VOLLEY_GUN;
            } else if (hasPiercing) {
                nameKey = Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_RIFLE;
            } else {
                nameKey = Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_PISTOL;
            }
        } else {
            if (canMultishot) {
                nameKey = Constants.ChiliBulletGun.DESCRIPTION_VOLLEY_GUN;
            } else if (hasPiercing) {
                nameKey = Constants.ChiliBulletGun.DESCRIPTION_RIFLE;
            } else {
                nameKey = Constants.ChiliBulletGun.DESCRIPTION_PISTOL;
            }
        }

        return Component.translatable(nameKey);
    }

    /**
     * Check if this item can be upgraded with upgrade items.
     *
     * @return True if this item can be upgraded with upgrade items, false otherwise.
     */
    public boolean isUpgradable(ItemStack itemStack) {
        // ChiliBulletGunItem is upgradable if it does not have the fixed component.
        return !itemStack.has(ModDataComponents.FIXED);
    }
}
