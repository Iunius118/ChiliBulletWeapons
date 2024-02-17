package com.github.iunius118.chilibulletweapons.block;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ToolActions;

import java.util.Collections;
import java.util.List;

public class ChiliPepperCrop extends CropBlock {
    public static final int REPRODUCTION_AGE = 3;

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public ChiliPepperCrop(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        return SHAPE_BY_AGE[this.getAge(blockState)];
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.CHILI_SEEDS;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        boolean isHarvestable = (blockState.getValue(AGE) == ChiliPepperCrop.MAX_AGE);
        var itemStack = player.getItemInHand(hand);

        if (!isHarvestable && itemStack.is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (isHarvestable && /* NeoForge */ itemStack.canPerformAction(ToolActions.SHEARS_HARVEST) /* itemStack.is(Items.SHEARS) */) {
            if (level.isClientSide) {
                return InteractionResult.CONSUME;
            }

            // Harvest
            List<ItemStack> dropItems = getRandomItemsFromLootTable(level, pos, blockState, itemStack);

            for(ItemStack dropItem : dropItems) {
                // Drop harvested items
                popResource(level, pos, dropItem);
            }

            // Remove crop block
            var blockstate = Blocks.AIR.defaultBlockState();
            level.setBlockAndUpdate(pos, blockstate);
            level.gameEvent(player, GameEvent.SHEAR, pos);
            level.playSound(null, pos, ModSoundEvents.CHILI_PEPPER_PICK_CHILI_PEPPERS, SoundSource.BLOCKS, 0.8F, 1.0F);

            if (!player.getAbilities().instabuild) {
                // Wear out shears when player is not creative mode
                itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }

            return InteractionResult.SUCCESS;
        } else {
            return super.use(blockState, level, pos, player, hand, hitResult);
        }
    }

    private List<ItemStack> getRandomItemsFromLootTable(Level level, BlockPos pos, BlockState blockState, ItemStack tool) {
        var server = level.getServer();

        if (server == null) {
            return Collections.emptyList();
        }

        var lootTableLocation = new ResourceLocation(ChiliBulletWeapons.MOD_ID, "blocks/chili_pepper");
        var lootTable = server.getLootData().getLootTable(lootTableLocation);
        var lootParams = new LootParams.Builder((ServerLevel) level)
                .withParameter(LootContextParams.ORIGIN, pos.getCenter())
                .withParameter(LootContextParams.TOOL, tool)
                .withParameter(LootContextParams.BLOCK_STATE, blockState)
                .create(LootContextParamSets.BLOCK);
        return lootTable.getRandomItems(lootParams);
    }
}
