package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<SoundEvent> WICKER_IDOL_SWOOSH = create("wicker_idol.use");

    public static final RegistryObject<SoundEvent> RUSTLING_IDLE = create("rustling_ambient");
    public static final RegistryObject<SoundEvent> RUSTLING_HURT = create("rustling_hurt");
    public static final RegistryObject<SoundEvent> RUSTLING_DEATH = create("rustling_death");
    public static final RegistryObject<SoundEvent> RUSTLING_SHEAR = create("rustling_shear");

    public static final RegistryObject<SoundEvent> RUSTMUNCHER_IDLE = create("rustmuncher_ambient");
    public static final RegistryObject<SoundEvent> RUSTMUNCHER_HURT = create("rustmuncher_hurt");
    public static final RegistryObject<SoundEvent> RUSTMUNCHER_DEATH = create("rustmuncher_death");

    private static RegistryObject<SoundEvent> create(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DruidsNDinosaurs.MOD_ID, name)));
    }
}
