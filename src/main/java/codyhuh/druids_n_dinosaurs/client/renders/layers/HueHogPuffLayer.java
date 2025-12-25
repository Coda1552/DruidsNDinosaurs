package codyhuh.druids_n_dinosaurs.client.renders.layers;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.HueHogModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.HueHog;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class HueHogPuffLayer extends RenderLayer<HueHog, HueHogModel<HueHog>> {
    private static final ResourceLocation HUE_HOG_TAIL_LOCATION = new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/hue_hog/hue_hog_tail.png");

    private final HueHogModel<HueHog> model;

    public HueHogPuffLayer(RenderLayerParent<HueHog, HueHogModel<HueHog>> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        this.model = new HueHogModel<>(pModelSet.bakeLayer(ModModelLayers.HUE_HOG_PUFF_LAYER));
    }

    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, HueHog pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!pLivingEntity.isInvisible()) {
            float f;
            float f1;
            float f2;
            if (pLivingEntity.hasCustomName() && "jeb_".equals(pLivingEntity.getName().getString())) {
                int i1 = 25;
                int i = pLivingEntity.tickCount / 25 + pLivingEntity.getId();
                int j = DyeColor.values().length;
                int k = i % j;
                int l = (i + 1) % j;
                float f3 = ((float)(pLivingEntity.tickCount % 25) + pPartialTicks) / 25.0F;
                float[] afloat1 = Sheep.getColorArray(DyeColor.byId(k));
                float[] afloat2 = Sheep.getColorArray(DyeColor.byId(l));
                f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
                f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
                f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            } else if (pLivingEntity.hasColor()){
                float[] afloat = Sheep.getColorArray(pLivingEntity.getColor());

                f = afloat[0];
                f1 = afloat[1];
                f2 = afloat[2];

                if (pLivingEntity.getColor() == DyeColor.PINK){
                    f = 1f;
                    f1 = 107/255f;
                    f2 = 216/255f;
                }
            }else {
                f = 195/255f;
                f1 = 171/255f;
                f2 = 134/255f;
            }


            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, HUE_HOG_TAIL_LOCATION, pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch, pPartialTicks, f, f1, f2);
        }
    }
}
