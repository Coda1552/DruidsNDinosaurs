package codyhuh.druids_n_dinosaurs.mixin;

import codyhuh.druids_n_dinosaurs.registry.ModEffects;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{

    @Shadow
    @Final
    private Map<MobEffect, MobEffectInstance> activeEffects;

    @Shadow
    public abstract boolean hasEffect(MobEffect pEffect);

    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(
            method = {"updateInvisibilityStatus"},
            at = @At(value = "TAIL"))
    private void registerData(CallbackInfo ci) {
        if (!activeEffects.isEmpty()){
            if (!this.hasEffect(MobEffects.INVISIBILITY)){
                this.setInvisible(this.hasEffect(ModEffects.ETHEREAL.get()));
            }
        }
    }

}
