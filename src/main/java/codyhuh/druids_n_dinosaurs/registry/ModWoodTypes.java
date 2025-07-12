package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final WoodType ALOEWOOD = WoodType.register(new WoodType(DruidsNDinosaurs.MOD_ID + ":aloewood", BlockSetType.OAK));
}
