package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.advancements.KilledByChiliBulletTrigger;
import com.github.iunius118.chilibulletweapons.advancements.ShotChiliBulletGunTrigger;
import com.github.iunius118.chilibulletweapons.advancements.UpgradedChiliBulletGunTrigger;
import com.github.iunius118.chilibulletweapons.component.GunContents;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {

    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new ModAdvancementGenerator()));
    }

    private static class ModAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
            final Item curvedChiliItem = getItem(Constants.Items.CURVED_CHILI);

            // Main root
            AdvancementHolder root = Advancement.Builder.recipeAdvancement()
                    .display(getItem(Constants.Items.ICON_MAIN),
                            Component.translatable("advancements.%s.main.root.title".formatted(Constants.MOD_ID)),
                            Component.translatable("advancements.%s.main.root.description".formatted(Constants.MOD_ID)),
                            ResourceLocation.withDefaultNamespace("textures/block/orange_concrete_powder.png"),
                            AdvancementType.TASK, false, false, false)
                    .addCriterion("has_curved_chili", InventoryChangeTrigger.TriggerInstance.hasItems(curvedChiliItem))
                    .save(consumer, "%s:main/root".formatted(Constants.MOD_ID));

            // 1. Hot Topic
            AdvancementHolder curvedChili = addItemAdvancement(root, curvedChiliItem, AdvancementType.TASK,
                    new Item[]{curvedChiliItem}, "main", consumer);

            // 1-1. Like a Bullet?
            AdvancementHolder bulletChili = addItemAdvancement(curvedChili, ModItems.BULLET_CHILI, AdvancementType.TASK,
                    new Item[]{ModItems.BULLET_CHILI}, "main", consumer);

            // 1-1-1. Bang!
            AdvancementHolder shotGun = addShotGunAdvancement(bulletChili, ModItems.GUN, AdvancementType.TASK,
                    new Item[]{ModItems.GUN, ModItems.MACHINE_GUN}, "main", consumer);

            // 1-1-1-1. Master Gunsmith
            AdvancementHolder upgradedChiliBulletGun = Advancement.Builder.recipeAdvancement()
                    .parent(shotGun)
                    .display(ModItems.GUN,
                            Component.translatable("advancements.%s.%s.%s.title".formatted(Constants.MOD_ID, "main", "upgraded_gun")),
                            Component.translatable("advancements.%s.%s.%s.description".formatted(Constants.MOD_ID, "main", "upgraded_gun")),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("upgraded_barrel", UpgradedChiliBulletGunTrigger.TriggerInstance.upgradedChiliBulletGun(ModItems.UPGRADE_GUN_BARREL))
                    .addCriterion("upgraded_bayonet", UpgradedChiliBulletGunTrigger.TriggerInstance.upgradedChiliBulletGun(ModItems.UPGRADE_GUN_BAYONET))
                    .addCriterion("upgraded_mechanism", UpgradedChiliBulletGunTrigger.TriggerInstance.upgradedChiliBulletGun(ModItems.UPGRADE_GUN_MECHANISM))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .save(consumer, "%s:%s/%s".formatted(Constants.MOD_ID, "main", "upgraded_gun"));

            // 1-1-1-1-1. Quad-sharp Shooter
            AdvancementHolder killedByChiliBullet = Advancement.Builder.recipeAdvancement()
                    .parent(upgradedChiliBulletGun)
                    .display(GunContents.DEFAULT.setPiercing(Constants.ChiliBulletGun.BASIC_PIERCING).setTo(new ItemStack(ModItems.GUN)),
                            Component.translatable("advancements.%s.%s.%s.title".formatted(Constants.MOD_ID, "main", "killed_by_chili_bullet")),
                            Component.translatable("advancements.%s.%s.%s.description".formatted(Constants.MOD_ID, "main", "killed_by_chili_bullet")),
                            null,
                            AdvancementType.CHALLENGE, true, true, true)
                    .rewards(AdvancementRewards.Builder.experience(75))
                    .addCriterion("killed_by_chili_bullet", KilledByChiliBulletTrigger.TriggerInstance
                            .killedByBullet(MinMaxBounds.Ints.atLeast(Constants.ChiliBulletGun.BASIC_PIERCING + 1)))
                    .save(consumer, "%s:%s/%s".formatted(Constants.MOD_ID, "main", "killed_by_chili_bullet"));

            // 1-1-1-2. Handle With Care
            AdvancementHolder shotMachineGun = addShotGunAdvancement(shotGun, ModItems.MACHINE_GUN, AdvancementType.TASK,
                    new Item[]{ModItems.MACHINE_GUN}, "main", consumer);

            // 1-1-1-2-1. Battle Has Changed
            AdvancementHolder machineGunWithMending = addEnchantmentAdvancement(shotMachineGun, ModItems.MACHINE_GUN, AdvancementType.GOAL,
                    new Item[]{ModItems.MACHINE_GUN}, Enchantments.MENDING, 1, provider, "main", consumer);
        }

        private AdvancementHolder addItemAdvancement(AdvancementHolder parent, Item icon, AdvancementType advancementType,
                                                     Item[] requirements, String tab, Consumer<AdvancementHolder> consumer) {
            String name = getItemId(requirements[0]).getPath();
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(icon,
                            Component.translatable("advancements.%s.%s.%s.title".formatted(Constants.MOD_ID, tab, name)),
                            Component.translatable("advancements.%s.%s.%s.description".formatted(Constants.MOD_ID, tab, name)),
                            null,
                            advancementType, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                builder.addCriterion("has_" + itemName,  InventoryChangeTrigger.TriggerInstance.hasItems(item));
            }

            return builder.save(consumer, "%s:%s/%s".formatted(Constants.MOD_ID, tab, name));
        }

        private AdvancementHolder addShotGunAdvancement(AdvancementHolder parent, Item icon, AdvancementType advancementType,
                                                     Item[] requirements, String tab, Consumer<AdvancementHolder> consumer) {
            String name = "shot_" + getItemId(requirements[0]).getPath();
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(icon,
                            Component.translatable("advancements.%s.%s.%s.title".formatted(Constants.MOD_ID, tab, name)),
                            Component.translatable("advancements.%s.%s.%s.description".formatted(Constants.MOD_ID, tab, name)),
                            null,
                            advancementType, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                builder.addCriterion("shot_" + itemName, ShotChiliBulletGunTrigger.TriggerInstance.shotChiliBulletGun(item));
            }

            return builder.save(consumer, "%s:%s/%s".formatted(Constants.MOD_ID, tab, name));
        }

        private AdvancementHolder addEnchantmentAdvancement(AdvancementHolder parent, Item icon, AdvancementType advancementType,
                                                                 Item[] requirements, ResourceKey<Enchantment> enchantment, int level,
                                                                 HolderLookup.Provider lookupProvider, String tab, Consumer<AdvancementHolder> consumer) {
            var enchantmentHolder = lookupProvider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment);
            String name = getItemId(requirements[0]).getPath() + "_" + getEnchantmentId(enchantmentHolder).getPath() + "_" + level;
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(icon,
                            Component.translatable("advancements.%s.%s.%s.title".formatted(Constants.MOD_ID, tab, name)),
                            Component.translatable("advancements.%s.%s.%s.description".formatted(Constants.MOD_ID, tab, name)),
                            null,
                            advancementType, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                ItemPredicate itemPredicate = ItemPredicate.Builder.item()
                        .of(item)
                        .withSubPredicate(ItemSubPredicates.ENCHANTMENTS,
                                ItemEnchantmentsPredicate.enchantments(List.of(
                                        new EnchantmentPredicate(enchantmentHolder, MinMaxBounds.Ints.atLeast(level)))))
                        .build();

                builder.addCriterion("has_" + itemName, InventoryChangeTrigger.TriggerInstance.hasItems(itemPredicate));
            }

            return builder.save(consumer, "%s:%s/%s".formatted(Constants.MOD_ID, tab, name));
        }

        private Item getItem(ResourceLocation id) {
            return BuiltInRegistries.ITEM.get(id);
        }

        private ResourceLocation getItemId(Item item) {
            return BuiltInRegistries.ITEM.getKey(item);
        }

        private ResourceLocation getEnchantmentId(Holder<Enchantment> enchantment) {
            return enchantment.unwrapKey().orElseThrow().location();
        }
    }
}
