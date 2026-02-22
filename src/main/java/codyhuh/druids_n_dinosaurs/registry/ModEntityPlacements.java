package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.common.entity.custom.Hart;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntityPlacements {

    public static void entityPlacement() {
        SpawnPlacements.register(ModEntities.HART.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Hart::checkAnimalSpawnRules);
    }
}
