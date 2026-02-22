package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Hart extends AbstractHorse {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState scratchAnimationState = new AnimationState();
    public int scratchAnimationTimeout;

    int COOLDOWN = 60*20*10;
    int REGROW_TIME = 60*20*20;
    BlockPos scratchPos;

    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.APPLE, Items.CARROT, Items.HAY_BLOCK);

    private static final EntityDataAccessor<Integer> SCRATCHING_TIME = SynchedEntityData.defineId(Hart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LEFT_ANTLER = SynchedEntityData.defineId(Hart.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RIGHT_ANTLER = SynchedEntityData.defineId(Hart.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> REGROW_TIME_L = SynchedEntityData.defineId(Hart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> REGROW_TIME_R = SynchedEntityData.defineId(Hart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SCRATCHING_COOLDOWN = SynchedEntityData.defineId(Hart.class, EntityDataSerializers.INT);

    public Hart(EntityType<? extends AbstractHorse> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3F).add(Attributes.JUMP_STRENGTH, 0.75);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        if (this.isBaby()) {
            return super.getDimensions(pPose).scale(1.15f, 1.15f);
        }else {
            return super.getDimensions(pPose);
        }
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1));
        this.goalSelector.addGoal(2, new HartScratchGoal(this, 0.75, 20));
        this.goalSelector.addGoal(3, new BreedGoal(this, 0.75));
        this.goalSelector.addGoal(4, new TemptGoal(this, 0.75, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 0.75));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SCRATCHING_TIME, 0);
        this.entityData.define(LEFT_ANTLER, true);
        this.entityData.define(RIGHT_ANTLER, true);
        this.entityData.define(REGROW_TIME_L, 0);
        this.entityData.define(REGROW_TIME_R, 0);
        this.entityData.define(SCRATCHING_COOLDOWN, COOLDOWN);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("LeftAntler", this.getLeftAntler());
        compound.putBoolean("RightAntler", this.getRightAntler());
        compound.putInt("RegrowTimeLeft", this.getRegrowTimeL());
        compound.putInt("RegrowTimeRight", this.getRegrowTimeR());
        compound.putInt("ScratchCooldown", this.getScratchCooldown());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setLeftAntler(compound.getBoolean("LeftAntler"));
        this.setRightAntler(compound.getBoolean("RightAntler"));
        this.setRegrowTimeL(compound.getInt("RegrowTimeLeft"));
        this.setRegrowTimeR(compound.getInt("RegrowTimeRight"));
        this.setScratchCooldown(compound.getInt("ScratchCooldown"));
    }

    public int getRegrowTimeL() {
        return this.entityData.get(REGROW_TIME_L);
    }

    public void setRegrowTimeL(int time) {
        this.entityData.set(REGROW_TIME_L, time);
    }

    public int getRegrowTimeR() {
        return this.entityData.get(REGROW_TIME_R);
    }

    public void setRegrowTimeR(int time) {
        this.entityData.set(REGROW_TIME_R, time);
    }

    public int getScratchCooldown() {
        return this.entityData.get(SCRATCHING_COOLDOWN);
    }

    public void setScratchCooldown(int time) {
        this.entityData.set(SCRATCHING_COOLDOWN, time);
    }

    public BlockPos getScratchPos() {
        return this.scratchPos;
    }

    public void setScratchPos(BlockPos pos) {
        this.scratchPos = pos;
    }

    public int getScratchingTime() {
        return this.entityData.get(SCRATCHING_TIME);
    }

    public void setScratchingTime(int time) {
        this.entityData.set(SCRATCHING_TIME, time);
    }

    public boolean getLeftAntler() {
        return this.entityData.get(LEFT_ANTLER);
    }

    public void setLeftAntler(boolean antler) {
        this.entityData.set(LEFT_ANTLER, antler);
    }

    public boolean getRightAntler() {
        return this.entityData.get(RIGHT_ANTLER);
    }

    public void setRightAntler(boolean antler) {
        this.entityData.set(RIGHT_ANTLER, antler);
    }

    public boolean isScratching(){
        return this.getScratchingTime()>0;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.HART.get().create(pLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
    }

    @Override
    public boolean canMate(Animal pOtherAnimal) {
        if (pOtherAnimal == this) {
            return false;
        } else if (pOtherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && pOtherAnimal.isInLove();
        }
    }

    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            double d0 = this.getMoveControl().getSpeedModifier();
            this.setPose(Pose.STANDING);
            this.setSprinting(d0 >= 0.75);
        } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(false);
        }
    }


    @Override
    public boolean isStanding() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isScratching() && (this.isBaby() || (!this.getRightAntler() && !this.getLeftAntler()))){
            this.setScratchingTime(0);
            this.setScratchCooldown(COOLDOWN);
        }
        if (this.isScratching() && !this.level().isClientSide){
            int futureScratching = this.getScratchingTime()-1;
            if (futureScratching == 10){
                if (this.getLeftAntler() && this.getRightAntler()){
                    if (this.getRandom().nextBoolean()){
                        System.out.println("Setting Left Antler Off from having both");
                        this.setLeftAntler(false);
                        this.setRegrowTimeL(REGROW_TIME);
                    }else{
                        System.out.println("Setting Right Antler Off from having both");
                        this.setRightAntler(false);
                        this.setRegrowTimeR(REGROW_TIME);
                    }
                }else{
                    if (this.getLeftAntler()){
                        System.out.println("Setting Left Antler Off from NOT having both");
                        this.setLeftAntler(false);
                        this.setRegrowTimeL(REGROW_TIME);
                    }else if (this.getRightAntler()){
                        System.out.println("Setting Right Antler Off from NOT having both");
                        this.setRightAntler(false);
                        this.setRegrowTimeR(REGROW_TIME);
                    }
                }

                this.spawnAtLocation(new ItemStack(ModItems.ANTLER.get(), this.getRandom().nextInt(2, 6)));
            }
            if (futureScratching == 0){
                this.setScratchCooldown(COOLDOWN);
            }
            this.setScratchingTime(futureScratching);

            if (this.getScratchPos() != null)
                this.getLookControl().setLookAt(this.getScratchPos().getX(), this.getScratchPos().getY()+1.75, this.getScratchPos().getZ());
        }

        if (this.getScratchCooldown() > 0){
            int futureCooldown = this.getScratchCooldown()-1;
            this.setScratchCooldown(futureCooldown);
        }
        if (this.getRegrowTimeL() > 0 && !this.getLeftAntler()){
            int futureTime = this.getRegrowTimeL()-1;
            if (futureTime == 0)
                this.setLeftAntler(true);
            this.setRegrowTimeL(futureTime);
        }
        if (this.getRegrowTimeR() > 0 && !this.getRightAntler()){
            int futureTime = this.getRegrowTimeL()-1;
            if (futureTime == 0)
                this.setRightAntler(true);
            this.setRegrowTimeR(futureTime);
        }

        if (this.level().isClientSide)
            this.setupAnimations();
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        super.travel(pTravelVector);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isScratching() && this.getScratchPos() != null)
            this.getLookControl().setLookAt(this.getScratchPos().getX(), this.getScratchPos().getY(), this.getScratchPos().getZ());
    }

    public void setupAnimations(){
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);

        if (this.isScratching() && scratchAnimationTimeout == 0 && !this.isBaby()){
            this.scratchAnimationTimeout = 50;
            this.scratchAnimationState.start(this.tickCount);
        }else if (scratchAnimationTimeout > 0){
            this.scratchAnimationTimeout--;
        }
    }

    @Override
    public boolean isImmobile() {
        return super.isImmobile() || this.isScratching();
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.GOAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.GOAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.GOAT_DEATH;
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }

    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return super.calculateFallDamage(pFallDistance, pDamageMultiplier) - 10;
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.isFood(itemstack)) {
            int i = this.getAge();
            if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
                this.usePlayerItem(pPlayer, pHand, itemstack);
                this.setInLove(pPlayer);
                return InteractionResult.SUCCESS;
            }

            if (this.isBaby()) {
                this.usePlayerItem(pPlayer, pHand, itemstack);
                this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            }
        }else if (!this.isVehicle() && !this.isBaby() && itemstack.isEmpty()) {
            this.doPlayerRide(pPlayer);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset()*0.65;
    }

    protected void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
        super.positionRider(pPassenger, pCallback);
        float f = Mth.sin(this.yBodyRot * ((float)Math.PI / 180F));
        float f1 = Mth.cos(this.yBodyRot * ((float)Math.PI / 180F));

        pCallback.accept(pPassenger, this.getX() + (double)(0.1F * f), this.getY(0.5D) + pPassenger.getMyRidingOffset() - 0.25D, this.getZ() - (double)(0.1F * f1));

        if (pPassenger instanceof LivingEntity) {
            ((LivingEntity)pPassenger).yBodyRot = this.yBodyRot;
        }

    }

    @Override
    public boolean isTamed() {
        return true;
    }

    @Override
    public boolean isSaddled() {
        return true;
    }

    public boolean canWearArmor() {
        return false;
    }

    public void openCustomInventoryScreen(Player pPlayer) {
    }

    public static class HartScratchGoal extends MoveToBlockGoal {

        BlockPos logPos;
        final Hart mob;

        public HartScratchGoal(Hart pMob, double pSpeedModifier, int pSearchRange) {
            super(pMob, pSpeedModifier, pSearchRange);
            this.mob = pMob;
        }

        @Override
        public boolean canUse() {
            if ((!this.mob.getRightAntler() && !this.mob.getLeftAntler()) || this.mob.getScratchCooldown() > 0 || this.mob.isInLove()){
                return false;
            }
            return super.canUse() && !this.mob.isScratching()
                    && !this.mob.isVehicle()
                    && (this.mob.getRightAntler() || this.mob.getLeftAntler())
                    && this.mob.getScratchCooldown() == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !this.mob.isVehicle() && !this.mob.isScratching() && this.mob.getScratchCooldown() == 0 && !this.mob.isInLove();
        }

        @Override
        public void tick() {
            super.tick();
            if (this.isReachedTarget()){
                this.mob.lookControl.setLookAt(blockPos.getX(), blockPos.getY()+1.75, blockPos.getZ());

                if (!this.mob.isScratching()){
                    this.mob.setScratchingTime(49);
                    this.mob.setScratchCooldown(this.mob.COOLDOWN);
                    this.mob.setScratchPos(this.blockPos);
                }

                this.stop();
            }
        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {

            if (pLevel.getBlockState(pPos.offset(1, 0, 1).above()).is(BlockTags.LOGS)){
                this.logPos = pPos.offset(1, 0, 1).above();
                return true;
            }
            if (pLevel.getBlockState(pPos.offset(-1, 0, 1).above()).is(BlockTags.LOGS)){
                this.logPos = pPos.offset(-1, 0, 1).above();
                return true;
            }
            if (pLevel.getBlockState(pPos.offset(1, 0, -1)).is(BlockTags.LOGS)){
                this.logPos = pPos.offset(1, 0, -1).above();
                return true;
            }
            if (pLevel.getBlockState(pPos.offset(-1, 0, -1).above()).is(BlockTags.LOGS)){
                this.logPos = pPos.offset(-1, 0, -1).above();
                return true;
            }

            return false;
        }
    }

    public ResourceLocation getDefaultLootTable() {
        if ((this.getLeftAntler() && !this.getRightAntler()) || (this.getRightAntler() && !this.getLeftAntler())){
            return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "entities/hart/hart_one_antler");
        }else if (!this.getLeftAntler() && !this.getRightAntler()){
            return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "entities/hart/hart_no_antlers");
        }else {
            return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "entities/hart/hart_two_antlers");
        }
    }
}
