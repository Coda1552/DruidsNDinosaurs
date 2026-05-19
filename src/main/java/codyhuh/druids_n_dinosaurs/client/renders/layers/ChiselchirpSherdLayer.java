package codyhuh.druids_n_dinosaurs.client.renders.layers;

import codyhuh.druids_n_dinosaurs.client.models.ChiselchirpModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Chiselchirp;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ChiselchirpSherdLayer<T extends Chiselchirp, M extends ChiselchirpModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public ChiselchirpSherdLayer(RenderLayerParent<T, M> pRenderer, ItemInHandRenderer pItemInHandRenderer) {
        super(pRenderer);
        this.itemInHandRenderer = pItemInHandRenderer;
    }

    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        ItemStack itemstack = pLivingEntity.getHeldSherd();

        if (!itemstack.isEmpty()) {
            pPoseStack.pushPose();

            this.renderSherd(pLivingEntity, itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, pPoseStack, pBuffer, pPackedLight);

            pPoseStack.popPose();
        }
    }

    protected void renderSherd(T pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (!pItemStack.isEmpty()) {
            pPoseStack.pushPose();

            this.getParentModel().translateToClaws(pPoseStack);

            this.itemInHandRenderer.renderItem(pLivingEntity, pItemStack, pDisplayContext, false, pPoseStack, pBuffer, pPackedLight);

            pPoseStack.popPose();
        }
    }
}
