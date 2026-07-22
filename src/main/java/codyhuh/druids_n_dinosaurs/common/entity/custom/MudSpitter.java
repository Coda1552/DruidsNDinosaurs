package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.common.entity.custom.item.SludgeBallEntity;
import codyhuh.druids_n_dinosaurs.common.entity.custom.item.TarBallProjectileEntity;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class MudSpitter extends TamableAnimal {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState spitAnimationState = new AnimationState();
    public int spitAnimationTimeout;
    public int eggTime = 60*20;
    private static final EntityDataAccessor<Integer> SPITTING_TICKS = SynchedEntityData.defineId(MudSpitter.class, EntityDataSerializers.INT);

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.STICK), false));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public MudSpitter(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 50).add(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPITTING_TICKS, 0);
    }

    public int getSpittingTicks() {
        return this.getEntityData().get(SPITTING_TICKS);
    }

    public void setSpittingTicks(int ticks){
        this.getEntityData().set(SPITTING_TICKS, ticks);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide)
            this.setUpAnimations();

        if (this.getSpittingTicks()>0){
            int prev = this.getSpittingTicks();

            this.setSpittingTicks(prev-1);

            if (this.getSpittingTicks()==(12)){
                this.spitTar();
            }
        }

    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide && this.isAlive() && --this.eggTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Items.MUD);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggTime = 60*20;
        }
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return !this.isVehicle();
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getDimensions(Pose.STANDING).height*0.6;
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

                this.setSpeed(this.getSpeed());
                Vec3 vec;

                vec = new Vec3(f, moveY, f1);

                super.travel(vec);

            } else {
                super.travel(pos);
            }
        }
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    protected void tickRidden(Player pPlayer, Vec3 pTravelVector) {
        super.tickRidden(pPlayer, pTravelVector);

        this.setRot(pPlayer.getYRot(),pPlayer.getXRot() * 0.5F);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.getSpittingTicks() == 0){
            if (!this.isVehicle() && !this.isBaby()){
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
                    if (itemstack.is(Items.MOSS_BLOCK) && this.getHealth() < this.getMaxHealth() && this.isTame()) {
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }

                        this.heal(10);
                        this.gameEvent(GameEvent.EAT, this);
                        return InteractionResult.SUCCESS;
                    }

                    if (itemstack.is(Items.SLIME_BALL)) {
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }

                        this.setSpittingTicks(20);
                        return InteractionResult.SUCCESS;
                    }
                } else if (!player.isShiftKeyDown() && !this.isBaby() && this.isTame() && !this.level().isClientSide) {
                    player.startRiding(this);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    public void spitTar(){
        SludgeBallEntity tarBall = new TarBallProjectileEntity(this.level(), this);
        tarBall.setItem(new ItemStack(ModItems.SLUDGE_BALL.get()));
        tarBall.shootFromRotation(this, this.getXRot(), this.yHeadRotO, 0.0F, 0.75F, 0.5F);
        this.level().addFreshEntity(tarBall);
    }

    protected void setUpAnimations(){
        idleAnimationState.animateWhen(this.isAlive(), this.tickCount);

        if (this.getSpittingTicks()>0 && this.spitAnimationTimeout == 0){
            this.spitAnimationTimeout = 20;
            this.spitAnimationState.start(this.tickCount);
        }else if (this.spitAnimationTimeout>0){
            this.spitAnimationTimeout--;
        }
    }

    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            double d0 = this.getMoveControl().getSpeedModifier();
            this.setPose(Pose.STANDING);
            this.setSprinting(d0 >= 1.1D);
        } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(false);
        }
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.STICK);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.MUDLING.get().create(pLevel);
    }

    protected float getRiddenSpeed(Player pPlayer) {
        float f = pPlayer.isSprinting() && this.onGround() ? 1.15f : 0.35f;
        this.setSprinting(pPlayer.isSprinting() && this.onGround());
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * f;
    }

    public boolean canSprint() {
        return this.onGround();
    }
}
