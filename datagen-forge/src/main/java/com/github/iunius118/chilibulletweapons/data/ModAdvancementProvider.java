package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.advancements.*;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGun;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletMachineGun;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {

    public ModAdvancementProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, existingFileHelper);
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> saver, ExistingFileHelper fileHelper) {
        new ModMainAdvancements().accept(saver);
    }

    public static class ModMainAdvancements implements Consumer<Consumer<Advancement>> {
        public void accept(Consumer<Advancement> saver) {
            final Item curvedChiliItem = getItem(Constants.Items.CURVED_CHILI.location());

            // Main root
            Advancement root = Advancement.Builder.advancement()
                    .display(getItem(Constants.Items.ICON_MAIN.location()),
                            getTranslatableTitle("main", "root"),
                            getTranslatableDescription("main", "root"),
                            new ResourceLocation("textures/block/orange_concrete_powder.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion("has_curved_chili", InventoryChangeTrigger.TriggerInstance.hasItems(curvedChiliItem))
                    .save(saver, getModAdvancementID("main", "root"));

            // 1. Hot Topic
            Advancement curvedChili = addItemAdvancement(root, ModItems.CURVED_CHILI, FrameType.TASK,
                    List.of(ModItems.CURVED_CHILI), "main", saver);

            // 1-1. Be Gentle
            String harvestedChiliPepperWithShearsName = "harvested_chili_pepper_with_shears";
            Advancement harvestedChiliPepperWithShears = Advancement.Builder.advancement()
                    .parent(curvedChili)
                    .display(Items.SHEARS,
                            getTranslatableTitle("main", harvestedChiliPepperWithShearsName),
                            getTranslatableDescription("main", harvestedChiliPepperWithShearsName),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion(harvestedChiliPepperWithShearsName,
                            HarvestedChiliPepperWithShearsTrigger.TriggerInstance.harvestedChiliPepperWithShears())
                    .save(saver, getModAdvancementID("main", harvestedChiliPepperWithShearsName));

            // 1-2. Let's Go Halves
            List<Item> halfSandwiches = List.of(
                    ModItems.HALF_CHILI_CHICKEN_SANDWICH,
                    ModItems.HALF_CHILI_FISH_SANDWICH,
                    ModItems.HALF_CHILI_MEAT_SANDWICH,
                    ModItems.HALF_CHILI_POTATO_SANDWICH);
            Advancement halfSandwich = addItemAdvancement(curvedChili, ModItems.HALF_CHILI_POTATO_SANDWICH,
                    FrameType.TASK, "half_sandwich", halfSandwiches, "main", saver);

            // 1-3. Non-Lethal?
            String threwHotSauceName = "threw_hot_sauce";
            Advancement threwHotSauce = Advancement.Builder.advancement()
                    .parent(curvedChili)
                    .display(ModItems.HOT_SAUCE,
                            getTranslatableTitle("main", threwHotSauceName),
                            getTranslatableDescription("main", threwHotSauceName),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("threw_hot_sauce",
                            ThrewHotSauceTrigger.TriggerInstance.threwHotSauce(ModItems.HOT_SAUCE))
                    .addCriterion("threw_green_hot_sauce",
                            ThrewHotSauceTrigger.TriggerInstance.threwHotSauce(ModItems.GREEN_HOT_SAUCE))
                    .requirements(RequirementsStrategy.OR)
                    .save(saver, getModAdvancementID("main", threwHotSauceName));

            // 2. Like a Bullet?
            Advancement bulletChili = addItemAdvancement(root, ModItems.BULLET_CHILI, FrameType.TASK,
                    List.of(ModItems.BULLET_CHILI), "main", saver);

            // 2-1. Boom!
            String explodedChiliArrowName = "exploded_chili_arrow";
            Advancement explodedChiliArrow = Advancement.Builder.advancement()
                    .parent(bulletChili)
                    .display(ModItems.CHILI_ARROW,
                            getTranslatableTitle("main", explodedChiliArrowName),
                            getTranslatableDescription("main", explodedChiliArrowName),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion(explodedChiliArrowName,
                            ExplodedChiliArrowTrigger.TriggerInstance.explodedChiliArrow())
                    .save(saver, getModAdvancementID("main", explodedChiliArrowName));

            // 2-2. Bang!
            Advancement shotGun = addShotGunAdvancement(bulletChili, ModItems.GUN, FrameType.TASK,
                    List.of(ModItems.GUN, ModItems.BAYONETED_GUN, ModItems.MACHINE_GUN), "main", saver);

            // 2-2-1. Master Gunsmith
            String upgradedGunName = "upgraded_gun";
            Advancement upgradedChiliBulletGun = Advancement.Builder.advancement()
                    .parent(shotGun)
                    .display(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.QUICK_CHARGE),
                            getTranslatableTitle("main", upgradedGunName),
                            getTranslatableDescription("main", upgradedGunName),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("upgraded_barrel", UpgradedChiliBulletGunTrigger.TriggerInstance
                            .upgradedChiliBulletGun(ModItems.UPGRADE_GUN_BARREL))
                    .addCriterion("upgraded_bayonet", UpgradedChiliBulletGunTrigger.TriggerInstance
                            .upgradedChiliBulletGun(ModItems.UPGRADE_GUN_BAYONET))
                    .addCriterion("upgraded_mechanism", UpgradedChiliBulletGunTrigger.TriggerInstance
                            .upgradedChiliBulletGun(ModItems.UPGRADE_GUN_MECHANISM))
                    .requirements(RequirementsStrategy.OR)
                    .save(saver, getModAdvancementID("main", upgradedGunName));

            // 2-2-1-1. Quad-sharp Shooter
            String killedByChiliBulletName = "killed_by_chili_bullet";
            Advancement killedByChiliBullet = Advancement.Builder.advancement()
                    .parent(upgradedChiliBulletGun)
                    .display(ChiliBulletGun.enchant(ModItems.GUN, Enchantments.PIERCING),
                            getTranslatableTitle("main", killedByChiliBulletName),
                            getTranslatableDescription("main", killedByChiliBulletName),
                            null,
                            FrameType.CHALLENGE, true, true, true)
                    .rewards(AdvancementRewards.Builder.experience(75))
                    .addCriterion(killedByChiliBulletName, KilledByChiliBulletTrigger.TriggerInstance
                            .killedByBullet(MinMaxBounds.Ints.atLeast(Constants.ChiliBulletGun.BASIC_PIERCING + 1)))
                    .save(saver, getModAdvancementID("main", killedByChiliBulletName));

            // 2-2-2. Handle With Care
            Advancement shotMachineGun = addShotGunAdvancement(shotGun, ModItems.MACHINE_GUN, FrameType.TASK,
                    List.of(ModItems.MACHINE_GUN), "main", saver);

            // 2-2-2-1. Battle Has Changed
            Advancement machineGunWithMending = addEnchantmentAdvancement(shotMachineGun,
                    ChiliBulletMachineGun.enchant(ModItems.MACHINE_GUN, Enchantments.MENDING),
                    FrameType.GOAL, List.of(ModItems.MACHINE_GUN), Enchantments.MENDING, 1, "main", saver);
        }

        /**
         * Adds an advancement that is achieved when any one of the items in the given list is obtained.
         */
        private Advancement addItemAdvancement(Advancement parent, ItemLike icon, FrameType frameType, String name,
                                               List<Item> requirements, String tab, Consumer<Advancement> saver) {
            var displayInfo = new DisplayInfo(
                    new ItemStack(icon.asItem()),
                    getTranslatableTitle(tab, name),
                    getTranslatableDescription(tab, name),
                    null,
                    frameType, true, true, false);
            Advancement.Builder builder = Advancement.Builder.advancement()
                    .parent(parent)
                    .display(displayInfo)
                    .requirements(RequirementsStrategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                builder.addCriterion("has_" + itemName,  InventoryChangeTrigger.TriggerInstance.hasItems(item));
            }

            return builder.save(saver, getModAdvancementID(tab, name));
        }

        /**
         * Adds an advancement that is achieved when any one of the items in the given list is obtained.
         */
        private Advancement addItemAdvancement(Advancement parent, Item icon, FrameType frameType,
                                               List<Item> requirements, String tab, Consumer<Advancement> saver) {
            return addItemAdvancement(parent, icon, frameType, getItemId(requirements.get(0)).getPath(),
                    requirements, tab, saver);
        }

        private Advancement addShotGunAdvancement(Advancement parent, Item icon, FrameType frameType,
                                                  List<Item> requirements, String tab, Consumer<Advancement> saver) {
            String name = "shot_" + getItemId(requirements.get(0)).getPath();
            Advancement.Builder builder = Advancement.Builder.advancement()
                    .parent(parent)
                    .display(icon,
                            getTranslatableTitle(tab, name),
                            getTranslatableDescription(tab, name),
                            null,
                            frameType, true, true, false)
                    .requirements(RequirementsStrategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                builder.addCriterion("shot_" + itemName,
                        ShotChiliBulletGunTrigger.TriggerInstance.shotChiliBulletGun(item));
            }

            return builder.save(saver, getModAdvancementID(tab, name));
        }

        /**
         * Adds an advancement that is achieved when any one of the items in the given list with a specific enchantment is obtained.
         */
        private Advancement addEnchantmentAdvancement(Advancement parent, ItemStack icon, FrameType advancementType,
                                                      List<Item> requirements, Enchantment enchantment, int level,
                                                      String tab, Consumer<Advancement> saver) {
            String name = "%s_%s_%d".formatted(getItemId(requirements.get(0)).getPath(),
                    getEnchantmentId(enchantment).getPath(), level);
            Advancement.Builder builder = Advancement.Builder.advancement()
                    .parent(parent)
                    .display(icon,
                            getTranslatableTitle(tab, name),
                            getTranslatableDescription(tab, name),
                            null,
                            advancementType, true, true, false)
                    .requirements(RequirementsStrategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                ItemPredicate itemPredicate = ItemPredicate.Builder.item()
                        .of(item)
                        .hasEnchantment(new EnchantmentPredicate(enchantment, MinMaxBounds.Ints.atLeast(level)))
                        .build();

                builder.addCriterion("has_" + itemName, InventoryChangeTrigger.TriggerInstance.hasItems(itemPredicate));
            }

            return builder.save(saver, getModAdvancementID(tab, name));
        }

        /**
         * Adds an advancement that is achieved when any one of the items in the given list with a specific enchantment is obtained.
         */
        private Advancement addEnchantmentAdvancement(Advancement parent, Item icon, FrameType advancementType,
                                                      List<Item> requirements, Enchantment enchantment, int level,
                                                      String tab, Consumer<Advancement> saver) {
            return addEnchantmentAdvancement(parent, new ItemStack(icon), advancementType, requirements,
                    enchantment, level, tab, saver);
        }

            private Item getItem(ResourceLocation id) {
            return ForgeRegistries.ITEMS.getValue(id);
        }

        private ResourceLocation getItemId(Item item) {
            return ForgeRegistries.ITEMS.getKey(item);
        }

        private ResourceLocation getEnchantmentId(Enchantment enchantment) {
            return ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
        }

        private Component getTranslatableTitle(String tab, String name) {
            return Component.translatable("advancements.%s.%s.%s.title".formatted(Constants.MOD_ID, tab, name));
        }

        private Component getTranslatableDescription(String tab, String name) {
            return Component.translatable("advancements.%s.%s.%s.description".formatted(Constants.MOD_ID, tab, name));
        }

        private String getModAdvancementID(String tab, String name) {
            return "%s:%s/%s".formatted(Constants.MOD_ID, tab, name);
        }
    }
}
