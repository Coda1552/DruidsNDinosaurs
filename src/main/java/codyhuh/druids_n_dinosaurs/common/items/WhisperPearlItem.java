package codyhuh.druids_n_dinosaurs.common.items;

import codyhuh.druids_n_dinosaurs.common.entity.custom.Whisp;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WhisperPearlItem extends Item {

    public final int variant;

    public WhisperPearlItem(Properties properties, int variant) {
        super(properties);
        this.variant = variant;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.SOURCE_ONLY);
        if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else if (!(pLevel instanceof ServerLevel)) {
            return InteractionResultHolder.success(itemstack);
        } else {

            BlockPos blockpos = blockhitresult.getBlockPos();

            if (pLevel.mayInteract(pPlayer, blockpos) && pPlayer.mayUseItemAt(blockpos, blockhitresult.getDirection(), itemstack)) {

                Whisp entity = ModEntities.WHISP.get().create(pLevel);

                if (entity == null)
                    return InteractionResultHolder.pass(itemstack);

                BlockHitResult rt = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.SOURCE_ONLY);
                Direction direction = rt.getDirection();

                BlockState blockstate = pLevel.getBlockState(blockpos);
                BlockPos blockpos1;
                if (blockstate.getCollisionShape(pLevel, blockpos).isEmpty()) {
                    blockpos1 = blockpos;
                } else {
                    blockpos1 = blockpos.relative(direction);
                }

                if (!pLevel.isClientSide) {
                    entity.moveTo(blockpos1.getX() + 0.5, blockpos1.getY(), blockpos1.getZ() + 0.5, pPlayer.getYRot(), 0f);
                    entity.setVariant(this.variant);
                    entity.setHomePos(new BlockPos(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ()));
                    pLevel.addFreshEntity(entity);
                    pLevel.playSound(null, entity.blockPosition(), ModSounds.WICKER_IDOL_SWOOSH.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
                }

                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                pLevel.gameEvent(pPlayer, GameEvent.ENTITY_PLACE, entity.position());
                return InteractionResultHolder.consume(itemstack);

            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }

}
