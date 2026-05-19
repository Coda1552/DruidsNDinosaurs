package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.ChiselchirpModel;
import codyhuh.druids_n_dinosaurs.client.renders.layers.ChiselchirpSherdLayer;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Chiselchirp;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ChiselchirpRenderer extends MobRenderer<Chiselchirp, ChiselchirpModel<Chiselchirp>> {

    public ChiselchirpRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ChiselchirpModel<>(pContext.bakeLayer(ModModelLayers.CHISELCHIRP)), 0.25f);
        this.addLayer(new ChiselchirpSherdLayer<>(this, pContext.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Chiselchirp pEntity) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/chiselchirp.png");
    }
}
