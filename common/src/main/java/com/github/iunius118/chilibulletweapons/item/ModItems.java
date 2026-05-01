package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final Map<Item, Float> COMPOSTABLES = new HashMap<>();

    // Plants
    public static final Item BULLET_CHILI = add(new Item(createProperties(Constants.Items.BULLET_CHILI)));
    public static final Item BULLET_CHILI_SACK = add(new Item(createProperties(Constants.Items.BULLET_CHILI_SACK)));

    // Weapons
    public static final Item CHILI_ARROW = add(new ChiliArrowItem(createProperties(Constants.Items.CHILI_ARROW)));
    public static final Item CHILI_BULLET = add(new Item(createProperties(Constants.Items.CHILI_BULLET)));
    public static final Item GUN = add(new ChiliBulletGunItem(
            createProperties(Constants.Items.GUN)
                    .stacksTo(1)
                    .durability(512)
                    .component(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY)
                    .enchantable(Constants.ChiliBulletGun.ENCHANTMENT_VALUE)));
    public static final Item MACHINE_GUN = add(new ChiliBulletMachineGunItem(
            createProperties(Constants.Items.MACHINE_GUN)
                    .stacksTo(1)
                    .durability(64)
                    .component(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY)
                    .enchantable(Constants.ChiliBulletGun.ENCHANTMENT_VALUE)
                    .fireResistant()));
    public static final Item UPGRADE_GUN_BAYONET = add(new UpgradeGunBayonetItem(
            createProperties(Constants.Items.UPGRADE_GUN_BAYONET)));
    public static final Item UPGRADE_GUN_BARREL = add(new UpgradeGunBarrelItem(
            createProperties(Constants.Items.UPGRADE_GUN_BARREL)));
    public static final Item UPGRADE_GUN_MECHANISM = add(new UpgradeGunMechanismItem(
            createProperties(Constants.Items.UPGRADE_GUN_MECHANISM)));

    private static Item add(Item item) {
        ITEMS.add(item);
        return item;
    }

    private static Item.Properties createProperties(Identifier id) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id));
    }

    static {
        // Add compostable items
        COMPOSTABLES.put(BULLET_CHILI, 0.3F);
    }

    public static List<ItemStack> getCreativeModeTabItems() {
        List<ItemStack> items = new ArrayList<>();

        for (Item item : ITEMS) {
            // For the gun, add all its variants to the creative tab
            if (item == GUN) {
                items.addAll(ChiliBulletGunHelper.getCreativeGuns());
            } else {
                items.add(new ItemStack(item));
            }
        }

        return items;
    }
}
