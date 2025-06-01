package com.github.iunius118.chilibulletweapons.platform;

import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.github.iunius118.chilibulletweapons.mixin.ProjectileAccessorForge;
import com.github.iunius118.chilibulletweapons.platform.services.IPlatformHelper;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public void tickProjectile(ChiliBullet chiliBullet) {
        //Constants.LOG.info(getPlatformName());
        ProjectileAccessorForge projectileAccessor = (ProjectileAccessorForge) chiliBullet;

        if (!projectileAccessor.getHasBeenShot()) {
            chiliBullet.gameEvent(GameEvent.PROJECTILE_SHOOT, chiliBullet.getOwner());
            projectileAccessor.setHasBeenShot(true);
        }

        if (!projectileAccessor.getLeftOwner()) {
            projectileAccessor.setLeftOwner(projectileAccessor.invokeCheckLeftOwner());
        }
    }
}
