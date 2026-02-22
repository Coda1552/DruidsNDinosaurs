package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.common.items.OrnateEgg;
import codyhuh.druids_n_dinosaurs.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TuffTotemPole extends Animal implements InventoryCarrier {

    private final SimpleContainer inventory = new SimpleContainer(1);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState cawingAnimationState = new AnimationState();
    public int cawAnimationTimeout;
    int phaseTime = 20*60*2;

    private static final EntityDataAccessor<Integer> EGG_PHASE = SynchedEntityData.defineId(TuffTotemPole.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CURRENT_PHASE_TIME = SynchedEntityData.defineId(TuffTotemPole.class, EntityDataSerializers.INT);

    public TuffTotemPole(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.1F)
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(EGG_PHASE, 0);
        this.entityData.define(CURRENT_PHASE_TIME, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("EggPhase", this.getEggPhase());
        compound.putInt("PhaseTime", this.getPhaseTime());
        ListTag listTag = new ListTag();
        for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack itemStack = this.inventory.getItem(i);
            if (itemStack.isEmpty()) continue;
            CompoundTag compoundTag2 = new CompoundTag();
            compoundTag2.putByte("Slot", (byte)i);
            itemStack.save(compoundTag2);
            listTag.add(compoundTag2);
        }
        compound.put("Items", listTag);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setEggPhase(compound.getInt("EggPhase"));
        this.setPhaseTime(compound.getInt("PhaseTime"));

        ListTag listtag = compound.getList("Items", 10);
        for (int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundTag2 = listtag.getCompound(i);
            int j = compoundTag2.getByte("Slot") & 0xFF;
            if (j >= this.inventory.getContainerSize()) continue;
            this.inventory.setItem(j, ItemStack.of(compoundTag2));
        }
    }

    public int getEggPhase() {
        return this.entityData.get(EGG_PHASE);
    }

    public void setEggPhase(int phase) {
        this.entityData.set(EGG_PHASE, phase);
    }

    public int getPhaseTime() {
        return this.entityData.get(CURRENT_PHASE_TIME);
    }

    public void setPhaseTime(int time) {
        this.entityData.set(CURRENT_PHASE_TIME, time);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            if (this.getEggPhase() == 1 && this.getRandom().nextBoolean()){
                this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(0.7D), this.getY() - 15.5 + this.getRandomY()/5,
                        this.getRandomZ(0.7D), 0, 0.02, 0.0D);
            }

            if (this.getRandom().nextInt(2) == 0 && this.getEggPhase() == 2) {

                this.level().addParticle(ModParticles.JADE_OMEN.get(),
                        this.getRandomX(0.5), this.getY() + 3.0D, this.getRandomZ(0.5),
                        this.getX() - this.getRandomX(2.5D),
                        this.getY() - this.getRandomY(),
                        this.getZ() - this.getRandomZ(2.5));
            }

            if (this.getRandom().nextBoolean() && this.getEggPhase() == 3){
                this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D),
                        this.getY() - 15 + this.getRandomY()/4,
                        this.getRandomZ(1.0D),
                        0.0D,
                        0.0D,
                        0.0D);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide)
            this.setupAnimationStates();

        if (this.getPhaseTime() > 0){

            int futureTime = this.getPhaseTime()-1;

            this.setPhaseTime(futureTime);

            if (this.getEggPhase() > 0){
                if (this.getPhaseTime() == 0 && this.getEggPhase() < 3){
                    int futurePhase = this.getEggPhase()+1;
                    this.setEggPhase(futurePhase);
                }

                if (this.getEggPhase() < 3 && this.getPhaseTime() == 0)
                    this.setPhaseTime(phaseTime);
                else if (this.getPhaseTime() == 0 && this.getEggPhase() == 3)
                    this.setPhaseTime(30);

                if (this.getEggPhase() == 3){
                    if (this.getPhaseTime() == 25 || this.getPhaseTime() == 15)
                        this.playSound(SoundEvents.CHICKEN_HURT, 1f, this.getVoicePitch());
                }
            }
        }
    }

    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);

        if (this.getEggPhase()== 3 && getPhaseTime()==30){
            this.cawingAnimationState.start(this.tickCount);
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.getItem() instanceof OrnateEgg && this.getEggPhase() == 0){
            ItemStack copy = stack.copy();
            this.getInventory().setItem(0, copy);
            this.setEggPhase(1);
            this.setPhaseTime(phaseTime);
            removeInteractionItem(pPlayer, stack);
            return InteractionResult.SUCCESS;
        }
        if (stack.isEmpty() && this.getEggPhase() > 0){
            ItemStack copy = this.getInventory().getItem(0);
            if (this.getEggPhase() == 3 && copy.getItem() instanceof OrnateEgg egg){
                egg.setEntityType(this.getRandom().nextInt(0, 4), copy);
            }
            this.spawnAtLocation(copy);
            this.setEggPhase(0);
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
    public boolean canBreed() {
        return false;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
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

    @Override
    public SimpleContainer getInventory() {
        return inventory;
    }

    protected void dropEquipment() {
        super.dropEquipment();
        this.inventory.removeAllItems().forEach(this::spawnAtLocation);
        ItemStack itemstack = this.getInventory().getItem(0);
        if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
            this.spawnAtLocation(itemstack);
            this.getInventory().setItem(0, ItemStack.EMPTY);
        }

    }
}
