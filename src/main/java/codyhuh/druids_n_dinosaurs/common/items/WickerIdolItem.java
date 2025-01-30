package codyhuh.druids_n_dinosaurs.common.items;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import codyhuh.druids_n_dinosaurs.registry.ModSounds;
import codyhuh.druids_n_dinosaurs.registry.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class WickerIdolItem extends Item {
    public static final String DATA_CREATURE = "EntityData";

    public WickerIdolItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        Level level = player.level();

        if (containsEntity(stack)) return InteractionResult.PASS;

        if (!target.getPassengers().isEmpty()) target.ejectPassengers();


        if (canBeCaught(target)) {
            return successful(target, player, hand, stack, level);
        }

        return InteractionResult.sidedSuccess(true);
    }

    public InteractionResult successful(LivingEntity target, Player player, InteractionHand hand, ItemStack stack, Level level) {
        ItemStack stack1 = player.getItemInHand(hand);

        /*boolean more = stack.getCount() > 1;

        if (more) {
            stack1 = new ItemStack(ModItems.WICKER_IDOL.get());
            stack.shrink(1);
        }*/

        CompoundTag targetTag = target.serializeNBT();
        targetTag.putString("OwnerName", player.getName().getString());
        CompoundTag tag = stack1.getOrCreateTag();
        tag.put(DATA_CREATURE, targetTag);
        stack1.setTag(tag);

        /*if (more) {
            if (!player.getInventory().add(stack1)) player.drop(stack1, true);
            else player.addItem(stack1);
        }*/

        target.discard();

        level.playSound(null, player.blockPosition(), ModSounds.WICKER_IDOL_SWOOSH.get(), SoundSource.AMBIENT, 1.0F, 1.0F);

        if (level instanceof ServerLevel serverLevel) {
            double width = target.getBbWidth();
            for (int i = 0; i <= Math.floor(width) * 25; ++i) {

                for (int j = 0; j < 3; j++) {
                    double x = target.getRandomX(1.0D);
                    double y = target.getRandomY();
                    double z = target.getRandomZ(1.0D);

                    serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }

        return InteractionResult.PASS;
    }

    private boolean canBeCaught(LivingEntity entity) {
        return !(entity instanceof WitherBoss) && !(entity instanceof EnderDragon) && !(entity instanceof Warden) && !entity.getType().is(ModTags.UNCATCHABLE);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        BlockHitResult rt = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        ItemStack stack = player.getItemInHand(hand);
        if (rt.getType() == HitResult.Type.MISS) return InteractionResultHolder.pass(stack);
        BlockPos pos = rt.getBlockPos();
        if (!(level.getBlockState(pos).getBlock() instanceof LiquidBlock)) return InteractionResultHolder.success(stack);
        return new InteractionResultHolder<>(releaseEntity(level, player, stack, pos, rt.getDirection()), stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (containsEntity(stack)) {
            CompoundTag tag = stack.getTag().getCompound(DATA_CREATURE);
            Component name;

            if (tag.contains("CustomName")) {
                name = Component.Serializer.fromJson(tag.getString("CustomName")).withStyle(ChatFormatting.GRAY);
            }
            else {
                name = EntityType.byString(tag.getString("id")).orElse(null).getDescription().copy().withStyle(ChatFormatting.GRAY);
            }
            tooltip.add(name);
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return containsEntity(stack);
    }

    public static boolean containsEntity(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(DATA_CREATURE);
    }

    private static InteractionResult releaseEntity(Level level, Player player, ItemStack stack, BlockPos pos, Direction direction) {
        if (!containsEntity(stack)) return InteractionResult.PASS;

        CompoundTag tag = stack.getTag().getCompound(DATA_CREATURE);
        EntityType<?> type = EntityType.byString(tag.getString("id")).orElse(null);
        LivingEntity entity;

        if (type == null || (entity = (LivingEntity) type.create(level)) == null) {
            DruidsNDinosaurs.LOGGER.error("Something went wrong releasing an entity from a Wicker Idol!");
            return InteractionResult.FAIL;
        }

        EntityDimensions size = entity.getDimensions(entity.getPose());
        if (!level.getBlockState(pos).getCollisionShape(level, pos).isEmpty())
            pos = pos.relative(direction, (int) (direction.getAxis().isHorizontal() ? size.width : 1));

        entity.absMoveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        AABB aabb = entity.getBoundingBox();

        if (!level.noCollision(entity, new AABB(aabb.minX, entity.getEyeY() - 0.35, aabb.minZ, aabb.maxX, entity.getEyeY() + 1.0, aabb.maxZ))) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide) {
            UUID id = entity.getUUID();
            entity.deserializeNBT(tag);
            entity.setUUID(id);
            entity.moveTo(pos.getX(), pos.getY() + direction.getStepY() + 1.0, pos.getZ(), player.getYRot(), 0f);

            if (stack.hasCustomHoverName()) entity.setCustomName(stack.getHoverName());
            stack.removeTagKey(DATA_CREATURE);
            level.addFreshEntity(entity);
            level.playSound(null, entity.blockPosition(), ModSounds.WICKER_IDOL_SWOOSH.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (!(world instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else if (context.getItemInHand().hasTag()) {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            ItemStack stack = context.getItemInHand();
            CompoundTag tag = stack.getTag().getCompound(DATA_CREATURE);
            EntityType<?> type = EntityType.byString(tag.getString("id")).orElse(null);
            LivingEntity entity = (LivingEntity) type.create(context.getLevel());
            if (entity == null) return InteractionResult.FAIL;

            UUID id = entity.getUUID();
            entity.deserializeNBT(tag);
            entity.setUUID(id);

            entity.moveTo(blockpos1.getX() + 0.5, blockpos1.getY(), blockpos1.getZ() + 0.5, context.getPlayer().getYRot(), 0f);

            if (stack.hasCustomHoverName()) entity.setCustomName(stack.getHoverName());
            stack.removeTagKey(DATA_CREATURE);

            if (context.getLevel().addFreshEntity(entity)) {
                itemstack.shrink(1);
            }
            context.getLevel().playSound(null, entity.blockPosition(), ModSounds.WICKER_IDOL_SWOOSH.get(), SoundSource.AMBIENT, 1, 1);
            context.getPlayer().setItemInHand(context.getHand(), new ItemStack(ModItems.WICKER_IDOL.get()));

            return InteractionResult.CONSUME;
        }
        else {
            return super.useOn(context);
        }
    }
}