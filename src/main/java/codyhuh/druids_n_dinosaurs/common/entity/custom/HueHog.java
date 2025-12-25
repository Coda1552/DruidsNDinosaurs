package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModTags;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class HueHog extends Animal implements Shearable, net.minecraftforge.common.IForgeShearable{

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState turnHeadAnimationState1 = new AnimationState();
    public final AnimationState turnHeadAnimationState2 = new AnimationState();
    public final AnimationState danceAnimationState = new AnimationState();
    public final AnimationState collectAnimationState = new AnimationState();
    public int turnHeadTimeout = this.random.nextInt(160) + 60;
    public int danceAnimationTimeout;
    public int collectAnimationTimeout;

    private static final Map<DyeColor, ItemLike> ITEM_BY_DYE = Util.make(Maps.newEnumMap(DyeColor.class), (p_29841_) -> {
        p_29841_.put(DyeColor.WHITE, Items.WHITE_DYE);
        p_29841_.put(DyeColor.ORANGE, Items.ORANGE_DYE);
        p_29841_.put(DyeColor.MAGENTA, Items.MAGENTA_DYE);
        p_29841_.put(DyeColor.LIGHT_BLUE, Items.LIGHT_BLUE_DYE);
        p_29841_.put(DyeColor.YELLOW, Items.YELLOW_DYE);
        p_29841_.put(DyeColor.LIME, Items.LIME_DYE);
        p_29841_.put(DyeColor.PINK, Items.PINK_DYE);
        p_29841_.put(DyeColor.GRAY, Items.GRAY_DYE);
        p_29841_.put(DyeColor.LIGHT_GRAY, Items.LIGHT_GRAY_DYE);
        p_29841_.put(DyeColor.CYAN, Items.CYAN_DYE);
        p_29841_.put(DyeColor.PURPLE, Items.PURPLE_DYE);
        p_29841_.put(DyeColor.BLUE, Items.BLUE_DYE);
        p_29841_.put(DyeColor.BROWN, Items.BROWN_DYE);
        p_29841_.put(DyeColor.GREEN, Items.GREEN_DYE);
        p_29841_.put(DyeColor.RED, Items.RED_DYE);
        p_29841_.put(DyeColor.BLACK, Items.BLACK_DYE);
    });

    private static final EntityDataAccessor<Byte> COLOR_ID = SynchedEntityData.defineId(HueHog.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> WANTS_TO_COLLECT_TICKS = SynchedEntityData.defineId(HueHog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COLLECTING_TICKS = SynchedEntityData.defineId(HueHog.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> WANTS_TO_CHANGE_COLOR_TICKS = SynchedEntityData.defineId(HueHog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DANCING_TICKS = SynchedEntityData.defineId(HueHog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CAN_CHANGE_COLORS = SynchedEntityData.defineId(HueHog.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_COLOR = SynchedEntityData.defineId(HueHog.class, EntityDataSerializers.BOOLEAN);

    public HueHog(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new HueHogMoveControl();
        this.lookControl = new HueHogLookControl();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.15D, Ingredient.of(Items.HONEYCOMB, Blocks.GOLD_BLOCK), false));
        this.goalSelector.addGoal(3, new HueHogChangeWoolColor(this, 1.15D, 20));
        this.goalSelector.addGoal(3, new HueHogGoToFlowerGoal(this, 1.15D, 20));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.15D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.HONEYCOMB);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 16).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WANTS_TO_COLLECT_TICKS, 20*60*3);
        this.entityData.define(WANTS_TO_CHANGE_COLOR_TICKS, 20*60*2);
        this.entityData.define(COLLECTING_TICKS, 0);
        this.entityData.define(DANCING_TICKS, 0);
        this.entityData.define(CAN_CHANGE_COLORS, false);
        this.entityData.define(COLOR_ID, (byte)0);
        this.entityData.define(HAS_COLOR, false);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("WantsToCollectTicks", this.wantsToCollectTicks());
        compoundTag.putInt("WantsToChangeColorTicks", this.wantsToChangeColorTicks());
        compoundTag.putBoolean("CanChangeColors", this.canChangeColors());
        compoundTag.putByte("Color", (byte)this.getColor().getId());
        compoundTag.putBoolean("HasColor", this.hasColor());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setWantsToCollectTicks(compoundTag.getInt("WantsToCollectTicks"));
        this.setwantsToChangeColorTicks(compoundTag.getInt("WantsToChangeColorTicks"));
        this.setcanChangeColors(compoundTag.getBoolean("CanChangeColors"));
        this.setColor(DyeColor.byId(compoundTag.getByte("Color")));
        this.setHasColor(compoundTag.getBoolean("HasColor"));
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(COLOR_ID) & 15);
    }

    public void setColor(DyeColor pDyeColor) {
        byte b0 = this.entityData.get(COLOR_ID);
        this.entityData.set(COLOR_ID, (byte)(b0 & 240 | pDyeColor.getId() & 15));
    }

    public int wantsToCollectTicks(){
        return this.entityData.get(WANTS_TO_COLLECT_TICKS);
    }

    public void setWantsToCollectTicks(int ticks){
        this.entityData.set(WANTS_TO_COLLECT_TICKS, ticks);
    }

    public int wantsToChangeColorTicks(){
        return this.entityData.get(WANTS_TO_CHANGE_COLOR_TICKS);
    }

    public void setwantsToChangeColorTicks(int ticks){
        this.entityData.set(WANTS_TO_CHANGE_COLOR_TICKS, ticks);
    }

    public int getCollectingTicks(){
        return this.entityData.get(COLLECTING_TICKS);
    }

    public void setCollectingTicks(int ticks){
        this.entityData.set(COLLECTING_TICKS, ticks);
    }

    public int getDancingTicks(){
        return this.entityData.get(DANCING_TICKS);
    }

    public void setDancingTicks(int ticks){
        this.entityData.set(DANCING_TICKS, ticks);
    }

    public boolean canChangeColors(){
        return this.entityData.get(CAN_CHANGE_COLORS);
    }

    public void setcanChangeColors(boolean canChange){
        this.entityData.set(CAN_CHANGE_COLORS, canChange);
    }

    public boolean hasColor(){
        return this.entityData.get(HAS_COLOR);
    }

    public void setHasColor(boolean canChange){
        this.entityData.set(HAS_COLOR, canChange);
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getItem() instanceof DyeItem item){
            if (!this.level().isClientSide) {
                this.level().playSound(pPlayer, this, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.setColor(item.getDyeColor());
                if (!this.hasColor()){
                    this.setHasColor(true);
                }
                if (!pPlayer.isCreative())
                    itemstack.shrink(1);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }

        }else if (itemstack.getItem() == Blocks.GOLD_BLOCK.asItem()) {
            if (!this.level().isClientSide && !this.canChangeColors()) {
                this.setcanChangeColors(true);
                itemstack.shrink(1);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        } else if (!itemstack.is(Items.HONEYCOMB) && !itemstack.is(Blocks.GOLD_BLOCK.asItem()) && this.canChangeColors()){
            if (!this.level().isClientSide) {
                this.setcanChangeColors(false);
                this.spawnAtLocation(Blocks.GOLD_BLOCK, 1);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();

        if (this.canChangeColors()){
            this.spawnAtLocation(new ItemStack(Blocks.GOLD_BLOCK));
        }
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public java.util.List<ItemStack> onSheared(@javax.annotation.Nullable Player player, @org.jetbrains.annotations.NotNull ItemStack item, Level world, BlockPos pos, int fortune) {
        world.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        this.gameEvent(GameEvent.SHEAR, player);
        if (!world.isClientSide) {
            this.setHasColor(false);

            java.util.List<ItemStack> items = new java.util.ArrayList<>();
            items.add(new ItemStack(ITEM_BY_DYE.get(this.getColor())));

            return items;
        }
        return java.util.Collections.emptyList();
    }

    public void shear(SoundSource pCategory) {
        this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, pCategory, 1.0F, 1.0F);
        this.setHasColor(false);
        int i = 1 + this.random.nextInt(3);

        for(int j = 0; j < i; ++j) {
            ItemEntity itementity = this.spawnAtLocation(ITEM_BY_DYE.get(this.getColor()), 1);
            if (itementity != null) {
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
            }
        }

    }

    @Override
    public boolean readyForShearing() {
        return this.hasColor();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide)
            this.setUpAnimations();

        if (this.getCollectingTicks()>0){
            int prevTicks = this.getCollectingTicks();
            this.setCollectingTicks(prevTicks-1);
            if (this.getCollectingTicks() == 0 && !this.hasColor())
                    this.setHasColor(true);
        }

        if (this.getDancingTicks()>0){
            int prevTicks = this.getDancingTicks();
            this.setDancingTicks(prevTicks-1);
        }

        if (this.wantsToCollectTicks()>0 && !this.canChangeColors()){
            int prevTicks = this.wantsToCollectTicks();
            this.setWantsToCollectTicks(prevTicks-1);
        }

        if (this.canChangeColors() && this.wantsToChangeColorTicks()>0
                && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this)){
            int prevTicks = this.wantsToChangeColorTicks();
            this.setwantsToChangeColorTicks(prevTicks-1);
        }
    }

    protected void setUpAnimations(){
        idleAnimationState.animateWhen(this.isAlive(), this.tickCount);

        if (this.turnHeadTimeout <= 0){
            this.turnHeadTimeout = this.random.nextInt(160) + 60;
            if (this.random.nextBoolean())
                this.turnHeadAnimationState1.start(this.tickCount);
            else
                this.turnHeadAnimationState2.start(this.tickCount);
        }else
            --this.turnHeadTimeout;

        if (this.getCollectingTicks()>0 && this.collectAnimationTimeout <= 0){
            this.collectAnimationTimeout= 49;
            this.collectAnimationState.start(this.tickCount);
        }else
            --this.collectAnimationTimeout;

        if (this.getDancingTicks()>0 && this.danceAnimationTimeout <= 0){
            this.danceAnimationTimeout = 59;
            this.danceAnimationState.start(this.tickCount);
        }else
            --this.danceAnimationTimeout;
    }

    @Override
    public void aiStep() {

        if (this.isImmobile()) {
            this.jumping = false;
            this.xxa = 0.0F;
            this.zza = 0.0F;
        }

        super.aiStep();
    }

    class HueHogMoveControl extends MoveControl {
        public HueHogMoveControl() {
            super(HueHog.this);
        }

        public void tick() {
            if (!HueHog.this.isImmobile()) {
                super.tick();
            }

        }
    }

    public class HueHogLookControl extends LookControl {
        public HueHogLookControl() {
            super(HueHog.this);
        }

        public void tick() {
            if (!HueHog.this.isImmobile()) {
                super.tick();
            }

        }
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() && this.getDancingTicks()<=0 && this.getCollectingTicks()<=0;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        return pEffectInstance.getEffect() != MobEffects.WITHER && super.canBeAffected(pEffectInstance);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.HUE_HOG.get().create(pLevel);
    }

    class HueHogGoToFlowerGoal extends MoveToBlockGoal{
        final HueHog hog;

        public HueHogGoToFlowerGoal(HueHog pMob, double pSpeedModifier, int pSearchRange) {
            super(pMob, pSpeedModifier, pSearchRange);
            this.hog = pMob;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.hog.wantsToCollectTicks()==0 && !this.hog.canChangeColors();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.hog.wantsToCollectTicks()==0 && !this.hog.canChangeColors();
        }

        @Override
        public void tick() {
            super.tick();
            if (this.isReachedTarget()){
                this.hog.setColor(hog, hog.level().getBlockState(blockPos.above()));
                if (this.hog.getCollectingTicks() == 0)
                    this.hog.setCollectingTicks(50);
                this.hog.setWantsToCollectTicks(20*60*(2+hog.getRandom().nextInt(0, 2)));
                this.stop();
            }
        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            return pLevel.getBlockState(pPos.above()).is(ModTags.Blocks.HUE_HOG_TARGETS);
        }
    }

    class HueHogChangeWoolColor extends MoveToBlockGoal{
        final HueHog hog;

        public HueHogChangeWoolColor(HueHog pMob, double pSpeedModifier, int pSearchRange) {
            super(pMob, pSpeedModifier, pSearchRange);
            this.hog = pMob;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.hog.hasColor() && this.hog.wantsToChangeColorTicks()==0 && this.hog.canChangeColors()
                    && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.hog.level(), this.hog);
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.hog.hasColor() && this.hog.wantsToChangeColorTicks()==0 && this.hog.canChangeColors()
                    && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.hog.level(), this.hog);
        }

        @Override
        public void tick() {
            super.tick();
            if (this.isReachedTarget()){
                this.hog.changeColor(hog, blockPos);
                this.stop();
            }
        }

        @Override
        public void stop() {
            this.hog.setDancingTicks(60);
            this.hog.setwantsToChangeColorTicks(20*60*(1+hog.getRandom().nextInt(0, 1)));
        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            return pLevel.getBlockState(pPos).is(BlockTags.WOOL);
        }

        @Override
        public double acceptedDistance() {
            return 1.5d;
        }
    }

    public void setColor(HueHog hog, BlockState flower){

        if (flower.is(Blocks.LILY_OF_THE_VALLEY))
            hog.setColor(DyeColor.WHITE);

        if (flower.is(Blocks.OXEYE_DAISY) || flower.is(Blocks.WHITE_TULIP) || flower.is(Blocks.AZURE_BLUET))
            hog.setColor(DyeColor.LIGHT_GRAY);

        if (flower.is(Blocks.WITHER_ROSE))
            hog.setColor(DyeColor.BLACK);

        if (flower.is(Blocks.RED_TULIP) || flower.is(Blocks.ROSE_BUSH) || flower.is(Blocks.POPPY))
            hog.setColor(DyeColor.RED);

        if (flower.is(Blocks.ORANGE_TULIP) || flower.is(Blocks.TORCHFLOWER))
            hog.setColor(DyeColor.ORANGE);

        if (flower.is(Blocks.SUNFLOWER) || flower.is(Blocks.DANDELION) || flower.is(ModBlocks.YELLOW_IRONWEED.get()))
            hog.setColor(DyeColor.YELLOW);

        if (flower.is(Blocks.PITCHER_PLANT))
            hog.setColor(DyeColor.CYAN);

        if (flower.is(Blocks.BLUE_ORCHID))
            hog.setColor(DyeColor.LIGHT_BLUE);

        if (flower.is(Blocks.CORNFLOWER))
            hog.setColor(DyeColor.BLUE);

        if (flower.is(Blocks.ALLIUM) || flower.is(Blocks.LILAC))
            hog.setColor(DyeColor.MAGENTA);

        if (flower.is(Blocks.PINK_TULIP) || flower.is(Blocks.PINK_PETALS) || flower.is(Blocks.PEONY))
            hog.setColor(DyeColor.PINK);
    }

    public void changeColor(HueHog hog, BlockPos wool){
        switch(hog.getColor()){
            case LIGHT_GRAY -> hog.level().setBlockAndUpdate(wool, Blocks.LIGHT_GRAY_WOOL.defaultBlockState());
            case BLACK -> hog.level().setBlockAndUpdate(wool, Blocks.BLACK_WOOL.defaultBlockState());
            case RED -> hog.level().setBlockAndUpdate(wool, Blocks.RED_WOOL.defaultBlockState());
            case ORANGE -> hog.level().setBlockAndUpdate(wool, Blocks.ORANGE_WOOL.defaultBlockState());
            case YELLOW -> hog.level().setBlockAndUpdate(wool, Blocks.YELLOW_WOOL.defaultBlockState());
            case CYAN -> hog.level().setBlockAndUpdate(wool, Blocks.CYAN_WOOL.defaultBlockState());
            case LIGHT_BLUE -> hog.level().setBlockAndUpdate(wool, Blocks.LIGHT_BLUE_WOOL.defaultBlockState());
            case BLUE -> hog.level().setBlockAndUpdate(wool, Blocks.BLUE_WOOL.defaultBlockState());
            case MAGENTA -> hog.level().setBlockAndUpdate(wool, Blocks.MAGENTA_WOOL.defaultBlockState());
            case PINK -> hog.level().setBlockAndUpdate(wool, Blocks.PINK_WOOL.defaultBlockState());
            case PURPLE -> hog.level().setBlockAndUpdate(wool, Blocks.PURPLE_WOOL.defaultBlockState());
            case GREEN -> hog.level().setBlockAndUpdate(wool, Blocks.GREEN_WOOL.defaultBlockState());
            case LIME -> hog.level().setBlockAndUpdate(wool, Blocks.LIME_WOOL.defaultBlockState());
            case BROWN -> hog.level().setBlockAndUpdate(wool, Blocks.BROWN_WOOL.defaultBlockState());
            case GRAY -> hog.level().setBlockAndUpdate(wool, Blocks.GRAY_WOOL.defaultBlockState());
            default -> hog.level().setBlockAndUpdate(wool, Blocks.WHITE_WOOL.defaultBlockState());
        }
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.RABBIT_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.RABBIT_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.RABBIT_DEATH;
    }
}
