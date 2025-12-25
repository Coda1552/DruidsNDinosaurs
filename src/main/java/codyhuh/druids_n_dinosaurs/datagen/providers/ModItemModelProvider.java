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

        simpleItem(ModItems.BOTTLE_O_SOUL);

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
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
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
