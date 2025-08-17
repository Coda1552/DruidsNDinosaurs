package codyhuh.druids_n_dinosaurs.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BounceshroomBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty BOUNCES = IntegerProperty.create("bounces", 0, 4);

    public BounceshroomBlock(Properties p_51021_) {
        super(p_51021_);
        this.registerDefaultState(this.stateDefinition.any().setValue(BOUNCES, 4).setValue(WATERLOGGED, false));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_49163_) {
        FluidState fluidstate = p_49163_.getLevel().getFluidState(p_49163_.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8).setValue(BOUNCES, 4);
    }

    public VoxelShape getShape(BlockState p_52419_, BlockGetter p_52420_, BlockPos p_52421_, CollisionContext p_52422_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        return Shapes.empty();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.isFaceSturdy(level, pos.below(), Direction.UP);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49180_) {
        p_49180_.add(WATERLOGGED).add(BOUNCES);
    }

    public FluidState getFluidState(BlockState p_49191_) {
        return p_49191_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_49191_);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (state.getValue(BOUNCES) < 4) {
            level.setBlock(pos, state.setValue(BOUNCES, state.getValue(BOUNCES) + 1), 2);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
       if (!entity.isSuppressingBounce() && state.getValue(BOUNCES) > 0) {
           this.bounceUp(state, level, pos, entity);
       }
    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity p_154570_, float p_154571_) {
        if (p_154570_.isSuppressingBounce() || state.getValue(BOUNCES) == 0) {
            super.fallOn(level, state, pos, p_154570_, p_154571_);
        } else {
            p_154570_.causeFallDamage(p_154571_, 0.0F, level.damageSources().fall());


        }
    }

    private void bounceUp(BlockState state, Level level, BlockPos pos, Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();

        if (vec3.y < 0.0D) {
            double d = 1.45D;
            entity.setDeltaMovement(vec3.x, -vec3.y * d, vec3.z);

            if (entity.fallDistance > 0.0D) {
                level.setBlock(pos, state.setValue(BOUNCES, state.getValue(BOUNCES) - 1), 2);
            }
        }
    }
}
