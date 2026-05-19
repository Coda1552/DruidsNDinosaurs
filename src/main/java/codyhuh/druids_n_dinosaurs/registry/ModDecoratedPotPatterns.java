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

    public static final RegistryObject<String> HART = DECORATED_POT_PATTERNS.register("hart_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":hart_pottery_pattern");
    public static final RegistryObject<String> GAZE = DECORATED_POT_PATTERNS.register("gaze_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":gaze_pottery_pattern");
    public static final RegistryObject<String> RUSTMUNCHER = DECORATED_POT_PATTERNS.register("rustmuncher_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":rustmuncher_pottery_pattern");
    public static final RegistryObject<String> CRACKLE = DECORATED_POT_PATTERNS.register("crackle_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":crackle_pottery_pattern");

    public static final RegistryObject<String> BLOOM = DECORATED_POT_PATTERNS.register("bloom_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":bloom_pottery_pattern");
    public static final RegistryObject<String> CROWN = DECORATED_POT_PATTERNS.register("crown_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":crown_pottery_pattern");
    public static final RegistryObject<String> LUNAR = DECORATED_POT_PATTERNS.register("lunar_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":lunar_pottery_pattern");
    public static final RegistryObject<String> STELLAR = DECORATED_POT_PATTERNS.register("stellar_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":stellar_pottery_pattern");

    public static final RegistryObject<String> WHISP = DECORATED_POT_PATTERNS.register("whisp_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":whisp_pottery_pattern");
    public static final RegistryObject<String> TRUNK = DECORATED_POT_PATTERNS.register("trunk_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":trunk_pottery_pattern");
    public static final RegistryObject<String> REBIRTH = DECORATED_POT_PATTERNS.register("rebirth_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":rebirth_pottery_pattern");
    public static final RegistryObject<String> WAYFIND_UP = DECORATED_POT_PATTERNS.register("wayfind_up_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":wayfind_up_pottery_pattern");

    public static final RegistryObject<String> WAYFIND_DOWN = DECORATED_POT_PATTERNS.register("wayfind_down_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":wayfind_down_pottery_pattern");
    public static final RegistryObject<String> WAYFIND_LEFT = DECORATED_POT_PATTERNS.register("wayfind_left_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":wayfind_left_pottery_pattern");
    public static final RegistryObject<String> WAYFIND_RIGHT = DECORATED_POT_PATTERNS.register("wayfind_right_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":wayfind_right_pottery_pattern");
    public static final RegistryObject<String> MONKE = DECORATED_POT_PATTERNS.register("monke_pottery_pattern", () -> DruidsNDinosaurs.MOD_ID + ":monke_pottery_pattern");


}
