package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import com.mojang.blaze3d.shaders.Effect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<Potion> TETANUS_POTION = POTIONS.register("tetanus_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.TETANUS.get(), 1800, 0)));

    public static final RegistryObject<Potion> TETANUS_POTION_2 = POTIONS.register("tetanus_potion_2",
            () -> new Potion(new MobEffectInstance(ModEffects.TETANUS.get(), 2400, 0)));

    public static final RegistryObject<Potion> WHISP_REGEN = POTIONS.register("whisp_regen_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.REGENERATION, 12000, 0)));
    public static final RegistryObject<Potion> WHISP_FIRE_RES = POTIONS.register("whisp_fire_res_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000, 0)));
    public static final RegistryObject<Potion> WHISP_STRENGTH = POTIONS.register("whisp_strength_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 12000, 0)));
    public static final RegistryObject<Potion> WHISP_JUMP = POTIONS.register("whisp_jump_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.JUMP, 12000, 0)));
    public static final RegistryObject<Potion> WHISP_SPEED = POTIONS.register("whisp_speed_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000, 0)));
    public static final RegistryObject<Potion> WHISP_WITHER = POTIONS.register("whisp_wither_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.WITHER, 400, 0)));


    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
