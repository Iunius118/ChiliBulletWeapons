package com.github.iunius118.chilibulletweapons.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Locale;

/**
 * Represents the colors of a dyed gun.
 *
 * @param metalRGB RGB value for the metal part
 * @param woodRGB RGB value for the wood part
 * @param bladeRGB RGB value for the blade part
 * @param showInTooltip Whether to show this component in the tooltip or not
 */
public record DyedGunColors(int metalRGB, int woodRGB, int bladeRGB, boolean showInTooltip) {
    public static final DyedGunColors DEFAULT = new DyedGunColors(0xFFFFFF, 0xFFFFFF, 0xFFFFFF, true);
    public static final Codec<DyedGunColors> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.optionalFieldOf("metal_rgb", 0xFFFFFF).forGetter(DyedGunColors::metalRGB),
                    Codec.INT.optionalFieldOf("wood_rgb", 0xFFFFFF).forGetter(DyedGunColors::woodRGB),
                    Codec.INT.optionalFieldOf("blade_rgb", 0xFFFFFF).forGetter(DyedGunColors::bladeRGB),
                    Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(DyedGunColors::showInTooltip)
            ).apply(instance, DyedGunColors::new)
    );
    public static final StreamCodec<ByteBuf, DyedGunColors> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DyedGunColors::metalRGB,
            ByteBufCodecs.INT, DyedGunColors::woodRGB,
            ByteBufCodecs.INT, DyedGunColors::bladeRGB,
            ByteBufCodecs.BOOL, DyedGunColors::showInTooltip,
            DyedGunColors::new
    );

    /**
     * Gets the dyed gun colors from the given item stack.
     *
     * @param stack Item stack to get the dyed gun colors from
     * @return The dyed gun colors, or the default if not present.
     */
    public static DyedGunColors getOrDefault(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.DYED_GUN_COLORS, DEFAULT);
    }

    /**
     * Returns the color of the metal part as an ARGB color.
     *
     * @return The ARGB color value.
     */
    public int metalColor() {
        return FastColor.ARGB32.opaque(metalRGB);
    }

    /**
     * Returns the color of the wood part as an ARGB color.
     *
     * @return The ARGB color value.
     */
    public int woodColor() {
        return FastColor.ARGB32.opaque(woodRGB);
    }

    /**
     * Returns the color of the blade part as an ARGB color.
     *
     * @return The ARGB color value.
     */
    public int bladeColor() {
        return FastColor.ARGB32.opaque(bladeRGB);
    }

    /**
     * Adds the dye colors to the tooltip.
     *
     * @param tooltipComponents List of tooltip components to add to
     * @param tooltipFlag Tooltip flag indicating whether to show advanced tooltips
     */
    public void addToTooltip(List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (this.showInTooltip) {
            if (tooltipFlag.isAdvanced()) {
                tooltipComponents.add(Component.translatable("item.color", String.format(Locale.ROOT, "#%06X", metalRGB)).withStyle(ChatFormatting.DARK_GRAY));
                tooltipComponents.add(Component.translatable("item.color", String.format(Locale.ROOT, "#%06X", woodRGB)).withStyle(ChatFormatting.DARK_GRAY));
                tooltipComponents.add(Component.translatable("item.color", String.format(Locale.ROOT, "#%06X", bladeRGB)).withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }
}
