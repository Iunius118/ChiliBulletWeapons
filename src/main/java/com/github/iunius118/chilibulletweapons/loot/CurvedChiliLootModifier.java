package com.github.iunius118.chilibulletweapons.loot;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CurvedChiliLootModifier extends LootModifier {
    public static final Supplier<Codec<CurvedChiliLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).and(
            Codec.INT.fieldOf("count").forGetter(m -> m.bonusCount)).apply(inst, CurvedChiliLootModifier::new)));

    private final int bonusCount;
    private final LootItemFunction lootItemFunction;

    public CurvedChiliLootModifier(LootItemCondition[] conditionsIn, int bonusCount) {
        super(conditionsIn);
        this.bonusCount = bonusCount;
        lootItemFunction = ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, bonusCount).build();
    }

    @Override
    @NotNull
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        var itemStack = lootItemFunction.apply(new ItemStack(ModItems.CURVED_CHILI), context);
        generatedLoot.add(itemStack);
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
