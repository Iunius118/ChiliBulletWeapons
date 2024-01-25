package com.github.iunius118.chilibulletweapons.sounds;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final SoundEvent PISTOL_SHOOT = SoundEvent.createVariableRangeEvent(new ResourceLocation(ChiliBulletWeapons.MOD_ID, "item.chilibulletweapons.pistol.shoot"));
    public static final SoundEvent PISTOL_ACTION_OPEN = SoundEvent.createVariableRangeEvent(new ResourceLocation(ChiliBulletWeapons.MOD_ID, "item.chilibulletweapons.pistol.action_open"));
    public static final SoundEvent PISTOL_ACTION_CLOSE = SoundEvent.createVariableRangeEvent(new ResourceLocation(ChiliBulletWeapons.MOD_ID, "item.chilibulletweapons.pistol.action_close"));
}
