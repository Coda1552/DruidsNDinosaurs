package codyhuh.druids_n_dinosaurs.common.entity.custom;

import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class Sludger extends Slime {
    public final AnimationState idleAnimationState = new AnimationState();

    public Sludger(EntityType<? extends Slime> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new SludgerAvoidEntityGoal<>(this, Player.class, 8.0F, 0.6D, 1.0D,
        (p_25052_) -> {
            if (p_25052_ instanceof Player player){
                return player.getItemInHand(InteractionHand.MAIN_HAND).is(ItemTags.FLOWERS);
            }
            return false;
        }));
    }

    @Override
    protected boolean isDealsDamage() {
        return false;
    }

    @Override
    public boolean canAttack(LivingEntity pLivingentity, TargetingConditions pCondition) {
        if (pLivingentity instanceof IronGolem)
            return false;
        return super.canAttack(pLivingentity, pCondition);
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
                return this.maxDist > this.toAvoid.distanceToSqr(this.mob);
            }
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

            MoveControl movecontrol = this.mob.getMoveControl();

            double d0 = this.toAvoid.getX() - this.mob.getX();
            double d2 = this.toAvoid.getZ() - this.mob.getZ();
            float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F - 180;

//            if (f<0)
//                f += -f*2;
//            if (f>360)
//                f -= 360;

            if (movecontrol instanceof Slime.SlimeMoveControl slime$slimemovecontrol) {
                slime$slimemovecontrol.setDirection(f, false);
            }
        }
    }
}
