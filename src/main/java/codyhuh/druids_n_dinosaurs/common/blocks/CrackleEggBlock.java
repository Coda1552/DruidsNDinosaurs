package codyhuh.druids_n_dinosaurs.common.blocks;

import codyhuh.druids_n_dinosaurs.common.entity.custom.Crackle;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class CrackleEggBlock extends Block {

    public static final IntegerProperty HATCH;

    public CrackleEggBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

    public int getHatchLevel(BlockState state) {
        return state.getValue(HATCH);
    }

    private boolean isReadyToHatch(BlockState state) {
        return this.getHatchLevel(state) == 2;
    }


    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (this.shouldUpdateHatchLevel(level, pos.below())) {
            int i = state.getValue(HATCH);

            if (i < 2) {
                level.playSound(null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                level.setBlock(pos, state.setValue(HATCH, this.getHatchLevel(state) + 1), 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
            } else {
                level.playSound(null, pos, SoundEvents.SNIFFER_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                level.removeBlock(pos, false);
                level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));

                level.levelEvent(2001, pos, Block.getId(state));
                Crackle crackle = ModEntities.CRACKLE.get().create(level);
                if (crackle != null) {
                    Vec3 vec3 = pos.getCenter();

                    crackle.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
                    level.addFreshEntity(crackle);
                }
            }
        }
    }

    private boolean shouldUpdateHatchLevel(Level level, BlockPos ground) {
        return level.random.nextInt(500) == 0 && level.getBlockState(ground).is(Blocks.HAY_BLOCK);
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        boolean flag = hatchBoost(level, pos);
        if (!level.isClientSide() && flag) {
            level.levelEvent(3009, pos, 0);
        }
        level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(state));
    }

    public static boolean hatchBoost(BlockGetter level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(Blocks.HAY_BLOCK);
    }

    static {
        HATCH = BlockStateProperties.HATCH;
    }
}
