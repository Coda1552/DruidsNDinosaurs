package codyhuh.druids_n_dinosaurs.mixin.client;

import codyhuh.druids_n_dinosaurs.registry.ModEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> {

    protected LivingEntityRendererMixin(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Redirect(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"
            )
    )
    private void redirectRender(EntityModel instance, PoseStack poseStack, VertexConsumer vertexConsumer, int pPackedLight, int pPackedOverlay,
                                float r, float g, float b, float a, T pEntity) {
        if (pEntity.hasEffect(ModEffects.ETHEREAL.get())) {
            instance.renderToBuffer(poseStack, vertexConsumer, pPackedLight, pPackedOverlay, r, g, b, 0.15f);
        } else {
            instance.renderToBuffer(poseStack, vertexConsumer, pPackedLight, pPackedOverlay, r, g, b, a);
        }
    }
}
