package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.advancements.ExplodedChiliArrowTrigger;
import com.github.iunius118.chilibulletweapons.advancements.KilledByChiliBulletTrigger;
import com.github.iunius118.chilibulletweapons.advancements.ShotChiliBulletGunTrigger;
import com.github.iunius118.chilibulletweapons.advancements.UpgradedChiliBulletGunTrigger;
import com.github.iunius118.chilibulletweapons.component.GunContents;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {

    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new ModAdvancementGenerator()));
    }

    private static class ModAdvancementGenerator implements AdvancementSubProvider {

        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
            var items = provider.lookupOrThrow(Registries.ITEM);

            // Main root
            final var curvedChiliItem = getItem(Constants.Items.CURVED_CHILI);
            AdvancementHolder root = Advancement.Builder.recipeAdvancement()
                    .display(getItem(Constants.Items.ICON_MAIN),
                            createTitle("main", "root"),
                            createDescription("main", "root"),
                            Identifier.withDefaultNamespace("block/orange_concrete_powder"),
                            AdvancementType.TASK, false, false, false)
                    .addCriterion("has_curved_chili", InventoryChangeTrigger.TriggerInstance.hasItems(curvedChiliItem))
                    .save(consumer, "%s:main/root".formatted(Constants.MOD_ID));

            // 1. Like a Bullet?
            AdvancementHolder bulletChili = addItemAdvancement(root, ModItems.BULLET_CHILI, AdvancementType.TASK,
                    new Item[]{ModItems.BULLET_CHILI}, "main", consumer);

            // 1-1. Boom!
            String explodedChiliArrowName = "exploded_chili_arrow";
            AdvancementHolder explodedChiliArrow = Advancement.Builder.recipeAdvancement()
                    .parent(bulletChili)
                    .display(ModItems.CHILI_ARROW,
                            createTitle("main", explodedChiliArrowName),
                            createDescription("main", explodedChiliArrowName),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion(explodedChiliArrowName,
                            ExplodedChiliArrowTrigger.TriggerInstance.explodedChiliArrow())
                    .save(consumer, "%s:%s/%s".formatted(Constants.MOD_ID, "main", explodedChiliArrowName));

            // 1-2. Bang!
            AdvancementHolder shotGun = addShotGunAdvancement(bulletChili, ModItems.GUN, AdvancementType.TASK,
                    new Item[]{ModItems.GUN, ModItems.MACHINE_GUN}, provider, "main", consumer);

            // 1-2-1. Master Gunsmith
            String upgradedGunName = "upgraded_gun";
            AdvancementHolder upgradedChiliBulletGun = Advancement.Builder.recipeAdvancement()
                    .parent(shotGun)
                    .display(ModItems.GUN,
                            createTitle("main", upgradedGunName),
                            createDescription("main", upgradedGunName),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("upgraded_barrel", UpgradedChiliBulletGunTrigger.TriggerInstance
                            .upgradedChiliBulletGun(items, ModItems.UPGRADE_GUN_BARREL))
                    .addCriterion("upgraded_bayonet", UpgradedChiliBulletGunTrigger.TriggerInstance
                            .upgradedChiliBulletGun(items, ModItems.UPGRADE_GUN_BAYONET))
                    .addCriterion("upgraded_mechanism", UpgradedChiliBulletGunTrigger.TriggerInstance
                            .upgradedChiliBulletGun(items, ModItems.UPGRADE_GUN_MECHANISM))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .save(consumer, "%s:%s/%s".formatted(Constants.MOD_ID, "main", upgradedGunName));

            // 1-2-1-1. Quad-sharp Shooter
            String killedByChiliBulletName = "killed_by_chili_bullet";
            AdvancementHolder killedByChiliBullet = Advancement.Builder.recipeAdvancement()
                    .parent(upgradedChiliBulletGun)
                    .display(GunContents.DEFAULT.setPiercing(Constants.ChiliBulletGun.BASIC_PIERCING)
                                    .setToTemplateOf(ModItems.GUN),
                            createTitle("main", killedByChiliBulletName),
                            createDescription("main", killedByChiliBulletName),
                            null,
                            AdvancementType.CHALLENGE, true, true, true)
                    .rewards(AdvancementRewards.Builder.experience(75))
                    .addCriterion(killedByChiliBulletName, KilledByChiliBulletTrigger.TriggerInstance
                            .killedByBullet(MinMaxBounds.Ints.atLeast(Constants.ChiliBulletGun.BASIC_PIERCING + 1)))
                    .save(consumer, "%s:%s/%s".formatted(Constants.MOD_ID, "main", killedByChiliBulletName));

            // 1-2-2. Handle With Care
            AdvancementHolder shotMachineGun = addShotGunAdvancement(shotGun, ModItems.MACHINE_GUN,
                    AdvancementType.TASK, new Item[]{ModItems.MACHINE_GUN}, provider, "main", consumer);

            // 1-2-2-1. Battle Has Changed
            AdvancementHolder machineGunWithMending =
                    addEnchantmentAdvancement(shotMachineGun, ModItems.MACHINE_GUN, AdvancementType.GOAL,
                            new Item[]{ModItems.MACHINE_GUN}, Enchantments.MENDING, 1, provider, "main", consumer);
        }

        private AdvancementHolder addItemAdvancement(AdvancementHolder parent, Item icon,
                                                     AdvancementType advancementType, Item[] requirements, String tab,
                                                     Consumer<AdvancementHolder> consumer) {
            String name = getItemId(requirements[0]).getPath();
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(icon,
                            createTitle(tab, name),
                            createDescription(tab, name),
                            null,
                            advancementType, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                builder.addCriterion("has_" + itemName,  InventoryChangeTrigger.TriggerInstance.hasItems(item));
            }

            return builder.save(consumer, "%s:%s/%s".formatted(Constants.MOD_ID, tab, name));
        }

        private AdvancementHolder addShotGunAdvancement(AdvancementHolder parent, Item icon,
                                                        AdvancementType advancementType, Item[] requirements,
                                                        HolderLookup.Provider provider, String tab,
                                                        Consumer<AdvancementHolder> consumer) {
            var items = provider.lookupOrThrow(Registries.ITEM);
            String name = "shot_" + getItemId(requirements[0]).getPath();
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(icon,
                            createTitle(tab, name),
                            createDescription(tab, name),
                            null,
                            advancementType, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                builder.addCriterion("shot_" + itemName,
                        ShotChiliBulletGunTrigger.TriggerInstance.shotChiliBulletGun(items, item));
            }

            return builder.save(consumer, "%s:%s/%s".formatted(Constants.MOD_ID, tab, name));
        }

        private AdvancementHolder addEnchantmentAdvancement(AdvancementHolder parent, Item icon,
                                                            AdvancementType advancementType, Item[] requirements,
                                                            ResourceKey<Enchantment> enchantment, int level,
                                                            HolderLookup.Provider provider, String tab,
                                                            Consumer<AdvancementHolder> consumer) {
            var items = provider.lookupOrThrow(Registries.ITEM);
            var enchantments = provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment);
            String name = "%s_%s_%s".formatted(
                    getItemId(requirements[0]).getPath(), getEnchantmentId(enchantments).getPath(), level);
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(icon,
                            createTitle(tab, name),
                            createDescription(tab, name),
                            null,
                            advancementType, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                ItemPredicate itemPredicate = ItemPredicate.Builder.item()
                        .of(items, item)
                        .withComponents(DataComponentMatchers.Builder.components()
                                .partial(DataComponentPredicates.ENCHANTMENTS,
                                        EnchantmentsPredicate.enchantments(List.of(
                                                new EnchantmentPredicate(enchantments, MinMaxBounds.Ints.atLeast(level))
                                                )
                                        )
                                )
                                .build())
                        .build();

                builder.addCriterion("has_" + itemName, InventoryChangeTrigger.TriggerInstance.hasItems(itemPredicate));
            }

            return builder.save(consumer, "%s:%s/%s".formatted(Constants.MOD_ID, tab, name));
        }

        private Item getItem(Identifier id) {
            return BuiltInRegistries.ITEM.getValue(id);
        }

        private Identifier getItemId(Item item) {
            return BuiltInRegistries.ITEM.getKey(item);
        }

        private Identifier getEnchantmentId(Holder<Enchantment> enchantment) {
            return enchantment.unwrapKey().orElseThrow().identifier();
        }

        private Component createTitle(String tab, String name) {
            return Component.translatable("advancements.%s.%s.%s.title".formatted(Constants.MOD_ID, tab, name));
        }

        private Component createDescription(String tab, String name) {
            return Component.translatable("advancements.%s.%s.%s.description".formatted(Constants.MOD_ID, tab, name));
        }
    }
}
