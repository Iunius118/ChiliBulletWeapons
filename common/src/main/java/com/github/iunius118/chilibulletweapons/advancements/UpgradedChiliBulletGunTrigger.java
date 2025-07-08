package com.github.iunius118.chilibulletweapons.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

public class UpgradedChiliBulletGunTrigger extends SimpleCriterionTrigger<UpgradedChiliBulletGunTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, instance -> instance.matches(stack));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        ItemPredicate.CODEC.optionalFieldOf("item").forGetter(TriggerInstance::item)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> upgradedChiliBulletGun(ItemPredicate item) {
            return ModCriteriaTriggers.UPGRADED_CHILI_BULLET_GUN
                    .createCriterion(new TriggerInstance(Optional.empty(), Optional.of(item)));
        }

        public static Criterion<TriggerInstance> upgradedChiliBulletGun(ItemLike item) {
            return upgradedChiliBulletGun(ItemPredicate.Builder.item().of(item).build());
        }

        public boolean matches(ItemStack item) {
            return this.item.isEmpty() || this.item.get().test(item);
        }
    }
}
