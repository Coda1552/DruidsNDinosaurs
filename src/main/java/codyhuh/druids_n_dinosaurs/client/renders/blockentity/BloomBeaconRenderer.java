package codyhuh.druids_n_dinosaurs.client.renders.blockentity;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.common.blocks.blockentity.BloomBeaconBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BloomBeaconRenderer<T extends BloomBeaconBlockEntity> implements BlockEntityRenderer<T> {
    public static final ResourceLocation BEAM_LOCATION = new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/beacon_bloom_beam.png");

    public BloomBeaconRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        long i = pBlockEntity.getLevel().getGameTime();
        List<BloomBeaconBlockEntity.BloomBeaconBeamSection> list = pBlockEntity.getBeamSections();
        int j = 0;

        for(int k = 0; k < list.size(); ++k) {
            BloomBeaconBlockEntity.BloomBeaconBeamSection beaconblockentity$beaconbeamsection = list.get(k);
            renderBeaconBeam(pPoseStack, pBuffer, pPartialTick, i, j, k == list.size() - 1 ? 1024 : beaconblockentity$beaconbeamsection.getHeight(), beaconblockentity$beaconbeamsection.getColor());
            j += beaconblockentity$beaconbeamsection.getHeight();
        }
    }

    private static void renderBeaconBeam(PoseStack pPoseStack, MultiBufferSource pBufferSource, float pPartialTick, long pGameTime, int pYOffset, int pHeight, float[] pColors) {
        BeaconRenderer.renderBeaconBeam(pPoseStack, pBufferSource, BEAM_LOCATION, pPartialTick, 1.0F, pGameTime, pYOffset, pHeight, pColors, 0.2F, 0.25F);
    }

    public boolean shouldRenderOffScreen(T pBlockEntity) {
        return true;
    }

    public int getViewDistance() {
        return 256;
    }

    public boolean shouldRender(T pBlockEntity, Vec3 pCameraPos) {
        return Vec3.atCenterOf(pBlockEntity.getBlockPos()).multiply(1.0D, 0.0D, 1.0D)
                .closerThan(pCameraPos.multiply(1.0D, 0.0D, 1.0D), (double)this.getViewDistance());
    }

}