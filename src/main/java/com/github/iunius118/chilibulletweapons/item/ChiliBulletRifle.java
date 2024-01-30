package com.github.iunius118.chilibulletweapons.item;

public class ChiliBulletRifle extends ChiliBulletGun {
    public static final int CAPACITY = 1;
    public static final float POWER = 4F;
    public static final float INACCURACY = 0.5F;
    public static final int RELOAD_DURATION = 24;

    public ChiliBulletRifle(Properties properties) {
        super(properties, CAPACITY, POWER, INACCURACY, RELOAD_DURATION);
    }
}
