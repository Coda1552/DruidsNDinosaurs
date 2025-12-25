package codyhuh.druids_n_dinosaurs.datagen.providers;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DruidsNDinosaurs.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        // misc
        blockItem(ModBlocks.BOUNCESHROOM);

        // gold
        doorBlockWithRenderType(((DoorBlock) ModBlocks.GOLD_DOOR.get()), modLoc("block/gold_door_bottom"), modLoc("block/gold_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.GOLD_TRAPDOOR.get()), modLoc("block/gold_trapdoor"), true, "cutout");
        blockWithItem(ModBlocks.CUT_GOLD);
        blockWithItem(ModBlocks.CHISELED_GOLD);
        stairsBlock((StairBlock)ModBlocks.CUT_GOLD_STAIRS.get(), blockTexture(ModBlocks.CUT_GOLD.get()));
        slabBlock(((SlabBlock) ModBlocks.CUT_GOLD_SLAB.get()), blockTexture(ModBlocks.CUT_GOLD.get()), blockTexture(ModBlocks.CUT_GOLD.get()));

        //aloewood woodset
        blockWithItem(ModBlocks.ALOEWOOD_PLANKS);
        stairsBlock(((StairBlock) ModBlocks.ALOEWOOD_STAIRS.get()), blockTexture(ModBlocks.ALOEWOOD_PLANKS.get()));
        slabBlock(((SlabBlock) ModBlocks.ALOEWOOD_SLAB.get()), blockTexture(ModBlocks.ALOEWOOD_PLANKS.get()), blockTexture(ModBlocks.ALOEWOOD_PLANKS.get()));
        buttonBlock(((ButtonBlock) ModBlocks.ALOEWOOD_BUTTON.get()), blockTexture(ModBlocks.ALOEWOOD_PLANKS.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.ALOEWOOD_PRESSURE_PLATE.get()), blockTexture(ModBlocks.ALOEWOOD_PLANKS.get()));
        fenceBlock(((FenceBlock) ModBlocks.ALOEWOOD_FENCE.get()), blockTexture(ModBlocks.ALOEWOOD_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.ALOEWOOD_FENCE_GATE.get()), blockTexture(ModBlocks.ALOEWOOD_PLANKS.get()));

        doorBlockWithRenderType(((DoorBlock) ModBlocks.ALOEWOOD_DOOR.get()), modLoc("block/aloewood_door_bottom"), modLoc("block/aloewood_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.ALOEWOOD_TRAPDOOR.get()), modLoc("block/aloewood_trapdoor"), true, "cutout");

        signBlock(((StandingSignBlock) ModBlocks.ALOEWOOD_SIGN.get()), ((WallSignBlock) ModBlocks.ALOEWOOD_WALL_SIGN.get()),
                blockTexture(ModBlocks.ALOEWOOD_PLANKS.get()));
        hangingSignBlock(ModBlocks.ALOEWOOD_HANGING_SIGN.get(), ModBlocks.ALOEWOOD_WALL_HANGING_SIGN.get(),
                blockTexture(ModBlocks.ALOEWOOD_PLANKS.get()));

        logBlock(((RotatedPillarBlock) ModBlocks.ALOEWOOD_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_ALOEWOOD_LOG.get()), blockTexture(ModBlocks.STRIPPED_ALOEWOOD_LOG.get()), new ResourceLocation(DruidsNDinosaurs.MOD_ID, "block/stripped_aloewood_log_top"));
        blockItem(ModBlocks.ALOEWOOD_LOG);
        blockItem(ModBlocks.STRIPPED_ALOEWOOD_LOG);

        axisBlock(((RotatedPillarBlock) ModBlocks.ALOEWOOD_WOOD.get()), blockTexture(ModBlocks.ALOEWOOD_LOG.get()), blockTexture(ModBlocks.ALOEWOOD_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_ALOEWOOD_WOOD.get()), blockTexture(ModBlocks.STRIPPED_ALOEWOOD_LOG.get()), blockTexture(ModBlocks.STRIPPED_ALOEWOOD_LOG.get()));
        blockItem(ModBlocks.ALOEWOOD_WOOD);
        blockItem(ModBlocks.STRIPPED_ALOEWOOD_WOOD);

        leavesBlock(ModBlocks.ALOEWOOD_LEAVES);

        simpleBlockWithItem(ModBlocks.ALOEWOOD_SAPLING.get(), models().cross(blockTexture(ModBlocks.ALOEWOOD_SAPLING.get()).getPath(),
                blockTexture(ModBlocks.ALOEWOOD_SAPLING.get())).renderType("cutout"));

        simpleBlockWithItem(ModBlocks.POTTED_ALOEWOOD_SAPLING.get(), models().singleTexture("potted_aloewood_sapling", new ResourceLocation("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.ALOEWOOD_SAPLING.get())).renderType("cutout"));
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(DruidsNDinosaurs.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(),
                cubeAll(blockRegistryObject.get()));
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
