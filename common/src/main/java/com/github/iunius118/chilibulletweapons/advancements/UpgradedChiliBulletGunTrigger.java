package com.github.iunius118.chilibulletweapons.advancements;

import com.github.iunius118.chilibulletweapons.Constants;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class UpgradedChiliBulletGunTrigger
        extends SimpleCriterionTrigger<UpgradedChiliBulletGunTrigger.TriggerInstance> {

    @Override
    protected UpgradedChiliBulletGunTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate,
                                                                  DeserializationContext deserializationContext) {
        ItemPredicate itempredicate = ItemPredicate.fromJson(json.get("item"));
        return new UpgradedChiliBulletGunTrigger.TriggerInstance(predicate, itempredicate);
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(stack));
    }

    @Override
    public ResourceLocation getId() {
        return Constants.CriterionTriggers.UPGRADED_CHILI_BULLET_GUN;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public TriggerInstance(ContextAwarePredicate player, ItemPredicate item) {
            super(Constants.CriterionTriggers.UPGRADED_CHILI_BULLET_GUN, player);
            this.item = item;
        }

        public static TriggerInstance upgradedChiliBulletGun(ItemPredicate item) {
            return new TriggerInstance(ContextAwarePredicate.ANY, item);
        }

        public static TriggerInstance upgradedChiliBulletGun(ItemLike item) {
            return upgradedChiliBulletGun(ItemPredicate.Builder.item().of(item).build());
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
