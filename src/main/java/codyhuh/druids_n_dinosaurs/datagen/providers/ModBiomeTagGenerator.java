package codyhuh.druids_n_dinosaurs.datagen.providers;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagGenerator  extends BiomeTagsProvider {

    public ModBiomeTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, DruidsNDinosaurs.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.addTags();
    }

    protected void addTags(){
        this.tag(ModTags.Biomes.JADE_HIGH_BIOMES).add(Biomes.JUNGLE).add(Biomes.SPARSE_JUNGLE).addTag(BiomeTags.IS_JUNGLE).add(Biomes.CHERRY_GROVE);
        this.tag(ModTags.Biomes.HART_BIOMES).addTag(BiomeTags.IS_FOREST).add(Biomes.PLAINS);
    }
}
