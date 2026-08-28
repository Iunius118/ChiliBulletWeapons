package com.github.iunius118.chilibulletweapons.block;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModBlocks {
    public static final Block CHILI_PEPPER = new ChiliPepperCrop(BlockBehaviour.Properties
            .of(Material.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP));
    public static final Block CURVED_CHILI_STRING = new Block(BlockBehaviour.Properties
            .of(Material.PLANT)
            .noCollission()
            .instabreak()
            .sound(SoundType.CROP));
    public static final Block POTTED_CHILI_PEPPER_FLOWERING =
            new PottedChiliPepperBlock(() -> ModItems.CHILI_SEEDS, BlockBehaviour.Properties
                    .of(Material.DECORATION)
                    .instabreak()
                    .noOcclusion());
    public static final Block POTTED_CHILI_PEPPER_GREEN =
            new PottedChiliPepperBlock(() -> ModItems.CURVED_GREEN_CHILI, BlockBehaviour.Properties
                    .of(Material.DECORATION)
                    .instabreak()
                    .noOcclusion());
    public static final Block POTTED_CHILI_PEPPER_RED =
            new PottedChiliPepperBlock(() -> ModItems.CURVED_CHILI, BlockBehaviour.Properties
                    .of(Material.DECORATION)
                    .instabreak()
                    .noOcclusion());
    public static final Block HOT_SAUCE_BARREL = new Block(BlockBehaviour.Properties
            .of(Material.WOOD)
            .strength(2.5F)
            .sound(SoundType.WOOD));
}
