package codyhuh.druids_n_dinosaurs.datagen.providers;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import codyhuh.druids_n_dinosaurs.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, DruidsNDinosaurs.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.copy(ModTags.Blocks.ALOEWOOD_LOG_BLOCK, ModTags.Items.ALOEWOOD_LOG_ITEM);

        this.tag(ItemTags.LOGS_THAT_BURN)
                .addTag(ModTags.Items.ALOEWOOD_LOG_ITEM);

        this.tag(ItemTags.PLANKS).add(
                ModBlocks.ALOEWOOD_PLANKS.get().asItem()
        );

        this.tag(ItemTags.LEAVES).add(
                ModBlocks.ALOEWOOD_LEAVES.get().asItem()
        );

        this.tag(ItemTags.WOODEN_FENCES).add(
                ModBlocks.ALOEWOOD_FENCE.get().asItem()
        );
        this.tag(ItemTags.FENCE_GATES).add(
                ModBlocks.ALOEWOOD_FENCE_GATE.get().asItem()
        );

        this.tag(ItemTags.WOODEN_DOORS).add(
                ModBlocks.ALOEWOOD_DOOR.get().asItem()
        );

        this.tag(ItemTags.WOODEN_TRAPDOORS).add(
                ModBlocks.ALOEWOOD_TRAPDOOR.get().asItem()
        );

        this.tag(ItemTags.WOODEN_STAIRS).add(
                ModBlocks.ALOEWOOD_STAIRS.get().asItem()
        );
        this.tag(ItemTags.WOODEN_SLABS).add(
                ModBlocks.ALOEWOOD_SLAB.get().asItem()
        );
        this.tag(ItemTags.WOODEN_BUTTONS).add(
                ModBlocks.ALOEWOOD_BUTTON.get().asItem()
        );
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(
                ModBlocks.ALOEWOOD_PRESSURE_PLATE.get().asItem()
        );

        this.tag(ItemTags.SIGNS).add(
                ModItems.ALOEWOOD_SIGN.get()
        );

        this.tag(ItemTags.HANGING_SIGNS).add(
                ModItems.ALOEWOOD_HANGING_SIGN.get()
        );

        this.tag(ItemTags.DECORATED_POT_SHERDS).add(
                ModItems.RUSTLING_SHERD.get());
    }
}
