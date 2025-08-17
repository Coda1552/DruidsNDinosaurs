package codyhuh.druids_n_dinosaurs.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BounceshroomBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BounceshroomBlock(Properties p_51021_) {
        super(p_51021_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_49163_) {
        FluidState fluidstate = p_49163_.getLevel().getFluidState(p_49163_.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8);
    }

    public VoxelShape getShape(BlockState p_52419_, BlockGetter p_52420_, BlockPos p_52421_, CollisionContext p_52422_) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState p_51042_, BlockGetter p_51043_, BlockPos p_51044_) {
        return true;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49180_) {
        p_49180_.add(WATERLOGGED);
    }

    public FluidState getFluidState(BlockState p_49191_) {
        return p_49191_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_49191_);
    }

    public void updateEntityAfterFallOn(BlockGetter p_56406_, Entity p_56407_) {
        if (p_56407_.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(p_56406_, p_56407_);
        } else {
            this.bounceUp(p_56407_);
        }

    }

    private void bounceUp(Entity p_56404_) {
        Vec3 vec3 = p_56404_.getDeltaMovement();
        if (vec3.y < 0.0D) {
            double d0 = 0.95D;
            p_56404_.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
        }
    }
}
