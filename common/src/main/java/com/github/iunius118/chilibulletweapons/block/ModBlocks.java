package com.github.iunius118.chilibulletweapons.block;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {
    public static final Block CHILI_PEPPER = new ChiliPepperCrop(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP)
            .pushReaction(PushReaction.DESTROY));
    public static final Block CURVED_CHILI_STRING = new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .instabreak()
            .sound(SoundType.CROP)
            .pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_CHILI_PEPPER_FLOWERING =
            new PottedChiliPepperBlock(() -> ModItems.CHILI_SEEDS, BlockBehaviour.Properties.of()
                    .instabreak()
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_CHILI_PEPPER_GREEN =
            new PottedChiliPepperBlock(() -> ModItems.CURVED_GREEN_CHILI, BlockBehaviour.Properties.of()
                    .instabreak()
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_CHILI_PEPPER_RED =
            new PottedChiliPepperBlock(() -> ModItems.CURVED_CHILI, BlockBehaviour.Properties.of()
                    .instabreak()
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY));
    public static final Block HOT_SAUCE_BARREL = new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.5F)
            .sound(SoundType.WOOD)
            .ignitedByLava());
}
