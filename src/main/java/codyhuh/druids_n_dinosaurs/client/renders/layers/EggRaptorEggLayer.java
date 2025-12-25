package codyhuh.druids_n_dinosaurs.client.renders.layers;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.EggRaptorModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.EggRaptor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class EggRaptorEggLayer extends RenderLayer<EggRaptor, EggRaptorModel<EggRaptor>> {
    private final EggRaptorModel<EggRaptor> model;

    private static final ResourceLocation TEXTURE = new ResourceLocation(DruidsNDinosaurs.MOD_ID,
            "textures/entity/egg_raptor/egg_raptor_egg.png");

    public EggRaptorEggLayer(RenderLayerParent<EggRaptor, EggRaptorModel<EggRaptor>> pRenderer, EntityRendererProvider.Context pContext) {
        super(pRenderer);
        this.model = new EggRaptorModel<>(pContext.bakeLayer(ModModelLayers.EGG_RAPTOR_EGG_LAYER));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, EggRaptor pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

        if (pLivingEntity.hasEggs()) {

            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
            this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);

            VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
            this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0F);

        }
    }
}
