package codyhuh.druids_n_dinosaurs.datagen.providers;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagGenerator extends EntityTypeTagsProvider {

    public ModEntityTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, DruidsNDinosaurs.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.addTags();
    }

    protected void addTags(){
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(ModEntities.SLUDGER.get()).add(ModEntities.JADE_AUTOMATON.get()).add(ModEntities.CHISELCHIRP.get()).add(ModEntities.TERRA_THUNK.get());
        this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(ModEntities.SLUDGER.get()).add(ModEntities.TUFF_TOTEM_POLE.get());
        this.tag(ModTags.EntityTypes.WICKER_IDOL_BLACKLIST).add(EntityType.ENDER_DRAGON).add(EntityType.WARDEN).add(EntityType.WITHER);
        this.tag(EntityTypeTags.DISMOUNTS_UNDERWATER).add(ModEntities.HART.get()).add(ModEntities.GILDED_GALLUMPHER.get()).add(ModEntities.TERRA_THUNK.get());
        this.tag(ModTags.EntityTypes.BLOOM_ENTITIES).add(ModEntities.RUSTLING.get());
        this.tag(ModTags.EntityTypes.OOZE_TRAIL_NO_STUCK).add(ModEntities.SLUDGER.get()).add(ModEntities.MUDLING.get()).add(ModEntities.MUDSPITTER.get());

    }
}
