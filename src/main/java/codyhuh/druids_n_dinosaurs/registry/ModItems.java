package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.common.entity.custom.ModBoatEntity;
import codyhuh.druids_n_dinosaurs.common.items.*;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<Item> WICKER_IDOL = ITEMS.register("wicker_idol",
            () -> new WickerIdolItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> GOURD_RAPTOR_SPAWN_EGG = ITEMS.register("gourd_raptor_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GOURD_RAPTOR, 0x30776c, 0xd7ce63, new Item.Properties()));

    public static final RegistryObject<Item> GOURD_EGG = ITEMS.register("gourd_egg",
            () -> new GourdEggItem(new Item.Properties().stacksTo(16)));

    //Aloewood
    public static final RegistryObject<Item> ALOEWOOD_SIGN = ITEMS.register("aloewood_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.ALOEWOOD_SIGN.get(),
                    ModBlocks.ALOEWOOD_WALL_SIGN.get()));

    public static final RegistryObject<Item> ALOEWOOD_HANGING_SIGN = ITEMS.register("aloewood_hanging_sign",
            () -> new HangingSignItem(ModBlocks.ALOEWOOD_HANGING_SIGN.get(), ModBlocks.ALOEWOOD_WALL_HANGING_SIGN.get(),
                    new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> ALOEWOOD_BOAT = ITEMS.register("aloewood_boat",
            () -> new ModBoatItem(false, ModBoatEntity.Type.ALOEWOOD, new Item.Properties()));
    public static final RegistryObject<Item> ALOEWOOD_CHEST_BOAT = ITEMS.register("aloewood_chest_boat",
            () -> new ModBoatItem(true, ModBoatEntity.Type.ALOEWOOD, new Item.Properties()));

    public static final RegistryObject<Item> RUST = ITEMS.register("rust", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUSTLING_SPAWN_EGG = ITEMS.register("rustling_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.RUSTLING, 0x4d7a41, 0xed9c3c, new Item.Properties()));
    public static final RegistryObject<Item> RUSTMUNCHER_SPAWN_EGG = ITEMS.register("rustmuncher_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.RUSTMUNCHER, 0x725b59, 0x4e9067, new Item.Properties()));

    public static final RegistryObject<Item> RUSTLING_SHERD = ITEMS.register("rustling_pottery_sherd",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BOTTLE_O_SOUL = ITEMS.register("bottle_o_soul",
            () -> new BottleOSoulItem(new Item.Properties()));

    public static final RegistryObject<Item> WHISP_SPAWN_EGG = ITEMS.register("whisp_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.WHISP, 0xf4a8ef, 0xb1619c, new Item.Properties()));
    public static final RegistryObject<Item> FUCHSIA_WHISPER_PEARL = ITEMS.register("fuchsia_whisper_pearl",
            () -> new WhisperPearlItem(new Item.Properties(), 0));
    public static final RegistryObject<Item> VERMILLION_WHISPER_PEARL = ITEMS.register("vermillion_whisper_pearl",
            () -> new WhisperPearlItem(new Item.Properties(), 1));
    public static final RegistryObject<Item> AMBER_WHISPER_PEARL = ITEMS.register("amber_whisper_pearl",
            () -> new WhisperPearlItem(new Item.Properties(), 2));
    public static final RegistryObject<Item> VERDANT_WHISPER_PEARL = ITEMS.register("verdant_whisper_pearl",
            () -> new WhisperPearlItem(new Item.Properties(), 3));
    public static final RegistryObject<Item> AZURE_WHISPER_PEARL = ITEMS.register("azure_whisper_pearl",
            () -> new WhisperPearlItem(new Item.Properties(), 4));
    public static final RegistryObject<Item> EBONY_WHISPER_PEARL = ITEMS.register("ebony_whisper_pearl",
            () -> new WhisperPearlItem(new Item.Properties(), 5));

    public static final RegistryObject<Item> CRACKLE_SPAWN_EGG = ITEMS.register("crackle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CRACKLE, 0xc5c1b7, 0x6f9dd0, new Item.Properties()));
    public static final RegistryObject<Item> EGG_SHARDS = ITEMS.register("egg_shards",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> EGG_RAPTOR_SPAWN_EGG = ITEMS.register("egg_raptor_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.EGG_RAPTOR, 0x885635, 0xeeb656, new Item.Properties()));

    public static final RegistryObject<Item> HUE_HOG_SPAWN_EGG = ITEMS.register("hue_hog_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HUE_HOG, 0xa48e6c, 0x6b5139, new Item.Properties()));

}
