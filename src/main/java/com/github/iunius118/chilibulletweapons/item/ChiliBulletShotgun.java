package com.github.iunius118.chilibulletweapons.item;

public class ChiliBulletShotgun extends ChiliBulletGun {
    public static final int CAPACITY = 2;
    public static final float POWER = 3F;
    public static final float INACCURACY = 5F;
    public static final int RELOAD_DURATION = 28;

    public ChiliBulletShotgun(Properties properties) {
        super(properties, CAPACITY, POWER, INACCURACY, RELOAD_DURATION);
    }
}
