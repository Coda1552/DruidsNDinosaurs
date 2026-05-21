package codyhuh.druids_n_dinosaurs.mixin;

import codyhuh.druids_n_dinosaurs.registry.ModEffects;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin extends net.minecraftforge.common.capabilities.CapabilityProvider<net.minecraft.world.entity.Entity> {

    protected EntityMixin(Class<net.minecraft.world.entity.Entity> baseClass) {
        super(baseClass);
    }

    @Inject(
            method = {"canBeHitByProjectile()Z"},
            at = @At(value = "HEAD"),
            cancellable = true)
    protected void canBeHitByProjectileMixin(CallbackInfoReturnable<Boolean> cir) {
        var self = (Entity) (Object) (this);
        if (self instanceof LivingEntity entity){
            if (entity.hasEffect(ModEffects.ETHEREAL.get())) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(
            method = {"Lnet/minecraft/world/entity/Entity;isInvisibleTo(Lnet/minecraft/world/entity/player/Player;)Z"},
            at = @At(value = "HEAD"),
            cancellable = true)
    public void isInvisibleToPlayerMixin(Player pPlayer, CallbackInfoReturnable<Boolean> cir) {
        var self = (Entity) (Object) (this);
        if (self instanceof LivingEntity entity){
            if (entity.isInvisible() && !entity.hasEffect(MobEffects.INVISIBILITY)) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}