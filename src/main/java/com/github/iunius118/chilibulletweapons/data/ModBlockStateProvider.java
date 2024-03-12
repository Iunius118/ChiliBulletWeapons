package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

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
            ModelFile model = models().crop(name, ChiliBulletWeapons.makeId("block/" + name)).renderType("cutout");
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }
}
