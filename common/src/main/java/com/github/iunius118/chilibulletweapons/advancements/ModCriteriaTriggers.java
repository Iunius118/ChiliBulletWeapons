package com.github.iunius118.chilibulletweapons.advancements;

import net.minecraft.advancements.CriterionTrigger;

import java.util.ArrayList;
import java.util.List;

public class ModCriteriaTriggers {
    public static final List<CriterionTrigger<?>> CRITERION_TRIGGERS = new ArrayList<>();

    public static final ThrewHotSauceTrigger THREW_HOT_SAUCE = add(new ThrewHotSauceTrigger());
    public static final HarvestedChiliPepperWithShearsTrigger HARVESTED_CHILI_PEPPER_WITH_SHEARS =
            add(new HarvestedChiliPepperWithShearsTrigger());
    public static final ExplodedChiliArrowTrigger EXPLODED_CHILI_ARROW = add(new ExplodedChiliArrowTrigger());
    public static final ShotChiliBulletGunTrigger SHOT_CHILI_BULLET_GUN = add(new ShotChiliBulletGunTrigger());
    public static final UpgradedChiliBulletGunTrigger UPGRADED_CHILI_BULLET_GUN =
            add(new UpgradedChiliBulletGunTrigger());
    public static final KilledByChiliBulletTrigger KILLED_BY_CHILI_BULLET = add(new KilledByChiliBulletTrigger());

    private static <T extends CriterionTrigger<?>> T add(T criterionTrigger) {
        CRITERION_TRIGGERS.add(criterionTrigger);
        return criterionTrigger;
    }
}
