package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.WhispModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Whisp;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class WhispRenderer extends MobRenderer<Whisp, WhispModel<Whisp>> {

   public WhispRenderer(EntityRendererProvider.Context p_173954_) {
      super(p_173954_, new WhispModel<>(p_173954_.bakeLayer(ModModelLayers.WHISP_LAYER)), 0.15F);
   }

   public ResourceLocation getTextureLocation(Whisp pEntity) {

      Whisp.WhispVariant variant = Whisp.WhispVariant.byId(pEntity.getVariant());

      return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/whisp/" + variant.getName() + "_whisp.png");
   }

   @Override
   protected int getBlockLightLevel(Whisp pEntity, BlockPos pPos) {
      return 15;
   }
}