package codyhuh.druids_n_dinosaurs.datagen.providers;

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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        this.stonecutting(Blocks.BONE_BLOCK, ModBlocks.CATACOMB_BONE_BLOCK.get(), 4).save(consumer);
        this.stonecutting(Blocks.TUFF, ModBlocks.HART_TUFF_TOTEM.get()).save(consumer);
        this.stonecutting(Blocks.TUFF, ModBlocks.ELEPHANT_TUFF_TOTEM.get()).save(consumer);
        this.stonecutting(Blocks.TUFF, ModBlocks.RIGHT_WING_TUFF_TOTEM.get()).save(consumer);
        this.stonecutting(Blocks.TUFF, ModBlocks.LEFT_WING_TUFF_TOTEM.get()).save(consumer);
        this.stonecutting(Blocks.TUFF, ModBlocks.BIRD_TUFF_TOTEM.get()).save(consumer);
        this.stonecutting(ModBlocks.LEFT_WING_TUFF_TOTEM.get(), ModBlocks.RIGHT_WING_TUFF_TOTEM.get()).save(consumer, "left_wing_tuff_totem_mirror");
        this.stonecutting(ModBlocks.RIGHT_WING_TUFF_TOTEM.get(), ModBlocks.LEFT_WING_TUFF_TOTEM.get()).save(consumer, "right_wing_tuff_totem_mirror");

        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.POLISHED_JADE_BLOCK.get(), 4).save(consumer);

        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.POLISHED_JADE_STAIRS.get(), 4).save(consumer);
        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.POLISHED_JADE_SLAB.get(), 8).save(consumer);

        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.JADE_STAIRS.get()).save(consumer);
        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.JADE_SLAB.get(), 2).save(consumer);

        this.stonecutting(ModBlocks.POLISHED_JADE_BLOCK, ModBlocks.POLISHED_JADE_STAIRS.get()).save(consumer, this.name("polished_jade_into_stairs"));
        this.stonecutting(ModBlocks.POLISHED_JADE_BLOCK, ModBlocks.POLISHED_JADE_SLAB.get(), 2).save(consumer, this.name("polished_jade_into_slabs"));

        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.SHATTERED_JADE.get(), 4).save(consumer);
        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.JADE_KINDRED_BULB.get(), 4).save(consumer);
        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.JADE_HUMMINGBIRD_BULB.get(), 4).save(consumer);

        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.JADE_BRICKS.get(), 4).save(consumer);
        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.JADE_BRICK_STAIRS.get(), 4).save(consumer);
        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.JADE_BRICK_SLAB.get(), 8).save(consumer);
        this.stonecutting(ModBlocks.JADE_BRICK_SLAB, ModBlocks.JADE_BRICK_STAIRS.get()).save(consumer, this.name("jade_bricks_into_stairs"));
        this.stonecutting(ModBlocks.JADE_BRICK_SLAB, ModBlocks.JADE_BRICK_SLAB.get(), 2).save(consumer, this.name("jade_bricks_into_slabs"));

        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.CHISELED_POLISHED_JADE.get(), 4).save(consumer);
        this.stonecutting(ModBlocks.JADE_BLOCK, ModBlocks.CHISELED_POLISHED_JADE_WALL.get(), 4).save(consumer, this.name("chiseled_polished_jade_wall_stonecutting"));

        this.stonecutting(ModBlocks.CHISELED_POLISHED_JADE, ModBlocks.CHISELED_POLISHED_JADE_WALL.get()).save(consumer, this.name("chiseled_polished_jade_into_walls"));

        this.makeWall(ModBlocks.CHISELED_POLISHED_JADE, ModBlocks.CHISELED_POLISHED_JADE_WALL).save(consumer);

        //Jade items
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.JADE_BRICK.get(), 1)
                .pattern("JJJ")
                .pattern("JJJ")
                .pattern("JJJ")
                .define('J', ModItems.JADE_SHARD.get())
                .unlockedBy(getHasName(ModItems.JADE_SHARD.get()), has(ModItems.JADE_SHARD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.JADE_SHARD.get(), 9)
                .requires(ModItems.JADE_BRICK.get())
                .unlockedBy(getHasName(ModItems.JADE_SHARD.get()), has(ModItems.JADE_SHARD.get()))
                .save(consumer);

        makeIngotToBlock(ModItems.JADE_BRICK, ModBlocks.JADE_BLOCK).save(consumer);

        //Jade axe
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.JADE_AXE.get(), 1)
                .pattern("JJ ")
                .pattern("JS ")
                .pattern(" S ")
                .define('J', ModItems.JADE_BRICK.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.JADE_BRICK.get()), has(ModItems.JADE_BRICK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.JADE_AXE.get(), 1)
                .pattern(" JJ")
                .pattern(" SJ")
                .pattern(" S ")
                .define('J', ModItems.JADE_BRICK.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.JADE_BRICK.get()), has(ModItems.JADE_BRICK.get()))
                .save(consumer, this.name("jade_axe_mirrored"));

        //Jade Doll
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.JADE_DOLL.get(), 1)
                .pattern("JJJ")
                .pattern("JWJ")
                .pattern("JJJ")
                .define('J', ModItems.JADE_BRICK.get())
                .define('W', ModItems.WICKER_IDOL.get())
                .unlockedBy(getHasName(ModItems.JADE_SHARD.get()), has(ModItems.JADE_SHARD.get()))
                .save(consumer);

        //Ornate Eggs
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_ORNATE_EGG.get(), 1)
                .pattern("CCC")
                .pattern("CSC")
                .pattern("CMC")
                .define('C', ModItems.EGG_SHARDS.get())
                .define('S', ModItems.BOTTLE_O_SOUL.get())
                .define('M', Items.COPPER_INGOT)
                .unlockedBy(getHasName(ModItems.BOTTLE_O_SOUL.get()), has(ModItems.BOTTLE_O_SOUL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLD_ORNATE_EGG.get(), 1)
                .pattern("CCC")
                .pattern("CSC")
                .pattern("CMC")
                .define('C', ModItems.EGG_SHARDS.get())
                .define('S', ModItems.BOTTLE_O_SOUL.get())
                .define('M', Items.GOLD_INGOT)
                .unlockedBy(getHasName(ModItems.BOTTLE_O_SOUL.get()), has(ModItems.BOTTLE_O_SOUL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_ORNATE_EGG.get(), 1)
                .pattern("CCC")
                .pattern("CSC")
                .pattern("CMC")
                .define('C', ModItems.EGG_SHARDS.get())
                .define('S', ModItems.BOTTLE_O_SOUL.get())
                .define('M', Items.DIAMOND)
                .unlockedBy(getHasName(ModItems.BOTTLE_O_SOUL.get()), has(ModItems.BOTTLE_O_SOUL.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 2)
                .requires(ModItems.ANTLER.get())
                .unlockedBy(getHasName(ModItems.ANTLER.get()), has(ModItems.ANTLER.get()))
                .save(consumer);

        //whisper pearls
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMBER_WHISPER_PEARL.get(), 1)
                .pattern("DCD")
                .pattern("CWC")
                .pattern("DCD")
                .define('D', Items.YELLOW_DYE)
                .define('C', Blocks.YELLOW_CANDLE.asItem())
                .define('W', ModItems.BOTTLE_O_SOUL.get())
                .unlockedBy(getHasName(ModItems.BOTTLE_O_SOUL.get()), has(ModItems.BOTTLE_O_SOUL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FUCHSIA_WHISPER_PEARL.get(), 1)
                .pattern("DCD")
                .pattern("CWC")
                .pattern("DCD")
                .define('D', Items.PINK_DYE)
                .define('C', Blocks.PINK_CANDLE.asItem())
                .define('W', ModItems.BOTTLE_O_SOUL.get())
                .unlockedBy(getHasName(ModItems.BOTTLE_O_SOUL.get()), has(ModItems.BOTTLE_O_SOUL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VERMILLION_WHISPER_PEARL.get(), 1)
                .pattern("DCD")
                .pattern("CWC")
                .pattern("DCD")
                .define('D', Items.ORANGE_DYE)
                .define('C', Blocks.ORANGE_CANDLE.asItem())
                .define('W', ModItems.BOTTLE_O_SOUL.get())
                .unlockedBy(getHasName(ModItems.BOTTLE_O_SOUL.get()), has(ModItems.BOTTLE_O_SOUL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VERDANT_WHISPER_PEARL.get(), 1)
                .pattern("DCD")
                .pattern("CWC")
                .pattern("DCD")
                .define('D', Items.LIME_DYE)
                .define('C', Blocks.LIME_CANDLE.asItem())
                .define('W', ModItems.BOTTLE_O_SOUL.get())
                .unlockedBy(getHasName(ModItems.BOTTLE_O_SOUL.get()), has(ModItems.BOTTLE_O_SOUL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AZURE_WHISPER_PEARL.get(), 1)
                .pattern("DCD")
                .pattern("CWC")
                .pattern("DCD")
                .define('D', Items.LIGHT_BLUE_DYE)
                .define('C', Blocks.LIGHT_BLUE_CANDLE.asItem())
                .define('W', ModItems.BOTTLE_O_SOUL.get())
                .unlockedBy(getHasName(ModItems.BOTTLE_O_SOUL.get()), has(ModItems.BOTTLE_O_SOUL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EBONY_WHISPER_PEARL.get(), 1)
                .pattern("DCD")
                .pattern("CWC")
                .pattern("DCD")
                .define('D', Items.BLACK_DYE)
                .define('C', Blocks.BLACK_CANDLE.asItem())
                .define('W', ModItems.BOTTLE_O_SOUL.get())
                .unlockedBy(getHasName(ModItems.BOTTLE_O_SOUL.get()), has(ModItems.BOTTLE_O_SOUL.get()))
                .save(consumer);

        //Aloewood woodset
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

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.GOLD_DOOR.get(), 3)
                .pattern("SS")
                .pattern("SS")
                .pattern("SS")
                .define('S', Items.GOLD_INGOT)
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.GOLD_TRAPDOOR.get(), 3)
                .pattern("SSS")
                .pattern("SSS")
                .define('S', Items.GOLD_INGOT)
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .save(consumer);

        //Aloewood sign
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.ALOEWOOD_SIGN.get(), 3)
                .pattern("SSS")
                .pattern("SSS")
                .pattern(" # ")
                .define('S', ModBlocks.ALOEWOOD_PLANKS.get())
                .define('#', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(ModBlocks.ALOEWOOD_LOG.get()), has(ModBlocks.ALOEWOOD_LOG.get()))
                .save(consumer);

        //Aloewood hanging sign
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.ALOEWOOD_HANGING_SIGN.get(), 6)
                .pattern("# #")
                .pattern("SSS")
                .pattern("SSS")
                .define('#', Items.CHAIN)
                .define('S', ModBlocks.STRIPPED_ALOEWOOD_LOG.get())
                .unlockedBy(getHasName(ModBlocks.ALOEWOOD_LOG.get()), has(ModBlocks.ALOEWOOD_LOG.get()))
                .save(consumer);

        //Rusticle
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUSTICLE.get(), 2)
                .pattern("RRR")
                .pattern(" R ")
                .define('R', ModItems.RUST.get())
                .unlockedBy(getHasName(ModItems.RUST.get()), has(ModItems.RUST.get()))
                .save(consumer);

        //Bounceshroom
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BOUNCESHROOM.get(), 1)
                .pattern("SSS")
                .pattern("SMS")
                .pattern("SSS")
                .define('S', Items.SLIME_BALL)
                .define('M', Tags.Items.MUSHROOMS)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.SLIME_BALL))
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

    public SingleItemRecipeBuilder stonecutting(ItemLike input, ItemLike result) {
        SingleItemRecipeBuilder var10000 = SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, result);
        IForgeRegistry var10001 = ForgeRegistries.BLOCKS;
        return var10000.unlockedBy("has_" + var10001.getKey(input), has(input));
    }

    public SingleItemRecipeBuilder stonecutting(ItemLike input, ItemLike result, int resultAmount) {
        SingleItemRecipeBuilder var10000 = SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, result, resultAmount);
        IForgeRegistry var10001 = ForgeRegistries.BLOCKS;
        return var10000.unlockedBy("has_" + var10001.getKey(input), has(input));
    }

    public SingleItemRecipeBuilder stonecutting(Supplier<Block> input, ItemLike result) {
        SingleItemRecipeBuilder var10000 = SingleItemRecipeBuilder.stonecutting(Ingredient.of(input.get()), RecipeCategory.BUILDING_BLOCKS, result);
        IForgeRegistry var10001 = ForgeRegistries.BLOCKS;
        return var10000.unlockedBy("has_" + var10001.getKey(input.get()), has(input.get()));
    }

    public SingleItemRecipeBuilder stonecutting(Supplier<Block> input, ItemLike result, int resultAmount) {
        SingleItemRecipeBuilder var10000 = SingleItemRecipeBuilder.stonecutting(Ingredient.of(input.get()), RecipeCategory.BUILDING_BLOCKS, result, resultAmount);
        IForgeRegistry var10001 = ForgeRegistries.BLOCKS;
        return var10000.unlockedBy("has_" + var10001.getKey(input.get()), has(input.get()));
    }

    public ShapedRecipeBuilder makeIngotToBlock(Supplier<? extends Item> ingotIn, Supplier<? extends Block> blockOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockOut.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ingotIn.get())
                .unlockedBy(getHasName(ingotIn.get()), has(ingotIn.get()));
    }

    public ShapedRecipeBuilder makeWall(Supplier<? extends Block> blockIn, Supplier<? extends Block> wallOut) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)wallOut.get(),
                6).pattern("MMM").pattern("MMM").define('M',
                (ItemLike)blockIn.get()).unlockedBy("has_" +
                ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    public ShapelessRecipeBuilder makeBlockToIngot(Supplier<? extends Block> blockIn, Supplier<? extends Item> ingotOut) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)ingotOut.get(), 9).requires((ItemLike)blockIn.get()).unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey((Block)blockIn.get()).getPath(), has((ItemLike)blockIn.get()));
    }

    private ResourceLocation name(String name) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, name);
    }
}
