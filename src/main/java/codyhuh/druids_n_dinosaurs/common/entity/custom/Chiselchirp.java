package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.common.items.OrnateEgg;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Chiselchirp extends Animal implements FlyingAnimal {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState chiselAnimationState = new AnimationState();
    int chiselAnimationStateTimeout;

    private static final EntityDataAccessor<ItemStack> SHERD = SynchedEntityData.defineId(Chiselchirp.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> CHISEL_TIME = SynchedEntityData.defineId(Chiselchirp.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHISEL_COOLDOWN = SynchedEntityData.defineId(Chiselchirp.class, EntityDataSerializers.INT);

    public Chiselchirp(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new ChiselchirpFlyingMoveControl(this, 20);
        this.lookControl = new SmoothSwimmingLookControl(this, 90);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.05F)
                .add(Attributes.FLYING_SPEED, 1F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.25, Ingredient.of(Tags.Items.SEEDS),false));
        this.goalSelector.addGoal(1, new EtchOntoGallumpherGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new Chiselchirp.ChiselchirpWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.25));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Tags.Items.SEEDS);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHERD, ItemStack.EMPTY);
        this.entityData.define(CHISEL_TIME, 0);
        this.entityData.define(CHISEL_COOLDOWN, 20*60*3);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);

        compoundTag.putInt("ChiselingTime", this.getChiselingTime());
        compoundTag.putInt("ChiselingCooldown", this.getChiselCooldown());
        if (!this.getHeldSherd().isEmpty()) {
            compoundTag.put("HeldSherd", this.getHeldSherd().save(new CompoundTag()));
        }
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);

        this.setChiselingTime(compoundTag.getInt("ChiselingTime"));
        this.setChiselCooldown(compoundTag.getInt("ChiselingCooldown"));
        CompoundTag compoundtag = compoundTag.getCompound("HeldSherd");
        if (compoundtag != null && !compoundtag.isEmpty()) {
            ItemStack itemstack = ItemStack.of(compoundtag);
            this.setHeldSherd(itemstack);
        }
    }

    public boolean isChiseling(){
        return this.getChiselingTime()>0;
    }

    public boolean hasSherd(){
        return !this.getHeldSherd().isEmpty();
    }

    public int getChiselingTime() {
        return this.getEntityData().get(CHISEL_TIME);
    }

    public void setChiselingTime(int chiselingTime){
        this.getEntityData().set(CHISEL_TIME, chiselingTime);
    }

    public int getChiselCooldown() {
        return this.getEntityData().get(CHISEL_COOLDOWN);
    }

    public void setChiselCooldown(int chiselingTime){
        this.getEntityData().set(CHISEL_COOLDOWN, chiselingTime);
    }

    public ItemStack getHeldSherd() {
        return this.getEntityData().get(SHERD);
    }

    public void setHeldSherd(ItemStack pStack) {
        if (!pStack.isEmpty()) {
            pStack = pStack.copyWithCount(1);
        }
        this.getEntityData().set(SHERD, pStack);
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (stack.is(ItemTags.DECORATED_POT_SHERDS) && !this.hasSherd()){
            if (!this.level().isClientSide){
                ItemStack copy = stack.copy();
                this.setHeldSherd(copy);
                removeInteractionItem(pPlayer, stack);
            }
            return InteractionResult.SUCCESS;
        }
        if (stack.isEmpty() && this.hasSherd()){
            if (!this.level().isClientSide){
                ItemStack copy = this.getHeldSherd();
                this.setHeldSherd(ItemStack.EMPTY);
                this.spawnAtLocation(copy);
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
    }

    private void removeInteractionItem(Player pPlayer, ItemStack pStack) {
        if (!pPlayer.getAbilities().instabuild) {
            pStack.shrink(1);
        }
    }

    protected boolean isFlapping() {
        return this.isFlying() && this.tickCount % 20 == 0;
    }

    protected void onFlap() {
        this.playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
    }

    static class ChiselchirpFlyingMoveControl extends MoveControl{
        private final int maxTurn;
        private final Chiselchirp chiselchirp;

        public ChiselchirpFlyingMoveControl(Chiselchirp pMob, int pMaxTurn) {
            super(pMob);
            this.maxTurn = pMaxTurn;
            this.chiselchirp = pMob;
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                this.operation = MoveControl.Operation.WAIT;
                this.mob.setNoGravity(true);
                double d0 = this.wantedX - this.mob.getX();
                double d1 = this.wantedY - this.mob.getY();
                double d2 = this.wantedZ - this.mob.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setYya(0.0F);
                    this.mob.setZza(0.0F);
                    return;
                }

                float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, 90.0F));
                float f1;
                if (this.mob.onGround()) {
                    f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                } else {
                    f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
                }

                this.mob.setSpeed(f1);
                double d4 = Math.sqrt(d0 * d0 + d2 * d2);
                if (Math.abs(d1) > (double)1.0E-5F || Math.abs(d4) > (double)1.0E-5F) {
                    float f2 = (float)(-(Mth.atan2(d1, d4) * (double)(180F / (float)Math.PI)));
                    this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f2, (float)this.maxTurn));
                    this.mob.setYya(d1 > 0.0D ? f1 : -f1);
                }
            } else {
                if (!this.chiselchirp.getMoveControl().hasWanted()) {
                    this.mob.setNoGravity(false);
                }

                this.mob.setYya(0.0F);
                this.mob.setZza(0.0F);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isChiseling()){
            int prev = this.getChiselingTime();
            this.setChiselingTime(prev-1);
        }
        if (this.getChiselCooldown()>0){
            int prev = this.getChiselCooldown();
            this.setChiselCooldown(prev-1);
        }

        if (this.level().isClientSide){
            this.setUpAnimations();
        }
    }
    public void aiStep() {
        super.aiStep();
        Vec3 vec3 = this.getDeltaMovement();

        if (!this.onGround() && vec3.y < 0.0D && this.navigation.isDone()) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }

        if (this.onGround() && (this.navigation.isInProgress()))
            this.addDeltaMovement(new Vec3(0, 0.05,0));


    }

    protected void setUpAnimations(){
        idleAnimationState.animateWhen(this.isAlive(), this.tickCount);

        if (this.isChiseling() && this.chiselAnimationStateTimeout == 0){
            this.chiselAnimationState.start(this.tickCount);
            this.chiselAnimationStateTimeout = (45);
        }else if (this.chiselAnimationStateTimeout >0){
            this.chiselAnimationStateTimeout--;
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.CHISELCHIRP.get().create(pLevel);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    protected void dropEquipment() {
        super.dropEquipment();
        ItemStack itemstack = this.getHeldSherd();
        if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
            this.spawnAtLocation(itemstack);
            this.setHeldSherd(ItemStack.EMPTY);
        }
    }

    static class ChiselchirpWanderGoal extends WaterAvoidingRandomFlyingGoal {
        public ChiselchirpWanderGoal(PathfinderMob p_186224_, double p_186225_) {
            super(p_186224_, p_186225_);
        }

        @javax.annotation.Nullable
        protected Vec3 getPosition() {
            Vec3 vec3 = null;
            if (this.mob.isInWater()) {
                vec3 = LandRandomPos.getPos(this.mob, 15, 15);
            }

            return vec3 == null ? super.getPosition() : vec3;
        }
    }

    static class EtchOntoGallumpherGoal extends Goal {

        private final Chiselchirp chiselchirp;

        @javax.annotation.Nullable
        private GildedGallumpher gallumpher;

        private final double speedModifier;
        private int timeToRecalcPath;

        public EtchOntoGallumpherGoal(Chiselchirp pAnimal, double pSpeedModifier) {
            this.chiselchirp = pAnimal;
            this.speedModifier = pSpeedModifier;
        }

        public boolean canUse() {
            if (!this.chiselchirp.hasSherd() || this.chiselchirp.getChiselCooldown() > 0) {
                return false;
            } else {
                List<? extends GildedGallumpher> list = this.chiselchirp.level().getEntitiesOfClass(GildedGallumpher.class,
                        this.chiselchirp.getBoundingBox().inflate(16.0D, 4.0D, 16.0D));
                GildedGallumpher animal = null;
                double d0 = Double.MAX_VALUE;

                if (!list.isEmpty()){
                    for(GildedGallumpher animal1 : list) {
                        if (animal1.getAge() >= 0 && !animal1.isFull()) {
                            double d1 = this.chiselchirp.distanceToSqr(animal1);
                            if (!(d1 > d0)) {
                                d0 = d1;
                                animal = animal1;
                            }
                        }
                    }
                }

                if (animal == null) {
                    return false;
                } else if (animal.isFull()) {
                    return false;
                } else if (d0 < 1.0D) {
                    return false;
                } else {
                    this.gallumpher = animal;
                    return true;
                }
            }
        }

        public boolean canContinueToUse() {
            if (!this.chiselchirp.hasSherd() || this.chiselchirp.getChiselCooldown() > 0 || this.gallumpher == null) {
                return false;
            } else if (!this.gallumpher.isAlive() || this.gallumpher.isFull()) {
                return false;
            } else {
                double d0 = this.chiselchirp.distanceToSqr(this.gallumpher);
                return !(d0 < 1.0D) && !(d0 > 256.0D);
            }
        }

        public void start() {
            this.timeToRecalcPath = 0;
        }

        public void stop() {
            this.gallumpher = null;
        }

        public void tick() {
            BlockPos blockpos = gallumpher.blockPosition();
            this.chiselchirp.lookControl.setLookAt(gallumpher);
            if (!blockpos.closerToCenterThan(this.chiselchirp.position(), this.acceptedDistance())) {
                if (--this.timeToRecalcPath <= 0) {
                    this.timeToRecalcPath = this.adjustedTickDelay(10);
                    this.chiselchirp.getNavigation().moveTo(this.gallumpher, this.speedModifier);
                }
            } else {
                this.gallumpher.getNavigation().stop();
                this.chiselchirp.getNavigation().stop();
                if (!chiselchirp.isChiseling() && this.chiselchirp.hasSherd()){
                    this.chiselchirp.setChiselingTime(45);
                }else if (chiselchirp.getChiselingTime() == 15){
                    if (this.gallumpher.getFrontSherd().isEmpty()){
                        this.gallumpher.setFrontSherd(this.chiselchirp.getHeldSherd().copy());
                    }else if (this.gallumpher.getRightSherd().isEmpty()){
                        this.gallumpher.setRightSherd(this.chiselchirp.getHeldSherd().copy());
                    }else if (this.gallumpher.getBackSherd().isEmpty()){
                        this.gallumpher.setBackSherd(this.chiselchirp.getHeldSherd().copy());
                    }else if (this.gallumpher.getLeftSherd().isEmpty()){
                        this.gallumpher.setLeftSherd(this.chiselchirp.getHeldSherd().copy());
                    }
                    this.chiselchirp.playSound(SoundEvents.DECORATED_POT_PLACE);
                    this.chiselchirp.setChiselCooldown(3*20*60);
                }
            }
        }

        public double acceptedDistance() {
            return 5.0D;
        }
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.PARROT_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }


}
