package codyhuh.druids_n_dinosaurs.common.worldgen;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_JADE = registerKey("add_jade");
    public static final ResourceKey<BiomeModifier> ADD_JADE_HIGH = registerKey("add_jade_high");

    public static void bootstrap(BootstapContext<BiomeModifier> context){
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_JADE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.JADE_DEEP)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_JADE_HIGH, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.JADE_HIGH_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.JADE_HIGH)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(DruidsNDinosaurs.MOD_ID, name));
    }
}
