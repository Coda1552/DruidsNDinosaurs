package codyhuh.druids_n_dinosaurs.common.blocks;

import codyhuh.druids_n_dinosaurs.common.entity.custom.JadeElephant;
import codyhuh.druids_n_dinosaurs.common.entity.custom.TuffTotemPole;
import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class JadeBlock extends Block {
    public JadeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.is(ModItems.BOTTLE_O_SOUL.get()) && this.isValidFormation(pLevel, pPos)){
            clearPatternBlocks(pLevel, pPos);

            TuffTotemPole pGolem = ModEntities.TUFF_TOTEM_POLE.get().create(pLevel);
            if (pGolem != null){
                pGolem.moveTo((double)pPos.getX() + 0.5D, (double)pPos.getY() + 1, (double)pPos.getZ() + 0.5D, 0.0F, 0.0F);
                pLevel.addFreshEntity(pGolem);

                for(ServerPlayer serverplayer : pLevel.getEntitiesOfClass(ServerPlayer.class, pGolem.getBoundingBox().inflate(5.0D))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, pGolem);
                }
            }

            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 2);
            pLevel.levelEvent(2001, pPos, Block.getId(pState));

            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public static void clearPatternBlocks(Level pLevel, BlockPos pPos) {
        for (int i = 1; i <= 3; i++){
            pLevel.setBlock(pPos.above(i), Blocks.AIR.defaultBlockState(), 2);
            pLevel.levelEvent(2001, pPos.above(i), Block.getId(pLevel.getBlockState(pPos.above(i))));
        }
    }

    boolean isValidFormation(Level pLevel, BlockPos pPos){
        return pLevel.getBlockState(pPos.above(3)).is(ModBlocks.BIRD_TUFF_TOTEM.get())
                && pLevel.getBlockState(pPos.above(2)).is(ModBlocks.HART_TUFF_TOTEM.get())
                && pLevel.getBlockState(pPos.above(1)).is(ModBlocks.ELEPHANT_TUFF_TOTEM.get());
    }
}
