package codyhuh.druids_n_dinosaurs.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties SOUL_BOTTLE = (new FoodProperties.Builder()).nutrition(0).saturationMod(0F)
            .effect(new MobEffectInstance(ModEffects.ETHEREAL.get(), 20*3*60, 0), 1.0F).alwaysEat().build();

}
