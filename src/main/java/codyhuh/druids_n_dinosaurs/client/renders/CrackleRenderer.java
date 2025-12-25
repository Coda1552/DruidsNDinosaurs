package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.CrackleModel;
import codyhuh.druids_n_dinosaurs.client.renders.layers.CrackleEyesLayer;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Crackle;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CrackleRenderer extends MobRenderer<Crackle, CrackleModel<Crackle>> {

    public CrackleRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CrackleModel<>(pContext.bakeLayer(ModModelLayers.CRACKLE_LAYER)), 0.5F);

        this.addLayer(new CrackleEyesLayer<>(this));
    }

    public ResourceLocation getTextureLocation(Crackle pEntity) {

        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/crackle/crackle.png");
    }
}
