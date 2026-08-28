package com.github.iunius118.chilibulletweapons.advancements;

import com.github.iunius118.chilibulletweapons.Constants;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class HarvestedChiliPepperWithShearsTrigger
        extends SimpleCriterionTrigger<HarvestedChiliPepperWithShearsTrigger.TriggerInstance> {

    @Override
    protected HarvestedChiliPepperWithShearsTrigger.TriggerInstance createInstance(
            JsonObject json, EntityPredicate.Composite entityPredicate, DeserializationContext deserializationContext) {
        return new TriggerInstance(entityPredicate);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, TriggerInstance::matches);
    }

    @Override
    public ResourceLocation getId() {
        return Constants.CriterionTriggers.HARVESTED_CHILI_PEPPER_WITH_SHEARS;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(EntityPredicate.Composite player) {
            super(Constants.CriterionTriggers.HARVESTED_CHILI_PEPPER_WITH_SHEARS, player);
        }

        public static TriggerInstance harvestedChiliPepperWithShears() {
            return new TriggerInstance(EntityPredicate.Composite.ANY);
        }

        public boolean matches() {
            return true;
        }
    }
}
