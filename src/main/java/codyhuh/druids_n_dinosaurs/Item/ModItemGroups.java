package codyhuh.druids_n_dinosaurs.Item;

import codyhuh.druids_n_dinosaurs.Druids_n_dinosaurs;
import codyhuh.druids_n_dinosaurs.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup DRUIDS_STUFF = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Druids_n_dinosaurs.MOD_ID, "jade_ingot"),
            FabricItemGroup.builder().displayName(Text.translatable("creativetab.druids_n_dinosaurs"))
                    .icon(() -> new ItemStack(ModItems.JADE_BRICK)).entries((displayContext, entries) -> {

                        entries.add(ModItems.JADE_BRICK);
                        entries.add(ModBlocks.JADE_BLOCK);

                    }).build());

    public static void registerItemGroups() {
        Druids_n_dinosaurs.LOGGER.info("Registering Item Groups for " +Druids_n_dinosaurs.MOD_ID);
    }
}