package com.github.iunius118.chilibulletweapons.component;

import com.github.iunius118.chilibulletweapons.Constants;
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
     * This gun can load bullets quickly.
     * It has a positive integer value representing the number of amount of loading time reduced.
     */
    public static final DataComponentType<Integer> QUICK_LOADING = new DataComponentType.Builder<Integer>()
            .persistent(ExtraCodecs.POSITIVE_INT)
            .networkSynchronized(ByteBufCodecs.VAR_INT)
            .build();
    /**
     * Bullets shot from this gun can penetrate entities.
     * It has a positive integer value representing the maximum number of entities that can be penetrated.
     */
    public static final DataComponentType<Integer> PIERCING = new DataComponentType.Builder<Integer>()
            .persistent(ExtraCodecs.intRange(1, Constants.ChiliBulletGun.MAX_PIERCING))
            .networkSynchronized(ByteBufCodecs.VAR_INT)
            .build();
    /**
     * This gun can shoot multiple bullets at once.
     * It has an integer value greater than or equal to 2 representing the number of bullets shot at once.
     */
    public static final DataComponentType<Integer> MULTISHOT = new DataComponentType.Builder<Integer>()
            .persistent(ExtraCodecs.intRange(2, Constants.ChiliBulletGun.CAPACITY_MULTISHOT))
            .networkSynchronized(ByteBufCodecs.VAR_INT)
            .build();
    /**
     * This gun has a bayonet attached.
     * It has a positive float value representing the damage value of the bayonet.
     */
    public static final DataComponentType<Float> BAYONETED = new DataComponentType.Builder<Float>()
            .persistent(ExtraCodecs.POSITIVE_FLOAT)
            .networkSynchronized(ByteBufCodecs.FLOAT)
            .build();
}
