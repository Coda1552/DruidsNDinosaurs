package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.common.entity.custom.ModBoatEntity;
import codyhuh.druids_n_dinosaurs.common.items.ModBoatItem;
import codyhuh.druids_n_dinosaurs.common.items.WickerIdolItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<Item> WICKER_IDOL = ITEMS.register("wicker_idol", () -> new WickerIdolItem(new Item.Properties().stacksTo(1)));

    //Aloewood
    public static final RegistryObject<Item> ALOEWOOD_SIGN = ITEMS.register("aloewood_sign",
            ()-> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.ALOEWOOD_SIGN.get(), ModBlocks.ALOEWOOD_WALL_SIGN.get()));

    public static final RegistryObject<Item> ALOEWOOD_HANGING_SIGN = ITEMS.register("aloewood_hanging_sign",
            ()-> new HangingSignItem(ModBlocks.ALOEWOOD_HANGING_SIGN.get(), ModBlocks.ALOEWOOD_WALL_HANGING_SIGN.get(),new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> ALOEWOOD_BOAT = ITEMS.register("aloewood_boat",
            () -> new ModBoatItem(false, ModBoatEntity.Type.ALOEWOOD, new Item.Properties()));
    public static final RegistryObject<Item> ALOEWOOD_CHEST_BOAT = ITEMS.register("aloewood_chest_boat",
            () -> new ModBoatItem(true, ModBoatEntity.Type.ALOEWOOD, new Item.Properties()));


}
