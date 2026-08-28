package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.CommonClass;
import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator generator, ExistingFileHelper exFileHelper) {
        super(generator, Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerChiliPepper();
        registerTrivialBlockWithItem(ModBlocks.HOT_SAUCE_BARREL);
        registerCrossBlock(ModBlocks.CURVED_CHILI_STRING);

        ResourceLocation chiliPepperPotModel = generateChiliPepperPotModel();
        createPottedChiliPepperModel(ModBlocks.POTTED_CHILI_PEPPER_FLOWERING, chiliPepperPotModel);
        createPottedChiliPepperModel(ModBlocks.POTTED_CHILI_PEPPER_GREEN, chiliPepperPotModel);
        createPottedChiliPepperModel(ModBlocks.POTTED_CHILI_PEPPER_RED, chiliPepperPotModel);
    }

    private void registerChiliPepper() {
        this.getVariantBuilder(ModBlocks.CHILI_PEPPER).forAllStatesExcept(state -> {
            Integer age = state.getValue(CropBlock.AGE);
            String name = "chili_pepper_stage" + age;
            ModelFile model = models().crop(name, CommonClass.modLocation("block/" + name)).renderType("cutout");
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    private void registerTrivialBlockWithItem(Block block) {
        ResourceLocation id = getBlockId(block);
        String namespace = id.getNamespace();
        String path = id.getPath();
        BlockModelBuilder model = models().cubeBottomTop(path,
                new ResourceLocation(namespace, "block/" + path + "_side"),
                new ResourceLocation(namespace, "block/" + path + "_bottom"),
                new ResourceLocation(namespace, "block/" + path + "_top")
        );
        this.simpleBlock(block, model);
        this.simpleBlockItem(block, model);
    }

    private void registerCrossBlock(Block block) {
        ResourceLocation id = getBlockId(block);
        String namespace = id.getNamespace();
        String path = id.getPath();
        this.simpleBlock(block,
                models().cross(path, new ResourceLocation(namespace, "block/" + path)).renderType("cutout"));
    }

    private ResourceLocation generateChiliPepperPotModel() {
        ResourceLocation modelLocation = CommonClass.modLocation("block/chili_pepper_pot");
        ModelFile model = this.models().getBuilder(modelLocation.toString())
                .renderType("cutout")
                .ao(false)
                .texture("particle", modelLocation)
                .texture("pot", modelLocation)
                .texture("dirt", mcLoc("block/dirt"))
                .element()
                    .from(4, 3, 4)
                    .to(5, 6, 12)
                    .face(Direction.DOWN).texture("#pot").uvs(4, 3, 5, 11).end()
                    .face(Direction.UP).texture("#pot").uvs(4, 3, 5, 11).end()
                    .face(Direction.NORTH).texture("#pot").uvs(11, 10, 12, 13).end()
                    .face(Direction.SOUTH).texture("#pot").uvs(4, 10, 5, 13).end()
                    .face(Direction.WEST).texture("#pot").uvs(4, 10, 12, 13).end()
                    .face(Direction.EAST).texture("#pot").uvs(4, 10, 12, 13).end()
                    .end()
                .element()
                    .from(11, 3, 4)
                    .to(12, 6, 12)
                    .face(Direction.DOWN).texture("#pot").uvs(11, 3, 12, 11).end()
                    .face(Direction.UP).texture("#pot").uvs(11, 3, 12, 11).end()
                    .face(Direction.NORTH).texture("#pot").uvs(4, 10, 5, 13).end()
                    .face(Direction.SOUTH).texture("#pot").uvs(11, 10, 12, 13).end()
                    .face(Direction.WEST).texture("#pot").uvs(4, 10, 12, 13).end()
                    .face(Direction.EAST).texture("#pot").uvs(4, 10, 12, 13).end()
                    .end()
                .element()
                    .from(5, 3, 4)
                    .to(11, 6, 5)
                    .face(Direction.DOWN).texture("#pot").uvs(5, 10, 11, 11).end()
                    .face(Direction.UP).texture("#pot").uvs(5, 3, 11, 4).end()
                    .face(Direction.NORTH).texture("#pot").uvs(5, 10, 11, 13).end()
                    .face(Direction.SOUTH).texture("#pot").uvs(5, 10, 11, 13).end()
                    .end()
                .element()
                    .from(5, 3, 11)
                    .to(11, 6, 12)
                    .face(Direction.DOWN).texture("#pot").uvs(5, 3, 11, 4).end()
                    .face(Direction.UP).texture("#pot").uvs(5, 10, 11, 11).end()
                    .face(Direction.NORTH).texture("#pot").uvs(5, 10, 11, 13).end()
                    .face(Direction.SOUTH).texture("#pot").uvs(5, 10, 11, 13).end()
                    .end()
                .element()
                    .from(5, 0, 5)
                    .to(11, 4, 11)
                    .face(Direction.DOWN).texture("#pot").uvs(5, 10, 11, 16).cullface(Direction.DOWN).end()
                    .face(Direction.UP).texture("#dirt").uvs(5, 5, 11, 11).end()
                    .face(Direction.NORTH).texture("#pot").uvs(5, 12, 11, 16).end()
                    .face(Direction.SOUTH).texture("#pot").uvs(5, 12, 11, 16).end()
                    .face(Direction.WEST).texture("#pot").uvs(5, 12, 11, 16).end()
                    .face(Direction.EAST).texture("#pot").uvs(5, 12, 11, 16).end()
                    .end()
                .element()
                    .from(2.6f, 4, 8)
                    .to(13.4f, 16, 8)
                    .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45).rescale(true).end()
                    .shade(false)
                    .face(Direction.NORTH).texture("#plant").uvs(0, 0, 16, 16).end()
                    .face(Direction.SOUTH).texture("#plant").uvs(0, 0, 16, 16).end()
                    .end()
                .element()
                    .from(8, 4, 2.6f)
                    .to(8, 16, 13.4f)
                    .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45).rescale(true).end()
                    .shade(false)
                    .face(Direction.WEST).texture("#plant").uvs(0, 0, 16, 16).end()
                    .face(Direction.EAST).texture("#plant").uvs(0, 0, 16, 16).end()
                    .end();

        return modelLocation;
    }

    private void createPottedChiliPepperModel(Block block, ResourceLocation potModel) {
        ResourceLocation blockId = getBlockId(block);
        ResourceLocation textureLoc = new ResourceLocation(blockId.getNamespace(), "block/" + blockId.getPath());
        this.simpleBlock(block,
                models().withExistingParent(blockId.toString(), potModel)
                        .texture("plant", textureLoc));
    }

    private ResourceLocation getBlockId(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
