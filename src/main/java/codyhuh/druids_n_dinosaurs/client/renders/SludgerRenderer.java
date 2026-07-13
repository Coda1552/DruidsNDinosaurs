package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.SludgerModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Sludger;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SludgerRenderer extends MobRenderer<Sludger, SludgerModel<Sludger>> {
    private static final ResourceLocation SLIME_LOCATION = new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/sludger.png");

    public SludgerRenderer(EntityRendererProvider.Context p_174391_) {
        super(p_174391_, new SludgerModel<>(p_174391_.bakeLayer(ModModelLayers.SLUDGER)), 0.25F);
    }

    public void render(Sludger pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.shadowRadius = 0.25F * (float)pEntity.getSize();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    protected void scale(Sludger pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        float f = 0.5F;
        pPoseStack.scale(f, f, f);
        pPoseStack.translate(0.0F, 0.001F, 0.0F);
        float f1 = (float)pLivingEntity.getSize();
        float f2 = Mth.lerp(pPartialTickTime, pLivingEntity.oSquish, pLivingEntity.squish) / (f1 * 0.25F + 1.0F);
        float f3 = 1.0F / (f2*2 + 1.0F);
        pPoseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    public ResourceLocation getTextureLocation(Sludger pEntity) {
        return SLIME_LOCATION;
    }
}
