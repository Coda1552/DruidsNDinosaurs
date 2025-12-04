package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.common.enchantment.EquipmentEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =  DeferredRegister.create(ForgeRegistries.ENCHANTMENTS,
            DruidsNDinosaurs.MOD_ID);

    public static final RegistryObject<Enchantment> SUNKISSED = ENCHANTMENTS.register("sunkissed",
            ()-> new EquipmentEnchantment("sunkissed",Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE,
                    1, false, EquipmentSlot.values()));

    public static final RegistryObject<Enchantment> MOONTOUCHED = ENCHANTMENTS.register("moontouched",
            ()-> new EquipmentEnchantment("moontouched",Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE,
                    1, false, EquipmentSlot.values()));

    public static boolean areCompatible(EquipmentEnchantment enchantment1, Enchantment enchantment2) {
        if((enchantment1 == SUNKISSED.get() && enchantment2 == Enchantments.MENDING) ||
                (enchantment1 == SUNKISSED.get() && enchantment2 == MOONTOUCHED.get()) ||
                (enchantment1 == MOONTOUCHED.get() && enchantment2 == Enchantments.MENDING))
            return false;
        return true;
    }

    public static void register (IEventBus eventBus){
        ENCHANTMENTS.register(eventBus);
    }
}