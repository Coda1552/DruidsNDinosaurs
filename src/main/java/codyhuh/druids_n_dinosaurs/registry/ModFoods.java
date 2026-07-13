package codyhuh.druids_n_dinosaurs.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties BOTTLE_O_SOUL = (new FoodProperties.Builder()).nutrition(0).saturationMod(0F)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 20*60, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20*60, 0), 1.0F).alwaysEat().build();

    public static final FoodProperties BOTTLE_O_ETHEREAL = (new FoodProperties.Builder()).nutrition(0).saturationMod(0F)
            .effect(new MobEffectInstance(ModEffects.ETHEREAL.get(), 20*3*60, 0), 1.0F).alwaysEat().build();

}
