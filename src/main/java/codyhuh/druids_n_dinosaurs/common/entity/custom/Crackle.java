package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class Crackle extends Animal {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState blinkAnimationState = new AnimationState();
    public int blinkAnimationTimeout = this.random.nextInt(80) + 40;

    public int eggShardTime = this.random.nextInt(6000) + 6000;

    public Crackle(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
                .add(Attributes.ARMOR, 20);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5));

        this.goalSelector.addGoal(1, new BreedGoal(this, 1.5));

        this.goalSelector.addGoal(1, new TemptGoal(this, 1.5, Ingredient.of(ItemTags.FISHES),false));

        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1));

        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ItemTags.FISHES);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("EggLayTime")) {
            this.eggShardTime = pCompound.getInt("EggshardTime");
        }

    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("EggshardTime", this.eggShardTime);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isBaby()){
            this.setAge(0);
        }

        if (this.level().isClientSide)
            this.setUpAnimations();
    }

    protected void setUpAnimations(){
        idleAnimationState.animateWhen(this.isAlive(), this.tickCount);

        if(blinkAnimationTimeout <= 0) {
            blinkAnimationTimeout = this.getRandom().nextInt(80) + 40;
            blinkAnimationState.start(this.tickCount);
        } else {
            --this.blinkAnimationTimeout;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.eggShardTime <= 0) {
            this.playSound(SoundEvents.SNIFFER_EGG_CRACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(ModItems.EGG_SHARDS.get());
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggShardTime = this.random.nextInt(6000) + 6000;
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {

        Entity entity = pSource.getDirectEntity();
        if (entity instanceof AbstractArrow){
            return false;
        }

        return super.hurt(pSource, pAmount);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SNIFFER_EGG_CRACK;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.SNIFFER_EGG_HATCH;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setAge(0);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public void spawnChildFromBreeding(ServerLevel pLevel, Animal pMate) {
        ItemStack itemstack = new ItemStack(ModBlocks.CRACKLE_EGG.get());
        ItemEntity itementity = new ItemEntity(pLevel, this.position().x(), this.position().y(), this.position().z(), itemstack);
        itementity.setDefaultPickUpDelay();
        this.finalizeSpawnChildFromBreeding(pLevel, pMate, (AgeableMob)null);
        this.playSound(SoundEvents.SNIFFER_EGG_PLOP, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.5F);
        pLevel.addFreshEntity(itementity);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.CRACKLE.get().create(pLevel);
    }
}
