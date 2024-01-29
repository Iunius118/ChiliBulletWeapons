package com.github.iunius118.chilibulletweapons.item;

import net.minecraft.world.item.Item;

public class ModItems {
    // Plants
    public static final Item BULLET_CHILI = new Item(new Item.Properties());
    public static final Item CURVED_CHILI = new Item(new Item.Properties());
    public static final Item CHILI_SEEDS = new Item(new Item.Properties());
    // Weapons
    public static final Item CHILI_BULLET = new Item(new Item.Properties());
    public static final Item PISTOL = new ChiliBulletPistol(new Item.Properties().stacksTo(1).durability(512));
    public static final Item RIFLE = new ChiliBulletRifle(new Item.Properties().stacksTo(1).durability(512));
}
