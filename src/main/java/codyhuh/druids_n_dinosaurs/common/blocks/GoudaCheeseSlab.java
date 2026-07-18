package codyhuh.druids_n_dinosaurs.common.blocks;

import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class GoudaCheeseSlab extends SlabBlock {
    public GoudaCheeseSlab(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (itemstack.is(ItemTags.SHOVELS)){
            if (pLevel.isClientSide) {
                if (eat(pLevel, pPos, pState, pPlayer).consumesAction()) {
                    return InteractionResult.SUCCESS;
                }

                if (itemstack.isEmpty()) {
                    return InteractionResult.CONSUME;
                }
            }

            return eat(pLevel, pPos, pState, pPlayer);
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    protected static InteractionResult eat(LevelAccessor pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pPlayer.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            pPlayer.getFoodData().eat(2, 0.1F);
            pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
            pLevel.playSound(pPlayer, pPlayer.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.PLAYERS);
            pLevel.levelEvent(2001, pPos, Block.getId((ModBlocks.GOUDA_CHEESE.get().defaultBlockState())));

            if (pState.getValue(TYPE)== SlabType.DOUBLE){
                pPlayer.getFoodData().eat(2, 0.1F);
                Direction direction = pPlayer.getDirection();

                if (!direction.getAxis().isHorizontal())
                    direction = Direction.NORTH;

                BlockState state = ModBlocks.GOUDA_CHEESE_STAIRS.get().defaultBlockState().setValue(StairBlock.FACING, direction);
                pLevel.setBlock(pPos, state, 1);
            } else {
                pPlayer.getFoodData().eat(4, 0.2F);
                pLevel.removeBlock(pPos, false);
                pLevel.gameEvent(pPlayer, GameEvent.BLOCK_DESTROY, pPos);
            }

            return InteractionResult.SUCCESS;
        }
    }
}
