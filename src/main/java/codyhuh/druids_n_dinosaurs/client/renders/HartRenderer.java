package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.HartModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Hart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HartRenderer extends MobRenderer<Hart, HartModel<Hart>> {

    public HartRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new HartModel<>(pContext.bakeLayer(ModModelLayers.HART_LAYER)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(Hart pEntity) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/hart.png");
    }
}
