package codyhuh.druids_n_dinosaurs.event;

import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonEvents {

    @SubscribeEvent
    public void hurtEntity(LivingHurtEvent event){
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof Mob && event.getSource() != null && event.getSource().getDirectEntity() instanceof LivingEntity directSource){
            ItemStack stack = directSource.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(ModItems.FOOLS_SCEPTER.get()) && stack.getHoverName().getString().equals("Wackus Bonkus")){
                event.setAmount(1000);
            }
        }
    }
}
