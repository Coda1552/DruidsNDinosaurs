package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.common.items.WickerIdolItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<Item> WICKER_IDOL = ITEMS.register("wicker_idol", () -> new WickerIdolItem(new Item.Properties().stacksTo(1)));
}
