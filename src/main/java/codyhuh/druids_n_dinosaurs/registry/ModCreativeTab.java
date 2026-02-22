package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB =
            CREATIVE_MODE_TABS.register("druids_n_dinosaurs", ()-> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WICKER_IDOL.get()))
                    .title(Component.translatable("creativetab.druids_n_dinosaurs"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.WICKER_IDOL.get());

                        output.accept(ModItems.EGG_SHARDS.get());
                        output.accept(ModBlocks.CRACKLE_EGG.get());

                        output.accept(ModItems.GOURD_EGG.get());

                        output.accept(ModItems.BOTTLE_O_SOUL.get());

                        output.accept(ModItems.AMBER_WHISPER_PEARL.get());
                        output.accept(ModItems.AZURE_WHISPER_PEARL.get());
                        output.accept(ModItems.EBONY_WHISPER_PEARL.get());
                        output.accept(ModItems.FUCHSIA_WHISPER_PEARL.get());
                        output.accept(ModItems.VERDANT_WHISPER_PEARL.get());
                        output.accept(ModItems.VERMILLION_WHISPER_PEARL.get());

                        output.accept(ModItems.RUST.get());
                        output.accept(ModBlocks.RUST_BLOCK.get());
                        output.accept(ModBlocks.RUSTICLE.get());
                        output.accept(ModItems.RUSTLING_SHERD.get());

                        output.accept(ModItems.COPPER_ORNATE_EGG.get());
                        output.accept(ModItems.GOLD_ORNATE_EGG.get());
                        output.accept(ModItems.DIAMOND_ORNATE_EGG.get());

                        output.accept(ModItems.ANTLER.get());
                        output.accept(ModItems.VENISON.get());
                        output.accept(ModItems.COOKED_VENISON.get());

                        output.accept(ModBlocks.YELLOW_IRONWEED.get());
                        output.accept(ModBlocks.BOUNCESHROOM.get());

                        output.accept(ModBlocks.ALOEWOOD_PLANKS.get());

                        output.accept(ModBlocks.ALOEWOOD_LOG.get());
                        output.accept(ModBlocks.STRIPPED_ALOEWOOD_LOG.get());
                        output.accept(ModBlocks.ALOEWOOD_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_ALOEWOOD_WOOD.get());

                        output.accept(ModBlocks.ALOEWOOD_STAIRS.get());
                        output.accept(ModBlocks.ALOEWOOD_SLAB.get());
                        output.accept(ModBlocks.ALOEWOOD_FENCE.get());
                        output.accept(ModBlocks.ALOEWOOD_FENCE_GATE.get());
                        output.accept(ModBlocks.ALOEWOOD_DOOR.get());
                        output.accept(ModBlocks.ALOEWOOD_TRAPDOOR.get());
                        output.accept(ModBlocks.ALOEWOOD_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.ALOEWOOD_BUTTON.get());

                        output.accept(ModItems.ALOEWOOD_SIGN.get());
                        output.accept(ModItems.ALOEWOOD_HANGING_SIGN.get());
                        output.accept(ModItems.ALOEWOOD_BOAT.get());
                        output.accept(ModItems.ALOEWOOD_CHEST_BOAT.get());

                        output.accept(ModBlocks.ALOEWOOD_LEAVES.get());
                        output.accept(ModBlocks.ALOEWOOD_SAPLING.get());

                        output.accept(ModBlocks.CUT_GOLD.get());
                        output.accept(ModBlocks.CUT_GOLD_SLAB.get());
                        output.accept(ModBlocks.CUT_GOLD_STAIRS.get());
                        output.accept(ModBlocks.CHISELED_GOLD.get());
                        output.accept(ModBlocks.GOLD_DOOR.get());
                        output.accept(ModBlocks.GOLD_TRAPDOOR.get());

                        output.accept(ModBlocks.CATACOMB_BONE_BLOCK.get());
                        output.accept(ModBlocks.BIRD_TUFF_TOTEM.get());
                        output.accept(ModBlocks.ELEPHANT_TUFF_TOTEM.get());
                        output.accept(ModBlocks.HART_TUFF_TOTEM.get());
                        output.accept(ModBlocks.LEFT_WING_TUFF_TOTEM.get());
                        output.accept(ModBlocks.RIGHT_WING_TUFF_TOTEM.get());

                        output.accept(ModItems.JADE_SHARD.get());
                        output.accept(ModItems.JADE_BRICK.get());
                        output.accept(ModItems.JADE_AXE.get());
                        output.accept(ModItems.JADE_DOLL.get());
                        output.accept(ModBlocks.JADE_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_JADE_ORE.get());
                        output.accept(ModBlocks.JADE_BLOCK.get());
                        output.accept(ModBlocks.JADE_STAIRS.get());
                        output.accept(ModBlocks.JADE_SLAB.get());
                        output.accept(ModBlocks.SHATTERED_JADE.get());
                        output.accept(ModBlocks.POLISHED_JADE_BLOCK.get());
                        output.accept(ModBlocks.POLISHED_JADE_STAIRS.get());
                        output.accept(ModBlocks.POLISHED_JADE_SLAB.get());
                        output.accept(ModBlocks.CHISELED_POLISHED_JADE.get());
                        output.accept(ModBlocks.CHISELED_POLISHED_JADE_WALL.get());
                        output.accept(ModBlocks.JADE_BRICKS.get());
                        output.accept(ModBlocks.JADE_BRICK_STAIRS.get());
                        output.accept(ModBlocks.JADE_BRICK_SLAB.get());
                        output.accept(ModBlocks.JADE_HUMMINGBIRD_BULB.get());
                        output.accept(ModBlocks.JADE_KINDRED_BULB.get());

                        output.accept(ModItems.CRACKLE_SPAWN_EGG.get());
                        output.accept(ModItems.EGG_RAPTOR_SPAWN_EGG.get());
                        output.accept(ModItems.GOURD_RAPTOR_SPAWN_EGG.get());
                        output.accept(ModItems.HART_SPAWN_EGG.get());
                        output.accept(ModItems.HUE_HOG_SPAWN_EGG.get());
                        output.accept(ModItems.JADE_AUTOMATON_SPAWN_EGG.get());
                        output.accept(ModItems.JADE_ELEPHANT_SPAWN_EGG.get());
                        output.accept(ModItems.RUSTLING_SPAWN_EGG.get());
                        output.accept(ModItems.RUSTMUNCHER_SPAWN_EGG.get());
                        output.accept(ModItems.TUFF_TOTEM_POLE_SPAWN_EGG.get());
                        output.accept(ModItems.WHISP_SPAWN_EGG.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
