package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.common.blockentity.BounceshroomBlockEntity;
import codyhuh.druids_n_dinosaurs.common.blockentity.ModHangingSignBlockEntity;
import codyhuh.druids_n_dinosaurs.common.blockentity.ModSignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> MOD_SIGN =
            BLOCK_ENTITIES.register( "mod_sign", () ->
                    BlockEntityType.Builder.of(ModSignBlockEntity::new,
                                    ModBlocks.ALOEWOOD_SIGN.get(),
                                    ModBlocks.ALOEWOOD_WALL_SIGN.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> MOD_HANGING_SIGN =
            BLOCK_ENTITIES.register( "mod_hanging_sign", () ->
                    BlockEntityType.Builder.of(ModHangingSignBlockEntity::new,
                                    ModBlocks.ALOEWOOD_HANGING_SIGN.get(),
                                    ModBlocks.ALOEWOOD_WALL_HANGING_SIGN.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<BounceshroomBlockEntity>> BOUNCESHROOM =
            BLOCK_ENTITIES.register("bounceshroom", () ->
                    BlockEntityType.Builder.of(BounceshroomBlockEntity::new,
                                    ModBlocks.BOUNCESHROOM.get())
                            .build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
