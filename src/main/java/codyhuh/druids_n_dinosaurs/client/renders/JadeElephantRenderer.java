package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.JadeElephantModel;
import codyhuh.druids_n_dinosaurs.client.renders.layers.JadeElephantItemLayer;
import codyhuh.druids_n_dinosaurs.common.entity.custom.JadeElephant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class JadeElephantRenderer extends MobRenderer<JadeElephant, JadeElephantModel<JadeElephant>> {

    public JadeElephantRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new JadeElephantModel<>(pContext.bakeLayer(ModModelLayers.JADE_ELEPHANT)), 0.6f);
        this.addLayer(new JadeElephantItemLayer<>(this, pContext.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(JadeElephant pEntity) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/jade_elephant.png");
    }
}
