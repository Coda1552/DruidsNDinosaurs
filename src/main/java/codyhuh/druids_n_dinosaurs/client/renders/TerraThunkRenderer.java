package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.GildedGallumpherModel;
import codyhuh.druids_n_dinosaurs.client.models.TerraThunkModel;
import codyhuh.druids_n_dinosaurs.client.renders.layers.GildedGallumpherItemsLayer;
import codyhuh.druids_n_dinosaurs.common.entity.custom.GildedGallumpher;
import codyhuh.druids_n_dinosaurs.common.entity.custom.TerraThunk;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TerraThunkRenderer extends MobRenderer<TerraThunk, TerraThunkModel<TerraThunk>> {

    public TerraThunkRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TerraThunkModel<>(pContext.bakeLayer(ModModelLayers.TERRA_THUNK)), 1.75f);
    }

    @Override
    public ResourceLocation getTextureLocation(TerraThunk pEntity) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/terra_thunk.png");
    }
}
