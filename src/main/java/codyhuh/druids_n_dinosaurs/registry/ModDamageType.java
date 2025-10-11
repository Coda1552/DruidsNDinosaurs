package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageType {
    public static final ResourceKey<DamageType> RUSTICLE = ResourceKey.create(Registries.DAMAGE_TYPE,
            new ResourceLocation(DruidsNDinosaurs.MOD_ID, "falling_rusticle"));
}
