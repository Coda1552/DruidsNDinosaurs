package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.RustMuncherModel;
import codyhuh.druids_n_dinosaurs.common.entity.RustMuncherEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RustMuncherRenderer extends MobRenderer<RustMuncherEntity, RustMuncherModel<RustMuncherEntity>> {

    public RustMuncherRenderer(EntityRendererProvider.Context p_173954_) {
        super(p_173954_, new RustMuncherModel<>(p_173954_.bakeLayer(ModModelLayers.RUSTMUNCHER)), 0.5F);
    }

    public ResourceLocation getTextureLocation(RustMuncherEntity pEntity) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/rustmuncher/rustmuncher_" + pEntity.getVariantName() + ".png");
    }
}
