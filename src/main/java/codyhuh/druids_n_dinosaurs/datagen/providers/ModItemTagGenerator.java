package codyhuh.druids_n_dinosaurs.datagen.providers;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import codyhuh.druids_n_dinosaurs.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
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

        this.tag(ModTags.Items.FLOWER_CROWNS).add(ModItems.FLOWER_CROWN.get()).add(ModItems.BLUE_FLOWER_CROWN.get());

        this.tag(ItemTags.PLANKS).add(
                ModBlocks.ALOEWOOD_PLANKS.get().asItem()
        );

        this.tag(Tags.Items.EGGS).add(
                ModBlocks.CRACKLE_EGG.get().asItem()
        );

        this.tag(ItemTags.LEAVES).add(
                ModBlocks.BLOOMED_ALOEWOOD_LEAVES.get().asItem(),
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

        this.tag(ItemTags.DECORATED_POT_SHERDS)
                .add(ModItems.RUSTLING_SHERD.get())
                .add(ModItems.HART_SHERD.get())
                .add(ModItems.GAZE_SHERD.get())
                .add(ModItems.RUSTMUNCHER_SHERD.get())
                .add(ModItems.CRACKLE_SHERD.get())
                .add(ModItems.BLOOM_SHERD.get())
                .add(ModItems.CROWN_SHERD.get())
                .add(ModItems.LUNAR_SHERD.get())
                .add(ModItems.STELLAR_SHERD.get())
                .add(ModItems.WHISP_SHERD.get())
                .add(ModItems.TRUNK_SHERD.get())
                .add(ModItems.REBIRTH_SHERD.get())
                .add(ModItems.WAYFIND_UP_SHERD.get())
                .add(ModItems.WAYFIND_DOWN_SHERD.get())
                .add(ModItems.WAYFIND_LEFT_SHERD.get())
                .add(ModItems.WAYFIND_RIGHT_SHERD.get())
                .add(ModItems.MONKE_SHERD.get());


        this.tag(Tags.Items.ARMORS_HELMETS).add(ModItems.FLOWER_CROWN.get()).add(ModItems.BLUE_FLOWER_CROWN.get());

        this.tag(ItemTags.SMALL_FLOWERS)
                .add(ModBlocks.GILDED_FORGET_ME_NOTS.get().asItem())
                .add(ModBlocks.BRIGHT_BLOOMS.get().asItem());

        this.tag(ItemTags.FLOWERS)
                .add(ModBlocks.BLOOM_BEACON.get().asItem());
    }
}
