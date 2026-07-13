package codyhuh.druids_n_dinosaurs;

import codyhuh.druids_n_dinosaurs.client.ClientEvents;
import codyhuh.druids_n_dinosaurs.common.entity.custom.*;
import codyhuh.druids_n_dinosaurs.common.entity.custom.item.ThrownGourdEgg;
import codyhuh.druids_n_dinosaurs.event.CommonEvents;
import codyhuh.druids_n_dinosaurs.event.ModEvents;
import codyhuh.druids_n_dinosaurs.registry.*;
import codyhuh.druids_n_dinosaurs.util.RustlingBrewingRecipe;
import com.google.common.collect.ImmutableMap;
import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
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
        ModEnchantments.register(bus);
        ModLootModifiers.register(bus);
        ModParticles.register(bus);

        bus.addListener(this::createAttributes);

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(new ModEvents());
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{

            registerPotPatterns();

            ModEntityPlacements.entityPlacement();

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.ALOEWOOD_SAPLING.getId(), ModBlocks.POTTED_ALOEWOOD_SAPLING);

            ComposterBlock.COMPOSTABLES.put(ModBlocks.ALOEWOOD_SAPLING.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.ALOEWOOD_LEAVES.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.GILDED_FORGET_ME_NOTS.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.BLOOMED_ALOEWOOD_LEAVES.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.BOUNCESHROOM.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.YELLOW_IRONWEED.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.BRIGHT_BLOOMS.get().asItem(), 0.3F);

            BrewingRecipeRegistry.addRecipe(new RustlingBrewingRecipe(Potions.AWKWARD,
                    ModItems.RUST.get(), ModPotions.TETANUS_POTION.get()));

            BrewingRecipeRegistry.addRecipe(new RustlingBrewingRecipe(ModPotions.TETANUS_POTION.get(),
                    Items.REDSTONE, ModPotions.TETANUS_POTION_2.get()));
        });

        DispenserBlock.registerBehavior(ModItems.GOURD_EGG.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return Util.make(new ThrownGourdEgg(p_123468_, p_123469_.x(), p_123469_.y(), p_123469_.z()), (p_123466_) -> {
                    p_123466_.setItem(p_123470_);
                });
            }
        });
    }

    private void createAttributes(EntityAttributeCreationEvent e) {
        e.put(ModEntities.GOURD_RAPTOR.get(), GourdRaptorEntity.createAttributes().build());
        e.put(ModEntities.RUSTLING.get(), Rustling.createRustlingAttributes().build());
        e.put(ModEntities.RUSTMUNCHER.get(), RustMuncherEntity.createAttributes().build());
        e.put(ModEntities.WHISP.get(), Whisp.createAttributes().build());
        e.put(ModEntities.CRACKLE.get(), Crackle.createAttributes().build());
        e.put(ModEntities.EGG_RAPTOR.get(), EggRaptor.createAttributes().build());
        e.put(ModEntities.HUE_HOG.get(), HueHog.createAttributes().build());
        e.put(ModEntities.JADE_AUTOMATON.get(), JadeAutomaton.createAttributes().build());
        e.put(ModEntities.JADE_ELEPHANT.get(), JadeElephant.createAttributes().build());
        e.put(ModEntities.TUFF_TOTEM_POLE.get(), TuffTotemPole.createAttributes().build());
        e.put(ModEntities.HART.get(), Hart.createAttributes().build());
        e.put(ModEntities.GILDED_GALLUMPHER.get(), GildedGallumpher.createAttributes().build());
        e.put(ModEntities.CHISELCHIRP.get(), Chiselchirp.createAttributes().build());
        e.put(ModEntities.TERRA_THUNK.get(), TerraThunk.createAttributes().build());
        e.put(ModEntities.SLUDGER.get(), Monster.createMonsterAttributes().build());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }

    private static void registerPotPatterns() {
        ImmutableMap.Builder<Item, ResourceKey<String>> map = ImmutableMap.builder();
        map.putAll(DecoratedPotPatterns.ITEM_TO_POT_TEXTURE);
        map.put(ModItems.RUSTLING_SHERD.get(), ModDecoratedPotPatterns.RUSTLING.getKey());

        map.put(ModItems.HART_SHERD.get(), ModDecoratedPotPatterns.HART.getKey());
        map.put(ModItems.GAZE_SHERD.get(), ModDecoratedPotPatterns.GAZE.getKey());
        map.put(ModItems.CRACKLE_SHERD.get(), ModDecoratedPotPatterns.CRACKLE.getKey());
        map.put(ModItems.BLOOM_SHERD.get(), ModDecoratedPotPatterns.BLOOM.getKey());

        map.put(ModItems.CROWN_SHERD.get(), ModDecoratedPotPatterns.CROWN.getKey());
        map.put(ModItems.RUSTMUNCHER_SHERD.get(), ModDecoratedPotPatterns.RUSTMUNCHER.getKey());
        map.put(ModItems.LUNAR_SHERD.get(), ModDecoratedPotPatterns.LUNAR.getKey());
        map.put(ModItems.STELLAR_SHERD.get(), ModDecoratedPotPatterns.STELLAR.getKey());

        map.put(ModItems.WHISP_SHERD.get(), ModDecoratedPotPatterns.WHISP.getKey());
        map.put(ModItems.TRUNK_SHERD.get(), ModDecoratedPotPatterns.TRUNK.getKey());
        map.put(ModItems.REBIRTH_SHERD.get(), ModDecoratedPotPatterns.REBIRTH.getKey());
        map.put(ModItems.WAYFIND_DOWN_SHERD.get(), ModDecoratedPotPatterns.WAYFIND_DOWN.getKey());

        map.put(ModItems.WAYFIND_UP_SHERD.get(), ModDecoratedPotPatterns.WAYFIND_UP.getKey());
        map.put(ModItems.WAYFIND_LEFT_SHERD.get(), ModDecoratedPotPatterns.WAYFIND_LEFT.getKey());
        map.put(ModItems.WAYFIND_RIGHT_SHERD.get(), ModDecoratedPotPatterns.WAYFIND_RIGHT.getKey());
        map.put(ModItems.MONKE_SHERD.get(), ModDecoratedPotPatterns.MONKE.getKey());
        DecoratedPotPatterns.ITEM_TO_POT_TEXTURE = map.build();
    }
}
