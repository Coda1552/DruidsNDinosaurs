package codyhuh.druids_n_dinosaurs.client.renders.layers;

import codyhuh.druids_n_dinosaurs.client.models.JadeElephantModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.JadeElephant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

public class JadeElephantItemLayer<T extends JadeElephant, M extends JadeElephantModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public JadeElephantItemLayer(RenderLayerParent<T, M> pRenderer, ItemInHandRenderer pItemInHandRenderer) {
        super(pRenderer);
        this.itemInHandRenderer = pItemInHandRenderer;
    }

    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        ItemStack itemstack = pLivingEntity.getItemInHand(InteractionHand.MAIN_HAND);

        if (!itemstack.isEmpty()) {
            pPoseStack.pushPose();

            this.renderTrunkWithItem(pLivingEntity, itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, pPoseStack, pBuffer, pPackedLight);

            pPoseStack.popPose();
        }
    }

    protected void renderTrunkWithItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (!pItemStack.isEmpty()) {
            pPoseStack.pushPose();

            this.getParentModel().translateToTrunk(pPoseStack);

            this.itemInHandRenderer.renderItem(pLivingEntity, pItemStack, pDisplayContext, false, pPoseStack, pBuffer, pPackedLight);

            pPoseStack.popPose();
        }
    }
}
