package codyhuh.druids_n_dinosaurs.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class ModNormalEffect extends MobEffect {

    public ModNormalEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }
}
