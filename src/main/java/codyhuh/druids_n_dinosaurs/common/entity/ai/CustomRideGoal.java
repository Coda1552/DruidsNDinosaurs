package codyhuh.druids_n_dinosaurs.common.entity.ai;

import codyhuh.druids_n_dinosaurs.common.entity.custom.TerraThunk;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class CustomRideGoal extends Goal {
    private final PathfinderMob tameableEntity;
    private LivingEntity player;
    private final double speed;
    private final boolean strafe;

    public CustomRideGoal(PathfinderMob mob, double speed) {
        this(mob, speed, true);
    }

    public CustomRideGoal(PathfinderMob mob, double speed, boolean strafe) {
        this.tameableEntity = mob;
        this.speed = speed;
        this.strafe = strafe;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        if (this.tameableEntity.getControllingPassenger() instanceof Player && this.tameableEntity.isVehicle()) {
            this.player = (Player)this.tameableEntity.getControllingPassenger();
            if (tameableEntity instanceof TerraThunk terraThunk){
                return !terraThunk.isBouncing();
            }
            return true;
        } else {
            this.tameableEntity.setSprinting(false);
            return false;
        }
    }

    public void start() {
        this.tameableEntity.getNavigation().stop();
    }

    public void tick() {
        this.tameableEntity.setMaxUpStep(1.0F);
        this.tameableEntity.getNavigation().stop();
        this.tameableEntity.setTarget((LivingEntity)null);
        double x = this.tameableEntity.getX();
        double y = this.tameableEntity.getY();
        double z = this.tameableEntity.getZ();
        if (this.strafe) {
            this.tameableEntity.xxa = this.player.xxa * 0.15F;
        }

        if (this.shouldMoveForward() && this.tameableEntity.isVehicle()) {
            this.tameableEntity.setSprinting(true);
            Vec3 lookVec = this.player.getLookAngle();
            if (this.shouldMoveBackwards()) {
                lookVec = lookVec.yRot((float)Math.PI);
            }

            x += lookVec.x * (double)10.0F;
            z += lookVec.z * (double)10.0F;
            y += this.modifyYPosition(lookVec.y);
            this.tameableEntity.getMoveControl().setWantedPosition(x, y, z, this.speed);
        } else {
            this.tameableEntity.setSprinting(false);
        }

    }

    public double modifyYPosition(double lookVecY) {
        return this.tameableEntity instanceof Mob ? lookVecY * (double)10.0F : (double)0.0F;
    }

    public boolean shouldMoveForward() {
        return this.player.zza != 0.0F;
    }

    public boolean shouldMoveBackwards() {
        return this.player.zza < 0.0F;
    }
}
