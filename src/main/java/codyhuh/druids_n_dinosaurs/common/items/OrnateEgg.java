package codyhuh.druids_n_dinosaurs.common.items;

import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;

public class OrnateEgg extends Item {

    public static final String DATA_CREATURE = "CreatureType";

    public OrnateEgg(Properties pProperties) {
        super(pProperties);
    }

    public static boolean containsEntity(ItemStack stack) {
        return stack.getTag() != null && stack.hasTag() && stack.getTag().contains(DATA_CREATURE);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.SOURCE_ONLY);

        if (containsEntity(itemstack)){
            if (blockhitresult.getType() != HitResult.Type.BLOCK) {
                return InteractionResultHolder.pass(itemstack);
            } else if (!(pLevel instanceof ServerLevel)) {
                return InteractionResultHolder.success(itemstack);
            } else {

                BlockPos blockpos = blockhitresult.getBlockPos();

                if (pLevel.mayInteract(pPlayer, blockpos) && pPlayer.mayUseItemAt(blockpos, blockhitresult.getDirection(), itemstack)) {

                    int tag = itemstack.getTag().getInt(DATA_CREATURE);

                    Animal entity = switch (tag) {
                        case 1 -> ModEntities.GOURD_RAPTOR.get().create(pLevel);
                        case 2 -> ModEntities.EGG_RAPTOR.get().create(pLevel);
                        case 3 -> ModEntities.HART.get().create(pLevel);
                        default -> ModEntities.HUE_HOG.get().create(pLevel);
                    };

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
                        if (itemstack.hasCustomHoverName()){
                            entity.setCustomName(itemstack.getHoverName());
                        }

                        entity.moveTo(blockpos1.getX() + 0.5, blockpos1.getY(), blockpos1.getZ() + 0.5, pPlayer.getYRot(), 0f);
                        entity.setAge(-24000);
                        pLevel.addFreshEntity(entity);
                        pLevel.playSound(null, entity.blockPosition(), SoundEvents.SNIFFER_EGG_HATCH, SoundSource.AMBIENT, 1.0F, 1.0F);
                    }

                    itemstack.removeTagKey(DATA_CREATURE);

                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    pLevel.gameEvent(pPlayer, GameEvent.ENTITY_PLACE, entity.position());
                    return InteractionResultHolder.consume(itemstack);

                } else {
                    return InteractionResultHolder.fail(itemstack);
                }
            }
        }

        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return containsEntity(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (containsEntity(stack)) {

            ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
            int tag = stack.getTag().getInt(DATA_CREATURE);
            MutableComponent instructions = Component.translatable("druids_n_dinosaurs.ornate_egg.instructions");

            String translatable = switch (tag) {
                case 1 -> "entity.druids_n_dinosaurs.gourd_raptor";
                case 2 -> "entity.druids_n_dinosaurs.egg_raptor";
                case 3 -> "entity.druids_n_dinosaurs.hart";
                default -> "entity.druids_n_dinosaurs.hue_hog";
            };

            instructions.append(Component.translatable(translatable)).withStyle(achatformatting);

            tooltip.add(instructions);
        }
    }

    public void setEntityType(int type, ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(DATA_CREATURE, type);
        stack.setTag(tag);
    }
}
