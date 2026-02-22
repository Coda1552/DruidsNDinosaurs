package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.JadeElephantModel;
import codyhuh.druids_n_dinosaurs.client.models.TotemPoleModel;
import codyhuh.druids_n_dinosaurs.client.renders.layers.JadeElephantItemLayer;
import codyhuh.druids_n_dinosaurs.client.renders.layers.TuffTotemPoleItemLayer;
import codyhuh.druids_n_dinosaurs.common.entity.custom.JadeElephant;
import codyhuh.druids_n_dinosaurs.common.entity.custom.TuffTotemPole;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TuffTotemPoleRenderer extends MobRenderer<TuffTotemPole, TotemPoleModel<TuffTotemPole>> {

    public TuffTotemPoleRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TotemPoleModel<>(pContext.bakeLayer(ModModelLayers.TUFF_TOTEM_POLE)), 0.6f);
        this.addLayer(new TuffTotemPoleItemLayer<>(this, pContext.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(TuffTotemPole pEntity) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/tuff_totem_pole.png");
    }
}
