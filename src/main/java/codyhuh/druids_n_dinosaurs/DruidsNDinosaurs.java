package codyhuh.druids_n_dinosaurs;

import codyhuh.druids_n_dinosaurs.client.renderers.ModBoatRenderer;
import codyhuh.druids_n_dinosaurs.common.items.WickerIdolItem;
import codyhuh.druids_n_dinosaurs.registry.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DruidsNDinosaurs.MOD_ID)
public class DruidsNDinosaurs {
    public static final String MOD_ID = "druids_n_dinosaurs";
    public static final Logger LOGGER = LogManager.getLogger();

    public DruidsNDinosaurs() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntities.register(bus);
        ModItems.ITEMS.register(bus);
        ModSounds.SOUNDS.register(bus);
        ModBlockEntities.register(bus);
        ModBlocks.registerBlocks(bus);
        ModConfiguredFeatures.register(bus);
        ModCreativeTab.register(bus);

        //bus.addListener(this::buildTabs);
        bus.addListener(this::commonSetup);
    }

//    private void buildTabs(BuildCreativeModeTabContentsEvent e) {
//        if (e.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
//            e.accept(ModItems.WICKER_IDOL.get());
//        }
//    }
    
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(()->{

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.ALOEWOOD_SAPLING.getId(), ModBlocks.POTTED_ALOEWOOD_SAPLING);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.ALOEWOOD_SAPLING.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.ALOEWOOD_LEAVES.get().asItem(), 0.4F);

        });
    }

    @Mod.EventBusSubscriber(modid = DruidsNDinosaurs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent e) {
            ItemProperties.register(ModItems.WICKER_IDOL.get(), new ResourceLocation(DruidsNDinosaurs.MOD_ID, "full"), (stack, world, player, i) -> !WickerIdolItem.containsEntity(stack) ? 0.0F : 1.0F);

            EntityRenderers.register(ModEntities.MOD_BOAT.get(), p_174010_ -> new ModBoatRenderer(p_174010_, false));
            EntityRenderers.register(ModEntities.MOD_CHEST_BOAT.get(), p_174010_ -> new ModBoatRenderer(p_174010_, true));
        }
    }
}
