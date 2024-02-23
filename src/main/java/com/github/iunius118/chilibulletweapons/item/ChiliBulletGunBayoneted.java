package com.github.iunius118.chilibulletweapons.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public class ChiliBulletGunBayoneted extends ChiliBulletGun {
    public static final String DESCRIPTION_PISTOL = "item.chilibulletweapons.bayoneted_gun.pistol";
    public static final String DESCRIPTION_RIFLE = "item.chilibulletweapons.bayoneted_gun.rifle";
    public static final String DESCRIPTION_SHOTGUN = "item.chilibulletweapons.bayoneted_gun.shotgun";
    public static final float ATTACK_DAMAGE = 5F;
    public static final float ATTACK_SPEED = -2.8F;
    public static final int RELOAD_ADDITIONAL = 2;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public ChiliBulletGunBayoneted(Properties properties) {
        super(properties);
        defaultModifiers = getDefaultModifiers();
    }

    @Override
    public int getReloadDuration(ItemStack itemStack) {
        return super.getReloadDuration(itemStack) + RELOAD_ADDITIONAL;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        // Wear out item with melee attacks.
        itemStack.hurtAndBreak(1, attacker, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? defaultModifiers : super.getAttributeModifiers(slot, stack);
    }

    private Multimap<Attribute, AttributeModifier> getDefaultModifiers() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", ATTACK_DAMAGE, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", ATTACK_SPEED, AttributeModifier.Operation.ADDITION));
        return builder.build();
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
