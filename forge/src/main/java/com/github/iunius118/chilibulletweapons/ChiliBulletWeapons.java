package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.platform.ForgeChiliBulletWeaponsConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class ChiliBulletWeapons {
    public static IEventBus modEventBus;

    public ChiliBulletWeapons(FMLJavaModLoadingContext context) {
        modEventBus = context.getModEventBus();

        // Register config handlers
        registerConfig(context);

        // Use Forge to bootstrap the Common mod.
        //Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        // Register mod event listeners
        modEventBus.addListener(this::onCommonSetup);

        if (FMLLoader.getDist().isClient()) {
            // Init client
            ChiliBulletWeaponsClient.onInitializeClient(modEventBus);
        }

        // Register forge event listeners
        MinecraftForge.EVENT_BUS.addListener(this::onFurnaceFuelBurnTimeEvent);
        MinecraftForge.EVENT_BUS.addListener(this::onLootTableLoad);
    }

    private void registerConfig(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, ForgeChiliBulletWeaponsConfig.COMMON_SPEC,
                Constants.MOD_ID + ".toml");
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        ComposterBlock.COMPOSTABLES.putAll(ModItems.COMPOSTABLES);
    }

    private void onFurnaceFuelBurnTimeEvent(final FurnaceFuelBurnTimeEvent event) {
        var item = event.getItemStack().getItem();

        if (ModItems.FURNACE_FUELS.containsKey(item)) {
            event.setBurnTime(ModItems.FURNACE_FUELS.get(item));
        }
    }

    // 1.20.2 or earlier
    @SuppressWarnings("removal")
    private static final ResourceLocation OLD_GRASS_LOOT_PATH = new ResourceLocation("blocks/grass");
    // 1.20.3 or later
    @SuppressWarnings("removal")
    private static final ResourceLocation NEW_GRASS_LOOT_PATH = new ResourceLocation("blocks/short_grass");

    private void onLootTableLoad(final LootTableLoadEvent event) {
        ResourceLocation name = event.getName();

        // Add loot pool of chili pepper to loot table of short grass
        if (OLD_GRASS_LOOT_PATH.equals(name) || NEW_GRASS_LOOT_PATH.equals(name)) {
            LootPool pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.CURVED_CHILI)
                            .when(LootItemRandomChanceCondition.randomChance(0.125F))
                            .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1)))
                    .build();
            event.getTable().addPool(pool);
        }
    }
}
