package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.common.blocks.*;
import codyhuh.druids_n_dinosaurs.common.blocks.RusticleBlock;
import codyhuh.druids_n_dinosaurs.common.worldgen.tree.AloewoodTreeGrower;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DruidsNDinosaurs.MOD_ID);

    // PLANTS
    public static final RegistryObject<Block> BOUNCESHROOM = registerBlock("bounceshroom",
            () -> new BounceshroomBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK).lightLevel(value -> 1).randomTicks()));
    public static final RegistryObject<Block> YELLOW_IRONWEED = registerBlock("yellow_ironweed",
            () -> new BonemealableDoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));

    public static final RegistryObject<Block> BLOOM_BEACON = registerBloomBeacon("bloom_beacon",
            () -> new BloomBeaconBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).sound(SoundType.MOSS)
                    .pushReaction(PushReaction.BLOCK).randomTicks().lightLevel((state) -> {
                        return 15;
                    })));

    public static final RegistryObject<Block> SULFUR_BLOCK = registerBlock("sulfur_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF)));

    // GOLD
    public static final RegistryObject<Block> CUT_GOLD = registerBlock("cut_gold",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> CUT_GOLD_SLAB = registerBlock("cut_gold_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> CUT_GOLD_STAIRS = registerBlock("cut_gold_stairs",
            () -> new StairBlock(() -> CUT_GOLD.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> CHISELED_GOLD = registerBlock("chiseled_gold",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> GOLD_DOOR = registerBlock("gold_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).noOcclusion(), ModBlockSetTypes.DND_GOLD));
    public static final RegistryObject<Block> GOLD_TRAPDOOR = registerBlock("gold_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR).noOcclusion(), ModBlockSetTypes.DND_GOLD));

    //Aloewood plank blocks
    public static final RegistryObject<Block> ALOEWOOD_PLANKS = registerBlock("aloewood_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).ignitedByLava()));
    public static final RegistryObject<Block> ALOEWOOD_STAIRS = registerBlock("aloewood_stairs",
            () -> new StairBlock(() -> ModBlocks.ALOEWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .ignitedByLava()));
    public static final RegistryObject<Block> ALOEWOOD_SLAB = registerBlock("aloewood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).ignitedByLava()));
    public static final RegistryObject<Block> ALOEWOOD_BUTTON = registerBlock("aloewood_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).ignitedByLava()
                    , BlockSetType.CRIMSON, 25, true));
    public static final RegistryObject<Block> ALOEWOOD_PRESSURE_PLATE = registerBlock("aloewood_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy
                    (Blocks.OAK_PRESSURE_PLATE).ignitedByLava(), BlockSetType.CRIMSON));
    public static final RegistryObject<Block> ALOEWOOD_FENCE = registerBlock("aloewood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).ignitedByLava()));
    public static final RegistryObject<Block> ALOEWOOD_FENCE_GATE = registerBlock("aloewood_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).ignitedByLava(),
                    SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));


    //Aloewood Door and Trapdoor
    public static final RegistryObject<Block> ALOEWOOD_DOOR = registerBlock("aloewood_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.CRIMSON));
    public static final RegistryObject<Block> ALOEWOOD_TRAPDOOR = registerBlock("aloewood_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.CRIMSON));

    //Aloewood signs
    public static final RegistryObject<Block> ALOEWOOD_SIGN = BLOCKS.register("aloewood_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_SIGN), ModBlockSetTypes.ALOEWOOD));
    public static final RegistryObject<Block> ALOEWOOD_WALL_SIGN = BLOCKS.register("aloewood_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_WALL_SIGN), ModBlockSetTypes.ALOEWOOD));
    public static final RegistryObject<Block> ALOEWOOD_HANGING_SIGN = BLOCKS.register("aloewood_hanging_sign",
            () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_HANGING_SIGN), ModBlockSetTypes.ALOEWOOD));
    public static final RegistryObject<Block> ALOEWOOD_WALL_HANGING_SIGN = BLOCKS.register("aloewood_wall_hanging_sign",
            () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_WALL_HANGING_SIGN), ModBlockSetTypes.ALOEWOOD));

    //Aloewood logs and wood
    public static final RegistryObject<Block> ALOEWOOD_LOG = registerBlock("aloewood_log",
            () -> new FlammableWoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> ALOEWOOD_WOOD = registerBlock("aloewood_wood",
            () -> new FlammableWoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> STRIPPED_ALOEWOOD_LOG = registerBlock("stripped_aloewood_log",
            () -> new FlammableWoodLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> STRIPPED_ALOEWOOD_WOOD = registerBlock("stripped_aloewood_wood",
            () -> new FlammableWoodLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));

    //Aloewood Leaves
    public static final RegistryObject<Block> ALOEWOOD_LEAVES = registerBlock("aloewood_leaves",
            () -> new AloewoodLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> BLOOMED_ALOEWOOD_LEAVES = registerBlock("bloomed_aloewood_leaves",
            () -> new BloomedAloewoodLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).lightLevel((pState)-> 14)));

    //Aloewood Sapling Blocks
    public static final RegistryObject<Block> ALOEWOOD_SAPLING = registerBlock("aloewood_sapling",
            () -> new SaplingBlock(new AloewoodTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).noOcclusion().noCollission()));
    public static final RegistryObject<Block> POTTED_ALOEWOOD_SAPLING = registerBlock("potted_aloewood_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.ALOEWOOD_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM).noOcclusion()));

    //Rust
    public static final RegistryObject<Block> RUST_BLOCK = registerBlock("rust_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
    public static final RegistryObject<Block> RUSTICLE = registerBlock("rusticle",
            () -> new RusticleBlock(BlockBehaviour.Properties.copy(Blocks.POINTED_DRIPSTONE)));
    public static final RegistryObject<Block> BRAMBLERUST = registerBlock("bramblerust",
            () -> new BramblerustBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noCollission()
                    .sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY)));

    //Crackle
    public static final RegistryObject<Block> CRACKLE_EGG = registerBlock("crackle_egg",
            () -> new CrackleEggBlock(BlockBehaviour.Properties.copy(Blocks.SNIFFER_EGG).randomTicks()));

    //Catacomb Bone Block
    public static final RegistryObject<Block> CATACOMB_BONE_BLOCK = registerBlock("catacomb_bone_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).requiresCorrectToolForDrops()));

    //Tuff totem blocks
    public static final RegistryObject<Block> BIRD_TUFF_TOTEM = registerBlock("bird_tuff_totem",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LEFT_WING_TUFF_TOTEM = registerBlock("left_wing_tuff_totem",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> RIGHT_WING_TUFF_TOTEM = registerBlock("right_wing_tuff_totem",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> HART_TUFF_TOTEM = registerBlock("hart_tuff_totem",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ELEPHANT_TUFF_TOTEM = registerBlock("elephant_tuff_totem",
            () -> new ElephantTotemBlock(BlockBehaviour.Properties.copy(Blocks.TUFF).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TUFF_BONE = registerBlock("tuff_bone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF)));

    //Jade
    public static final RegistryObject<Block> JADE_ORE = registerBlock("jade_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> DEEPSLATE_JADE_ORE = registerBlock("deepslate_jade_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)));
    public static final RegistryObject<Block> JADE_BLOCK = registerBlock("jade_block",
            () -> new JadeBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> JADE_STAIRS = registerBlock("jade_stairs",
            ()-> new StairBlock(() -> ModBlocks.JADE_BLOCK.get().defaultBlockState(),BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> JADE_SLAB = registerBlock("jade_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> POLISHED_JADE_BLOCK = registerBlock("polished_jade",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> POLISHED_JADE_STAIRS = registerBlock("polished_jade_stairs",
            ()-> new StairBlock(() -> ModBlocks.POLISHED_JADE_BLOCK.get().defaultBlockState(),BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> POLISHED_JADE_SLAB = registerBlock("polished_jade_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> JADE_HUMMINGBIRD_BULB = registerBlock("jade_hummingbird_bulb",
            () -> new BulbBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)
                    .lightLevel((pState) -> {return pState.getValue(BulbBlock.LIT) ? 15 : 0;})));
    public static final RegistryObject<Block> JADE_KINDRED_BULB = registerBlock("jade_kindred_bulb",
            () -> new BulbBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)
                    .lightLevel((pState) -> {return pState.getValue(BulbBlock.LIT) ? 15 : 0;})));
    public static final RegistryObject<Block> JADE_BRICKS = registerBlock("jade_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> JADE_BRICK_STAIRS = registerBlock("jade_brick_stairs",
            ()-> new StairBlock(() -> ModBlocks.JADE_BRICKS.get().defaultBlockState(),BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> JADE_BRICK_SLAB = registerBlock("jade_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> CHISELED_POLISHED_JADE = registerBlock("chiseled_polished_jade",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> CHISELED_POLISHED_JADE_WALL = registerBlock("chiseled_polished_jade_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> SHATTERED_JADE = registerBlock("shattered_jade",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    //Flowers
    public static final RegistryObject<Block> BRIGHT_BLOOMS = registerBlock("bright_blooms",
            () -> new PinkPetalsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT)
                    .noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY).lightLevel((state) -> {
                        return switch (state.getValue(PinkPetalsBlock.AMOUNT)){
                            case 1 -> 2;
                            case 2 -> 6;
                            case 3 -> 8;
                            default -> 10;
                        };
                    })));

    public static final RegistryObject<Block> GILDED_FORGET_ME_NOTS = registerBlock("gilded_forget_me_nots",
            () -> new BonemealableFlowerBlock(MobEffects.SATURATION, 7, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT)
                    .noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    //Antler
    public static final RegistryObject<Block> ANTLER_BLOCK = registerBlock("antler_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));

    //Ooze
    public static final RegistryObject<Block> OOZE_TRAIL = registerBlock("ooze_trail",
            () -> new OozeTrailBlock(BlockBehaviour.Properties.copy(Blocks.GLOW_LICHEN).lightLevel((state)->0)
                    .sound(SoundType.SLIME_BLOCK).mapColor(DyeColor.BLACK)));

    //Gouda
    public static final RegistryObject<Block> GOUDA_CHEESE = registerBlock("gouda_cheese",
            () -> new GoudaCheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE).mapColor(DyeColor.YELLOW)));

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }

    private static <T extends Block> RegistryObject<T> registerBloomBeacon(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBloomBeaconItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBloomBeaconItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void registerBlocks(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
