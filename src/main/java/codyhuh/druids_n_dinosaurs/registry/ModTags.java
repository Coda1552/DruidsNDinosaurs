package codyhuh.druids_n_dinosaurs.registry;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {

    private static TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(DruidsNDinosaurs.MOD_ID, name));
    }
    
    public static class Blocks {
        public static final TagKey<Block> ALOEWOOD_LOG_BLOCK = tag("aloewood_log_block");

        public static final TagKey<Block> HUE_HOG_TARGETS = tag("hue_hog_targets");

        public static final TagKey<Block> BEACON_BLOOM_TOTEMS = tag("beacon_bloom_totems");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(DruidsNDinosaurs.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> ALOEWOOD_LOG_ITEM = tag("aloewood_log_item");
        public static final TagKey<Item> FLOWER_CROWNS = tag("flower_crowns");
        
        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(DruidsNDinosaurs.MOD_ID, name));
        }
    }

    public static class Biomes {

        public static final TagKey<Biome> JADE_LOW_CONCENTRATION_BIOMES = tag("jade_low_concentration_biomes");
        public static final TagKey<Biome> JADE_HIGH_CONCENTRATION_BIOMES = tag("jade_high_concentration_biomes");
        public static final TagKey<Biome> JADE_HIGH_ALTITUDE_BIOMES = tag("jade_high_altitude_biomes");
        public static final TagKey<Biome> HART_BIOMES = tag("hart_biomes");

        private static TagKey<Biome> tag(String pName) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(DruidsNDinosaurs.MOD_ID, pName));
        }
    }



    public static class EntityTypes {
        public static final TagKey<EntityType<?>> WICKER_IDOL_BLACKLIST = tag("wicker_idol_blacklist");
        public static final TagKey<EntityType<?>> BLOOM_ENTITIES = tag("bloom_entities");
        public static final TagKey<EntityType<?>> OOZE_TRAIL_NO_STUCK = tag("ooze_trail_no_stuck");

        private static TagKey<net.minecraft.world.entity.EntityType<?>> tag(String name){
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(DruidsNDinosaurs.MOD_ID, name));
        }
    }
}
