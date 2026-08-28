package com.github.iunius118.chilibulletweapons.advancements;

import com.github.iunius118.chilibulletweapons.Constants;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ExplodedChiliArrowTrigger extends SimpleCriterionTrigger<ExplodedChiliArrowTrigger.TriggerInstance> {

    @Override
    protected ExplodedChiliArrowTrigger.TriggerInstance createInstance(
            JsonObject json, EntityPredicate.Composite entityPredicate, DeserializationContext deserializationContext) {
        return new ExplodedChiliArrowTrigger.TriggerInstance(entityPredicate);
    }

    public void trigger(ServerPlayer shooter) {
        this.trigger(shooter, TriggerInstance::matches);
    }

    @Override
    public ResourceLocation getId() {
        return Constants.CriterionTriggers.EXPLODED_CHILI_ARROW;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(EntityPredicate.Composite player) {
            super(Constants.CriterionTriggers.EXPLODED_CHILI_ARROW, player);
        }

        public static TriggerInstance explodedChiliArrow() {
            return new ExplodedChiliArrowTrigger.TriggerInstance(EntityPredicate.Composite.ANY);
        }

        public boolean matches() {
            return true;
        }
    }
}
