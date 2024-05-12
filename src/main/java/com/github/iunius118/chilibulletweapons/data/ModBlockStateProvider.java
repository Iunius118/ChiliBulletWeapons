package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
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
        registerTrivialBlockWithItem(ModBlocks.HOT_SAUCE_BARREL);
        registerCrossBlock(ModBlocks.CURVED_CHILI_STRING);
    }

    private void registerChiliPepper() {
        this.getVariantBuilder(ModBlocks.CHILI_PEPPER).forAllStatesExcept(state -> {
            Integer age = state.getValue(CropBlock.AGE);
            String name = "chili_pepper_stage" + age;
            ModelFile model = models().crop(name, ChiliBulletWeapons.makeId("block/" + name)).renderType("cutout");
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    private void registerTrivialBlockWithItem(Block block) {
        ResourceLocation id = getBlockId(block);
        String namespace = id.getNamespace();
        String path = id.getPath();
        this.simpleBlockWithItem(block,
                models().cubeBottomTop(path,
                        new ResourceLocation(namespace, "block/" + path + "_side"),
                        new ResourceLocation(namespace, "block/" + path + "_bottom"),
                        new ResourceLocation(namespace, "block/" + path + "_top")
                ));
    }

    private void registerCrossBlock(Block block) {
        ResourceLocation id = getBlockId(block);
        String namespace = id.getNamespace();
        String path = id.getPath();
        this.simpleBlock(block, models().cross(path, new ResourceLocation(namespace, "block/" + path)).renderType("cutout"));
    }

    private ResourceLocation getBlockId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
