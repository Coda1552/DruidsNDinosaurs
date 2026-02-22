package codyhuh.druids_n_dinosaurs.mixin;

import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.BegGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BegGoal.class)
public abstract class BegGoalMixin extends Goal {

    @Shadow
    Wolf wolf;

    @Inject(
            method = {"playerHoldingInteresting"},
            cancellable = true,
            at = @At(value = "HEAD")
    )
    private void playerHoldingInteresting(Player pPlayer, CallbackInfoReturnable<Boolean> cir) {
        for(InteractionHand interactionhand : InteractionHand.values()) {
            ItemStack itemstack = pPlayer.getItemInHand(interactionhand);
            if (this.wolf.isTame() && itemstack.is(ModItems.ANTLER.get())) {
                cir.setReturnValue(true);
            }
        }
    }
}
