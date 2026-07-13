package codyhuh.druids_n_dinosaurs.client;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.client.particles.CustomItemParticles;
import codyhuh.druids_n_dinosaurs.client.particles.JadeOmenParticle;
import codyhuh.druids_n_dinosaurs.client.particles.SeedlingParticle;
import codyhuh.druids_n_dinosaurs.client.particles.SludgedParticle;
import codyhuh.druids_n_dinosaurs.client.renders.ModBoatRenderer;
import codyhuh.druids_n_dinosaurs.common.items.WickerIdolItem;
import codyhuh.druids_n_dinosaurs.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = DruidsNDinosaurs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    private static final UUID SPEED_UUID = UUID.fromString("dcd9ce31-977d-4e43-9be4-98c04ef49db8");
    private static final UUID SLOW_UUID = UUID.fromString("4eadfa87-767f-4bd8-bdba-f443cc94e14b");

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e) {
        ItemProperties.register(ModItems.WICKER_IDOL.get(), new ResourceLocation(DruidsNDinosaurs.MOD_ID, "full"), (stack, world, player, i) -> !WickerIdolItem.containsEntity(stack) ? 0.0F : 1.0F);

        EntityRenderers.register(ModEntities.MOD_BOAT.get(), p_174010_ -> new ModBoatRenderer(p_174010_, false));
        EntityRenderers.register(ModEntities.MOD_CHEST_BOAT.get(), p_174010_ -> new ModBoatRenderer(p_174010_, true));
    }

    @SubscribeEvent
    public void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {

        Entity player = Minecraft.getInstance().getCameraEntity();
        float partialTick = Minecraft.getInstance().getPartialTick();

        if (player != null && player instanceof LivingEntity livingEntity && livingEntity.hasEffect(ModEffects.TETANUS.get())) {

            AttributeInstance attributeinstance = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);

            if (attributeinstance != null) {
                if (attributeinstance.getModifier(SPEED_UUID) != null) {
                    //speed
                    event.setPitch(event.getPitch() + (float) (Math.sin((player.tickCount + partialTick) * 0.2F) * 10F));
                }
                if (attributeinstance.getModifier(SLOW_UUID) != null) {
                    //slowness
                    event.setYaw(event.getYaw() + (float) (Math.sin((player.tickCount + partialTick) * 0.2F) * 10F));
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.JADE_OMEN.get(), JadeOmenParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SEEDLING.get(), SeedlingParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SLUDGER.get(), CustomItemParticles.SludgeProvider::new);
        event.registerSpriteSet(ModParticles.SLUDGED.get(), SludgedParticle.SludgedProvider::new);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){
        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) ->{
                if (pTintIndex != 0) {
                    return pLevel != null && pPos != null ? BiomeColors.getAverageGrassColor(pLevel, pPos) : GrassColor.getDefaultColor();
                } else {
                    return -1;
                }
            }, ModBlocks.BRIGHT_BLOOMS.get());
    }
}
