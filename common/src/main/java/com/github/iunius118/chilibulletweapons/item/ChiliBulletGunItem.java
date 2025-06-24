package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
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
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ChiliBulletGunItem extends CrossbowItem {
    public static final Predicate<ItemStack> IS_CHILI_BULLET = itemStack -> itemStack.is(ModItems.CHILI_BULLET);

    public ChiliBulletGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack stack) {
        super.verifyComponentsAfterLoad(stack);

        // Migrate old components to gun contents
        GunContents.migrateFromOldComponents(stack);
        // Apply bayonet attack damage if the gun is bayoneted
        applyBayonetAttackDamage(stack);
    }

    public void applyBayonetAttackDamage(ItemStack stack) {
        Float bayonetAttackDamage = stack.get(ModDataComponents.BAYONETED);

        if (bayonetAttackDamage != null) {
            // Set attack damage and attack speed modifiers for bayoneted gun
            ChiliBulletGunHelper.setItemAttributeModifiers(stack,
                    bayonetAttackDamage, Constants.ChiliBulletGun.BAYONET_ATTACK_SPEED);
        }
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);

        if (ChiliBulletGunHelper.isLoaded(itemStack) && !ChiliBulletGunHelper.isLoading(itemStack)) {
            // When the gun is loaded, shoot bullets
            performShooting(level, player, hand, itemStack,
                    ChiliBulletGunHelper.getShootingPower(itemStack), ChiliBulletGunHelper.getInaccuracy(itemStack), null);
            return InteractionResultHolder.consume(itemStack);
        } else if (!player.getProjectile(itemStack).isEmpty()) {
            // When the gun is unloaded, start loading
            if (!ChiliBulletGunHelper.isLoading(itemStack)) {
                openAction(level, player, itemStack);
            }

            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemStack);
        } else {
            // When the gun is unloaded and the player has no ammo
            if (ChiliBulletGunHelper.isLoading(itemStack)) {
                // Close action without loading
                closeAction(level, player, itemStack);
            }

            return InteractionResultHolder.fail(itemStack);
        }
    }

    public void performShooting(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon,
                                float velocity, float inaccuracy, LivingEntity target) {
        if (level instanceof ServerLevel serverlevel) {
            // Server side only
            // Get and remove loaded projectiles
            var chargedprojectiles = weapon.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);

            if (chargedprojectiles != null && !chargedprojectiles.isEmpty()) {
                // Shoot all loaded bullets
                shoot(serverlevel, shooter, hand, weapon, chargedprojectiles.getItems(),
                        velocity, inaccuracy, false, target);

                if (shooter instanceof ServerPlayer serverplayer) {
                    serverplayer.awardStat(Stats.ITEM_USED.get(weapon.getItem()));
                }
            }
        }
    }

    public void performShootingByNonPlayer(Level level, LivingEntity livingEntity, InteractionHand hand, ItemStack stack) {
        performShooting(level, livingEntity, hand, stack,
                ChiliBulletGunHelper.getShootingPower(stack), ChiliBulletGunHelper.getInaccuracy(stack), null);
    }

    @Override
    protected void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, List<ItemStack> projectileItems,
                         float velocity, float inaccuracy, boolean isCrit, LivingEntity target) {
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
        hurtAndBreak(weapon, abrasion, shooter, LivingEntity.getSlotForHand(hand));
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        return new ChiliBullet(level, shooter, weapon);
    }

    @Override
    protected int getDurabilityUse(ItemStack stack) {
        return 1;
    }

    public void hurtAndBreak(ItemStack stack, int amount, LivingEntity entityLiving, EquipmentSlot slot) {
        if (stack.has(DataComponents.CUSTOM_NAME)
                && entityLiving.getRandom().nextInt(2) == 0) {
            // If the item has a custom name, there is a 50% chance to not wear out the gun
            return;
        }

        // Wear out gun
        stack.hurtAndBreak(amount, entityLiving, slot);
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
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if ((stack.getUseDuration(entityLiving) - timeLeft) >= getReloadDuration(stack)
                && !ChiliBulletGunHelper.isLoaded(stack)) {
            // Ready to fire
            ChiliBulletGunHelper.changeLoading(stack, false);
        }
        //if (!level.isClientSide) Constants.LOG.info("[CBGun] Release {}/{}", itemStack.getUseDuration() - ticks, getReloadDuration(itemStack));
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        int usingCount = stack.getUseDuration(livingEntity) - count;
        boolean isLoading = ChiliBulletGunHelper.isLoading(stack);

        if (usingCount == 0 && !isLoading) {
            // For handled by non-player
            openAction(level, livingEntity, stack);
        }

        if (level.isClientSide) {
            return;
        }

        // Server side only
        //Constants.LOG.info("[CBGun] Using {}/{}", usingCount, getReloadDuration(stack));

        if (usingCount >= getReloadDuration(stack)
                && isLoading && tryLoadProjectiles(livingEntity, stack)) {
            // Finish loading
            closeAction(level, livingEntity, stack);
            //Constants.LOG.info("[CBGun] loaded");
        }
    }

    private void openAction(Level level, LivingEntity livingEntity, ItemStack stack) {
        ChiliBulletGunHelper.changeLoading(stack, true);
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                ModSoundEvents.GUN_ACTION_OPEN, livingEntity.getSoundSource(), 1.0F, getRandomPitch(livingEntity.getRandom()));
    }

    private void closeAction(Level level, LivingEntity livingEntity, ItemStack stack) {
        ChiliBulletGunHelper.changeLoading(stack, false);
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                ModSoundEvents.GUN_ACTION_CLOSE, livingEntity.getSoundSource(), 1.0F, getRandomPitch(livingEntity.getRandom()));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return getReloadDuration(stack) + 3;
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
            stack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(list));
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
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.has(ModDataComponents.BAYONETED)) {
            // Wear out bayoneted gun
            hurtAndBreak(stack, 1, attacker, EquipmentSlot.MAINHAND);
        }
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public int getEnchantmentValue() {
        return Constants.ChiliBulletGun.ENCHANTMENT_VALUE;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        GunContents.getOrDefault(stack).addToTooltip(tooltipComponents, tooltipFlag);

        var dyedGunColors = stack.get(ModDataComponents.DYED_GUN_COLORS);

        if (dyedGunColors != null) {
            DyedGunColors.getOrDefault(stack).addToTooltip(tooltipComponents, tooltipFlag);
        }
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        boolean isBayoneted = stack.has(ModDataComponents.BAYONETED);
        boolean canMultishot = ChiliBulletGunHelper.canMultishot(stack);
        boolean hasPiercing = ChiliBulletGunHelper.getPiercing(stack) > 0;

        // Change item display name by upgrading
        if (isBayoneted) {
            if (canMultishot) {
                return Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_VOLLEY_GUN;
            } else if (hasPiercing) {
                return Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_RIFLE;
            } else {
                return Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_PISTOL;
            }
        } else {
            if (canMultishot) {
                return Constants.ChiliBulletGun.DESCRIPTION_VOLLEY_GUN;
            } else if (hasPiercing) {
                return Constants.ChiliBulletGun.DESCRIPTION_RIFLE;
            } else {
                return Constants.ChiliBulletGun.DESCRIPTION_PISTOL;
            }
        }
    }

    /**
     * Check if this item can be upgraded with upgrade items.
     *
     * @return True if this item can be upgraded with upgrade items, false otherwise.
     */
    public boolean isUpgradable(ItemStack stack) {
        // ChiliBulletGunItem is upgradable if it does not have the fixed component.
        return !stack.has(ModDataComponents.FIXED);
    }
}
