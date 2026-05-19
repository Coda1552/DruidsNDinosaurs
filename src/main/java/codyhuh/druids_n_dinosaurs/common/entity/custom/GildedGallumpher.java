package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.common.entity.ai.CustomRideGoal;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class GildedGallumpher extends TamableAnimal {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState mudAnimationState = new AnimationState();
    int mudAnimationStateTimeout;
    public final AnimationState eatAnimationState = new AnimationState();
    int eatAnimationStateTimeout;

    private static final EntityDataAccessor<Integer> MUD_TIME = SynchedEntityData.defineId(GildedGallumpher.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MUD_COOLDOWN = SynchedEntityData.defineId(GildedGallumpher.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> EATING_TIME = SynchedEntityData.defineId(GildedGallumpher.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<ItemStack> FRONT_SHERD = SynchedEntityData.defineId(GildedGallumpher.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> BACK_SHERD = SynchedEntityData.defineId(GildedGallumpher.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> LEFT_SHERD = SynchedEntityData.defineId(GildedGallumpher.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> RIGHT_SHERD = SynchedEntityData.defineId(GildedGallumpher.class, EntityDataSerializers.ITEM_STACK);

    public GildedGallumpher(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CustomRideGoal(this, 0.5f));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.5));
        this.goalSelector.addGoal(3, new GildedGallumpherBreedGoal(this, 1.25));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(Items.GLOW_BERRIES),false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(5, new GrazeGoal(this));
        this.goalSelector.addGoal(5, new GildedGallumpher.goToMudGoal(this, 1, 1));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this){
            @Override
            public boolean canUse() {
                return super.canUse() && (!GildedGallumpher.this.isVehicle() || !GildedGallumpher.this.isPassenger());
            }
        });
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MUD_TIME, 0);
        this.entityData.define(MUD_COOLDOWN, 20*60*10);
        this.entityData.define(EATING_TIME, 0);
        this.entityData.define(FRONT_SHERD, ItemStack.EMPTY);
        this.entityData.define(BACK_SHERD, ItemStack.EMPTY);
        this.entityData.define(LEFT_SHERD, ItemStack.EMPTY);
        this.entityData.define(RIGHT_SHERD, ItemStack.EMPTY);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("MudPlayingTime", this.getMudTime());
        compoundTag.putInt("MudCooldown", this.getMudCooldown());
        compoundTag.putInt("EatingTime", this.getEatingTime());

        if (!this.getBackSherd().isEmpty()) {
            compoundTag.put("BackSherd", this.getBackSherd().save(new CompoundTag()));
        }
        if (!this.getFrontSherd().isEmpty()) {
            compoundTag.put("FrontSherd", this.getFrontSherd().save(new CompoundTag()));
        }
        if (!this.getRightSherd().isEmpty()) {
            compoundTag.put("RightSherd", this.getRightSherd().save(new CompoundTag()));
        }
        if (!this.getLeftSherd().isEmpty()) {
            compoundTag.put("LeftSherd", this.getLeftSherd().save(new CompoundTag()));
        }
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setMudTime(compoundTag.getInt("MudPlayingTime"));
        this.setMudCooldown(compoundTag.getInt("MudCooldown"));
        this.setEatingTime(compoundTag.getInt("EatingTime"));

        CompoundTag compoundtag = compoundTag.getCompound("BackSherd");
        if (compoundtag != null && !compoundtag.isEmpty()) {
            ItemStack itemstack = ItemStack.of(compoundtag);
            this.setBackSherd(itemstack);
        }
        CompoundTag compoundtag2 = compoundTag.getCompound("FrontSherd");
        if (compoundtag2 != null && !compoundtag2.isEmpty()) {
            ItemStack itemstack = ItemStack.of(compoundtag2);
            this.setFrontSherd(itemstack);
        }
        CompoundTag compoundtag3 = compoundTag.getCompound("LeftSherd");
        if (compoundtag3 != null && !compoundtag3.isEmpty()) {
            ItemStack itemstack = ItemStack.of(compoundtag3);
            this.setLeftSherd(itemstack);
        }
        CompoundTag compoundtag4 = compoundTag.getCompound("RightSherd");
        if (compoundtag4 != null && !compoundtag4.isEmpty()) {
            ItemStack itemstack = ItemStack.of(compoundtag4);
            this.setRightSherd(itemstack);
        }
    }

    public boolean hasSherds(){
        return !this.getFrontSherd().isEmpty();
    }

    public boolean isFull(){
        return this.getSherdCount()>=4;
    }

    public int getSherdCount(){
        int count = 0;
        if (!this.getFrontSherd().isEmpty())
            count++;
        if (!this.getRightSherd().isEmpty())
            count++;
        if (!this.getBackSherd().isEmpty())
            count++;
        if (!this.getLeftSherd().isEmpty())
            count++;
        return count;
    }

    public ItemStack getBackSherd() {
        return this.getEntityData().get(BACK_SHERD);
    }

    public void setBackSherd(ItemStack pStack) {
        if (!pStack.isEmpty()) {
            pStack = pStack.copyWithCount(1);
        }
        this.getEntityData().set(BACK_SHERD, pStack);
    }

    public ItemStack getFrontSherd() {
        return this.getEntityData().get(FRONT_SHERD);
    }

    public void setFrontSherd(ItemStack pStack) {
        if (!pStack.isEmpty()) {
            pStack = pStack.copyWithCount(1);
        }
        this.getEntityData().set(FRONT_SHERD, pStack);
    }

    public ItemStack getLeftSherd() {
        return this.getEntityData().get(LEFT_SHERD);
    }

    public void setLeftSherd(ItemStack pStack) {
        if (!pStack.isEmpty()) {
            pStack = pStack.copyWithCount(1);
        }
        this.getEntityData().set(LEFT_SHERD, pStack);
    }

    public ItemStack getRightSherd() {
        return this.getEntityData().get(RIGHT_SHERD);
    }

    public void setRightSherd(ItemStack pStack) {
        if (!pStack.isEmpty()) {
            pStack = pStack.copyWithCount(1);
        }
        this.getEntityData().set(RIGHT_SHERD, pStack);
    }

    public boolean isEating(){
        return this.getEatingTime()>0;
    }

    public boolean isPlayingInMud(){
        return this.getMudTime()>0;
    }

    public boolean wantsToPlayInMud(){
        return this.getMudCooldown()<=0;
    }

    public int getMudTime(){
        return this.entityData.get(MUD_TIME);
    }

    public void setMudTime(int time){
        this.entityData.set(MUD_TIME, time);
    }

    public int getEatingTime(){
        return this.entityData.get(EATING_TIME);
    }

    public void setEatingTime(int time){
        this.entityData.set(EATING_TIME, time);
    }

    public int getMudCooldown(){
        return this.entityData.get(MUD_COOLDOWN);
    }

    public void setMudCooldown(int time){
        this.entityData.set(MUD_COOLDOWN, time);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isPlayingInMud()){
            this.getNavigation().stop();
            this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
        }

        if (!this.level().isClientSide){
            if (this.isPlayingInMud()){
                int prevTime = this.getMudTime();
                this.setMudTime(prevTime-1);
            }else if (!this.wantsToPlayInMud()){
                int prevTime = this.getMudCooldown();
                this.setMudCooldown(prevTime-1);
            }

            if (this.isEating()){
                int prevTime = this.getEatingTime();
                this.setEatingTime(prevTime-1);
            }
        }else {
            this.setUpAnimations();
        }
    }

    protected void setUpAnimations(){
        idleAnimationState.animateWhen(this.isAlive(), this.tickCount);

        if (this.isPlayingInMud() && this.mudAnimationStateTimeout == 0){
            this.mudAnimationState.start(this.tickCount);
            this.mudAnimationStateTimeout = (6*20);
        }else if (this.mudAnimationStateTimeout>0){
            this.mudAnimationStateTimeout--;
        }

        if (this.isEating() && this.eatAnimationStateTimeout == 0){
            this.eatAnimationState.start(this.tickCount);
            this.eatAnimationStateTimeout = (80);
        }else if (this.eatAnimationStateTimeout>0){
            this.eatAnimationStateTimeout--;
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

        if (hand == InteractionHand.MAIN_HAND && itemstack.is(Items.MOSS_BLOCK) && !this.isTame() && !this.isBaby()) {

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
                this.playSound(SoundEvents.HORSE_EAT, 0.75f, this.getVoicePitch()-0.75f);
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }else if (itemstack.is(ItemTags.PICKAXES) && this.hasSherds()){

                if (!this.getLeftSherd().isEmpty()){
                    this.spawnAtLocation(this.getLeftSherd());
                    this.setLeftSherd(ItemStack.EMPTY);

                }else if (!this.getBackSherd().isEmpty()){
                    this.spawnAtLocation(this.getBackSherd());
                    this.setBackSherd(ItemStack.EMPTY);

                }else if (!this.getRightSherd().isEmpty()){
                    this.spawnAtLocation(this.getRightSherd());
                    this.setRightSherd(ItemStack.EMPTY);

                }else if (!this.getFrontSherd().isEmpty()){
                    this.spawnAtLocation(this.getFrontSherd());
                    this.setFrontSherd(ItemStack.EMPTY);
                }
                this.playSound(SoundEvents.DECORATED_POT_SHATTER, 0.75f, this.getVoicePitch());

                return InteractionResult.SUCCESS;
            }
        } else if (itemstack.isEmpty() && !player.isShiftKeyDown() && !this.isBaby()
                && this.isTame() && !this.isPlayingInMud() && !this.isEating() && !this.level().isClientSide) {
            player.startRiding(this);

            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @javax.annotation.Nullable
    public LivingEntity getControllingPassenger() {
        for(Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                return (Player)passenger;
            }
        }
        return null;
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getDimensions(Pose.STANDING).height*0.9;
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

                if (this.isControlledByLocalInstance() && this.isInWater()){
                    moveY = Minecraft.getInstance().options.keyJump.isDown() ? 5F : 0F;
                }

                this.setSpeed(0.1F);

                Vec3 vec = new Vec3(f, moveY, f1);

                super.travel(vec);

            } else {
                if (this.isPlayingInMud() || this.isEating()) {
                    if (this.getNavigation().getPath() != null) {
                        this.getNavigation().stop();
                    }
                    pos = Vec3.ZERO;
                }
                super.travel(pos);
            }
        }
    }

    public static class goToMudGoal extends MoveToBlockGoal {
        private final GildedGallumpher gallumpher;
        private final double acceptedDistance;

        public goToMudGoal(GildedGallumpher animal, double pSpeedModifier, double acceptedDistance) {
            super(animal, pSpeedModifier, 16);
            this.gallumpher = animal;
            this.acceptedDistance = acceptedDistance;
        }

        public boolean canUse() {
            return this.gallumpher.wantsToPlayInMud() && super.canUse();
        }

        public boolean canContinueToUse() {
            return this.gallumpher.wantsToPlayInMud() && !this.gallumpher.isPlayingInMud() && super.canContinueToUse();
        }

        public void tick() {
            super.tick();
            if (!this.gallumpher.isInWater() && this.isReachedTarget()) {
                this.gallumpher.getNavigation().stop();
                this.gallumpher.setMudTime(6*20);
                this.gallumpher.setMudCooldown(20*60*10);
                this.stop();
            }
        }

        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            return pLevel.isEmptyBlock(pPos.above()) && pLevel.getBlockState(pPos).is(Blocks.MUD);
        }

        public double acceptedDistance() {
            return acceptedDistance;
        }
    }

    public BlockPos getHeadBlockPos() {
        final float angle = (0.0174532925F * this.yBodyRot);
        final double headX = 1.25F * getScale() * Mth.sin(Mth.PI + angle);
        final double headZ = 1.25F * getScale() * Mth.cos(angle);

        return new BlockPos((int)(this.getX() + headX), this.getBlockY(), (int)(this.getZ() + headZ));
    }

    class GrazeGoal extends Goal {
        private static final int EAT_ANIMATION_TICKS = 40;
        private static final Predicate<BlockState> IS_TALL_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS);
        private final GildedGallumpher mob;
        private final Level level;

        public GrazeGoal(GildedGallumpher pMob) {
            this.mob = pMob;
            this.level = pMob.level();
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        public boolean canUse() {
            if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
                return false;
            } else {
                BlockPos blockpos = this.mob.getHeadBlockPos();
                if (IS_TALL_GRASS.test(this.level.getBlockState(blockpos))) {
                    return true;
                } else {
                    return this.level.getBlockState(blockpos.below()).is(Blocks.GRASS_BLOCK);
                }
            }
        }

        public void start() {
            this.level.broadcastEntityEvent(this.mob, (byte) 10);
            this.mob.getNavigation().stop();
            this.mob.setEatingTime(80);
        }

        public void stop() {
            this.mob.setEatingTime(0);
        }

        public boolean canContinueToUse() {
            return this.mob.isEating();
        }

        public void tick() {
            if (this.mob.getEatingTime() == 10) {
                BlockPos blockpos = this.mob.getHeadBlockPos();
                if (IS_TALL_GRASS.test(this.level.getBlockState(blockpos))) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.mob)) {
                        this.level.destroyBlock(blockpos, false);
                    }
                    this.mob.ate();
                } else {
                    BlockPos blockpos1 = blockpos.below();
                    if (this.level.getBlockState(blockpos1).is(Blocks.GRASS_BLOCK)) {
                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.mob)) {
                            this.level.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                            this.level.setBlock(blockpos1, Blocks.DIRT.defaultBlockState(), 2);
                        }
                        this.mob.ate();
                    }
                }

            }
        }
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return !this.isPlayingInMud() && !this.isVehicle();
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.GLOW_BERRIES);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.GILDED_GALLUMPHER.get().create(pLevel);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.SNIFFER_IDLE;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SNIFFER_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.SNIFFER_DEATH;
    }

    protected void dropEquipment() {
        super.dropEquipment();
        ItemStack itemstack = this.getBackSherd();
        ItemStack itemstack2 = this.getFrontSherd();
        ItemStack itemstack3 = this.getLeftSherd();
        ItemStack itemstack4 = this.getRightSherd();

        if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
            this.spawnAtLocation(itemstack);
            this.setBackSherd(ItemStack.EMPTY);
        }
        if (!itemstack2.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack2)) {
            this.spawnAtLocation(itemstack2);
            this.setFrontSherd(ItemStack.EMPTY);
        }
        if (!itemstack3.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack3)) {
            this.spawnAtLocation(itemstack3);
            this.setLeftSherd(ItemStack.EMPTY);
        }
        if (!itemstack4.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack4)) {
            this.spawnAtLocation(itemstack4);
            this.setRightSherd(ItemStack.EMPTY);
        }
    }


    @javax.annotation.Nullable
    private Vec3 getDismountLocationInDirection(Vec3 pDirection, LivingEntity pPassenger) {
        double d0 = this.getX() + pDirection.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + pDirection.z;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Pose pose : pPassenger.getDismountPoses()) {
            blockpos$mutableblockpos.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + 0.75D;

            while(true) {
                double d4 = this.level().getBlockFloorHeight(blockpos$mutableblockpos);
                if ((double)blockpos$mutableblockpos.getY() + d4 > d3) {
                    break;
                }

                if (DismountHelper.isBlockFloorValid(d4)) {
                    AABB aabb = pPassenger.getLocalBoundsForPose(pose);
                    Vec3 vec3 = new Vec3(d0, (double)blockpos$mutableblockpos.getY() + d4, d2);
                    if (DismountHelper.canDismountTo(this.level(), pPassenger, aabb.move(vec3))) {
                        pPassenger.setPose(pose);
                        return vec3;
                    }
                }

                blockpos$mutableblockpos.move(Direction.UP);
                if (!((double)blockpos$mutableblockpos.getY() < d3)) {
                    break;
                }
            }
        }

        return null;
    }

    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        Vec3 vec3 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)pLivingEntity.getBbWidth(), this.getYRot() + (pLivingEntity.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
        Vec3 vec31 = this.getDismountLocationInDirection(vec3, pLivingEntity);
        if (vec31 != null) {
            return vec31;
        } else {
            Vec3 vec32 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)pLivingEntity.getBbWidth(), this.getYRot() + (pLivingEntity.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
            Vec3 vec33 = this.getDismountLocationInDirection(vec32, pLivingEntity);
            return vec33 != null ? vec33 : this.position();
        }
    }

    class GildedGallumpherBreedGoal extends BreedGoal{

        private final double speedModifier;
        public GildedGallumpherBreedGoal(Animal pAnimal, double pSpeedModifier) {
            super(pAnimal, pSpeedModifier);
            this.speedModifier = pSpeedModifier;
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
