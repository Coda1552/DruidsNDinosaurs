package codyhuh.druids_n_dinosaurs.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BulbBlock extends Block {
    public static final BooleanProperty POWERED;
    public static final BooleanProperty LIT;

    public BulbBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false).setValue(POWERED, false));
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (oldState.getBlock() != state.getBlock() && level instanceof ServerLevel serverlevel) {
            this.checkAndFlip(state, serverlevel, pos);
        }

    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (level instanceof ServerLevel serverlevel) {
            this.checkAndFlip(state, serverlevel, pos);
        }

    }

    public void checkAndFlip(BlockState state, ServerLevel level, BlockPos pos) {
        boolean flag = level.hasNeighborSignal(pos);
        if (flag != state.getValue(POWERED)) {
            BlockState blockstate = state;
            if (!(Boolean)state.getValue(POWERED)) {
                blockstate = state.cycle(LIT);
                level.playSound(null, pos, blockstate.getValue(LIT) ? SoundEvents.TRIPWIRE_CLICK_ON : SoundEvents.TRIPWIRE_CLICK_OFF, SoundSource.BLOCKS);
            }

            level.setBlock(pos, blockstate.setValue(POWERED, flag), 3);
        }

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, POWERED);
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return level.getBlockState(pos).getValue(LIT) ? 15 : 0;
    }

    static {
        POWERED = BlockStateProperties.POWERED;
        LIT = BlockStateProperties.LIT;
    }
}
