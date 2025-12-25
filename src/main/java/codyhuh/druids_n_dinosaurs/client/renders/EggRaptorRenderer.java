package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.EggRaptorModel;
import codyhuh.druids_n_dinosaurs.client.renders.layers.EggRaptorEggLayer;
import codyhuh.druids_n_dinosaurs.common.entity.custom.EggRaptor;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EggRaptorRenderer extends MobRenderer<EggRaptor, EggRaptorModel<EggRaptor>> {

    public EggRaptorRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new EggRaptorModel<>(pContext.bakeLayer(ModModelLayers.EGG_RAPTOR_LAYER)), 0.25F);

        this.addLayer(new EggRaptorEggLayer(this, pContext));
    }

    public ResourceLocation getTextureLocation(EggRaptor pEntity) {

        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/egg_raptor/egg_raptor_eggless.png");
    }
}
