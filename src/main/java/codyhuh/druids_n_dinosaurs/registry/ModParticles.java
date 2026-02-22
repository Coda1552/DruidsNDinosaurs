package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<SimpleParticleType> JADE_OMEN = register("jade_omen", false);

    private static RegistryObject<SimpleParticleType> register(String id, boolean flag) {
        return PARTICLES.register(id, () -> new SimpleParticleType(flag));
    }

    public static void register(IEventBus eventBus){
        PARTICLES.register(eventBus);
    }
}
