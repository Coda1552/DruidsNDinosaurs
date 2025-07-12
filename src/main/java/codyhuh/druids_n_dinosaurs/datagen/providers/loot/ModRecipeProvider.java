package codyhuh.druids_n_dinosaurs.datagen.providers.loot;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModBlocks;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import codyhuh.druids_n_dinosaurs.registry.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        //Sigillaria woodset
        makePlanks(ModBlocks.ALOEWOOD_PLANKS, ModTags.Items.ALOEWOOD_LOG_ITEM).save(consumer);
        makeWood(ModBlocks.ALOEWOOD_WOOD, ModBlocks.ALOEWOOD_LOG).save(consumer);
        makeWood(ModBlocks.STRIPPED_ALOEWOOD_WOOD, ModBlocks.STRIPPED_ALOEWOOD_LOG).save(consumer);
        makeStairs(ModBlocks.ALOEWOOD_PLANKS, ModBlocks.ALOEWOOD_STAIRS).save(consumer);
        makeSlab(ModBlocks.ALOEWOOD_PLANKS, ModBlocks.ALOEWOOD_SLAB).save(consumer);
        makeFence(ModBlocks.ALOEWOOD_FENCE, ModBlocks.ALOEWOOD_PLANKS).save(consumer);
        makeFenceGate(ModBlocks.ALOEWOOD_FENCE_GATE, ModBlocks.ALOEWOOD_PLANKS).save(consumer);
        makeDoor(ModBlocks.ALOEWOOD_DOOR, ModBlocks.ALOEWOOD_PLANKS).save(consumer);
        makeTrapdoor(ModBlocks.ALOEWOOD_TRAPDOOR, ModBlocks.ALOEWOOD_PLANKS).save(consumer);
        makeButton(ModBlocks.ALOEWOOD_BUTTON, ModBlocks.ALOEWOOD_PLANKS).save(consumer);
        makePressurePlate(ModBlocks.ALOEWOOD_PRESSURE_PLATE, ModBlocks.ALOEWOOD_PLANKS).save(consumer);
        
        //Sigillaria sign
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.ALOEWOOD_SIGN.get(), 3)
                .pattern("SSS")
                .pattern("SSS")
                .pattern(" # ")
                .define('S', ModBlocks.ALOEWOOD_PLANKS.get())
                .define('#', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(ModBlocks.ALOEWOOD_LOG.get()), has(ModBlocks.ALOEWOOD_LOG.get()))
                .save(consumer);

        //Sigillaria hanging sign
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.ALOEWOOD_HANGING_SIGN.get(), 6)
                .pattern("# #")
                .pattern("SSS")
                .pattern("SSS")
                .define('#', Items.CHAIN)
                .define('S', ModBlocks.STRIPPED_ALOEWOOD_LOG.get())
                .unlockedBy(getHasName(ModBlocks.ALOEWOOD_LOG.get()), has(ModBlocks.ALOEWOOD_LOG.get()))
                .save(consumer);
    }
    public ShapelessRecipeBuilder makePlanks(Supplier<? extends Block> plankOut, TagKey<Item> logIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)plankOut.get(), 4).requires(logIn).group("planks").unlockedBy("has_log", has(logIn));
    }

    public ShapedRecipeBuilder makeDoor(Supplier<? extends Block> doorOut, Supplier<? extends Block> plankIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)doorOut.get(), 3).pattern("PP").pattern("PP").pattern("PP").define('P', (ItemLike)plankIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)plankIn.get()).getPath(), has((ItemLike)plankIn.get()));
    }

    public ShapedRecipeBuilder makeTrapdoor(Supplier<? extends Block> trapdoorOut, Supplier<? extends Block> plankIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)trapdoorOut.get(), 2).pattern("PPP").pattern("PPP").define('P', (ItemLike)plankIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)plankIn.get()).getPath(), has((ItemLike)plankIn.get()));
    }

    public ShapelessRecipeBuilder makeButton(Supplier<? extends Block> buttonOut, Supplier<? extends Block> blockIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)buttonOut.get()).requires((ItemLike)blockIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makePressurePlate(Supplier<? extends Block> pressurePlateOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)pressurePlateOut.get())
                .pattern("BB")
                .define('B', (ItemLike)blockIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeStairs(Supplier<? extends Block> blockIn, Supplier<? extends Block> stairsOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)stairsOut.get(), 4)
                .pattern("M  ")
                .pattern("MM ")
                .pattern("MMM")
                .define('M', (ItemLike)blockIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeSlab(Supplier<? extends Block> blockIn, Supplier<? extends Block> slabOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)slabOut.get(), 6)
                .pattern("MMM")
                .define('M', (ItemLike)blockIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeFence(Supplier<? extends Block> fenceOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)fenceOut.get(), 6).pattern("M/M").pattern("M/M").define('M', (ItemLike)blockIn.get()).define('/', Tags.Items.RODS_WOODEN).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeFenceGate(Supplier<? extends Block> fenceGateOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)fenceGateOut.get()).pattern("/M/")
                .pattern("/M/")
                .define('M',
                        (ItemLike)blockIn.get()).define('/', Tags.Items.RODS_WOODEN).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapedRecipeBuilder makeWood(Supplier<? extends Block> woodOut, Supplier<? extends Block> logIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)woodOut.get(), 3)
                .pattern("MM")
                .pattern("MM")
                .define('M', (ItemLike)logIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)logIn.get()).getPath(), has((ItemLike)logIn.get()));
    }

    private ResourceLocation name(String name) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, name);
    }
}
