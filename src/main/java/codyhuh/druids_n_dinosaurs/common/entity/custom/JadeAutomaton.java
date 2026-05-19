package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.registry.ModEffects;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import codyhuh.druids_n_dinosaurs.registry.ModParticles;
import codyhuh.druids_n_dinosaurs.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;

public class JadeAutomaton extends TamableAnimal implements NeutralMob {

    private static final EntityDataAccessor<Boolean> IS_LIT = SynchedEntityData.defineId(JadeAutomaton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_WANDERING = SynchedEntityData.defineId(JadeAutomaton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(JadeAutomaton.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(JadeAutomaton.class, EntityDataSerializers.BLOCK_POS);

    //0 = normal, 1 = eidolon, 2 = phantasm, 3 = ori chalcos
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(JadeAutomaton.class, EntityDataSerializers.INT);

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    public JadeAutomaton(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.refreshDimensions();
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true) {
            public void stop() {
                super.stop();
                JadeAutomaton.this.setAggressive(false);
            }

            public void start() {
                super.start();
                JadeAutomaton.this.setAggressive(true);
            }

            protected double getAttackReachSqr(LivingEntity pAttackTarget) {
                return super.getAttackReachSqr(pAttackTarget) * 1.5f;
            }
        });
        this.goalSelector.addGoal(2, new AutomatonFollowOwnerGoal(this));
        this.goalSelector.addGoal(3, new AutomatonGoHomeGoal(this));
        this.goalSelector.addGoal(9, new AutomatonRandomStrollGoal(this, 0.65D));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
    }

    @Override
    public boolean wantsToAttack(LivingEntity pTarget, LivingEntity pOwner) {
        return !this.isAlliedTo(pTarget);
    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        if (pEntity instanceof OwnableEntity entity)
            return entity.getOwner() == this.getOwner();
        return super.isAlliedTo(pEntity);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.35F)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ARMOR_TOUGHNESS, 0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_LIT, false);
        this.entityData.define(IS_WANDERING, false);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
        this.entityData.define(HOME_POS, BlockPos.ZERO);
        this.entityData.define(VARIANT, 0);
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.25F, 1.0F);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putBoolean("IsLit", this.isLitData());
        pCompound.putBoolean("IsWandering", this.isWandering());
        pCompound.putInt("HomePosX", this.getHomePos().getX());
        pCompound.putInt("HomePosY", this.getHomePos().getY());
        pCompound.putInt("HomePosZ", this.getHomePos().getZ());
        pCompound.putInt("Variant", this.getVariant());
        this.addPersistentAngerSaveData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        int i = pCompound.getInt("HomePosX");
        int j = pCompound.getInt("HomePosY");
        int k = pCompound.getInt("HomePosZ");
        this.setHomePos(new BlockPos(i, j, k));

        super.readAdditionalSaveData(pCompound);

        this.setLit(pCompound.getBoolean("IsLit"));
        this.setWandering(pCompound.getBoolean("IsWandering"));
        this.setVariant(pCompound.getInt("Variant"));
        this.readPersistentAngerSaveData(this.level(), pCompound);
    }

    public void setCustomName(@javax.annotation.Nullable Component pName) {
        super.setCustomName(pName);
        if (!this.isEidolon() && pName != null && pName.getString().equals("Eidolon")) {
            this.setVariant(1);
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(300);
            this.setHealth(300.0F);
            this.getAttribute(Attributes.ARMOR).setBaseValue(30);
            this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(20);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(100);
        } else if (!this.isPhantasm() && pName != null && pName.getString().equals("Phantasm")) {
            this.setVariant(2);
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50);
            this.setHealth(50);
            this.setAge(-24000);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35F+(0.35F*0.3));
            this.getAttribute(Attributes.ARMOR).setBaseValue(7);
            this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(10);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(40);
            this.addEffect(new MobEffectInstance(ModEffects.ETHEREAL.get(), 20));
        } else if (!this.isOriChalcos() && pName != null && pName.getString().equals("Ori Chalcos")) {
            this.setVariant(3);
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(100);
            this.setHealth(100);
        } else if (this.isCustomName() && pName != null) {
            this.setVariant(0);
            if (isLitData()){
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60);
                this.setHealth(60.0F);
            }else {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40);
                this.setHealth(40.0F);
            }
            this.getAttribute(Attributes.ARMOR).setBaseValue(0);
            this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(0);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3);
        }
    }

    public boolean isLitData(){
        return this.entityData.get(IS_LIT);
    }

    public boolean isActuallyLit() {
        return this.isLitData() || this.isEidolon() || this.isPhantasm() || this.isOriChalcos();
    }

    public boolean isCustomName() {
        return this.isEidolon() || this.isPhantasm() || this.isOriChalcos();
    }

    public void setLit(boolean lit) {
        if (!this.isActuallyLit() && lit) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60);
            this.setHealth(60.0F);
        }
        this.entityData.set(IS_LIT, lit);
    }

    public boolean isEidolon(){
        return this.getVariant() == 1;
    }

    public boolean isPhantasm(){
        return this.getVariant() == 2;
    }

    public boolean isOriChalcos(){
        return this.getVariant() == 3;
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public boolean isWandering() {
        return this.entityData.get(IS_WANDERING) || !this.isTame();
    }

    public void setWandering(boolean wandering) {
        this.entityData.set(IS_WANDERING, wandering);
    }

    public void setHomePos(BlockPos homePos) {
        this.entityData.set(HOME_POS, homePos);
    }

    public BlockPos getHomePos() {
        return this.entityData.get(HOME_POS);
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        this.setHomePos(this.blockPosition());
        if (this.isTame() && pPlayer == this.getOwner()) {
            if (itemstack.is(Items.DANDELION) && !this.isWandering()) {
                this.setWandering(true);
                return InteractionResult.SUCCESS;
            }

            if (itemstack.is(ModItems.JADE_SHARD.get()) && this.isWandering()) {
                this.setWandering(false);
                return InteractionResult.SUCCESS;
            }

            if (itemstack.is(ModItems.BOTTLE_O_SOUL.get()) && !this.isActuallyLit()) {
                this.removeInteractionItem(pPlayer, itemstack);
                if (!pPlayer.getAbilities().instabuild){
                    pPlayer.setItemInHand(pHand, new ItemStack(Items.GLASS_BOTTLE));
                }
                this.setLit(true);
                this.playSound(SoundEvents.BEACON_ACTIVATE);
                return InteractionResult.SUCCESS;
            }

            if (this.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && itemstack.is(ModItems.JADE_AXE.get())) {
                ItemStack stack = itemstack.copyWithCount(1);
                this.setItemInHand(InteractionHand.MAIN_HAND, stack);
                this.removeInteractionItem(pPlayer, itemstack);
                return InteractionResult.SUCCESS;
            }

            if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty() &&
                    itemstack.is(ModItems.FLOWER_CROWN.get()) || itemstack.is(ModItems.BLUE_FLOWER_CROWN.get())) {
                ItemStack stack = itemstack.copyWithCount(1);
                this.setItemSlot(EquipmentSlot.HEAD, stack);
                this.removeInteractionItem(pPlayer, itemstack);
                return InteractionResult.SUCCESS;
            }

            if (this.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.JADE_AXE.get()) && itemstack.isEmpty() && pHand == InteractionHand.MAIN_HAND) {
                ItemStack itemstack1 = this.getItemInHand(InteractionHand.MAIN_HAND);
                pPlayer.addItem(itemstack1);
                this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                this.swing(InteractionHand.MAIN_HAND);
                return InteractionResult.SUCCESS;
            }

            if (itemstack.is(Items.SHEARS) && pHand == InteractionHand.MAIN_HAND && (this.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.BLUE_FLOWER_CROWN.get())
                            || this.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.FLOWER_CROWN.get()))) {
                ItemStack itemstack1 = this.getItemBySlot(EquipmentSlot.HEAD);
                this.spawnAtLocation(itemstack1);
                this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                this.playSound(SoundEvents.SHEEP_SHEAR);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }

    private void removeInteractionItem(Player pPlayer, ItemStack pStack) {
        if (!pPlayer.getAbilities().instabuild) {
            pStack.shrink(1);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive() && !this.isDeadOrDying()) {
            if ((this.isEidolon() || this.isPhantasm()) && this.tickCount % 20 == 0 && this.getMaxHealth() > this.getHealth()) {
                float currentHealth = this.getHealth();
                this.setHealth(currentHealth + 1);
            }
        }
        if (!this.isPhantasm() && this.isBaby()){
            this.setAge(1);
        }else if (this.isPhantasm() && !this.isBaby()){
            this.setAge(-24000);
        }else if (this.isPhantasm()){
            int age = this.getAge();
            this.setAge(age-1);
            if (this.tickCount % 20 == 0)
                this.addEffect(new MobEffectInstance(ModEffects.ETHEREAL.get(), 20));
        }
    }

    public void aiStep() {
        if (this.level().isClientSide && (this.isEidolon() || this.isPhantasm())) {
            for (int i = 0; i < 2; ++i) {
                this.level().addParticle(ModParticles.JADE_OMEN.get(), this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
            }
        }

        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }

        super.aiStep();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return 0;
    }

    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean canBreed() {
        return false;
    }

    @Override
    public boolean canAttack(LivingEntity pLivingentity, TargetingConditions pCondition) {
        if (pLivingentity instanceof TamableAnimal ownable)
            if (ownable.isTame() && this.isTame())
                return this.getOwner() != ownable.getOwner();
        return super.canAttack(pLivingentity, pCondition);
    }

    @Override
    public void kill() {
        this.setHealth(0);
        super.kill();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    protected int decreaseAirSupply(int pAir) {
        return pAir;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pPotioneffect) {
        return (pPotioneffect.getEffect() != MobEffects.WITHER || pPotioneffect.getEffect() != MobEffects.POISON) && super.canBeAffected(pPotioneffect);
    }

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int pTime) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pTime);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @javax.annotation.Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID pTarget) {
        this.persistentAngerTarget = pTarget;
    }

    public class AutomatonFollowOwnerGoal extends FollowOwnerGoal{

        public AutomatonFollowOwnerGoal(TamableAnimal pTamable) {
            super(pTamable, 1.0D, 4.0F, 2.0F, false);
        }

        @Override
        public boolean canUse() {
            return !JadeAutomaton.this.isWandering() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !JadeAutomaton.this.isWandering() && super.canContinueToUse();
        }

        @Override
        public boolean canTeleportTo(BlockPos pPos) {
            return false;
        }
    }

    boolean closerThan(BlockPos pPos, BlockPos target, int pDistance) {
        return pPos.closerThan(target, pDistance);
    }

    public class AutomatonRandomStrollGoal extends WaterAvoidingRandomStrollGoal{
        public AutomatonRandomStrollGoal(PathfinderMob pMob, double pSpeedModifier) {
            super(pMob, pSpeedModifier);
        }

        @Override
        public boolean canUse() {
            return JadeAutomaton.this.isWandering() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return JadeAutomaton.this.isWandering() && super.canContinueToUse();
        }
    }

    public static class AutomatonGoHomeGoal extends Goal {
        final JadeAutomaton automaton;

        AutomatonGoHomeGoal(JadeAutomaton automaton) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.automaton = automaton;
        }

        public boolean canUse() {
            if (automaton.getHomePos() == BlockPos.ZERO || automaton.getHomePos() == null)
                return false;
            return !automaton.closerThan(automaton.blockPosition(), automaton.getHomePos(), 16) && automaton.isWandering();
        }

        public boolean canContinueToUse() {
            if (automaton.getHomePos() == BlockPos.ZERO || automaton.getHomePos() == null)
                return false;
            return automaton.navigation.isInProgress() && automaton.isWandering();
        }

        public void start() {
            Vec3 vec3 = automaton.getHomePos().getCenter();
            automaton.navigation.moveTo(automaton.navigation.createPath(automaton.getHomePos(), 3), 1.0D);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setHomePos(this.blockPosition());
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return this.isOriChalcos() ? ModSounds.ORI_CHALCOS_IDLE.get() : super.getAmbientSound();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return this.isOriChalcos() ? ModSounds.ORI_CHALCOS_EUGH.get() : SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return this.isOriChalcos() ? ModSounds.ORI_CHALCOS_EUGH.get() : SoundEvents.IRON_GOLEM_DEATH;
    }

}
