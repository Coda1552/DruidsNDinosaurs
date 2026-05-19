package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.GildedGallumpherModel;
import codyhuh.druids_n_dinosaurs.client.models.JadeElephantModel;
import codyhuh.druids_n_dinosaurs.client.renders.layers.GildedGallumpherItemsLayer;
import codyhuh.druids_n_dinosaurs.client.renders.layers.JadeElephantItemLayer;
import codyhuh.druids_n_dinosaurs.common.entity.custom.GildedGallumpher;
import codyhuh.druids_n_dinosaurs.common.entity.custom.JadeElephant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GildedGallumpherRenderer extends MobRenderer<GildedGallumpher, GildedGallumpherModel<GildedGallumpher>> {

    public GildedGallumpherRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GildedGallumpherModel<>(pContext.bakeLayer(ModModelLayers.GILDED_GALLUMPHER)), 1.25f);
        this.addLayer(new GildedGallumpherItemsLayer<>(this, pContext.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(GildedGallumpher pEntity) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/gilded_gallumpher.png");
    }
}
