package codyhuh.druids_n_dinosaurs.common.effects;

import codyhuh.druids_n_dinosaurs.registry.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class SludgedEffect extends MobEffect {

    public SludgedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
//        this.addAttributeModifier(Attributes.JUMP_STRENGTH, UUID.randomUUID().toString(), -0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL);
//        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID.randomUUID().toString(), -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }
}
