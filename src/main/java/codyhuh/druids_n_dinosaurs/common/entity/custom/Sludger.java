package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import codyhuh.druids_n_dinosaurs.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

public class Sludger extends Slime {
    public final AnimationState idleAnimationState = new AnimationState();

    public Sludger(EntityType<? extends Slime> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SludgerTemptGoal(this, 1, Ingredient.of(Items.SLIME_BALL, ModItems.SLUDGE_BALL.get()), false));
        this.goalSelector.addGoal(2, new SludgerAvoidEntityGoal<>(this, Player.class, 8.0F, 0.6D, 1.0D,
        (p_25052_) -> {
            if (p_25052_ instanceof Player player){
                return player.getItemInHand(InteractionHand.MAIN_HAND).is(ItemTags.FLOWERS);
            }
            return false;
        }));

    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide)
            this.setUpAnimations();
    }

    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && this.onGround()) {
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this)) {
                return;
            }

            BlockState blockstate = ModBlocks.OOZE_TRAIL.get().defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(Direction.DOWN), true);

            for (int i = 0; i < 4; ++i) {
                int j = Mth.floor(this.getX() + (double) ((float) (i % 2 * 2 - 1) * 0.25F));
                int k = Mth.floor(this.getY());
                int l = Mth.floor(this.getZ() + (double) ((float) (i / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos = new BlockPos(j, k, l);
                BlockState state = this.level().getBlockState(blockpos.below());
                if (this.level().isEmptyBlock(blockpos) && state.isFaceSturdy(this.level(), blockpos.below(), Direction.UP)) {
                    this.level().setBlockAndUpdate(blockpos, blockstate);
                    this.level().gameEvent(GameEvent.BLOCK_PLACE, blockpos, GameEvent.Context.of(this, blockstate));
                }
            }
        }

    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public void setUpAnimations() {
        this.idleAnimationState.animateWhen(true, this.tickCount);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
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

    protected ParticleOptions getParticleType() {
        return ModParticles.SLUDGER.get();
    }

    class SludgerTemptGoal extends Goal {
        private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
        private final TargetingConditions targetingConditions;
        protected final Sludger mob;
        private final double speedModifier;
        private double px;
        private double py;
        private double pz;
        private double pRotX;
        private double pRotY;
        @Nullable
        protected Player player;
        private int calmDown;
        private boolean isRunning;
        private final Ingredient items;
        private final boolean canScare;

        public SludgerTemptGoal(Sludger pMob, double pSpeedModifier, Ingredient pItems, boolean pCanScare) {
            this.mob = pMob;
            this.speedModifier = pSpeedModifier;
            this.items = pItems;
            this.canScare = pCanScare;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
        }

        public boolean canUse() {
            if (this.calmDown > 0) {
                --this.calmDown;
                return false;
            } else {
                this.player = this.mob.level().getNearestPlayer(this.targetingConditions, this.mob);
                return this.player != null;
            }
        }

        private boolean shouldFollow(LivingEntity p_148139_) {
            return this.items.test(p_148139_.getMainHandItem()) || this.items.test(p_148139_.getOffhandItem());
        }

        public boolean canContinueToUse() {
            if (this.canScare()) {
                if (this.mob.distanceToSqr(this.player) < 36.0D) {
                    if (this.player.distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002D) {
                        return false;
                    }

                    if (Math.abs((double) this.player.getXRot() - this.pRotX) > 5.0D || Math.abs((double) this.player.getYRot() - this.pRotY) > 5.0D) {
                        return false;
                    }
                } else {
                    this.px = this.player.getX();
                    this.py = this.player.getY();
                    this.pz = this.player.getZ();
                }

                this.pRotX = (double) this.player.getXRot();
                this.pRotY = (double) this.player.getYRot();
            }

            return this.canUse();
        }

        protected boolean canScare() {
            return this.canScare;
        }

        public void start() {
            this.px = this.player.getX();
            this.py = this.player.getY();
            this.pz = this.player.getZ();
            this.isRunning = true;
        }

        public void stop() {
            this.player = null;
            this.mob.getNavigation().stop();
            this.calmDown = reducedTickDelay(100);
            this.isRunning = false;
        }

        public void tick() {
            this.mob.getLookControl().setLookAt(this.player, (float) (this.mob.getMaxHeadYRot() + 20), (float) this.mob.getMaxHeadXRot());
            if (this.mob.distanceToSqr(this.player) < 6.25D) {
                this.mob.getNavigation().stop();
            } else {
                this.mob.getNavigation().moveTo(this.player, this.speedModifier);
            }

        }

        public boolean isRunning() {
            return this.isRunning;
        }
    }

    public class SludgerAvoidEntityGoal<T extends LivingEntity> extends Goal {
        protected final Sludger mob;
        private final double walkSpeedModifier;
        private final double sprintSpeedModifier;
        @Nullable
        protected T toAvoid;
        protected final float maxDist;
        @Nullable
        protected Path path;
        protected final PathNavigation pathNav;

        protected final Class<T> avoidClass;
        protected final Predicate<LivingEntity> avoidPredicate;
        protected final Predicate<LivingEntity> predicateOnAvoidEntity;
        private final TargetingConditions avoidEntityTargeting;

        public SludgerAvoidEntityGoal(Sludger pMob, Class<T> pEntityClassToAvoid, float pMaxDistance, double pWalkSpeedModifier, double pSprintSpeedModifier) {
            this(pMob, pEntityClassToAvoid, (p_25052_) -> {
                return true;
            }, pMaxDistance, pWalkSpeedModifier, pSprintSpeedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
        }

        public SludgerAvoidEntityGoal(Sludger pMob, Class<T> pEntityClassToAvoid, Predicate<LivingEntity> pAvoidPredicate, float pMaxDistance, double pWalkSpeedModifier, double pSprintSpeedModifier, Predicate<LivingEntity> pPredicateOnAvoidEntity) {
            this.mob = pMob;
            this.avoidClass = pEntityClassToAvoid;
            this.avoidPredicate = pAvoidPredicate;
            this.maxDist = pMaxDistance;
            this.walkSpeedModifier = pWalkSpeedModifier;
            this.sprintSpeedModifier = pSprintSpeedModifier;
            this.predicateOnAvoidEntity = pPredicateOnAvoidEntity;
            this.pathNav = pMob.getNavigation();
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.avoidEntityTargeting = TargetingConditions.forCombat().range((double) pMaxDistance).selector(pPredicateOnAvoidEntity.and(pAvoidPredicate));
        }

        public SludgerAvoidEntityGoal(Sludger pMob, Class<T> pEntityClassToAvoid, float pMaxDistance, double pWalkSpeedModifier, double pSprintSpeedModifier, Predicate<LivingEntity> pPredicateOnAvoidEntity) {
            this(pMob, pEntityClassToAvoid, (p_25049_) -> {
                return true;
            }, pMaxDistance, pWalkSpeedModifier, pSprintSpeedModifier, pPredicateOnAvoidEntity);
        }

        public boolean canUse() {
            if (this.mob.getSize()>1)
                return false;
            this.toAvoid = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate((double) this.maxDist, 3.0D, (double) this.maxDist), (p_148078_) -> {
                return true;
            }), this.avoidEntityTargeting, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
            if (this.toAvoid == null) {
                return false;
            } else {
                Vec3 vec3 = getPosAway(this.mob, 16, 7, this.toAvoid.position());
                if (vec3 == null) {
                    return false;
                } else if (this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.toAvoid.distanceToSqr(this.mob)) {
                    return false;
                } else {
                    this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                    return this.path != null;
                }
            }
        }

        @Nullable
        public static Vec3 getPosAway(Sludger pMob, int pRadius, int pYRange, Vec3 pVectorPosition) {
            Vec3 vec3 = pMob.position().subtract(pVectorPosition);
            boolean flag = mobRestricted(pMob, pRadius);
            return generateRandomPos(pMob, () -> {
                BlockPos blockpos = RandomPos.generateRandomDirectionWithinRadians(pMob.getRandom(), pRadius, pYRange, 0, vec3.x, vec3.z, (double)((float)Math.PI / 2F));
                return blockpos == null ? null : generateRandomPosTowardDirection(pMob, pRadius, flag, blockpos);
            });
        }

        @Nullable
        public static Vec3 generateRandomPos(Sludger pMob, Supplier<BlockPos> pPosSupplier) {
            return randomPos(pPosSupplier, pMob::getWalkTargetValue);
        }

        @Nullable
        public static Vec3 randomPos(Supplier<BlockPos> pPosSupplier, ToDoubleFunction<BlockPos> pToDoubleFunction) {
            double d0 = Double.NEGATIVE_INFINITY;
            BlockPos blockpos = null;

            for(int i = 0; i < 10; ++i) {
                BlockPos blockpos1 = pPosSupplier.get();
                if (blockpos1 != null) {
                    double d1 = pToDoubleFunction.applyAsDouble(blockpos1);
                    if (d1 > d0) {
                        d0 = d1;
                        blockpos = blockpos1;
                    }
                }
            }

            return blockpos != null ? Vec3.atBottomCenterOf(blockpos) : null;
        }

        @Nullable
        private static BlockPos generateRandomPosTowardDirection(Sludger pMob, int pRadius, boolean pShortCircuit, BlockPos pPos) {
            BlockPos blockpos = generateRandomPosTowardDirection(pMob, pRadius, pMob.getRandom(), pPos);
            return !isOutsideLimits(blockpos, pMob) && !isRestricted(pShortCircuit, pMob, blockpos) && !GoalUtils.isNotStable(pMob.getNavigation(), blockpos) && !hasMalus(pMob, blockpos) ? blockpos : null;
        }

        public static boolean hasMalus(Sludger pMob, BlockPos pPos) {
            return pMob.getPathfindingMalus(WalkNodeEvaluator.getBlockPathTypeStatic(pMob.level(), pPos.mutable())) != 0.0F;
        }

        public static boolean isRestricted(boolean pShortCircuit, Sludger pMob, BlockPos pPos) {
            return pShortCircuit && !pMob.isWithinRestriction(pPos);
        }

        public static boolean isOutsideLimits(BlockPos pPos, Sludger pMob) {
            return pPos.getY() < pMob.level().getMinBuildHeight() || pPos.getY() > pMob.level().getMaxBuildHeight();
        }

        public static BlockPos generateRandomPosTowardDirection(Sludger pMob, int pRange, RandomSource pRandom, BlockPos pPos) {
            int i = pPos.getX();
            int j = pPos.getZ();
            if (pMob.hasRestriction() && pRange > 1) {
                BlockPos blockpos = pMob.getRestrictCenter();
                if (pMob.getX() > (double)blockpos.getX()) {
                    i -= pRandom.nextInt(pRange / 2);
                } else {
                    i += pRandom.nextInt(pRange / 2);
                }

                if (pMob.getZ() > (double)blockpos.getZ()) {
                    j -= pRandom.nextInt(pRange / 2);
                } else {
                    j += pRandom.nextInt(pRange / 2);
                }
            }

            return BlockPos.containing((double)i + pMob.getX(), (double)pPos.getY() + pMob.getY(), (double)j + pMob.getZ());
        }

        public static boolean mobRestricted(Sludger pMob, int pRadius) {
            return pMob.hasRestriction() && pMob.getRestrictCenter().closerToCenterThan(pMob.position(), (double)(pMob.getRestrictRadius() + (float)pRadius) + 1.0D);
        }

        public boolean canContinueToUse() {
            return !this.pathNav.isDone();
        }

        public void start() {
            this.pathNav.moveTo(this.path, this.walkSpeedModifier);
        }

        public void stop() {
            this.toAvoid = null;
        }

        public void tick() {
            if (this.mob.distanceToSqr(this.toAvoid) < 49.0D) {
                this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
            } else {
                this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
            }

        }
    }

    private double getWalkTargetValue(BlockPos blockPos) {
        return 0;
    }
}
