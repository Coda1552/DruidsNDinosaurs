package codyhuh.druids_n_dinosaurs.mixin;

import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Wolf.class)
abstract public class WolfMixin extends TamableAnimal {
    protected WolfMixin(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
    public void druids_n_dinosaurs_interact(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        var self = (Wolf) (Object) (this);

        if (!self.isTame() && itemstack.is(ModItems.ANTLER.get()) && !self.isAngry()) {
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (!net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                this.tame(pPlayer);
                this.navigation.stop();
                this.setTarget(null);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte)7);
            } else {
                this.level().broadcastEntityEvent(this, (byte)6);
            }

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
