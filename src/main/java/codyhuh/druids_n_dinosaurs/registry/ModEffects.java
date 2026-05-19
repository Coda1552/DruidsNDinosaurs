package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.common.effects.BloomEffect;
import codyhuh.druids_n_dinosaurs.common.effects.ModNormalEffect;
import codyhuh.druids_n_dinosaurs.common.effects.TetanusEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<MobEffect> TETANUS = MOB_EFFECTS.register("tetanus",
            ()-> new TetanusEffect(MobEffectCategory.HARMFUL, 0xb56a28));

    public static final RegistryObject<MobEffect> ETHEREAL = MOB_EFFECTS.register("ethereal",
            ()-> new ModNormalEffect(MobEffectCategory.BENEFICIAL, 0xbaf7d0));

    public static final RegistryObject<MobEffect> BLOOM = MOB_EFFECTS.register("bloom",
            ()-> new BloomEffect(MobEffectCategory.BENEFICIAL, 0xf2bb4e));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
