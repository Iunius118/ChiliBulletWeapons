package com.github.iunius118.chilibulletweapons.mixin;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootItemBlockStatePropertyCondition.Builder.class)
public interface LootItemBlockStatePropertyConditionBuilderAccessor {
    @Accessor
    void setProperties(StatePropertiesPredicate properties);
}
