package com.github.iunius118.chilibulletweapons.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final Map<Item, Float> COMPOSTABLES = new HashMap<>();

    // Plants
    public static final Item BULLET_CHILI = add(new Item(new Item.Properties()));
    public static final Item BULLET_CHILI_SACK = add(new Item(new Item.Properties()));

    // Weapons
    public static final Item CHILI_ARROW = add(new ChiliArrowItem(new Item.Properties()));
    public static final Item CHILI_BULLET = add(new Item(new Item.Properties()));
    public static final Item GUN = add(new ChiliBulletGunItem(new Item.Properties().stacksTo(1).durability(512)));
    public static final Item MACHINE_GUN = add(new ChiliBulletMachineGunItem(new Item.Properties().stacksTo(1).durability(64).fireResistant()));
    public static final Item UPGRADE_GUN_BAYONET = add(new UpgradeGunBayonetItem(new Item.Properties()));
    public static final Item UPGRADE_GUN_BARREL = add(new UpgradeGunBarrelItem(new Item.Properties()));
    public static final Item UPGRADE_GUN_MECHANISM = add(new UpgradeGunMechanismItem(new Item.Properties()));

    private static Item add(Item item) {
        ITEMS.add(item);
        return item;
    }

    static {
        // Add compostable items
        COMPOSTABLES.put(BULLET_CHILI, 0.3F);
    }

    public static List<ItemStack> getCreativeModeTabItems() {
        List<ItemStack> items = new ArrayList<>();

        for (Item item : ITEMS) {
            if (item == GUN) {
                items.addAll(ChiliBulletGunHelper.getCreativeGuns());
            } else {
                items.add(new ItemStack(item));
            }
        }

        return items;
    }
}
