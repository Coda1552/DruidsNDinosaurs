package codyhuh.druids_n_dinosaurs.common.blocks;

import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GoudaCheeseBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 4);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape THREE_QUAT_N = Shapes.or(
            Block.box(0, 8, 8, 16, 16, 16), Block.box(0, 0, 8, 16, 8, 16), Block.box(0, 0, 0, 16, 8, 8));
    protected static final VoxelShape THREE_QUAT_E = Shapes.or(Block.box(0, 8, 0, 8, 16, 16), Block.box(0, 0, 0, 16, 8, 16));
    protected static final VoxelShape THREE_QUAT_W = Shapes.or(Block.box(8, 8, 0, 16, 16, 16), Block.box(0, 0, 0, 16, 8, 16));
    protected static final VoxelShape THREE_QUAT_S = Shapes.or(Block.box(0, 8, 0, 16, 16, 8), Block.box(0, 0, 0, 16, 8, 16));
    protected static final VoxelShape HALF = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape ONE_QUART_N = Block.box(0, 0, 8, 16, 8, 16);
    protected static final VoxelShape ONE_QUART_S = Block.box(0, 0, 0, 16, 8, 8);
    protected static final VoxelShape ONE_QUART_W = Block.box(8, 0, 0, 16, 8, 16);
    protected static final VoxelShape ONE_QUART_E = Block.box(0, 0, 0, 7, 8, 16);

    public GoudaCheeseBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0).setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int bites = pState.getValue(BITES);
        return switch (bites){
            case 1 ->{
                switch (pState.getValue(FACING)){
                    case SOUTH -> {
                        yield THREE_QUAT_S;
                    }
                    case EAST -> {
                        yield THREE_QUAT_E;
                    }
                    case WEST -> {
                        yield THREE_QUAT_W;
                    }
                    default -> {
                        yield THREE_QUAT_N;
                    }
                }
            }
            case 2 -> HALF;
            case 3 -> {
                switch (pState.getValue(FACING)){
                    case SOUTH -> {
                        yield ONE_QUART_S;
                    }
                    case EAST -> {
                        yield ONE_QUART_E;
                    }
                    case WEST -> {
                        yield ONE_QUART_W;
                    }
                    default -> {
                        yield ONE_QUART_N;
                    }
                }
            }
            default -> super.getShape(pState, pLevel, pPos, pContext);
        };
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

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

    protected static InteractionResult eat(LevelAccessor pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pPlayer.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            pPlayer.getFoodData().eat(2, 0.1F);
            int i = pState.getValue(BITES);
            pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
            pLevel.playSound(pPlayer, pPlayer.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.PLAYERS);
            pLevel.levelEvent(2001, pPos, Block.getId((ModBlocks.GOUDA_CHEESE.get().defaultBlockState())));
            if (i < 3) {
                pLevel.setBlock(pPos, pState.setValue(BITES, Integer.valueOf(i + 1)), 3);
            } else {
                pLevel.removeBlock(pPos, false);
                pLevel.gameEvent(pPlayer, GameEvent.BLOCK_DESTROY, pPos);
            }

            return InteractionResult.SUCCESS;
        }
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BITES).add(FACING).add(WATERLOGGED);
    }

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
}
