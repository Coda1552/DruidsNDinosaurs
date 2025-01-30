package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModTags {
    public static final TagKey<EntityType<?>> UNCATCHABLE = create("uncatchable");

    private static TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(DruidsNDinosaurs.MOD_ID, name));
    }
}
