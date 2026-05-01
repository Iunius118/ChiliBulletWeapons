package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.component.GunContents;
import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Weapon;

import java.util.ArrayList;
import java.util.List;

public class ChiliBulletGunHelper {

    /**
     * Whether the gun is loaded with ammunition or not.
     *
     * @param itemStack Item stack
     * @return True if the gun is loaded, false otherwise.
     */
    public static boolean isLoaded(ItemStack itemStack) {
        return isLoaded(itemStack.get(DataComponents.CHARGED_PROJECTILES));
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
     * @param itemStack Item stack
     * @return True if the gun is loading, false otherwise.
     */
    public static boolean isLoading(ItemStack itemStack) {
        return itemStack.has(ModDataComponents.LOADING);
    }

    /**
     * Change whether the gun is in the process of loading ammunition.
     *
     * @param itemStack Item stack
     * @param isLoading True if the gun is loading, false otherwise.
     */
    public static void changeLoading(ItemStack itemStack, boolean isLoading) {
        if (itemStack.has(ModDataComponents.LOADING)) {
            if (!isLoading) {
                // Remove IS_LOADING component if it was previously set
                itemStack.remove(ModDataComponents.LOADING);
            }
        } else if (isLoading) {
            // Set IS_LOADING component if it was not previously set
            itemStack.set(ModDataComponents.LOADING, Unit.INSTANCE);
        }
    }

    /**
     * Get a upgrade level of quick loading.
     *
     * @param itemStack Item stack
     * @return Quick loading level
     */
    public static int getQuickLoading(ItemStack itemStack) {
        return GunContents.getQuickLoading(itemStack);
    }

    /**
     * Get a upgrade level of piercing.
     *
     * @param itemStack Item stack
     * @return Piercing level
     */
    public static int getPiercing(ItemStack itemStack) {
        return GunContents.getPiercing(itemStack);
    }

    /**
     * Get a number of barrels that the gun has.
     *
     * @param itemStack Item stack
     * @return Number of barrels
     */
    public static int getBarrelCount(ItemStack itemStack) {
        return GunContents.getBarrelCount(itemStack);
    }

    /**
     * Whether the gun can shoot multiple bullets at once.
     *
     * @param itemStack Item stack
     * @return True if the gun can shoot multiple bullets at once, false otherwise.
     */
    public static boolean canMultishot(ItemStack itemStack) {
        return getBarrelCount(itemStack) > Constants.ChiliBulletGun.CAPACITY_BASIC;
    }

    /**
     * Whether the gun has a bayonet attached or not.
     *
     * @param itemStack Item stack
     * @return True if the gun has a bayonet, false otherwise.
     */
    public static boolean hasBayonet(ItemStack itemStack) {
        // From v3 onward, a bayonet is considered attached if the gun item has the weapon data component
        return itemStack.has(DataComponents.WEAPON);
    }

    /**
     * Get shooting power from piercing level.
     * The shooting power refers to the initial velocity of the bullet.
     *
     * @param piercing Piercing level
     * @return Shooting power
     */
    public static float getShootingPower(int piercing) {
        if (piercing < 1) {
            return Constants.ChiliBulletGun.POWER_BASIC;
        } else {
            return Constants.ChiliBulletGun.POWER_PIERCING;
        }
    }

    /**
     * Get shooting power with piercing level applied that item stack has.
     * The shooting power refers to the initial velocity of the bullet.
     *
     * @param itemStack Item stack
     * @return Shooting power
     */
    public static float getShootingPower(ItemStack itemStack) {
        return getShootingPower(getPiercing(itemStack));
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
     * @param itemStack Item stack
     * @return Reload duration
     */
    public static int getReloadDuration(ItemStack itemStack) {
        int basicDuration = Constants.ChiliBulletGun.RELOAD_BASIC;

        if (getBarrelCount(itemStack) > Constants.ChiliBulletGun.CAPACITY_BASIC) {
            basicDuration = Constants.ChiliBulletGun.RELOAD_MULTISHOT;
        }

        if (hasBayonet(itemStack)) {
            basicDuration += Constants.ChiliBulletGun.RELOAD_BAYONET_ADDITIONAL;
        }

        int quickLoading = getQuickLoading(itemStack);

        if (quickLoading == 0) {
            return basicDuration;
        } else {
            return Math.max(0, basicDuration - Constants.ChiliBulletGun.RELOAD_PER_QUICK_CHARGE * quickLoading);
        }
    }

    /**
     * Attach a bayonet to the item stack by setting item attribute modifiers.
     *
     * @param itemStack    Item stack to attach bayonet
     * @param attackDamage attack damage
     * @param attackSpeed  attack speed
     */
    public static void attachBayonet(ItemStack itemStack, float attackDamage, float attackSpeed) {
        // Set attack damage and attack speed modifiers to the gun
        var itemAttributeModifiers = ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                Item.BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
        itemStack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemAttributeModifiers);
        // The gun loses 1 durability with each melee attack
        itemStack.set(DataComponents.WEAPON, new Weapon(1));
    }

    /**
     * Get a list of all guns available in creative mode.
     *
     * @return List of all guns available in creative mode
     */
    public static List<ItemStack> getCreativeGuns() {
        if (ModItems.GUN == null) {
            // If the gun item is null, return an empty list
            return List.of();
        }

        List<ItemStack> guns = new ArrayList<>();

        ItemStack gun = new ItemStack(ModItems.GUN);
        guns.add(gun);

        if (ModItems.UPGRADE_GUN_BARREL == null || ModItems.UPGRADE_GUN_BAYONET == null) {
            // If upgrade items are null, return only pistol
            return guns;
        }

        var upgradeGunBarrel = (UpgradeGunPartItem) ModItems.UPGRADE_GUN_BARREL;
        var upgradeGunBayonet = (UpgradeGunPartItem) ModItems.UPGRADE_GUN_BAYONET;


        ItemStack rifle = upgradeGunBarrel.upgrade(gun);
        ItemStack volleyGun = upgradeGunBarrel.upgrade(rifle);

        guns.add(rifle);
        guns.add(volleyGun);
        guns.add(upgradeGunBayonet.upgrade(gun));
        guns.add(upgradeGunBayonet.upgrade(rifle));
        guns.add(upgradeGunBayonet.upgrade(volleyGun));

        return guns;
    }

    private ChiliBulletGunHelper() {}
}
