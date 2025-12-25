package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.common.entity.ai.LookForItemGoal;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class EggRaptor extends Animal implements InventoryCarrier {

    private final SimpleContainer inventory = new SimpleContainer(1);

    public final AnimationState idleAnimationState = new AnimationState();

    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(EggRaptor.class, EntityDataSerializers.BOOLEAN);

    static final Predicate<ItemEntity> ALLOWED_ITEMS =
            itemEntity -> !itemEntity.hasPickUpDelay() && itemEntity.isAlive() && itemEntity.getItem().is(ModItems.EGG_SHARDS.get());

    public EggRaptor(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setCanPickUpLoot(this.canPickUpLoot());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new LookForItemGoal(this, ALLOWED_ITEMS, 1.25f));
        this.goalSelector.addGoal(1, new DepositEggsInChest(this, 1.0D, 25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.15D, Ingredient.of(ModItems.EGG_SHARDS.get(), Items.EGG, Items.WHEAT_SEEDS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.15D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_EGG, false);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("HasEggShards", this.hasEggs());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setHasEggs(compoundTag.getBoolean("HasEggShards"));
    }

    public boolean hasEggs(){
        return this.entityData.get(HAS_EGG);
    }

    public void setHasEggs(boolean hasEggs){
        this.entityData.set(HAS_EGG, hasEggs);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide)
            this.setUpAnimations();

    }

    protected void setUpAnimations(){
        idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.WHEAT_SEEDS);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.EGG_RAPTOR.get().create(pLevel);
    }

    @Override
    public SimpleContainer getInventory() {
        return inventory;
    }

    public boolean wantsToPickUp(ItemStack pStack) {
        boolean flag;
        if (this.getInventory().isEmpty()){
            flag = true;
        }else {
            flag = this.getInventory().getItem(0).getCount() < 16;
        }
        return flag && this.inventory.canAddItem(pStack) && pStack.is(ModItems.EGG_SHARDS.get())
                && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this);
    }

    public boolean canPickUpLoot() {
        boolean flag;
        if (this.getInventory().isEmpty()){
            flag = true;
        }else {
            flag = this.getInventory().getItem(0).getCount() < 16;
        }
        return flag;
    }

    protected void pickUpItem(ItemEntity itemEntity) {
        if (itemEntity.getItem().is(ModItems.EGG_SHARDS.get()) && !this.hasEggs())
            this.setHasEggs(true);

        InventoryCarrier.pickUpItem(this, this, itemEntity);
    }

    protected void dropEquipment() {
        super.dropEquipment();

        if (this.inventory != null) {
            for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    this.spawnAtLocation(itemstack);
                }
            }
        }
    }



    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            double d0 = this.getMoveControl().getSpeedModifier();
            this.setPose(Pose.STANDING);
            this.setSprinting(d0 >= 1.2D);
        } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(false);
        }
    }

    class DepositEggsInChest extends MoveToBlockGoal{

        private final EggRaptor raptor;
        private BarrelBlockEntity barrel;

        public DepositEggsInChest(EggRaptor pMob, double pSpeedModifier, int pSearchRange) {
            super(pMob, pSpeedModifier, pSearchRange, 5);
            this.raptor = pMob;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && raptor.hasEggs() && !raptor.canPickUpLoot();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && raptor.hasEggs() && !raptor.canPickUpLoot();
        }

        @Override
        public void tick() {
            super.tick();

            if (this.isReachedTarget() && this.barrel != null){
                try{
                    final Direction deposit = raptor.getDirection();
                    final LazyOptional<IItemHandler> handler = barrel.getCapability(ForgeCapabilities.ITEM_HANDLER, deposit);

                    if(handler.orElse(null) != null) {
                        ItemStack duplicate = raptor.getInventory().getItem(0).copy();
                        ItemStack insertSimulate = ItemHandlerHelper.insertItem(handler.orElse(null), duplicate, true);
                        if (!insertSimulate.equals(duplicate)) {
                            ItemStack shrunkenStack = ItemHandlerHelper.insertItem(handler.orElse(null), duplicate, false);
                            raptor.playSound(SoundEvents.BARREL_OPEN, 1.0F, (raptor.random.nextFloat() - raptor.random.nextFloat()) * 0.2F + 1.0F);
                            if(shrunkenStack.isEmpty()){
                                raptor.getInventory().setItem(0, ItemStack.EMPTY);
                            }else{
                                raptor.getInventory().setItem(0, shrunkenStack);
                            }
                            if (raptor.getInventory().getItem(0).isEmpty()){
                                raptor.setHasEggs(false);
                            }
                        }

                    }
                }catch (Exception e){
                }

                this.stop();
            }
        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            BlockState state = pLevel.getBlockState(pPos);
            if(state.getBlock() instanceof BarrelBlock){
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof BarrelBlockEntity barrel){
                    for(ItemStack itemstack : barrel.items) {
                        if (itemstack.isEmpty() || (itemstack.is(ModItems.EGG_SHARDS.get())
                                && itemstack.getCount() != itemstack.getMaxStackSize())) {
                            this.barrel = barrel;
                            return true;
                        }
                    }
                    return false;
                }
            }
            return false;
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_28262_) {
        return SoundEvents.CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.CHICKEN_DEATH;
    }

    protected void playStepSound(BlockPos p_28254_, BlockState p_28255_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }
}
