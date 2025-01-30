package codyhuh.druids_n_dinosaurs;

import codyhuh.druids_n_dinosaurs.common.items.WickerIdolItem;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import codyhuh.druids_n_dinosaurs.registry.ModSounds;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DruidsNDinosaurs.MOD_ID)
public class DruidsNDinosaurs {
    public static final String MOD_ID = "druids_n_dinosaurs";
    public static final Logger LOGGER = LogManager.getLogger();

    public DruidsNDinosaurs() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(bus);
        ModSounds.SOUNDS.register(bus);

        bus.addListener(this::buildTabs);
    }

    private void buildTabs(BuildCreativeModeTabContentsEvent e) {
        if (e.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            e.accept(ModItems.WICKER_IDOL.get());
        }
    }

    @Mod.EventBusSubscriber(modid = DruidsNDinosaurs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent e) {
            ItemProperties.register(ModItems.WICKER_IDOL.get(), new ResourceLocation(DruidsNDinosaurs.MOD_ID, "full"), (stack, world, player, i) -> !WickerIdolItem.containsEntity(stack) ? 0.0F : 1.0F);
        }
    }
}
