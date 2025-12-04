package codyhuh.druids_n_dinosaurs.common.enchantment;

import codyhuh.druids_n_dinosaurs.registry.ModEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EquipmentEnchantment extends Enchantment {

    private String registryName;
    private int levels;
    private boolean isTreasureOnly;

    public EquipmentEnchantment(String name, Enchantment.Rarity pRarity, EnchantmentCategory pCategory, int levels, boolean isTreasureOnly, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        this.registryName = name;
        this.levels = levels;
        this.isTreasureOnly = isTreasureOnly;
    }

    protected boolean checkCompatibility(Enchantment enchantment) {
        return this != enchantment && ModEnchantments.areCompatible(this, enchantment);
    }

    public int getMaxLevel() {
        return levels;
    }

    public String getName(){
        return registryName;
    }

    public int getMinCost(int p_45102_) {
        return p_45102_ * 25;
    }

    public int getMaxCost(int p_45105_) {
        return this.getMinCost(p_45105_) + 50;
    }

    @Override
    public boolean isTreasureOnly() {
        return isTreasureOnly;
    }
}