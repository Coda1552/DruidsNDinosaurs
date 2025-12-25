package codyhuh.druids_n_dinosaurs.client.renders.layers;

import codyhuh.druids_n_dinosaurs.client.models.HueHogModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.HueHog;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class HueHogItemLayer <T extends HueHog, M extends HueHogModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public HueHogItemLayer(RenderLayerParent<T, M> pRenderer, ItemInHandRenderer pItemInHandRenderer) {
        super(pRenderer);
        this.itemInHandRenderer = pItemInHandRenderer;
    }

    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        boolean flag = pLivingEntity.canChangeColors();

        ItemStack itemstack = flag ? new ItemStack(Blocks.GOLD_BLOCK) : ItemStack.EMPTY;
        if (!itemstack.isEmpty()) {
            pPoseStack.pushPose();

            if (this.getParentModel().young) {
                pPoseStack.scale(0.6f, 0.6f, 0.6f);
                pPoseStack.translate(0, 1, 0);
            }

            this.renderMouthWithItem(pLivingEntity, itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, pPoseStack, pBuffer, pPackedLight);

            pPoseStack.popPose();
        }
    }

    protected void renderMouthWithItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (!pItemStack.isEmpty()) {
            pPoseStack.pushPose();
            this.getParentModel().translateToMouth(pPoseStack);

            pPoseStack.translate(0, 0.125F, -0.625F);

            if (this.getParentModel().young){
                pPoseStack.scale(1.35F, 1.35F, 1.35F);
            }

            this.itemInHandRenderer.renderItem(pLivingEntity, pItemStack, pDisplayContext, false, pPoseStack, pBuffer, pPackedLight);

            pPoseStack.popPose();
        }
    }
}
