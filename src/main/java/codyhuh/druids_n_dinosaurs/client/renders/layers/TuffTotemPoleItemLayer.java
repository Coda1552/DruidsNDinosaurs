package codyhuh.druids_n_dinosaurs.client.renders.layers;

import codyhuh.druids_n_dinosaurs.client.models.TotemPoleModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.TuffTotemPole;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class TuffTotemPoleItemLayer<T extends TuffTotemPole, M extends TotemPoleModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public TuffTotemPoleItemLayer(RenderLayerParent<T, M> pRenderer, ItemInHandRenderer pItemInHandRenderer) {
        super(pRenderer);
        this.itemInHandRenderer = pItemInHandRenderer;
    }

    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        ItemStack itemstack = pLivingEntity.getInventory().getItem(0);
        if (!itemstack.isEmpty() && pLivingEntity.getEggPhase()>0) {
            pPoseStack.pushPose();

            this.renderItem(pLivingEntity, itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, pPoseStack, pBuffer, pPackedLight);

            pPoseStack.popPose();
        }
    }

    protected void renderItem(T pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (!pItemStack.isEmpty()) {
            pPoseStack.pushPose();

            switch (pLivingEntity.getEggPhase()){
                case 2:
                    this.getParentModel().translateToHart(pPoseStack, pLivingEntity.tickCount);
                    break;
                case 3:
                    this.getParentModel().translateToNest(pPoseStack, pLivingEntity.tickCount);
                    break;
                default:
                    this.getParentModel().translateToTrunk(pPoseStack);
            }

            this.itemInHandRenderer.renderItem(pLivingEntity, pItemStack, pDisplayContext, false, pPoseStack, pBuffer, pPackedLight);

            pPoseStack.popPose();
        }
    }
}
