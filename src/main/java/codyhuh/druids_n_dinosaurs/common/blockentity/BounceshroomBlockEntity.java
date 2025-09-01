package codyhuh.druids_n_dinosaurs.common.blockentity;

import codyhuh.druids_n_dinosaurs.common.blocks.BounceshroomBlock;
import codyhuh.druids_n_dinosaurs.registry.ModBlockEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BounceshroomBlockEntity extends BlockEntity  {
    public int cooldown = 0;
    public int bounces = 4;

    public BounceshroomBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOUNCESHROOM.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        cooldown = tag.getInt("Cooldown");
        bounces = tag.getInt("Bounces");
        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("Cooldown", cooldown);
        tag.putInt("Bounces", bounces);
        super.saveAdditional(tag);
    }

    public void bounceUp(BounceshroomBlockEntity bounceshroom, Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        double d = 1.5D;
        double y = vec3.y;

        entity.setDeltaMovement(vec3.x, -y * d, vec3.z);

        int bounces = Math.max(bounceshroom.bounces - 1, 0);

        bounceshroom.bounces = bounces;
        level.setBlock(getBlockPos(), getBlockState().setValue(BounceshroomBlock.BOUNCES, bounces), 2);

        //Minecraft.getInstance().getChatListener().handleSystemMessage(Component.literal("" + (-y * d)), true);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BounceshroomBlockEntity bounceshroom) {
        if (bounceshroom.bounces < 4 && bounceshroom.cooldown++ == 100) {
            int bounces = Math.min(bounceshroom.bounces + 1, 4);

            bounceshroom.bounces = bounces;
            level.setBlock(pos, state.setValue(BounceshroomBlock.BOUNCES, bounces), 2);

            bounceshroom.cooldown = 0;
        }
    }
}
