package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.HueHogModel;
import codyhuh.druids_n_dinosaurs.client.renders.layers.HueHogItemLayer;
import codyhuh.druids_n_dinosaurs.client.renders.layers.HueHogPuffLayer;
import codyhuh.druids_n_dinosaurs.common.entity.custom.HueHog;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HueHogRenderer extends MobRenderer<HueHog, HueHogModel<HueHog>> {

    public HueHogRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new HueHogModel<>(pContext.bakeLayer(ModModelLayers.HUE_HOG_LAYER)), 0.15F);
        this.addLayer(new HueHogPuffLayer(this, pContext.getModelSet()));
        this.addLayer(new HueHogItemLayer<>(this, pContext.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(HueHog pEntity) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/hue_hog/hue_hog.png");
    }
}
