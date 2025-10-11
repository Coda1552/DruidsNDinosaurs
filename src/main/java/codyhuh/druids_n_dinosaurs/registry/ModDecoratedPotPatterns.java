package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DruidsNDinosaurs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDecoratedPotPatterns {

    public static final DeferredRegister<String> DECORATED_POT_PATTERNS = DeferredRegister.create(Registries.DECORATED_POT_PATTERNS, DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<String> RUSTLING = DECORATED_POT_PATTERNS.register("rustling_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":rustling_pottery_pattern");

}
