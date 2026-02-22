package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.common.entity.ai.AnimatedAttackGoal;
import codyhuh.druids_n_dinosaurs.common.entity.ai.ElephantBegGoal;
import codyhuh.druids_n_dinosaurs.common.entity.ai.LookForItemGoal;
import codyhuh.druids_n_dinosaurs.common.entity.custom.base.IAnimatedAttacker;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class JadeElephant extends Animal implements IAnimatedAttacker, InventoryCarrier {

    private final SimpleContainer inventory = new SimpleContainer(1);
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState interestedAnimationState = new AnimationState();
    public int attackAnimationTimeout;

    private static final EntityDataAccessor<Boolean> IS_ATTACKING = SynchedEntityData.defineId(JadeElephant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_INTERESTED_ID = SynchedEntityData.defineId(JadeElephant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> INTERESTED_TIME = SynchedEntityData.defineId(JadeElephant.class, EntityDataSerializers.INT);

    public JadeElephant(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setCanPickUpLoot(true);
    }

    static final Predicate<ItemEntity> ALLOWED_ITEMS =
            itemEntity -> !itemEntity.hasPickUpDelay() && itemEntity.isAlive() && itemEntity.getItem().is(Tags.Items.STONE);

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ElephantBegGoal(this, 16));
        this.goalSelector.addGoal(1, new AnimatedAttackGoal(this, 1.75D, true, 4, 16));
        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 1, 32));

        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0F, Ingredient.of(Tags.Items.STONE), false));
        this.goalSelector.addGoal(2, new LookForItemGoal(this, ALLOWED_ITEMS, 1.25f));

        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.15F)
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ATTACKING, false);
        this.entityData.define(DATA_INTERESTED_ID, false);
        this.entityData.define(INTERESTED_TIME, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsAttacking", this.isAttacking());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAttacking(compound.getBoolean("IsAttacking"));
    }

    public int getInterestedTime() {
        return this.entityData.get(INTERESTED_TIME);
    }

    public void setTimedInterest(int time) {
        this.entityData.set(INTERESTED_TIME, time);
    }

    public boolean isInterested() {
        return this.entityData.get(DATA_INTERESTED_ID) || this.getInterestedTime() > 0;
    }

    public void setIsInterested(boolean pIsInterested) {
        this.entityData.set(DATA_INTERESTED_ID, pIsInterested);
    }

    @Override
    public boolean isAttacking() {
        return this.entityData.get(IS_ATTACKING);
    }

    @Override
    public void setAttacking(boolean attacking) {
        this.entityData.set(IS_ATTACKING, attacking);
    }

    @Override
    public int attackAnimationTimeout() {
        return this.attackAnimationTimeout;
    }

    @Override
    public void setAttackAnimationTimeout(int attackAnimationTimeout) {
        this.attackAnimationTimeout = attackAnimationTimeout;
    }

    @Override
    public boolean wantsToPickUp(ItemStack pStack) {
        return this.getInterestedTime() == 0 && pStack.is(Tags.Items.STONE) && super.wantsToPickUp(pStack);
    }

    @Override
    protected void pickUpItem(ItemEntity pItemEntity) {
        this.onItemPickup(pItemEntity);

        this.take(pItemEntity, 1);
        ItemStack copy = pItemEntity.getItem().copyWithCount(1);
        ItemStack itemstack = removeOneItemFromItemEntity(pItemEntity);
        if (itemstack.is(Tags.Items.STONE)){
            this.setTimedInterest(60);
            this.setItemInHand(InteractionHand.MAIN_HAND, copy);
        }
    }

    @Override
    public void tick() {

        super.tick();

        if (this.level().isClientSide)
            this.setupAnimationStates();

        if (this.getInterestedTime() > 0){
            if (!this.getNavigation().isDone()){
                this.getNavigation().stop();
            }
            this.goalSelector.getRunningGoals().forEach(Goal::stop);

            int futureTime = this.getInterestedTime()-1;

            if (futureTime == 0){
                int quantity = this.getItemInHand(InteractionHand.MAIN_HAND).getCount();
                this.getInventory().clearContent();
                this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                throwItemsTowardRandomPos(this, new ItemStack(ModItems.JADE_SHARD.get(), quantity));
            }
            this.setTimedInterest(futureTime);
        }
    }

    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);

        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 20;

            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        this.interestedAnimationState.animateWhen(this.isInterested(), this.tickCount);
    }

    private static ItemStack removeOneItemFromItemEntity(ItemEntity pItemEntity) {
        ItemStack itemstack = pItemEntity.getItem();
        ItemStack itemstack1 = itemstack.split(1);
        if (itemstack.isEmpty()) {
            pItemEntity.discard();
        } else {
            pItemEntity.setItem(itemstack);
        }
        return itemstack1;
    }

    private static void throwItemsTowardRandomPos(JadeElephant pPiglin, ItemStack pStack) {
        throwItemsTowardPos(pPiglin, pStack, getRandomNearbyPos(pPiglin));
    }

    private static void throwItemsTowardPos(JadeElephant pPiglin, ItemStack pStack, Vec3 pPos) {
        if (!pStack.isEmpty()) {
            BehaviorUtils.throwItem(pPiglin, pStack, pPos.add(0.0D, 1.0D, 0.0D));
        }

    }

    private static Vec3 getRandomNearbyPos(JadeElephant pPiglin) {
        Vec3 vec3 = LandRandomPos.getPos(pPiglin, 4, 2);
        return vec3 == null ? pPiglin.position() : vec3;
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.hasEffect(MobEffects.DAMAGE_BOOST) && itemstack.is(Items.DANDELION)){
            this.removeInteractionItem(pPlayer, itemstack);
            this.spawnAtLocation(ModItems.FOOLS_SCEPTER.get());
            return InteractionResult.SUCCESS;
        }else if (itemstack.is(Tags.Items.STONE)){
            ItemStack copy = itemstack.copyWithCount(1);
            itemstack.shrink(1);
            this.setItemInHand(InteractionHand.MAIN_HAND, copy);
            this.setTimedInterest(60);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
    }

    private void removeInteractionItem(Player pPlayer, ItemStack pStack) {
        if (!pPlayer.getAbilities().instabuild) {
            pStack.shrink(1);
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public boolean isBaby() {
        return false;
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
    public SimpleContainer getInventory() {
        return inventory;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 1.0F);
    }
}
