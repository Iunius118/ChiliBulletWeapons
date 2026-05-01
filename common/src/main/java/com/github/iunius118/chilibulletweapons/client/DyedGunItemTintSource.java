package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.component.ModDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/**
 * An item tint source that provides the colors for each layer of the dyed gun item model.
 * It returns the color value of the layer corresponding to the given tint index.
 *
 * @param tintIndex The index of the tint layer.
 */
public record DyedGunItemTintSource(int tintIndex) implements ItemTintSource {
    public static final MapCodec<DyedGunItemTintSource> MAP_CODEC =
            RecordCodecBuilder.mapCodec((i) ->
                    i.group(Codec.INT.fieldOf("tintIndex").forGetter(DyedGunItemTintSource::tintIndex))
                            .apply(i, DyedGunItemTintSource::new));

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
        var dyedGunColors = itemStack.get(ModDataComponents.DYED_GUN_COLORS);

        if (dyedGunColors == null) {
            return 0xFFFFFFFF;
        }

        return switch (tintIndex) {
            case 0 -> dyedGunColors.metalColor();
            case 1 -> dyedGunColors.woodColor();
            case 2 -> dyedGunColors.bladeColor();
            default -> 0xFFFFFFFF;
        };
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
