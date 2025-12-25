package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    
    public static final TagKey<EntityType<?>> UNCATCHABLE = create("uncatchable");

    private static TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(DruidsNDinosaurs.MOD_ID, name));
    }
    
    public static class Blocks {
        public static final TagKey<Block> ALOEWOOD_LOG_BLOCK = tag("aloewood_log_block");

        public static final TagKey<Block> HUE_HOG_TARGETS = tag("hue_hog_targets");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(DruidsNDinosaurs.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> ALOEWOOD_LOG_ITEM = tag("aloewood_log_item");
        
        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(DruidsNDinosaurs.MOD_ID, name));
        }
    }
}
