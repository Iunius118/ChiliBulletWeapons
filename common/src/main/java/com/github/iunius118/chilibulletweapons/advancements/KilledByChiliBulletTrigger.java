package com.github.iunius118.chilibulletweapons.advancements;

import com.github.iunius118.chilibulletweapons.Constants;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class KilledByChiliBulletTrigger extends SimpleCriterionTrigger<KilledByChiliBulletTrigger.TriggerInstance> {

    @Override
    public KilledByChiliBulletTrigger.TriggerInstance createInstance(
            JsonObject json, EntityPredicate.Composite entityPredicate, DeserializationContext deserializationContext) {
        MinMaxBounds.Ints killedEntities = MinMaxBounds.Ints.fromJson(json.get("killed_entities"));
        return new KilledByChiliBulletTrigger.TriggerInstance(entityPredicate, killedEntities);
    }

    public void trigger(ServerPlayer shooter, int killedEntities) {
        this.trigger(shooter, triggerInstance -> triggerInstance.matches(killedEntities));
    }

    @Override
    public ResourceLocation getId() {
        return Constants.CriterionTriggers.KILLED_BY_CHILI_BULLET;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final MinMaxBounds.Ints killedEntities;

        public TriggerInstance(EntityPredicate.Composite player, MinMaxBounds.Ints killedEntities) {
            super(Constants.CriterionTriggers.KILLED_BY_CHILI_BULLET, player);
            this.killedEntities = killedEntities;
        }

        public static TriggerInstance killedByBullet(MinMaxBounds.Ints killedEntities) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, killedEntities);
        }

        public boolean matches(int killedEntities) {
            return this.killedEntities.matches(killedEntities);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonobject = super.serializeToJson(conditions);
            jsonobject.add("killed_entities", this.killedEntities.serializeToJson());
            return jsonobject;
        }
    }
}
