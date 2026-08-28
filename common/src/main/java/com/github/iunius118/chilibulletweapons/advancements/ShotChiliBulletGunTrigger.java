package com.github.iunius118.chilibulletweapons.advancements;

import com.github.iunius118.chilibulletweapons.Constants;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ShotChiliBulletGunTrigger extends SimpleCriterionTrigger<ShotChiliBulletGunTrigger.TriggerInstance> {

    @Override
    protected ShotChiliBulletGunTrigger.TriggerInstance createInstance(
            JsonObject json, EntityPredicate.Composite entityPredicate, DeserializationContext deserializationContext) {
        ItemPredicate itempredicate = ItemPredicate.fromJson(json.get("item"));
        return new ShotChiliBulletGunTrigger.TriggerInstance(entityPredicate, itempredicate);
    }

    public void trigger(ServerPlayer shooter, ItemStack stack) {
        this.trigger(shooter, triggerInstance -> triggerInstance.matches(stack));
    }

    @Override
    public ResourceLocation getId() {
        return Constants.CriterionTriggers.SHOT_CHILI_BULLET_GUN;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public TriggerInstance(EntityPredicate.Composite player, ItemPredicate item) {
            super(Constants.CriterionTriggers.SHOT_CHILI_BULLET_GUN, player);
            this.item = item;
        }

        public static TriggerInstance shotChiliBulletGun(ItemPredicate item) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, item);
        }

        public static TriggerInstance shotChiliBulletGun(ItemLike item) {
            return shotChiliBulletGun(ItemPredicate.Builder.item().of(item).build());
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
