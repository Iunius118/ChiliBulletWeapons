package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

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
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
