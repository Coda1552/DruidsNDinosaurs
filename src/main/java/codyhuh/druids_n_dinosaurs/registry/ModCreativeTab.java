package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MARVELOUS_MENAGERIE_MOBS =
            CREATIVE_MODE_TABS.register("druids_n_dinosaurs", ()-> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WICKER_IDOL.get()))
                    .title(Component.translatable("creativetab.druids_n_dinosaurs"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.WICKER_IDOL.get());

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

                        output.accept(ModItems.RUST.get());
                        output.accept(ModBlocks.RUST_BLOCK.get());
                        output.accept(ModBlocks.RUSTICLE.get());
                        output.accept(ModItems.RUSTLING_SPAWN_EGG.get());
                        output.accept(ModItems.RUSTMUNCHER_SPAWN_EGG.get());

                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
