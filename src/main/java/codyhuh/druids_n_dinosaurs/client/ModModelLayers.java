package codyhuh.druids_n_dinosaurs.client;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {

    public static final ModelLayerLocation GOURD_RAPTOR_LAYER = new ModelLayerLocation(
            new ResourceLocation(DruidsNDinosaurs.MOD_ID, "gourd_raptor"), "main");

    public static final ModelLayerLocation ALOEWOOD_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(DruidsNDinosaurs.MOD_ID, "boat/aloewood"), "main");

    public static final ModelLayerLocation ALOEWOOD_CHEST_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(DruidsNDinosaurs.MOD_ID, "chest_boat/aloewood"), "main");

    public static final ModelLayerLocation RUSTLING = create("rustling");
    public static final ModelLayerLocation RUSTMUNCHER = create("rustmuncher");

    private static ModelLayerLocation create(String name) {
        return new ModelLayerLocation(new ResourceLocation(DruidsNDinosaurs.MOD_ID, name), "main");
    }
}
