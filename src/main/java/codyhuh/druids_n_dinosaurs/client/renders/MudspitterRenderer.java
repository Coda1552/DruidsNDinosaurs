package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.MudSpitterModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.MudSpitter;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MudspitterRenderer<T extends MudSpitter> extends MobRenderer<T, MudSpitterModel<T>> {

    public MudspitterRenderer(EntityRendererProvider.Context p_173954_) {
        super(p_173954_, new MudSpitterModel<>(p_173954_.bakeLayer(ModModelLayers.MUDSPITTER)), 0.5F);
    }

    public ResourceLocation getTextureLocation(T pEntity) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/mudspitter.png");
    }
}
