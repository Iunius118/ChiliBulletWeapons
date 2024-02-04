package com.github.iunius118.chilibulletweapons.sounds;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final SoundEvent CHILI_PEPPER_PICK_CHILI_PEPPERS = SoundEvent.createVariableRangeEvent(new ResourceLocation(ChiliBulletWeapons.MOD_ID, "block.chili_pepper.pick_chili_peppers"));
    public static final SoundEvent GUN_SHOOT = SoundEvent.createVariableRangeEvent(new ResourceLocation(ChiliBulletWeapons.MOD_ID, "item.chilibulletweapons.gun.shoot"));
    public static final SoundEvent GUN_ACTION_OPEN = SoundEvent.createVariableRangeEvent(new ResourceLocation(ChiliBulletWeapons.MOD_ID, "item.chilibulletweapons.gun.action_open"));
    public static final SoundEvent GUN_ACTION_CLOSE = SoundEvent.createVariableRangeEvent(new ResourceLocation(ChiliBulletWeapons.MOD_ID, "item.chilibulletweapons.gun.action_close"));
}
