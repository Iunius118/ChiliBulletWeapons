package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

public class ChiliBulletGunHelper {

    /**
     * Whether the gun is loaded with ammunition or not.
     *
     * @param stack Item stack
     * @return True if the gun is loaded, false otherwise.
     */
    public static boolean isLoaded(ItemStack stack) {
        return isLoaded(stack.get(DataComponents.CHARGED_PROJECTILES));
    }

    /**
     * Whether the gun is loaded with ammunition or not.
     *
     * @param chargedProjectiles Charged projectiles component
     * @return True if the gun is loaded, false otherwise.
     */
    public static boolean isLoaded(ChargedProjectiles chargedProjectiles) {
        return chargedProjectiles != null && !chargedProjectiles.isEmpty();
    }

    /**
     * Whether the gun is in the process of loading ammunition or not.
     * In chili bullet gun, whether the action is open or not.
     *
     * @param stack Item stack
     * @return True if the gun is loading, false otherwise.
     */
    public static boolean isLoading(ItemStack stack) {
        return stack.has(ModDataComponents.LOADING);
    }

    /**
     * Change whether the gun is in the process of loading ammunition.
     *
     * @param stack     Item stack
     * @param isLoading True if the gun is loading, false otherwise.
     */
    public static void changeLoading(ItemStack stack, boolean isLoading) {
        if (stack.has(ModDataComponents.LOADING)) {
            if (!isLoading) {
                // Remove IS_LOADING component if it was previously set
                stack.remove(ModDataComponents.LOADING);
            }
        } else if (isLoading) {
            // Set IS_LOADING component if it was not previously set
            stack.set(ModDataComponents.LOADING, Unit.INSTANCE);
        }
    }

    /**
     * Get a upgrade level of quick loading.
     *
     * @param stack Item stack
     * @return Quick loading level
     */
    public static int getQuickLoading(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.QUICK_LOADING, 0);
    }

    /**
     * Get a upgrade level of piercing.
     *
     * @param stack Item stack
     * @return Piercing level
     */
    public static int getPiercing(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.PIERCING, 0);
    }

    /**
     * Get a number of barrels that the gun has.
     *
     * @param stack Item stack
     * @return Number of barrels
     */
    public static int getBarrelCount(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.MULTISHOT, Constants.ChiliBulletGun.CAPACITY_BASIC);
    }

    /**
     * Whether the gun has a bayonet attached or not.
     *
     * @param stack Item stack
     * @return True if the gun has a bayonet, false otherwise.
     */
    public static boolean isBayoneted(ItemStack stack) {
        return stack.has(ModDataComponents.BAYONETED);
    }

    /**
     * Get shooting power with piercing level applied that item stack has.
     * The shooting power refers to the initial velocity of the bullet.
     *
     * @param stack Item stack
     * @return Shooting power
     */
    public static float getShootingPower(ItemStack stack) {
        if (getPiercing(stack) < 1) {
            return Constants.ChiliBulletGun.POWER_BASIC;
        } else {
            return Constants.ChiliBulletGun.POWER_PIERCING;
        }
    }

    /**
     * Get inaccuracy with Piercing applied that item stack has.
     *
     * @param stack Item stack
     * @return Inaccuracy
     */
    public static float getInaccuracy(ItemStack stack) {
        if (getPiercing(stack) < 1) {
            return Constants.ChiliBulletGun.INACCURACY_BASIC;
        } else {
            return Constants.ChiliBulletGun.INACCURACY_PIERCING;
        }
    }

    /**
     * Get reload duration with data components applied that item stack has.
     *
     * @param stack Item stack
     * @return Reload duration
     */
    public static int getReloadDuration(ItemStack stack) {
        int basicDuration = Constants.ChiliBulletGun.RELOAD_BASIC;

        if (stack.has(ModDataComponents.MULTISHOT)) {
            basicDuration = Constants.ChiliBulletGun.RELOAD_MULTISHOT;
        }

        if (stack.has(ModDataComponents.BAYONETED)) {
            basicDuration += Constants.ChiliBulletGun.RELOAD_BAYONETED_ADDITIONAL;
        }

        int quickLoading = getQuickLoading(stack);

        if (quickLoading == 0) {
            return basicDuration;
        } else {
            return Math.max(0, basicDuration - Constants.ChiliBulletGun.RELOAD_PER_QUICK_CHARGE * quickLoading);
        }
    }

    /**
     * Set item attribute modifiers to the item stack.
     *
     * @param stack        Item stack to attach item attribute modifiers
     * @param attackDamage attack damage
     * @param attackSpeed  attack speed
     */
    public static void setItemAttributeModifiers(ItemStack stack, float attackDamage, float attackSpeed) {
        // Set attack damage and attack speed modifiers
        var itemAttributeModifiers = ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();

        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemAttributeModifiers);
    }

    /**
     * Add barrel information to the tooltip.
     * It will appear as “V[Shooting Power]([Piercing Level]) Barrel x[Barrel Count]” via placeholders.
     *
     * @param stack             Item stack
     * @param tooltipComponents List of components for the tooltip
     */
    public static void addBarrelCountTooltip(ItemStack stack, List<Component> tooltipComponents) {
        String shootingPower = ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(getShootingPower(stack));
        int barrelCount = getBarrelCount(stack);
        int piercingLevel = getPiercing(stack);
        var component = Component.translatable(Constants.ChiliBulletGun.TOOLTIP_BARREL_COUNT,
                        shootingPower, piercingLevel, barrelCount)
                .withStyle(ChatFormatting.DARK_GRAY);
        tooltipComponents.add(component);
    }

    /**
     * Add information about quick loading to the tooltip.
     *
     * @param stack             Item stack
     * @param tooltipComponents List of components for the tooltip
     */
    public static void addQuickLoadTooltip(ItemStack stack, List<Component> tooltipComponents) {
        int quickLoading = ChiliBulletGunHelper.getQuickLoading(stack);

        if (quickLoading > 0) {
            // Add quick loading tooltip
            var component = Component.translatable(Constants.ChiliBulletGun.TOOLTIP_QUICK_LOAD, quickLoading)
                    .withStyle(ChatFormatting.GRAY);
            tooltipComponents.add(component);
        }
    }

    private ChiliBulletGunHelper() {}
}
