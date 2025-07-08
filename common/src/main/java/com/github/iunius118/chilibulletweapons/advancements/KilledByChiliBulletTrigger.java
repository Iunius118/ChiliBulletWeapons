package com.github.iunius118.chilibulletweapons.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class KilledByChiliBulletTrigger extends SimpleCriterionTrigger<KilledByChiliBulletTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer shooter, int killedEntities) {
        this.trigger(shooter, instance -> instance.matches(killedEntities));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints killedEntities) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("killed_entities", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::killedEntities)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> killedByBullet(MinMaxBounds.Ints killedEntities) {
            return ModCriteriaTriggers.KILLED_BY_CHILI_BULLET
                    .createCriterion(new TriggerInstance(Optional.empty(), killedEntities));
        }

        public boolean matches(int killedEntities) {
            return this.killedEntities.matches(killedEntities);
        }
    }
}
