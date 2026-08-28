package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.loot.CurvedChiliLootModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(DataGenerator generator) {
        super(generator, Constants.MOD_ID);
    }

    @Override
    protected void start() {
        add("curved_chili", new CurvedChiliLootModifier(getLootTableConditions("blocks/grass", 0.125F), 1));
    }

    private LootItemCondition[] getLootTableConditions(String id, float chance) {
        return new LootItemCondition[]{
                LootTableIdCondition.builder(new ResourceLocation(id)).build(),
                LootItemRandomChanceCondition.randomChance(chance).build()
        };
    }
}
