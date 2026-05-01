package com.github.iunius118.chilibulletweapons.component;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGunHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

/**
 * Component with upgraded gun performance.
 *
 * @param quickLoading Quick loading value (0-3)
 * @param piercing Piercing value (0-5)
 * @param barrelCount Number of barrels (1-4)
 * @param showInTooltip Whether to show this component in the tooltip or not
 */
public record GunContents(int quickLoading, int piercing, int barrelCount, boolean showInTooltip) {
    public static final int DEFAULT_QUICK_LOADING = 0;
    public static final int DEFAULT_PIERCING = 0;
    public static final int DEFAULT_BARREL_COUNT = Constants.ChiliBulletGun.CAPACITY_BASIC;
    public static final GunContents DEFAULT = new GunContents(
            DEFAULT_QUICK_LOADING, DEFAULT_PIERCING, DEFAULT_BARREL_COUNT, true
    );
    public static final Codec<GunContents> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ExtraCodecs.NON_NEGATIVE_INT
                            .optionalFieldOf("quick_loading", DEFAULT_QUICK_LOADING)
                            .forGetter(GunContents::quickLoading),
                    ExtraCodecs.intRange(0, Constants.ChiliBulletGun.MAX_PIERCING)
                            .optionalFieldOf("piercing", DEFAULT_PIERCING)
                            .forGetter(GunContents::piercing),
                    ExtraCodecs.intRange(DEFAULT_BARREL_COUNT, Constants.ChiliBulletGun.CAPACITY_MULTISHOT)
                            .optionalFieldOf("barrel_count", DEFAULT_BARREL_COUNT)
                            .forGetter(GunContents::barrelCount),
                    Codec.BOOL.optionalFieldOf("show_in_tooltip", true)
                            .forGetter(GunContents::showInTooltip)
            ).apply(instance, GunContents::new)
    );
    public static final StreamCodec<ByteBuf, GunContents> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, GunContents::quickLoading,
            ByteBufCodecs.INT, GunContents::piercing,
            ByteBufCodecs.INT, GunContents::barrelCount,
            ByteBufCodecs.BOOL, GunContents::showInTooltip,
            GunContents::new
    );

    /**
     * Get the gun contents from the item itemStack.
     *
     * @param itemStack Item stack to get the GunContents from
     * @return GunContents instance from the itemStack, or the default instance if not present.
     */
    public static GunContents getOrDefault(ItemStack itemStack) {
        return itemStack.getOrDefault(ModDataComponents.GUN_CONTENTS, DEFAULT);
    }

	/**
     * Set this GunContents instance to the given item stack.
     *
	 * @param itemStack Item stack to set the GunContents to
	 * @return The same item stack with the GunContents set to it
	 */
    public ItemStack setTo(ItemStack itemStack) {
        itemStack.set(ModDataComponents.GUN_CONTENTS, this);
        return itemStack;
    }

	/**
     * Create a new ItemStack of the given item and set this GunContents instance to it.
     *
	 * @param item Item to create an ItemStack of and set this GunContents to
	 * @return A new ItemStack of the given item with this GunContents set to it
	 */
    public ItemStack setToStackOf(ItemLike item) {
        return setTo(new ItemStack(item));
    }

	/**
     * Create a new ItemStackTemplate of the given item and set this GunContents instance to it.
     *
	 * @param item Item to create an ItemStackTemplate of and set this GunContents to
	 * @return A new ItemStackTemplate of the given item with this GunContents set to it
	 */
    public ItemStackTemplate setToTemplateOf(Item item) {
        var dataComponentPatch = DataComponentPatch.builder()
                .set(ModDataComponents.GUN_CONTENTS, this)
                .build();
        return new ItemStackTemplate(item, dataComponentPatch);
    }

    /**
     * Get the quick loading value of the gun.
     *
     * @param itemStack Item stack
     * @return Quick loading value of the gun.
     */
    public static int getQuickLoading(ItemStack itemStack) {
        return getOrDefault(itemStack).quickLoading;
    }

    /**
     * Get the piercing value of the gun.
     *
     * @param itemStack Item stack
     * @return Piercing value of the gun.
     */
    public static int getPiercing(ItemStack itemStack) {
        return getOrDefault(itemStack).piercing;
    }

    /**
     * Get the number of barrels that the gun has.
     *
     * @param itemStack Item stack
     * @return Number of barrels of the gun.
     */
    public static int getBarrelCount(ItemStack itemStack) {
        return getOrDefault(itemStack).barrelCount;
    }

    /**
     * Set a new quick loading value for the gun.
     *
     * @param newQuickLoading New quick loading value (0-3)
     * @return A new GunContents instance with the updated quick loading value.
     */
    public GunContents setQuickLoading(int newQuickLoading) {
        return new GunContents(newQuickLoading, piercing, barrelCount, showInTooltip);
    }

    /**
     * Set a new piercing value for the gun.
     *
     * @param newPiercing New piercing value (0-5)
     * @return A new GunContents instance with the updated piercing value.
     */
    public GunContents setPiercing(int newPiercing) {
        return new GunContents(quickLoading, newPiercing, barrelCount, showInTooltip);
    }

    /**
     * Set a new barrel count for the gun.
     *
     * @param newBarrelCount New barrel count (1-4)
     * @return A new GunContents instance with the updated barrel count.
     */
    public GunContents setBarrelCount(int newBarrelCount) {
        return new GunContents(quickLoading, piercing, newBarrelCount, showInTooltip);
    }

    /**
     * Add the gun contents information to the tooltip.
     *
     * @param builder Consumer of components to add the tooltip to
     * @param tooltipFlag Tooltip flag to determine if the tooltip is advanced or not
     */
    public void addToTooltip(Consumer<Component> builder, TooltipFlag tooltipFlag) {
        if (this.showInTooltip) {
            if (quickLoading > 0) {
                // Add quick loading tooltip
                builder.accept(Component
                        .translatable(Constants.ChiliBulletGun.TOOLTIP_QUICK_LOAD, quickLoading)
                        .withStyle(ChatFormatting.GRAY));
            }

            if (tooltipFlag.isAdvanced()) {
                // Add barrel count and piercing tooltip
                String shootingPower = ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT
                        .format(ChiliBulletGunHelper.getShootingPower(piercing));
                builder.accept(Component
                        .translatable(Constants.ChiliBulletGun.TOOLTIP_BARREL_INFO,
                                shootingPower, piercing, barrelCount)
                        .withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }
}
