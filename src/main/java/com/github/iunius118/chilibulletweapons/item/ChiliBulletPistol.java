package com.github.iunius118.chilibulletweapons.item;

public class ChiliBulletPistol extends ChiliBulletGun {
    public static final int CAPACITY = 1;
    public static final float POWER = 3F;
    public static final float INACCURACY = 1F;
    public static final int RELOAD_DURATION = 20;

    public ChiliBulletPistol(Properties properties) {
        super(properties, CAPACITY, POWER, INACCURACY, RELOAD_DURATION);
    }
}
