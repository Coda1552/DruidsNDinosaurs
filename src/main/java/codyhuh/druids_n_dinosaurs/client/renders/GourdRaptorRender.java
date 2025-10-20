package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.GourdRaptorModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.GourdRaptorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GourdRaptorRender extends MobRenderer<GourdRaptorEntity, GourdRaptorModel<GourdRaptorEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/gourd_raptor.png");
    private static final ResourceLocation TEETH = new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/gourd_raptor_teeth.png");

    public GourdRaptorRender(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new GourdRaptorModel(p_173952_.bakeLayer(ModModelLayers.GOURD_RAPTOR_LAYER)), 0.4F);
    }

    public ResourceLocation getTextureLocation(GourdRaptorEntity p_113998_) {
        return p_113998_.getVariant() == 1 ? TEETH : LOCATION;
    }

    protected float getBob(GourdRaptorEntity p_114000_, float p_114001_) {
        float $$2 = Mth.lerp(p_114001_, p_114000_.oFlap, p_114000_.flap);
        float $$3 = Mth.lerp(p_114001_, p_114000_.oFlapSpeed, p_114000_.flapSpeed);
        return (Mth.sin($$2) + 1.0F) * $$3;
    }
}
