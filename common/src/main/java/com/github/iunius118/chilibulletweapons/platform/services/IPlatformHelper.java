package com.github.iunius118.chilibulletweapons.platform.services;

import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.github.iunius118.chilibulletweapons.mixin.ProjectileAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Platform-specific tick method for ChiliBullet projectiles.
     *
     * @param chiliBullet Instance of ChiliBullet
     */
    default void tickProjectile(ChiliBullet chiliBullet) {
        //Constants.LOG.info(getPlatformName());
        ProjectileAccessor projectileAccessor = (ProjectileAccessor) chiliBullet;

        if (!projectileAccessor.getHasBeenShot()) {
            chiliBullet.gameEvent(GameEvent.PROJECTILE_SHOOT, chiliBullet.getOwner());
            projectileAccessor.setHasBeenShot(true);
        }

        if (!projectileAccessor.getLeftOwner()) {
            projectileAccessor.setLeftOwner(projectileAccessor.invokeCheckLeftOwner());
        }
    }
}
