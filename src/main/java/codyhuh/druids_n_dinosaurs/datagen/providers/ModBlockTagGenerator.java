package codyhuh.druids_n_dinosaurs.datagen.providers;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {


    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DruidsNDinosaurs.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                ModBlocks.ALOEWOOD_PLANKS.get(),
                ModBlocks.ALOEWOOD_STAIRS.get(),
                ModBlocks.ALOEWOOD_SLAB.get(),
                ModBlocks.ALOEWOOD_BUTTON.get(),
                ModBlocks.ALOEWOOD_PRESSURE_PLATE.get(),
                ModBlocks.ALOEWOOD_FENCE.get(),
                ModBlocks.ALOEWOOD_FENCE_GATE.get(),
                ModBlocks.ALOEWOOD_DOOR.get(),
                ModBlocks.ALOEWOOD_TRAPDOOR.get(),
                ModBlocks.ALOEWOOD_LOG.get(),
                ModBlocks.STRIPPED_ALOEWOOD_LOG.get(),
                ModBlocks.ALOEWOOD_WOOD.get(),
                ModBlocks.STRIPPED_ALOEWOOD_WOOD.get(),
                ModBlocks.ALOEWOOD_SIGN.get(),
                ModBlocks.ALOEWOOD_WALL_SIGN.get(),
                ModBlocks.ALOEWOOD_HANGING_SIGN.get(),
                ModBlocks.ALOEWOOD_WALL_HANGING_SIGN.get()
        );

        this.tag(BlockTags.PLANKS).add(
                ModBlocks.ALOEWOOD_PLANKS.get()
        );

        this.tag(ModTags.Blocks.ALOEWOOD_LOG_BLOCK).add(
                ModBlocks.ALOEWOOD_LOG.get(),
                ModBlocks.STRIPPED_ALOEWOOD_LOG.get(),
                ModBlocks.ALOEWOOD_WOOD.get(),
                ModBlocks.STRIPPED_ALOEWOOD_WOOD.get()
        );
        this.tag(BlockTags.LOGS_THAT_BURN)
                .addTag(ModTags.Blocks.ALOEWOOD_LOG_BLOCK)
        ;

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                ModBlocks.ALOEWOOD_LEAVES.get()
        );

        this.tag(BlockTags.LEAVES).add(
                ModBlocks.ALOEWOOD_LEAVES.get()
        );
        this.tag(BlockTags.WOODEN_FENCES).add(
                ModBlocks.ALOEWOOD_FENCE.get()
        );
        this.tag(BlockTags.FENCE_GATES).add(
                ModBlocks.ALOEWOOD_FENCE_GATE.get()
        );

        this.tag(BlockTags.WOODEN_DOORS).add(
                ModBlocks.ALOEWOOD_DOOR.get(),
                ModBlocks.GOLD_DOOR.get()
        );

        this.tag(BlockTags.WOODEN_TRAPDOORS).add(
                ModBlocks.ALOEWOOD_TRAPDOOR.get(),
                ModBlocks.GOLD_TRAPDOOR.get()
        );

        this.tag(BlockTags.WOODEN_STAIRS).add(
                ModBlocks.ALOEWOOD_STAIRS.get()
        );

        this.tag(BlockTags.WOODEN_SLABS).add(
                ModBlocks.ALOEWOOD_SLAB.get()
        );

        this.tag(BlockTags.WOODEN_BUTTONS).add(
                ModBlocks.ALOEWOOD_BUTTON.get()
        );

        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
                ModBlocks.ALOEWOOD_PRESSURE_PLATE.get()
        );

        this.tag(BlockTags.SIGNS).add(
                ModBlocks.ALOEWOOD_SIGN.get()
        );

        this.tag(BlockTags.WALL_SIGNS).add(
                ModBlocks.ALOEWOOD_WALL_SIGN.get()
        );

        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(
                ModBlocks.ALOEWOOD_HANGING_SIGN.get()
        );

        this.tag(BlockTags.WALL_HANGING_SIGNS).add(
                ModBlocks.ALOEWOOD_WALL_HANGING_SIGN.get()
        );
    }
}
