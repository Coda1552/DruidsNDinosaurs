package codyhuh.druids_n_dinosaurs.event;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.GourdRaptorModel;
import codyhuh.druids_n_dinosaurs.client.renders.GourdRaptorRender;
import codyhuh.druids_n_dinosaurs.client.renders.ModBoatRenderer;
import codyhuh.druids_n_dinosaurs.common.items.WickerIdolItem;
import codyhuh.druids_n_dinosaurs.registry.ModBlockEntities;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DruidsNDinosaurs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e) {
        ItemProperties.register(ModItems.WICKER_IDOL.get(), new ResourceLocation(DruidsNDinosaurs.MOD_ID, "full"), (stack, world, player, i) -> !WickerIdolItem.containsEntity(stack) ? 0.0F : 1.0F);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.ALOEWOOD_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.ALOEWOOD_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.GOURD_RAPTOR_LAYER, GourdRaptorModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRender(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);

        EntityRenderers.register(ModEntities.MOD_BOAT.get(), p_174010_ -> new ModBoatRenderer(p_174010_, false));
        EntityRenderers.register(ModEntities.MOD_CHEST_BOAT.get(), p_174010_ -> new ModBoatRenderer(p_174010_, true));

        event.registerEntityRenderer(ModEntities.GOURD_RAPTOR.get(), GourdRaptorRender::new);
        event.registerEntityRenderer(ModEntities.GOURD_EGG.get(), ThrownItemRenderer::new);
    }
}
