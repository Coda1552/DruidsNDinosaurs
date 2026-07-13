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

    public static final ModelLayerLocation JADE_AUTOMATON_LAYER = create("jade_automaton");
    public static final ModelLayerLocation JADE_AUTOMATON_LAYER_INNER = create("jade_automaton_inner_armor");
    public static final ModelLayerLocation JADE_AUTOMATON_LAYER_OUTER = create("jade_automaton_outer_armor");
    public static final ModelLayerLocation JADE_ELEPHANT = create("jade_elephant");
    public static final ModelLayerLocation TUFF_TOTEM_POLE = create("tuff_totem_pole");
    public static final ModelLayerLocation HART_LAYER = create("hart_layer");
    public static final ModelLayerLocation GILDED_GALLUMPHER = create("gilded_gallumpher_layer");
    public static final ModelLayerLocation CHISELCHIRP = create("chiselchirp_layer");
    public static final ModelLayerLocation TERRA_THUNK = create("terra_thunk_layer");
    public static final ModelLayerLocation SLUDGER = create("sludger_layer");

    private static ModelLayerLocation create(String name) {
        return new ModelLayerLocation(new ResourceLocation(DruidsNDinosaurs.MOD_ID, name), "main");
    }
}
