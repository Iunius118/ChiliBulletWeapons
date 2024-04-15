package com.github.iunius118.chilibulletweapons.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static final EntityType<ChiliArrow> CHILI_ARROW = EntityType.Builder.<ChiliArrow>of(ChiliArrow::new, MobCategory.MISC)
            .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
            .build(ChiliArrow.ID.toString());
    public static final EntityType<ChiliBullet> CHILI_BULLET = EntityType.Builder.<ChiliBullet>of(ChiliBullet::new, MobCategory.MISC)
            .sized(0.5F, 0.5F).clientTrackingRange(8).updateInterval(20)
            .build(ChiliBullet.ID.toString());
}
