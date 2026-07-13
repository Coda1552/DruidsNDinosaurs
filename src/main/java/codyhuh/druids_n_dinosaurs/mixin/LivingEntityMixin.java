package codyhuh.druids_n_dinosaurs.mixin;

import codyhuh.druids_n_dinosaurs.registry.ModEffects;
import codyhuh.druids_n_dinosaurs.registry.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
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
        var self = (LivingEntity) (Object) (this);
        if (!activeEffects.isEmpty()){
            if (!self.hasEffect(MobEffects.INVISIBILITY)){
                self.setInvisible(self.hasEffect(ModEffects.ETHEREAL.get()));
            }
        }
    }

    @Inject(
            method = {"tick"},
            at = @At(value = "TAIL"))
    private void tick(CallbackInfo ci) {
        var self = (LivingEntity) (Object) (this);

        if(self.hasEffect(ModEffects.SLUDGED.get())){

            if (this.random.nextFloat() < 0.05F){
                for(int i = 0; i < self.getRandom().nextInt(2) + 1; ++i) {
                    this.spawnFluidParticle(self.level(),
                            this.getRandomX(1.0D) - (double)0.3F,
                            this.getRandomX(1.0D) + (double)0.3F,
                            this.getRandomZ(1.0D) - (double)0.3F,
                            this.getRandomZ(1.0D) + (double)0.3F,
                            this.getRandomY(), ModParticles.SLUDGED.get());
                }
            }
        }
    }

    private void spawnFluidParticle(Level pLevel, double pStartX, double pEndX, double pStartZ, double pEndZ, double pPosY, ParticleOptions pParticleOption) {
        pLevel.addParticle(pParticleOption, Mth.lerp(pLevel.random.nextDouble(), pStartX, pEndX), pPosY, Mth.lerp(pLevel.random.nextDouble(), pStartZ, pEndZ), 0.0D, 0.0D, 0.0D);
    }

}
