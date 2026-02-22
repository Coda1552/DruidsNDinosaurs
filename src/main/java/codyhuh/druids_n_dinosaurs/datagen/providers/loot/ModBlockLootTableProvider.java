package codyhuh.druids_n_dinosaurs.datagen.providers.loot;

import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
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

        this.dropSelf(ModBlocks.CATACOMB_BONE_BLOCK.get());
        this.dropSelf(ModBlocks.HART_TUFF_TOTEM.get());
        this.dropSelf(ModBlocks.ELEPHANT_TUFF_TOTEM.get());
        this.dropSelf(ModBlocks.LEFT_WING_TUFF_TOTEM.get());
        this.dropSelf(ModBlocks.RIGHT_WING_TUFF_TOTEM.get());
        this.dropSelf(ModBlocks.BIRD_TUFF_TOTEM.get());

        this.dropSelf(ModBlocks.JADE_BLOCK.get());
        this.dropSelf(ModBlocks.SHATTERED_JADE.get());
        this.dropSelf(ModBlocks.CHISELED_POLISHED_JADE_WALL.get());
        this.dropSelf(ModBlocks.CHISELED_POLISHED_JADE.get());
        this.dropSelf(ModBlocks.JADE_BRICKS.get());
        this.dropSelf(ModBlocks.JADE_BRICK_STAIRS.get());
        this.add(ModBlocks.JADE_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.JADE_BRICK_SLAB.get()));
        this.dropSelf(ModBlocks.JADE_HUMMINGBIRD_BULB.get());
        this.dropSelf(ModBlocks.JADE_KINDRED_BULB.get());
        this.dropSelf(ModBlocks.POLISHED_JADE_BLOCK.get());
        this.dropSelf(ModBlocks.POLISHED_JADE_STAIRS.get());
        this.add(ModBlocks.POLISHED_JADE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.POLISHED_JADE_SLAB.get()));
        this.dropSelf(ModBlocks.JADE_STAIRS.get());
        this.add(ModBlocks.JADE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.POLISHED_JADE_SLAB.get()));

        this.add(ModBlocks.JADE_ORE.get(),
                block -> createJadeOreDrops(ModBlocks.JADE_ORE.get()));
        this.add(ModBlocks.DEEPSLATE_JADE_ORE.get(),
                block -> createJadeOreDrops(ModBlocks.DEEPSLATE_JADE_ORE.get()));
    }

    protected LootTable.Builder createJadeOreDrops(Block pBlock) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(ModItems.JADE_SHARD.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
