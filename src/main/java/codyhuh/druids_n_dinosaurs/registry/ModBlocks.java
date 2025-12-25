package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.common.blocks.*;
import codyhuh.druids_n_dinosaurs.common.blocks.RusticleBlock;
import codyhuh.druids_n_dinosaurs.common.worldgen.tree.AloewoodTreeGrower;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
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
            () -> new DoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));

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
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));

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

    //Crackle
    public static final RegistryObject<Block> CRACKLE_EGG = registerBlock("crackle_egg",
            () -> new CrackleEggBlock(BlockBehaviour.Properties.copy(Blocks.SNIFFER_EGG)));

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void registerBlocks(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
