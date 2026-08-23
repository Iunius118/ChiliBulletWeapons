package com.github.iunius118.chilibulletweapons.item;

import com.github.iunius118.chilibulletweapons.Constants;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public class ChiliBulletGunBayoneted extends ChiliBulletGun {
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public ChiliBulletGunBayoneted(Properties properties) {
        super(properties);
        defaultModifiers = getDefaultModifiers();
    }

    @Override
    public int getReloadDuration(ItemStack itemStack) {
        return super.getReloadDuration(itemStack) + Constants.ChiliBulletGun.RELOAD_BAYONETED_ADDITIONAL;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        // Wear out item with melee attacks.
        this.hurtAndBreak(itemStack, 1, attacker, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    private Multimap<Attribute, AttributeModifier> getDefaultModifiers() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                Constants.ChiliBulletGun.BAYONET_ATTACK_DAMAGE, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(
                BASE_ATTACK_SPEED_UUID, "Weapon modifier",
                Constants.ChiliBulletGun.BAYONET_ATTACK_SPEED, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    @Override
    public String getDescriptionId(ItemStack itemStack) {
        // Change item display name by enchantment
        if (getMultishotLevel(itemStack) != 0) {
            return Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_VOLLEY_GUN;
        } else if (getPiercingLevel(itemStack) > 0) {
            return Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_RIFLE;
        } else {
            return Constants.ChiliBulletGun.DESCRIPTION_BAYONETED_PISTOL;
        }
    }
}
