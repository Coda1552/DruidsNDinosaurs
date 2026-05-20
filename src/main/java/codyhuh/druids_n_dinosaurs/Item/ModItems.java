package codyhuh.druids_n_dinosaurs.Item;

import codyhuh.druids_n_dinosaurs.Druids_n_dinosaurs;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item JADE_BRICK = registerItem("jade_brick",
            new Item(new FabricItemSettings()));



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Druids_n_dinosaurs.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Druids_n_dinosaurs.LOGGER.info("Registering Mod Items for " + Druids_n_dinosaurs.MOD_ID);

        //This is kinda useless, but ima keep it for keepsakes
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemsGroup);

    }
}
