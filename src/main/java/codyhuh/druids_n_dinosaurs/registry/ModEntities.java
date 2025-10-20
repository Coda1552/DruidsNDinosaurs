package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.common.entity.custom.GourdRaptorEntity;
import codyhuh.druids_n_dinosaurs.common.entity.RustMuncherEntity;
import codyhuh.druids_n_dinosaurs.common.entity.Rustling;
import codyhuh.druids_n_dinosaurs.common.entity.custom.ModBoatEntity;
import codyhuh.druids_n_dinosaurs.common.entity.custom.ModChestBoatEntity;
import codyhuh.druids_n_dinosaurs.common.entity.custom.item.ThrownGourdEgg;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DruidsNDinosaurs.MOD_ID);


    public static final RegistryObject<EntityType<GourdRaptorEntity>> GOURD_RAPTOR =
            ENTITY_TYPES.register("gourd_raptor", () -> EntityType.Builder.of(GourdRaptorEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 0.8f).build("gourd_raptor"));

    public static final RegistryObject<EntityType<ThrownGourdEgg>> GOURD_EGG =
            ENTITY_TYPES.register("gourd_egg", () -> EntityType.Builder.<ThrownGourdEgg>of(ThrownGourdEgg::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build("gourd_raptor"));


    public static final RegistryObject<EntityType<ModBoatEntity>> MOD_BOAT =
            ENTITY_TYPES.register("mod_boat", () -> EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("mod_boat"));
    public static final RegistryObject<EntityType<ModChestBoatEntity>> MOD_CHEST_BOAT =
            ENTITY_TYPES.register("mod_chest_boat", () -> EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("mod_chest_boat"));


    public static final RegistryObject<EntityType<Rustling>> RUSTLING = ENTITY_TYPES.register("rustling",
            () -> EntityType.Builder.of(Rustling::new, MobCategory.CREATURE).sized(0.625F, 1.2F).build("rustling"));

    public static final RegistryObject<EntityType<RustMuncherEntity>> RUSTMUNCHER = ENTITY_TYPES.register("rustmuncher",
            () -> EntityType.Builder.of(RustMuncherEntity::new, MobCategory.CREATURE).sized(0.8F, 1.4F).fireImmune()
                    .build("rustmuncher"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
