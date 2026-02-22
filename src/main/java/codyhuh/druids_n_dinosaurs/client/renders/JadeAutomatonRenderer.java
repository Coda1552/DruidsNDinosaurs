package codyhuh.druids_n_dinosaurs.client.renders;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.ModModelLayers;
import codyhuh.druids_n_dinosaurs.client.models.JadeAutomatonModel;
import codyhuh.druids_n_dinosaurs.common.entity.custom.JadeAutomaton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class JadeAutomatonRenderer<T extends JadeAutomaton> extends HumanoidMobRenderer<T, JadeAutomatonModel<T>> {

    public JadeAutomatonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new JadeAutomatonModel<>(pContext.bakeLayer(ModModelLayers.JADE_AUTOMATON_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/entity/jade_automaton/jade_automaton"
                +(pEntity.isLit() || pEntity.isEidolon() ? "_lit" : "_unlit")+".png");
    }
}
