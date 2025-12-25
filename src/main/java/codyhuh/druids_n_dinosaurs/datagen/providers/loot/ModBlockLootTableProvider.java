package codyhuh.druids_n_dinosaurs.datagen.providers.loot;

import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    public ModBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.ALOEWOOD_PLANKS.get());
        this.dropSelf(ModBlocks.ALOEWOOD_STAIRS.get());
        this.dropSelf(ModBlocks.ALOEWOOD_FENCE.get());
        this.dropSelf(ModBlocks.ALOEWOOD_FENCE_GATE.get());
        this.dropSelf(ModBlocks.ALOEWOOD_BUTTON.get());
        this.dropSelf(ModBlocks.ALOEWOOD_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.ALOEWOOD_TRAPDOOR.get());
        this.dropSelf(ModBlocks.BOUNCESHROOM.get());
        this.dropSelf(ModBlocks.GOLD_TRAPDOOR.get());
        this.dropSelf(ModBlocks.CUT_GOLD.get());
        this.dropSelf(ModBlocks.CUT_GOLD_SLAB.get());
        this.dropSelf(ModBlocks.CUT_GOLD_STAIRS.get());
        this.dropSelf(ModBlocks.CHISELED_GOLD.get());
        this.add(ModBlocks.GOLD_DOOR.get(),
                block -> createDoorTable(ModBlocks.GOLD_DOOR.get()));

        this.add(ModBlocks.YELLOW_IRONWEED.get(),
                block -> createDoublePlantShearsDrop(ModBlocks.YELLOW_IRONWEED.get()));

        this.add(ModBlocks.ALOEWOOD_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.ALOEWOOD_SLAB.get()));
        this.add(ModBlocks.ALOEWOOD_DOOR.get(),
                block -> createDoorTable(ModBlocks.ALOEWOOD_DOOR.get()));

        this.add(ModBlocks.ALOEWOOD_SIGN.get(),
                block -> createSingleItemTable(ModItems.ALOEWOOD_SIGN.get()));
        this.add(ModBlocks.ALOEWOOD_WALL_SIGN.get(),
                block -> createSingleItemTable(ModItems.ALOEWOOD_SIGN.get()));
        this.add(ModBlocks.ALOEWOOD_HANGING_SIGN.get(),
                block -> createSingleItemTable(ModItems.ALOEWOOD_HANGING_SIGN.get()));
        this.add(ModBlocks.ALOEWOOD_WALL_HANGING_SIGN.get(),
                block -> createSingleItemTable(ModItems.ALOEWOOD_HANGING_SIGN.get()));

        this.dropSelf(ModBlocks.ALOEWOOD_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_ALOEWOOD_LOG.get());
        this.dropSelf(ModBlocks.ALOEWOOD_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ALOEWOOD_WOOD.get());

        this.dropSelf(ModBlocks.ALOEWOOD_SAPLING.get());
        this.add(ModBlocks.POTTED_ALOEWOOD_SAPLING.get(),
                createPotFlowerItemTable(ModBlocks.ALOEWOOD_SAPLING.get()));

        this.add(ModBlocks.ALOEWOOD_LEAVES.get(),
                block -> createLeavesDrops(ModBlocks.ALOEWOOD_LEAVES.get(), ModBlocks.ALOEWOOD_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F));

        this.dropSelf(ModBlocks.RUST_BLOCK.get());
        this.dropSelf(ModBlocks.RUSTICLE.get());

        this.dropSelf(ModBlocks.CRACKLE_EGG.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
