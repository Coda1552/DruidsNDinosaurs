package codyhuh.druids_n_dinosaurs.datagen.providers;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DruidsNDinosaurs.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        // GOLD
        simpleBlockItem(ModBlocks.GOLD_DOOR);
        trapdoorItem(ModBlocks.GOLD_TRAPDOOR);
        evenSimplerBlockItem(ModBlocks.CUT_GOLD_STAIRS);
        evenSimplerBlockItem(ModBlocks.CUT_GOLD_SLAB);

        // ALOEWOOD
        simpleBlockItem(ModBlocks.ALOEWOOD_DOOR);

        simpleBlockItem(ModBlocks.ALOEWOOD_SAPLING);

        trapdoorItem(ModBlocks.ALOEWOOD_TRAPDOOR);

        evenSimplerBlockItem(ModBlocks.ALOEWOOD_STAIRS);
        evenSimplerBlockItem(ModBlocks.ALOEWOOD_SLAB);
        evenSimplerBlockItem(ModBlocks.ALOEWOOD_FENCE_GATE);
        evenSimplerBlockItem(ModBlocks.ALOEWOOD_PRESSURE_PLATE);

        fenceItem(ModBlocks.ALOEWOOD_FENCE, ModBlocks.ALOEWOOD_PLANKS);
        buttonItem(ModBlocks.ALOEWOOD_BUTTON, ModBlocks.ALOEWOOD_PLANKS);

        simpleItem(ModItems.ALOEWOOD_SIGN);
        simpleItem(ModItems.ALOEWOOD_HANGING_SIGN);

        simpleItem(ModItems.ALOEWOOD_BOAT);
        simpleItem(ModItems.ALOEWOOD_CHEST_BOAT);

        simpleItem(ModItems.RUSTLING_SHERD);

        simpleItem(ModItems.HART_SHERD);
        simpleItem(ModItems.GAZE_SHERD);
        simpleItem(ModItems.RUSTMUNCHER_SHERD);
        simpleItem(ModItems.CRACKLE_SHERD);

        simpleItem(ModItems.BLOOM_SHERD);
        simpleItem(ModItems.CROWN_SHERD);
        simpleItem(ModItems.LUNAR_SHERD);
        simpleItem(ModItems.STELLAR_SHERD);

        simpleItem(ModItems.WHISP_SHERD);
        simpleItem(ModItems.TRUNK_SHERD);
        simpleItem(ModItems.REBIRTH_SHERD);
        simpleItem(ModItems.WAYFIND_UP_SHERD);

        simpleItem(ModItems.WAYFIND_DOWN_SHERD);
        simpleItem(ModItems.WAYFIND_LEFT_SHERD);
        simpleItem(ModItems.WAYFIND_RIGHT_SHERD);
        simpleItem(ModItems.MONKE_SHERD);

        simpleItem(ModItems.BOTTLE_O_SOUL);
        simpleItem(ModItems.BOTTLE_O_ETHEREAL);

        withExistingParent(ModItems.WHISP_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(ModItems.AMBER_WHISPER_PEARL);
        simpleItem(ModItems.AZURE_WHISPER_PEARL);
        simpleItem(ModItems.EBONY_WHISPER_PEARL);
        simpleItem(ModItems.FUCHSIA_WHISPER_PEARL);
        simpleItem(ModItems.VERDANT_WHISPER_PEARL);
        simpleItem(ModItems.VERMILLION_WHISPER_PEARL);

        withExistingParent(ModItems.CRACKLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleItem(ModItems.EGG_SHARDS);

        withExistingParent(ModItems.EGG_RAPTOR_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.HUE_HOG_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.JADE_AUTOMATON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.JADE_ELEPHANT_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TUFF_TOTEM_POLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.HART_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GILDED_GALLUMPHER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CHISELCHIRP_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TERRA_THUNK_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SLUDGER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleItem(ModItems.JADE_SHARD);
        simpleItem(ModItems.JADE_BRICK);
        simpleItem(ModItems.JADE_DOLL);

        simpleTool(ModItems.JADE_AXE);
        simpleTool(ModItems.FOOLS_SCEPTER);

        wallItem(ModBlocks.CHISELED_POLISHED_JADE_WALL, ModBlocks.CHISELED_POLISHED_JADE);

        evenSimplerBlockItem(ModBlocks.JADE_BRICK_SLAB);
        evenSimplerBlockItem(ModBlocks.JADE_BRICK_STAIRS);

        evenSimplerBlockItem(ModBlocks.POLISHED_JADE_SLAB);
        evenSimplerBlockItem(ModBlocks.POLISHED_JADE_STAIRS);

        evenSimplerBlockItem(ModBlocks.JADE_SLAB);
        evenSimplerBlockItem(ModBlocks.JADE_STAIRS);

        simpleItem(ModItems.COPPER_ORNATE_EGG);
        simpleItem(ModItems.GOLD_ORNATE_EGG);
        simpleItem(ModItems.DIAMOND_ORNATE_EGG);

        simpleItem(ModItems.ANTLER);
        simpleItem(ModItems.VENISON);
        simpleItem(ModItems.COOKED_VENISON);

        simpleBlockItem(ModBlocks.BRAMBLERUST);
        simpleBlockItem(ModBlocks.GILDED_FORGET_ME_NOTS);
        simpleItem(ModItems.PALEO_MEAT);
        simpleItem(ModItems.COOKED_PALEO_MEAT);

        simpleItem(ModItems.FLOWER_CROWN);
        simpleItem(ModItems.BLUE_FLOWER_CROWN);
        simpleItem(ModItems.SLUDGE_BALL);

        simpleItem(ModItems.SULFUR);

        evenSimplerBlockItem(ModBlocks.GOUDA_CHEESE_SLAB);
        evenSimplerBlockItem(ModBlocks.GOUDA_CHEESE_STAIRS);
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", new ResourceLocation(DruidsNDinosaurs.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DruidsNDinosaurs.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleTool(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(DruidsNDinosaurs.MOD_ID, "item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(DruidsNDinosaurs.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(DruidsNDinosaurs.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(DruidsNDinosaurs.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DruidsNDinosaurs.MOD_ID,"item/" + item.getId().getPath()));
    }
}
