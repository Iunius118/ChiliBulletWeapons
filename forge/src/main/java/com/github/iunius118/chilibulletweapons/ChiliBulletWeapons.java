package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.advancements.ModCriteriaTriggers;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.loot.CurvedChiliLootModifier;
import com.github.iunius118.chilibulletweapons.platform.ForgeChiliBulletWeaponsConfig;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class ChiliBulletWeapons {
    public static IEventBus modEventBus;

    public ChiliBulletWeapons() {
        modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register config handlers
        registerConfig(ModLoadingContext.get());

        // Use Forge to bootstrap the Common mod.
        //Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        // Register mod event listeners
        modEventBus.addListener(this::registerGlobalLootModifiers);
        modEventBus.addListener(this::onCommonSetup);

        // Register forge event listeners
        MinecraftForge.EVENT_BUS.addListener(this::onFurnaceFuelBurnTimeEvent);

        if (FMLLoader.getDist().isClient()) {
            // Init client
            ChiliBulletWeaponsClient.onInitializeClient(modEventBus);
        }
    }

    private void registerConfig(ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, ForgeChiliBulletWeaponsConfig.COMMON_SPEC,
                Constants.MOD_ID + ".toml");
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        ModCriteriaTriggers.CRITERION_TRIGGERS.forEach(CriteriaTriggers::register);
        ComposterBlock.COMPOSTABLES.putAll(ModItems.COMPOSTABLES);
    }

    private void onFurnaceFuelBurnTimeEvent(final FurnaceFuelBurnTimeEvent event) {
        var item = event.getItemStack().getItem();

        if (ModItems.FURNACE_FUELS.containsKey(item)) {
            event.setBurnTime(ModItems.FURNACE_FUELS.get(item));
        }
    }

    private void registerGlobalLootModifiers(final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS,
                Constants.LootModifiers.CURVED_CHILI, () -> CurvedChiliLootModifier.CODEC);
    }
}
