package com.github.iunius118.chilibulletweapons.platform.services;

public interface IChiliBulletWeaponsConfig {

    /**
     * Checks if a single target can be damaged multiple times at once by bullets shot from a volley gun.
     * If set to false, a single target will take damage from only one bullet at the same time.
     *
     * @return True if a single target can be damaged multiple times at once by bullets shot from a volley gun, false otherwise.
     */
    boolean canMultishotMultiHit();

    /**
     * Gets the multiplier of the explosive power of bullet chili arrows.
     *
     * @return The multiplier of the explosive power of bullet chili arrows.
     */
    float getChiliArrowDamageMultiplier();

    /**
     * Gets the base damage amount of a chili bullet shot from a gun.
     * If set to 0.0, chili bullets will do no damage.
     *
     * @return The base damage amount of a chili bullet shot from a gun.
     */
    double getChiliBulletBaseDamage();
}
