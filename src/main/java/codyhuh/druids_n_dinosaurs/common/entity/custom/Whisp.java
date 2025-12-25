package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.registry.ModPotions;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.IntFunction;

public class Whisp extends PathfinderMob {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState spinAnimationState = new AnimationState();
    public int spinAnimationTimeout;

    int seekCandleCooldown = 20*60*10;
    int seekingCandleTicks = 3*20*60;

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Whisp.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(Whisp.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Integer> SEEK_CANDLE_COOLDOWN = SynchedEntityData.defineId(Whisp.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SEEKING_CANDLE_TICKS = SynchedEntityData.defineId(Whisp.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SPINNING_TIME = SynchedEntityData.defineId(Whisp.class, EntityDataSerializers.INT);

    private int prevSeekCandleCooldown;
    private int prevSeekingCandleTicks;
    private int prevSpinningTicks;

    public Whisp(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new WhispMoveControl(this);
        this.setMaxUpStep(1);
    }

    public static class WhispMoveControl extends MoveControl {

        final Whisp whisp;
        public WhispMoveControl(Whisp pMob) {
            super(pMob);
            this.whisp = pMob;
        }

        @Override
        public void tick() {
            if (!this.whisp.isSpinning())
                super.tick();
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5));

        this.goalSelector.addGoal(1, new TemptGoal(this, 1.5, Ingredient.of(ItemTags.CANDLES),false));

        this.goalSelector.addGoal(2, new WhispGoHomeGoal(this));
        this.goalSelector.addGoal(3, new WhispGoToCandleGoal(this));
        this.goalSelector.addGoal(4, new WhispWanderGoal(this));

        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(HOME_POS, BlockPos.ZERO);
        this.entityData.define(SPINNING_TIME, 0);
        this.entityData.define(SEEK_CANDLE_COOLDOWN, 20*60*10);
        this.entityData.define(SEEKING_CANDLE_TICKS, 0);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant());
        compoundTag.putInt("HomePosX", this.getHomePos().getX());
        compoundTag.putInt("HomePosY", this.getHomePos().getY());
        compoundTag.putInt("HomePosZ", this.getHomePos().getZ());
        compoundTag.putInt("SeekCandleCooldown", this.getSeekCandleCooldown());
        compoundTag.putInt("SeekingCandleTicks", this.getSeekingCandleTicks());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        int i = compoundTag.getInt("HomePosX");
        int j = compoundTag.getInt("HomePosY");
        int k = compoundTag.getInt("HomePosZ");
        this.setHomePos(new BlockPos(i, j, k));
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(compoundTag.getInt("Variant"));
        this.setSeekCandleCooldown(compoundTag.getInt("SeekCandleCooldown"));
        this.setSeekingCandleTicks(compoundTag.getInt("SeekingCandleTicks"));
    }

    public void setHomePos(BlockPos homePos) {
        this.entityData.set(HOME_POS, homePos);
    }

    public BlockPos getHomePos() {
        return this.entityData.get(HOME_POS);
    }

    public void setSpinningTime(int spinningTime) {
        this.entityData.set(SPINNING_TIME, spinningTime);
    }

    public int getSpinningTime() {
        return  this.entityData.get(SPINNING_TIME);
    }

    public boolean isSpinning() {
        return this.getSpinningTime() > 0;
    }

    public void setSeekCandleCooldown(int seekCandleTicks) {
        this.entityData.set(SEEK_CANDLE_COOLDOWN, seekCandleTicks);
    }

    public int getSeekCandleCooldown() {
        return this.entityData.get(SEEK_CANDLE_COOLDOWN);
    }

    public void setSeekingCandleTicks(int seekCandleTicks) {
        this.entityData.set(SEEKING_CANDLE_TICKS, seekCandleTicks);
    }

    public int getSeekingCandleTicks() {
        return this.entityData.get(SEEKING_CANDLE_TICKS);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    boolean closerThan(BlockPos blockPos, int distance) {
        return blockPos.closerThan(this.blockPosition(), distance);
    }

    @Override
    public void tick() {

        if (this.getSeekCandleCooldown() > 0){
            prevSeekCandleCooldown = this.getSeekCandleCooldown();
            this.setSeekCandleCooldown(prevSeekCandleCooldown-1);

            if (this.getSeekCandleCooldown() == 0 && this.getSeekingCandleTicks() == 0){
                this.setSeekingCandleTicks(seekingCandleTicks);
            }else if (this.getSeekingCandleTicks() > 0){
                this.setSeekingCandleTicks(0);
            }
        }

        if (this.getSeekingCandleTicks() > 0 && !this.isSpinning()){
            prevSeekingCandleTicks = this.getSeekingCandleTicks();
            this.setSeekingCandleTicks(prevSeekingCandleTicks-1);

            if (this.getSeekCandleCooldown() == 0 && this.getSeekingCandleTicks() == 0){
                this.setSeekCandleCooldown(seekCandleCooldown);
            }else if (this.getSeekCandleCooldown() > 0){
                this.setSeekCandleCooldown(0);
            }
        }

        if (this.isSpinning()){
            prevSpinningTicks = this.getSpinningTime();
            this.setSpinningTime(prevSpinningTicks-1);

            if (this.getSpinningTime() == 0){
                this.setSeekCandleCooldown(seekCandleCooldown);
                this.setSeekingCandleTicks(0);
            }
        }

        super.tick();

        if (this.level().isClientSide)
            setupAnimationStates();
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {

        this.setHomePos(this.blockPosition());

        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (itemstack.is(ItemTags.CANDLES) && ((this.getSeekCandleCooldown()>0 && this.getSeekCandleCooldown() < seekCandleCooldown/2) || pPlayer.isCreative()) && this.getSeekingCandleTicks() <= 0){
            if (!this.level().isClientSide) {
                this.level().playSound(pPlayer, this, SoundEvents.ALLAY_ITEM_GIVEN, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.setSeekingCandleTicks(seekingCandleTicks);
                this.setSeekCandleCooldown(0);
                if (!pPlayer.isCreative())
                    itemstack.shrink(1);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);

        if(this.isSpinning() && spinAnimationTimeout <= 0) {
            spinAnimationTimeout = 40;
            spinAnimationState.start(this.tickCount);
        } else {
            --this.spinAnimationTimeout;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }
    }

    protected void checkFallDamage(double p_27754_, boolean p_27755_, BlockState p_27756_, BlockPos p_27757_) {
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ALLAY_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ALLAY_DEATH;
    }

    public boolean removeWhenFarAway(double p_27598_) {
        return false;
    }

    public boolean canBeAffected(MobEffectInstance mobEffect) {
        return !(mobEffect.getEffect() == MobEffects.WITHER && this.getVariant() == 5) && super.canBeAffected(mobEffect);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance instance, MobSpawnType pReason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        this.setHomePos(this.blockPosition());

        if (pReason == MobSpawnType.SPAWN_EGG){
            WhispVariant variant = Util.getRandom(WhispVariant.values(), this.random);

            this.setVariant(variant.getVariantNumber());
        }

        return super.finalizeSpawn(levelAccessor, instance, pReason, spawnData, tag);
    }

    static class WhispWanderGoal extends WaterAvoidingRandomStrollGoal {
        final Whisp whisp;

        WhispWanderGoal(Whisp pWhisp) {
            super(pWhisp, 1);
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.whisp = pWhisp;
        }

        public boolean canUse() {
            return super.canUse() && whisp.closerThan(whisp.getHomePos(), 16) && whisp.getSeekCandleCooldown()>0;
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && whisp.getSeekCandleCooldown()>0;
        }

        @Nullable
        protected Vec3 getPosition() {
            if (this.mob.isInWaterOrBubble()) {
                Vec3 vec3 = LandRandomPos.getPos(this.mob, 7, 7);
                return vec3 == null ? super.getPosition() : vec3;
            } else {
                return this.mob.getRandom().nextFloat() >= this.probability ?
                        LandRandomPos.getPos(this.mob, 6, 7) : super.getPosition();
            }
        }
    }

    static class WhispGoHomeGoal extends Goal {
        final Whisp whisp;

        WhispGoHomeGoal(Whisp pWhisp) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.whisp = pWhisp;
        }

        public boolean canUse() {
            return !whisp.closerThan(whisp.getHomePos(), 16) && whisp.getSeekCandleCooldown()>0;
        }

        public boolean canContinueToUse() {
            return whisp.navigation.isInProgress() && whisp.getSeekCandleCooldown()>0;
        }

        public void start() {
            Vec3 vec3 = whisp.getHomePos().getCenter();
            whisp.navigation.moveTo(whisp.navigation.createPath(BlockPos.containing(vec3), 1), 1.0D);
        }
    }

    class WhispGoToCandleGoal extends MoveToBlockGoal{

        final Whisp whisp;
        public WhispGoToCandleGoal(Whisp mob) {
            super(mob, 1, 64, 15);
            this.whisp = mob;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.whisp.getSeekCandleCooldown() <= 0
                    && this.whisp.getSeekingCandleTicks()>0
                    && !this.whisp.isSpinning();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.whisp.getSeekCandleCooldown() <= 0
                    && this.whisp.getSeekingCandleTicks()>0
                    && !this.whisp.isSpinning();
        }

        @Override
        public void tick() {
            super.tick();
            if (this.isReachedTarget()){
                WhispVariant variant = WhispVariant.byId(whisp.getVariant());

                if (!this.whisp.isSpinning()){
                    if (this.whisp.navigation.getPath() != null){
                        this.whisp.navigation.stop();
                    }

                    this.whisp.setDeltaMovement(Vec3.ZERO);

                    ThrownPotion thrownpotion = new ThrownPotion(this.whisp.level(), this.whisp);
                    thrownpotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), variant.getPotion()));
                    thrownpotion.setXRot(thrownpotion.getXRot() - -20.0F);
                    thrownpotion.shoot(0, 5, 0, 0.75F, 1.0F);

                    if (!this.whisp.level().isClientSide)
                        this.whisp.level().addFreshEntity(thrownpotion);

                    this.whisp.setSpinningTime(40);
                }

            }
        }

        @Override
        protected boolean isValidTarget(LevelReader levelReader, BlockPos blockpos) {
            BlockState block = levelReader.getBlockState(blockpos);
            WhispVariant variant = WhispVariant.byId(whisp.getVariant());
            if (block.is(BlockTags.CANDLES)){
                return block.getValue(CandleBlock.LIT) && block.is(variant.getCandle());
            }
            return false;
        }

        @Override
        public double acceptedDistance() {
            return 0.75d;
        }
    }

    public enum WhispVariant {
        FUCHSIA(0, "fuchsia", ModPotions.WHISP_REGEN.get(), Blocks.PINK_CANDLE),
        VERMILLION(1, "vermillion", ModPotions.WHISP_FIRE_RES.get(), Blocks.ORANGE_CANDLE),
        AMBER(2, "amber", ModPotions.WHISP_STRENGTH.get(), Blocks.YELLOW_CANDLE),
        VERDANT(3, "verdant", ModPotions.WHISP_JUMP.get(), Blocks.LIME_CANDLE),
        AZURE(4, "azure", ModPotions.WHISP_SPEED.get(), Blocks.LIGHT_BLUE_CANDLE),
        EBONY(5, "ebony", ModPotions.WHISP_WITHER.get(), Blocks.BLACK_CANDLE);

        private final int variantNumber;
        private final String name;
        private final Potion potion;
        private final Block candle;

        WhispVariant(int variant, String name, Potion potion, Block candle){
            this.variantNumber = variant;
            this.name = name;
            this.potion = potion;
            this.candle = candle;
        }

        public int getVariantNumber(){
            return this.variantNumber;
        }

        public String getName(){
            return this.name;
        }

        public Potion getPotion(){
            return this.potion;
        }

        public Block getCandle(){
            return this.candle;
        }

        private static final IntFunction<Whisp.WhispVariant> BY_ID
                = ByIdMap.sparse(Whisp.WhispVariant::getVariantNumber, values(), FUCHSIA);


        public static WhispVariant byId(int pId) {
            return BY_ID.apply(pId);
        }
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
    }

    @Override
    public boolean fireImmune() {
        return this.getVariant() == 1;
    }

    @Override
    public boolean isOnFire() {
        return this.getVariant() != 1 && super.isOnFire();
    }


}
