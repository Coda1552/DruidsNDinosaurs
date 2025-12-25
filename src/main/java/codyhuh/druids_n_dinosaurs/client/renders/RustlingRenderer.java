package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.RustlingModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Rustling;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RustlingRenderer extends MobRenderer<Rustling, RustlingModel<Rustling>> {

   public RustlingRenderer(EntityRendererProvider.Context p_173954_) {
      super(p_173954_, new RustlingModel<>(p_173954_.bakeLayer(ModModelLayers.RUSTLING)), 0.5F);
   }

   public ResourceLocation getTextureLocation(Rustling pEntity) {
      return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/rustling/rustling_" + pEntity.getRustLevel() + ".png");
   }
}