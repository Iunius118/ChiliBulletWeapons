package com.github.iunius118.chilibulletweapons.advancements;

import com.github.iunius118.chilibulletweapons.Constants;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ThrewHotSauceTrigger extends SimpleCriterionTrigger<ThrewHotSauceTrigger.TriggerInstance> {

    @Override
    protected ThrewHotSauceTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate,
                                                                  DeserializationContext deserializationContext) {
        ItemPredicate itempredicate = ItemPredicate.fromJson(json.get("item"));
        return new TriggerInstance(predicate, itempredicate);
    }

    public void trigger(ServerPlayer shooter, ItemStack itemStack) {
        this.trigger(shooter, (triggerInstance) -> triggerInstance.matches(itemStack));
    }

    @Override
    public ResourceLocation getId() {
        return Constants.CriterionTriggers.THREW_HOT_SAUCE;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public TriggerInstance(ContextAwarePredicate player, ItemPredicate item) {
            super(Constants.CriterionTriggers.THREW_HOT_SAUCE, player);
            this.item = item;
        }

        public static TriggerInstance threwHotSauce(ItemPredicate item) {
            return new TriggerInstance(ContextAwarePredicate.ANY, item);
        }

        public static TriggerInstance threwHotSauce(ItemLike item) {
            return threwHotSauce(ItemPredicate.Builder.item().of(item).build());
        }

        public boolean matches(ItemStack item) {
            return this.item.matches(item);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonobject = super.serializeToJson(conditions);
            jsonobject.add("item", this.item.serializeToJson());
            return jsonobject;
        }
    }
}
