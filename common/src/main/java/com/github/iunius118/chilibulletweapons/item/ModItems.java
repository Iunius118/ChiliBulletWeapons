package com.github.iunius118.chilibulletweapons.item;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();

    // Plants
    public static final Item BULLET_CHILI = add(new Item(new Item.Properties()));
    public static final Item BULLET_CHILI_SACK = add(new Item(new Item.Properties()));

    // Weapons
    public static final Item CHILI_ARROW = add(new ChiliArrowItem(new Item.Properties()));
    public static final Item CHILI_BULLET = add(new Item(new Item.Properties()));
    public static final Item GUN = add(new ChiliBulletGunItem(new Item.Properties().stacksTo(1).durability(512)));
    public static final Item MACHINE_GUN = add(new ChiliBulletMachineGunItem(new Item.Properties().stacksTo(1).durability(64).fireResistant()));

    private static Item add(Item item) {
        ITEMS.add(item);
        return item;
    }
}
