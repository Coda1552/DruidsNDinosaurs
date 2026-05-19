package codyhuh.druids_n_dinosaurs.client.renders.layers;

import codyhuh.druids_n_dinosaurs.client.models.GildedGallumpherModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.GildedGallumpher;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class GildedGallumpherItemsLayer<T extends GildedGallumpher, M extends GildedGallumpherModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public GildedGallumpherItemsLayer(RenderLayerParent<T, M> pRenderer, ItemInHandRenderer pItemInHandRenderer) {
        super(pRenderer);
        this.itemInHandRenderer = pItemInHandRenderer;
    }

    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        ItemStack itemstack = pLivingEntity.getFrontSherd();
        ItemStack itemstack2 = pLivingEntity.getRightSherd();
        ItemStack itemstack3 = pLivingEntity.getBackSherd();
        ItemStack itemstack4 = pLivingEntity.getLeftSherd();


        pPoseStack.pushPose();
        if (!itemstack.isEmpty()) {
            this.renderSherd(pLivingEntity, itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, pPoseStack, pBuffer, pPackedLight, 1);
        }
        if (!itemstack2.isEmpty()) {
            this.renderSherd(pLivingEntity, itemstack2, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, pPoseStack, pBuffer, pPackedLight, 2);
        }
        if (!itemstack3.isEmpty()) {
            this.renderSherd(pLivingEntity, itemstack3, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, pPoseStack, pBuffer, pPackedLight, 3);
        }
        if (!itemstack4.isEmpty()) {
            this.renderSherd(pLivingEntity, itemstack4, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, pPoseStack, pBuffer, pPackedLight, 4);
        }
        pPoseStack.popPose();
    }

    protected void renderSherd(T pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext,
                               PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int slot) {
        if (!pItemStack.isEmpty()) {
            pPoseStack.pushPose();

            this.getParentModel().translateToBody(pPoseStack, slot);

            this.itemInHandRenderer.renderItem(pLivingEntity, pItemStack, pDisplayContext, false, pPoseStack, pBuffer, pPackedLight);

            pPoseStack.popPose();
        }
    }
}
