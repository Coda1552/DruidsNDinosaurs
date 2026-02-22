package codyhuh.druids_n_dinosaurs.common.entity.ai;

import codyhuh.druids_n_dinosaurs.common.entity.custom.JadeElephant;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class ElephantBegGoal extends Goal {
    private final JadeElephant elephant;
    @Nullable
    private Player player;
    private final Level level;
    private final float lookDistance;
    private int lookTime;
    private final TargetingConditions begTargeting;

    public ElephantBegGoal(JadeElephant pElephant, float pLookDistance) {
        this.elephant = pElephant;
        this.level = pElephant.level();
        this.lookDistance = pLookDistance;
        this.begTargeting = TargetingConditions.forNonCombat().range((double)pLookDistance);
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    public boolean canUse() {
        this.player = this.level.getNearestPlayer(this.begTargeting, this.elephant);
        return this.player == null ? false : this.playerHoldingInteresting(this.player);
    }

    public boolean canContinueToUse() {
        if (!this.player.isAlive()) {
            return false;
        } else if (this.elephant.distanceToSqr(this.player) > (double)(this.lookDistance * this.lookDistance)) {
            return false;
        } else {
            return this.lookTime > 0 && this.playerHoldingInteresting(this.player);
        }
    }

    public void start() {
        this.elephant.setIsInterested(true);
        this.lookTime = this.adjustedTickDelay(40 + this.elephant.getRandom().nextInt(40));
    }

    public void stop() {
        this.elephant.setIsInterested(false);
        this.player = null;
    }

    public void tick() {
        this.elephant.getLookControl().setLookAt(this.player.getX(), this.player.getEyeY(), this.player.getZ(), 10.0F, (float)this.elephant.getMaxHeadXRot());
        --this.lookTime;
    }

    private boolean playerHoldingInteresting(Player pPlayer) {
        for(InteractionHand interactionhand : InteractionHand.values()) {
            ItemStack itemstack = pPlayer.getItemInHand(interactionhand);
            return itemstack.is(Tags.Items.STONE);
        }

        return false;
    }
}
