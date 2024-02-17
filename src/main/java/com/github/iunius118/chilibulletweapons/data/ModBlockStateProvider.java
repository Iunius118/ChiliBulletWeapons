package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CropBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, String modId, ExistingFileHelper exFileHelper) {
        super(output, modId, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerChiliPepper();
    }

    private void registerChiliPepper() {
        getVariantBuilder(ModBlocks.CHILI_PEPPER).forAllStatesExcept(state -> {
            Integer age = state.getValue(CropBlock.AGE);
            String name = "chili_pepper_stage" + age;
            ModelFile model = models().crop(name, new ResourceLocation(ChiliBulletWeapons.MOD_ID, "block/" + name)).renderType("cutout");
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }
}
