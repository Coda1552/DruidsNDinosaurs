package codyhuh.druids_n_dinosaurs.event;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModEnchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DruidsNDinosaurs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public void serverTick(TickEvent.PlayerTickEvent event){
        Player player = event.player;

        Level level = player.level();

        if (level.canSeeSky(player.blockPosition()) && player.tickCount % 20 == 0 && !level.isClientSide()){

            ItemStack[] inventoryItems = new ItemStack[]{
                    player.getItemInHand(InteractionHand.MAIN_HAND),
                    player.getItemInHand(InteractionHand.OFF_HAND),
                    player.getItemBySlot(EquipmentSlot.HEAD),
                    player.getItemBySlot(EquipmentSlot.CHEST),
                    player.getItemBySlot(EquipmentSlot.LEGS),
                    player.getItemBySlot(EquipmentSlot.FEET)
            };

            long localTime = level.getDayTime() % 24000;
            int healingValue = 0;

            if (localTime>=0 && localTime<=12000){
                healingValue++;

                if ((localTime>3000 && localTime<=10000)){
                    healingValue++;

                    if (localTime>5000 && localTime<=7000){
                        healingValue++;
                        healingValue++;
                    }
                }

                for (ItemStack item : inventoryItems){
                    if (item.getEnchantmentLevel(ModEnchantments.SUNKISSED.get())>0){
                        this.repairItem(item, healingValue);
                    }
                }

            }else if (localTime>=12000){
                float moonBrightness = level.getMoonBrightness();
                if (moonBrightness == 0 && player.tickCount % 40 == 0) {
                    healingValue++;
                }else if (moonBrightness>0){
                    healingValue++;

                    if (moonBrightness>0.25f){
                        healingValue++;

                        if (moonBrightness>0.5f){
                            healingValue++;
                            if (moonBrightness>0.75f){
                                healingValue++;
                                healingValue++;
                            }
                        }
                    }
                }

                for (ItemStack item : inventoryItems){
                    if (item != ItemStack.EMPTY)
                        if (item.getEnchantmentLevel(ModEnchantments.MOONTOUCHED.get())>0){
                            this.repairItem(item, healingValue);
                        }
                }
            }
        }
    }


    public void repairItem(ItemStack item, int value){
        int prevDmgValue = item.getDamageValue();
        item.setDamageValue(prevDmgValue-value);
    }

}
