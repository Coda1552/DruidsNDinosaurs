package codyhuh.druids_n_dinosaurs;

import codyhuh.druids_n_dinosaurs.client.ClientEvents;
import codyhuh.druids_n_dinosaurs.client.renderers.ModBoatRenderer;
import codyhuh.druids_n_dinosaurs.common.entity.RustMuncherEntity;
import codyhuh.druids_n_dinosaurs.common.entity.Rustling;
import codyhuh.druids_n_dinosaurs.common.items.WickerIdolItem;
import codyhuh.druids_n_dinosaurs.registry.*;
import codyhuh.druids_n_dinosaurs.util.RustlingBrewingRecipe;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DruidsNDinosaurs.MOD_ID)
public class DruidsNDinosaurs {
    public static final String MOD_ID = "druids_n_dinosaurs";
    public static final Logger LOGGER = LogManager.getLogger();

    public DruidsNDinosaurs() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntities.register(bus);
        ModItems.ITEMS.register(bus);
        ModSounds.SOUNDS.register(bus);
        ModBlockEntities.register(bus);
        ModBlocks.registerBlocks(bus);
        ModConfiguredFeatures.register(bus);
        ModCreativeTab.register(bus);
        ModEffects.register(bus);
        ModPotions.register(bus);
        ModDecoratedPotPatterns.DECORATED_POT_PATTERNS.register(bus);

        bus.addListener(this::createAttributes);

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{

            registerPotPatterns();

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.ALOEWOOD_SAPLING.getId(), ModBlocks.POTTED_ALOEWOOD_SAPLING);

            ComposterBlock.COMPOSTABLES.put(ModBlocks.ALOEWOOD_SAPLING.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.ALOEWOOD_LEAVES.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.BOUNCESHROOM.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.YELLOW_IRONWEED.get().asItem(), 0.65F);

            BrewingRecipeRegistry.addRecipe(new RustlingBrewingRecipe(Potions.AWKWARD,
                    ModItems.RUST.get(), ModPotions.TETANUS_POTION.get()));

            BrewingRecipeRegistry.addRecipe(new RustlingBrewingRecipe(ModPotions.TETANUS_POTION.get(),
                    Items.REDSTONE, ModPotions.TETANUS_POTION_2.get()));
        });
    }

    private void createAttributes(EntityAttributeCreationEvent e) {
        e.put(ModEntities.RUSTLING.get(), Rustling.createRustlingAttributes().build());
        e.put(ModEntities.RUSTMUNCHER.get(), RustMuncherEntity.createAttributes().build());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }

    private static void registerPotPatterns() {
        ImmutableMap.Builder<Item, ResourceKey<String>> map = ImmutableMap.builder();
        map.putAll(DecoratedPotPatterns.ITEM_TO_POT_TEXTURE);
        map.put(ModItems.RUSTLING_SHERD.get(), ModDecoratedPotPatterns.RUSTLING.getKey());
        DecoratedPotPatterns.ITEM_TO_POT_TEXTURE = map.build();
    }
}
