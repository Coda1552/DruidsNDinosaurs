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
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(ModEntities.JADE_AUTOMATON.get());
    }
}
