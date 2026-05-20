package codyhuh.druids_n_dinosaurs.block;

import codyhuh.druids_n_dinosaurs.Druids_n_dinosaurs;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block JADE_BLOCK = registerBlock("jade_block",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Druids_n_dinosaurs.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Druids_n_dinosaurs.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Druids_n_dinosaurs.LOGGER.info("Registering ModBlocks for " + Druids_n_dinosaurs.MOD_ID);
    }
}