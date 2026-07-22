package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.common.entity.ai.CustomRideGoal;
import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModSounds;
import com.google.common.annotations.VisibleForTesting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class TerraThunk extends TamableAnimal {

    public final AnimationState idleAnimationState = new AnimationState();
    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.DANDELION, Items.GLOW_BERRIES, ModBlocks.BRIGHT_BLOOMS.get());

    private Vec3 spinDelta;
    private float spinYRot;
    private int changeSpinAngleCooldown = 0;
    public int spinCounter = 0;

    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState standUpAnimationState = new AnimationState();
    public final AnimationState sitPoseAnimationState = new AnimationState();
    public final AnimationState bounceAnimationState = new AnimationState();
    int bounceAnimationTimeout;

    private static final EntityDataAccessor<Integer> BOUNCING_TIME = SynchedEntityData.defineId(TerraThunk.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(TerraThunk.class, EntityDataSerializers.LONG);

    @Override
    public boolean isImmobile() {
        return this.isInPoseTransition() || super.isImmobile();
    }

    public TerraThunk(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CustomRideGoal(this, 1));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5){
            @Override
            public boolean canUse() {
                return super.canUse() && !TerraThunk.this.isBouncing();
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && !TerraThunk.this.isBouncing();
            }
        });
        this.goalSelector.addGoal(1, new TerraThunkBreedGoal(this, 1.25));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.25, FOOD_ITEMS,false){
            @Override
            public boolean canUse() {
                return super.canUse() && !TerraThunk.this.isBouncing();
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && !TerraThunk.this.isBouncing();
            }
        });
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1){
            @Override
            public boolean canUse() {
                return super.canUse() && !TerraThunk.this.isBouncing();
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && !TerraThunk.this.isBouncing();
            }
        });

        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this){
            @Override
            public boolean canUse() {
                return super.canUse() && (!TerraThunk.this.isVehicle() || !TerraThunk.this.isPassenger());
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BOUNCING_TIME, 0);
        this.entityData.define(LAST_POSE_CHANGE_TICK, 0L);

    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("BouncingTime", this.getBouncingTime());
        compoundTag.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setBouncingTime(compoundTag.getInt("BouncingTime"));
        long i = compoundTag.getLong("LastPoseTick");
        if (i < 0L) {
            this.setPose(Pose.SITTING);
        }

        this.resetLastPoseChangeTick(i);
    }

    public int getBouncingTime(){
        return this.entityData.get(BOUNCING_TIME);
    }

    public void setBouncingTime(int time){
        this.entityData.set(BOUNCING_TIME, time);
    }

    public boolean isBouncing(){
        return this.getBouncingTime() > 0;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
    }

    @Override
    public void tick() {
        if (this.level().isClientSide)
            this.setUpAnimations();

        super.tick();

        if (this.isBouncing()) {

            this.handleSpin();
            if (this.isAlive() && spinCounter > 5 && !this.isBaby()) {
                for (Entity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.3F))) {
                    Vec3i vec = collideDirectionAndSound().getNormal();
                    entity.addDeltaMovement(new Vec3(vec.getX(), vec.getY(), vec.getZ()).scale(2));
                }
            }
            if (!this.isAlive()) {
                this.setBouncingTime(0);
            }
            if (this.horizontalCollision) {
                if(changeSpinAngleCooldown == 0){
                    changeSpinAngleCooldown = 10;
                    float f = collideDirectionAndSound().getAxis() == Direction.Axis.X ? this.spinYRot - 180 : 180 - this.spinYRot;
                    f += random.nextInt(40) - 20;
                    this.setYRot(f);
                    this.copySpinDelta(f, Vec3.ZERO);
                }

            }
            if (changeSpinAngleCooldown > 0) {
                changeSpinAngleCooldown--;
            }
        }else if (this.isSitting()){
            this.standUp();
        }

        if (this.isSitting() && (this.isInWater() || this.isInLava())) {
            this.standUpInstantly();
        }
    }

    public void standUpInstantly() {
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTickToFullStand(this.level().getGameTime());
        this.setBouncingTime(0);
    }

    private Direction collideDirectionAndSound(){
        HitResult raytraceresult = ProjectileUtil.getHitResultOnMoveVector(this, entity -> false);
        if(raytraceresult instanceof BlockHitResult){
            BlockState state = level().getBlockState(((BlockHitResult) raytraceresult).getBlockPos());
            if(state != null && !this.isSilent()){
            }
            return ((BlockHitResult) raytraceresult).getDirection();
        }
        return Direction.DOWN;
    }

    private void copySpinDelta(float spinRot, Vec3 motionIn) {
        final float f = spinRot * Mth.DEG_TO_RAD;
        final float f1 = this.isBaby() ? 0.3F : 0.5F;
        this.spinYRot = spinRot;
        this.spinDelta = new Vec3(motionIn.x + (double) (-Mth.sin(f) * f1), 0.0D, motionIn.z + (double) (Mth.cos(f) * f1));
        this.setDeltaMovement(this.spinDelta.add(0.0D, 0.0D, 0.0D));
    }

    private void handleSpin() {
        if (this.isVehicle()){
            this.ejectPassengers();
        }
        ++this.spinCounter;
        int prev = this.getBouncingTime();
        this.setBouncingTime(prev-1);
        if (!this.level().isClientSide) {
            if (this.getBouncingTime()<=0) {
                this.setBouncingTime(0);
                this.standUp();
                this.spinCounter = 0;
            } else {
                Vec3 vec3 = this.getDeltaMovement();
                if (this.spinCounter == 1) {
                    copySpinDelta(this.getYRot(), vec3);
                } else {
                    this.setYRot(spinYRot);
                    this.setYHeadRot(spinYRot);
                    this.setYBodyRot(spinYRot);
                    this.setDeltaMovement(this.spinDelta.x, vec3.y, this.spinDelta.z);
                }
            }
        }
    }

    protected void setUpAnimations(){
        idleAnimationState.animateWhen(this.isAlive(), this.tickCount);

        if (this.isVisuallySitting()) {
            this.standUpAnimationState.stop();
            if (this.isVisuallySittingDown()) {
                this.sitAnimationState.startIfStopped(this.tickCount);
                this.sitPoseAnimationState.stop();
            } else {
                this.sitAnimationState.stop();
                this.sitPoseAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.sitAnimationState.stop();
            this.sitPoseAnimationState.stop();
            this.standUpAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }

        if (this.isBouncing() && this.bounceAnimationTimeout<=0){
            this.bounceAnimationTimeout = 20;
            this.bounceAnimationState.start(this.tickCount);
        }else if (this.bounceAnimationTimeout>0){
            --this.bounceAnimationTimeout;
        }
    }

    @Override
    protected void tickRidden(Player pPlayer, Vec3 pTravelVector) {
        super.tickRidden(pPlayer, pTravelVector);

        this.setRot(pPlayer.getYRot(),pPlayer.getXRot() * 0.5F);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!this.isVehicle() && !this.isBaby()){
            if (!this.isBouncing()){
                if (hand == InteractionHand.MAIN_HAND && itemstack.is(Items.MOSS_BLOCK) && !this.isTame()) {

                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    this.playSound(SoundEvents.HORSE_EAT);

                    if (this.random.nextInt(5) == 0) {
                        this.tame(player);
                        this.navigation.stop();
                        this.level().broadcastEntityEvent(this, (byte)7);
                    }else{
                        this.level().broadcastEntityEvent(this, (byte)6);
                    }

                    return InteractionResult.SUCCESS;

                } else if (hand == InteractionHand.MAIN_HAND && !this.level().isClientSide) {
                    if (itemstack.is(Items.MOSS_BLOCK) && this.getHealth() < this.getMaxHealth()) {
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }

                        this.heal(10);
                        this.gameEvent(GameEvent.EAT, this);
                        return InteractionResult.SUCCESS;
                    }
                    if (itemstack.is(Items.COBBLESTONE)) {
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                        this.sitDown();
                        return InteractionResult.SUCCESS;
                    }
                } else if (!player.isShiftKeyDown() && !this.isBaby() && this.isTame() && !this.level().isClientSide) {
                    player.startRiding(this);
                    return InteractionResult.SUCCESS;
                }
            } else if (player.isShiftKeyDown() && itemstack.isEmpty() && hand == InteractionHand.MAIN_HAND && !this.level().isClientSide) {
                this.standUp();
                this.setBouncingTime(0);
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.isBouncing() ? super.getPassengersRidingOffset() : this.getDimensions(Pose.STANDING).height*0.9;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.TERRA_THUNK_IDLE.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.TERRA_THUNK_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.TERRA_THUNK_DEATH.get();
    }

    @javax.annotation.Nullable
    public LivingEntity getControllingPassenger() {
        for(Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player && !this.isBouncing()) {
                return (Player)passenger;
            }
        }
        return null;
    }

    public void travel(Vec3 pos) {
        if (this.isAlive()) {
            LivingEntity livingentity = this.getControllingPassenger();
            if (this.isVehicle() && livingentity instanceof Player) {

                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }
                double moveY = pos.y;

                if (this.isControlledByLocalInstance()){
                    moveY = 0;
                    if (Minecraft.getInstance().options.keyJump.isDown()){
                        if (this.isInWater()){
                            moveY = 5f;
                        }
                    }
                }

                this.setSpeed(0.1F);
                Vec3 vec;

                vec = new Vec3(f, moveY, f1);

                super.travel(vec);

            } else {
                super.travel(pos);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.isVehicle() && this.isBouncing() && !this.onGround() && !this.isInWater()){
            this.addDeltaMovement(new Vec3(0, 0.5f, 0));
        }
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        if (this.isBouncing())
            return 0;
        return super.calculateFallDamage(pFallDistance, pDamageMultiplier);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        if (this.isBouncing())
            return false;
        return super.causeFallDamage(pFallDistance, pMultiplier, pSource);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.resetLastPoseChangeTickToFullStand(pLevel.getLevel().getGameTime());

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public boolean isSitting() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }

    public boolean isVisuallySitting() {
        return this.getPoseTime() < 0L != this.isSitting();
    }

    public int getSitDuration(){
        return 20;
    }

    public int getStandDuration(){
        return 20;
    }

    public boolean isInPoseTransition() {
        long i = this.getPoseTime();
        return i < (long)(this.isSitting() ? this.getSitDuration() : this.getStandDuration());
    }

    public boolean isVisuallySittingDown() {
        return this.isSitting() && this.getPoseTime() < this.getSitDuration() && this.getPoseTime() >= 0L;
    }

    public void sitDown() {
        if (this.isVehicle())
            this.ejectPassengers();

        if (!this.isSitting()) {
            this.setPose(Pose.SITTING);
            this.resetLastPoseChangeTick(-this.level().getGameTime());
            this.refreshDimensions();

            this.setBouncingTime(20*60*6);
        }
    }

    public void standUp() {
        if (this.isSitting()) {
            if (this.isBouncing()){
                this.setBouncingTime(0);
                this.spinCounter = 0;
            }
            this.setPose(Pose.STANDING);
            this.resetLastPoseChangeTick(this.level().getGameTime());
            this.refreshDimensions();
        }
    }

    @VisibleForTesting
    public void resetLastPoseChangeTick(long pLastPoseChangeTick) {
        this.entityData.set(LAST_POSE_CHANGE_TICK, pLastPoseChangeTick);
    }

    private void resetLastPoseChangeTickToFullStand(long pLastPoseChangedTick) {
        this.resetLastPoseChangeTick(Math.max(0L, pLastPoseChangedTick - this.getStandDuration() - 1L));
    }

    public long getPoseTime() {
        return this.level().getGameTime() - Math.abs(this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return !this.isBouncing() && !this.isVehicle();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.TERRA_THUNK.get().create(pLevel);
    }

    class TerraThunkBreedGoal extends BreedGoal{

        private final double speedModifier;
        public TerraThunkBreedGoal(Animal pAnimal, double pSpeedModifier) {
            super(pAnimal, pSpeedModifier);
            this.speedModifier = pSpeedModifier;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !TerraThunk.this.isBouncing();
        }

        public void tick() {
            this.animal.getLookControl().setLookAt(this.partner, 10.0F, (float)this.animal.getMaxHeadXRot());
            this.animal.getNavigation().moveTo(this.partner, this.speedModifier);
            ++this.loveTime;
            if (this.loveTime >= this.adjustedTickDelay(60) && this.animal.getBoundingBox().inflate(0.5f).intersects(this.partner.getBoundingBox().inflate(0.5f))) {
                this.breed();
            }

        }
    }

}
