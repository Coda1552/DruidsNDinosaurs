package codyhuh.druids_n_dinosaurs.common.worldgen;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> JADE_DEEP = registerKey("jade_deep");
    public static final ResourceKey<PlacedFeature> JADE_HIGH = registerKey("jade_high");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {

        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, JADE_DEEP, configuredFeatures.getOrThrow(ModConfiguredFeatures.JADE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(0))));

        register(context, JADE_HIGH, configuredFeatures.getOrThrow(ModConfiguredFeatures.JADE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(0),
                        VerticalAnchor.absolute(120))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(DruidsNDinosaurs.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
