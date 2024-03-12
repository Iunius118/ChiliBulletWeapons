package com.github.iunius118.chilibulletweapons.sounds;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final SoundEvent CHILI_PEPPER_PICK_CHILI_PEPPERS = SoundEvent.createVariableRangeEvent(ChiliBulletWeapons.makeId("block.chili_pepper.pick_chili_peppers"));
    public static final SoundEvent GUN_SHOOT = SoundEvent.createVariableRangeEvent(ChiliBulletWeapons.makeId("item.chilibulletweapons.gun.shoot"));
    public static final SoundEvent GUN_ACTION_OPEN = SoundEvent.createVariableRangeEvent(ChiliBulletWeapons.makeId("item.chilibulletweapons.gun.action_open"));
    public static final SoundEvent GUN_ACTION_CLOSE = SoundEvent.createVariableRangeEvent(ChiliBulletWeapons.makeId("item.chilibulletweapons.gun.action_close"));
    public static final SoundEvent GUN_UPGRADE = SoundEvent.createVariableRangeEvent(ChiliBulletWeapons.makeId("item.chilibulletweapons.gun.upgrade"));
}
