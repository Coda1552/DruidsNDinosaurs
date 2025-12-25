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

    public static final ModelLayerLocation WHISP_LAYER = create("whisp");
    public static final ModelLayerLocation CRACKLE_LAYER = create("crackle");

    public static final ModelLayerLocation EGG_RAPTOR_LAYER = create("egg_raptor");
    public static final ModelLayerLocation EGG_RAPTOR_EGG_LAYER = create("egg_raptor_egg");

    public static final ModelLayerLocation HUE_HOG_LAYER = create("hue_hog");
    public static final ModelLayerLocation HUE_HOG_PUFF_LAYER = create("hue_hog_puff");

    private static ModelLayerLocation create(String name) {
        return new ModelLayerLocation(new ResourceLocation(DruidsNDinosaurs.MOD_ID, name), "main");
    }
}
