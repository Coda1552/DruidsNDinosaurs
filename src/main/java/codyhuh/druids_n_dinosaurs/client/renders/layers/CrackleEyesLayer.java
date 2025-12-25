package codyhuh.druids_n_dinosaurs.client.renders.layers;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.models.CrackleModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Crackle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrackleEyesLayer<T extends Crackle, M extends CrackleModel<T>> extends EyesLayer<T, M> {
    private static final RenderType CRACKLE_EYES = RenderType.eyes(new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/crackle/crackle_eyes.png"));

    public CrackleEyesLayer(RenderLayerParent<T, M> p_117507_) {
        super(p_117507_);
    }

    public RenderType renderType() {
        return CRACKLE_EYES;
    }
}
