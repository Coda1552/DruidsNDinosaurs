package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.MudlingModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Mudling;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MudlingRenderer<T extends Mudling> extends MobRenderer<T, MudlingModel<T>> {

    public MudlingRenderer(EntityRendererProvider.Context p_173954_) {
        super(p_173954_, new MudlingModel<>(p_173954_.bakeLayer(ModModelLayers.MUDLING)), 0.5F);
    }

    public ResourceLocation getTextureLocation(T pEntity) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/mudling.png");
    }
}
