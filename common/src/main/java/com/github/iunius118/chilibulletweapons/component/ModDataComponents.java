package com.github.iunius118.chilibulletweapons.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;

public class ModDataComponents {
    /**
     * This gun is now loading ammo.
     * In chili bullet gun, the action is open.
     */
    public static final DataComponentType<Unit> LOADING = new DataComponentType.Builder<Unit>()
            .persistent(Unit.CODEC)
            .networkSynchronized(StreamCodec.unit(Unit.INSTANCE))
            .build();
    /**
     * This gun has had its barrel or mechanism upgraded.
     * It has a {@link GunContents} value representing the contents of the gun.
     */
    public static  final DataComponentType<GunContents> GUN_CONTENTS = new DataComponentType.Builder<GunContents>()
            .persistent(GunContents.CODEC)
            .networkSynchronized(GunContents.STREAM_CODEC)
            .build();
    /**
     * This gun has a bayonet attached.
     * It has a positive float value representing the damage value of the bayonet.
     */
    public static final DataComponentType<Float> BAYONETED = new DataComponentType.Builder<Float>()
            .persistent(ExtraCodecs.POSITIVE_FLOAT)
            .networkSynchronized(ByteBufCodecs.FLOAT)
            .build();
    /**
     * This gun cannot be upgraded with upgrade items.
     * This data component is only available for Chili Bullet Gun.
     */
    public static final DataComponentType<Unit> FIXED = new DataComponentType.Builder<Unit>()
            .persistent(Unit.CODEC)
            .networkSynchronized(StreamCodec.unit(Unit.INSTANCE))
            .build();
    /**
     * This gun has been dyed.
     * It has a {@link DyedGunColors} value representing the colors of the gun.
     */
    public static final DataComponentType<DyedGunColors> DYED_GUN_COLORS = new DataComponentType.Builder<DyedGunColors>()
            .persistent(DyedGunColors.CODEC)
            .networkSynchronized(DyedGunColors.STREAM_CODEC)
            .build();
}
