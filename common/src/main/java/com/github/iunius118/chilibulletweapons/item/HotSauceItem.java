package com.github.iunius118.chilibulletweapons.item;

import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.List;

public class HotSauceItem extends Item {
    // Duration of the effects in ticks (2 seconds)
    public static final int HOT_SAUCE_DURATION = 40;
    // Duration of the effects in ticks (10 seconds)
    public static final int CAPSICUM_CRYSTAL_DURATION = 200;
    // Splash potion colors
    public static final int RED_HOT_SAUCE_COLOR = 0xFFCF0408;
    public static final int GREEN_HOT_SAUCE_COLOR = 0xFFAAC60E;
    public static final int CAPSICUM_CRYSTAL_COLOR = 0xFFFFF0F0;

    public final int color;
    public final int effectDuration;

    public HotSauceItem(Properties properties, int effectDuration, int color) {
        super(properties);
        this.color = color;
        this.effectDuration = effectDuration;

        // Register dispense behavior
        DispenserBlock.registerBehavior(this, new DispenseBehavior(this));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            // On server side
            // Create and throw a splash potion with effects
            ThrownPotion thrownpotion = new ThrownPotion(level, player);
            thrownpotion.setItem(getSplashPotion());
            thrownpotion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
            level.addFreshEntity(thrownpotion);

            // Play sound effect
            level.playSound(null, thrownpotion.getX(), thrownpotion.getY(), thrownpotion.getZ(),
                    SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS,
                    0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        }

        // Trigger advancement
        player.awardStat(Stats.ITEM_USED.get(this));

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public ItemStack getSplashPotion() {
        // Create a splash potion item with effects
        ItemStack splashPotion = new ItemStack(Items.SPLASH_POTION);
        PotionUtils.setCustomEffects(splashPotion, getMobEffects());
        splashPotion.getOrCreateTag().putInt(PotionUtils.TAG_CUSTOM_POTION_COLOR, color);
        return splashPotion;
    }

    public List<MobEffectInstance> getMobEffects() {
        // Add Slowdown IV and Blindness effects
        return List.of(
                new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, effectDuration, 3),
                new MobEffectInstance(MobEffects.BLINDNESS, effectDuration, 0)
        );
    }

    public static class DispenseBehavior extends AbstractProjectileDispenseBehavior {
        private final HotSauceItem hotSauce;

        public DispenseBehavior(HotSauceItem hotSauce) {
            this.hotSauce = hotSauce;
        }

        @Override
        protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
            var thrownPotion = new ThrownPotion(level, position.x(), position.y(), position.z());
            thrownPotion.setItem(hotSauce.getSplashPotion());
            return thrownPotion;
        }

        @Override
        protected float getUncertainty() {
            return super.getUncertainty() * 0.5F;
        }

        @Override
        protected float getPower() {
            return super.getPower() * 1.25F;
        }
    }
}
