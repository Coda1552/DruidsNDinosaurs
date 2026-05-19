package codyhuh.druids_n_dinosaurs.common.blocks.blockentity;

import codyhuh.druids_n_dinosaurs.registry.ModBlockEntities;
import codyhuh.druids_n_dinosaurs.registry.ModEffects;
import codyhuh.druids_n_dinosaurs.registry.ModParticles;
import codyhuh.druids_n_dinosaurs.registry.ModTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;

import java.util.Arrays;
import java.util.List;

public class BloomBeaconBlockEntity extends BlockEntity {

    int levels;
    public int lastCheckY;
    List<BloomBeaconBeamSection> beamSections = Lists.newArrayList();
    private List<BloomBeaconBeamSection> checkingBeamSections = Lists.newArrayList();

    public BloomBeaconBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BLOOM_BEACON.get(), pPos, pBlockState);
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("Levels", this.levels);
    }

    public int getLevels(){
        return this.levels;
    }

    public void setRemoved() {
        playSound(this.level, this.worldPosition, SoundEvents.BEACON_DEACTIVATE);
        super.setRemoved();
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, BloomBeaconBlockEntity pBlockEntity) {
        int i = pPos.getX();
        int j = pPos.getY();
        int k = pPos.getZ();

        if (isValidArrangement(pLevel, i, j, k)){
            BlockPos blockpos;
            if (pBlockEntity.lastCheckY < j) {
                blockpos = pPos;
                pBlockEntity.checkingBeamSections = Lists.newArrayList();
                pBlockEntity.lastCheckY = pPos.getY() - 1;
            } else {
                blockpos = new BlockPos(i, pBlockEntity.lastCheckY + 1, k);
            }

            BloomBeaconBeamSection beaconblockentity$beaconbeamsection = pBlockEntity.checkingBeamSections.isEmpty() ? null : pBlockEntity.checkingBeamSections.get(pBlockEntity.checkingBeamSections.size() - 1);
            int l = pLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, k);

            for(int i1 = 0; i1 < 10 && blockpos.getY() <= l; ++i1) {
                BlockState blockstate = pLevel.getBlockState(blockpos);

                float[] afloat = blockstate.getBeaconColorMultiplier(pLevel, blockpos, pPos);
                if (afloat != null) {
                    if (pBlockEntity.checkingBeamSections.size() <= 1) {
                        beaconblockentity$beaconbeamsection = new BloomBeaconBeamSection(afloat);
                        pBlockEntity.checkingBeamSections.add(beaconblockentity$beaconbeamsection);
                    } else if (beaconblockentity$beaconbeamsection != null) {
                        if (Arrays.equals(afloat, beaconblockentity$beaconbeamsection.color)) {
                            beaconblockentity$beaconbeamsection.increaseHeight();
                        } else {
                            beaconblockentity$beaconbeamsection = new BloomBeaconBeamSection(new float[]{(beaconblockentity$beaconbeamsection.color[0] + afloat[0]) / 2.0F, (beaconblockentity$beaconbeamsection.color[1] + afloat[1]) / 2.0F, (beaconblockentity$beaconbeamsection.color[2] + afloat[2]) / 2.0F});
                            pBlockEntity.checkingBeamSections.add(beaconblockentity$beaconbeamsection);
                        }
                    }
                } else {
                    if (beaconblockentity$beaconbeamsection == null || blockstate.getLightBlock(pLevel, blockpos) >= 15 && !blockstate.is(Blocks.BEDROCK)) {
                        pBlockEntity.checkingBeamSections.clear();
                        pBlockEntity.lastCheckY = l;
                        break;
                    }

                    beaconblockentity$beaconbeamsection.increaseHeight();
                }

                blockpos = blockpos.above();
                ++pBlockEntity.lastCheckY;
            }

            int j1 = pBlockEntity.levels;
            if (pLevel.getGameTime() % 80L == 0L) {

                if (!pBlockEntity.beamSections.isEmpty()) {
                    pBlockEntity.levels = updateLevel(pLevel, i, j, k);
                }

                if (pBlockEntity.levels > -1 && !pBlockEntity.beamSections.isEmpty()) {
                    applyEffects(pLevel, pPos, pBlockEntity.levels);
                    playSound(pLevel, pPos, SoundEvents.BEACON_AMBIENT);
                }
            }

            if (pBlockEntity.lastCheckY >= l) {
                pBlockEntity.lastCheckY = pLevel.getMinBuildHeight() - 1;
                boolean flag = j1 > -1;
                pBlockEntity.beamSections = pBlockEntity.checkingBeamSections;
                if (!pLevel.isClientSide) {
                    boolean flag1 = pBlockEntity.levels > -1;
                    if (!flag && flag1) {
                        playSound(pLevel, pPos, SoundEvents.BEACON_ACTIVATE);
                    } else if (flag && !flag1) {
                        playSound(pLevel, pPos, SoundEvents.BEACON_DEACTIVATE);
                    }
                }
            }
        }else {
            if (pBlockEntity.levels>-1){
                pBlockEntity.levels = -1;

                playSound(pLevel, pPos, SoundEvents.BEACON_DEACTIVATE);
            }
        }
    }

    public static boolean isValidArrangement(Level pLevel, int pX, int pY, int pZ){
        //zxRadius = 6, not including center
        //total height = 5, including center

        int startX = pX - 6;
        int endX = pX + 6;
        int startZ = pZ - 6;
        int endZ = pZ + 6;
        int startY = pY + 3;
        int endY = pY - 1;

        for (int y = startY; y >= endY; y--){
            for (int x = startX; x <= endX; x++){
                for (int z = startZ; z <= endZ; z++) {

                    if (!(z == pZ && x == pX && y == pY)){
                        boolean flagX = x == startX || x == endX;
                        boolean flagZ = z == startZ || z == endZ;

                        if (x == pX && z == pZ && y > pY){
                            if (!(pLevel.getBlockState(new BlockPos(x, y, z)).is(Blocks.AIR))){
                                if (pLevel.getBlockState(new BlockPos(x, y, z)).canOcclude()){
                                    return false;
                                }
                            }

                        //very top layer
                        }else if (y == startY){
                            boolean flagArchXSide = x >= pX - 2 && x <= pX + 2;
                            boolean flagArchZSide = z >= pZ - 2 && z <= pZ + 2;

                            //checking for the totems
                            if ((z == pZ && flagX) || (x == pX && flagZ)){
                                if (!pLevel.getBlockState(new BlockPos(x, y, z)).is(ModTags.Blocks.BEACON_BLOOM_TOTEMS)){
                                    return false;
                                }
                                //checking for the rest of the arches
                            }else if ((flagArchZSide && flagX) || (flagArchXSide && flagZ)){
                                if (!(pLevel.getBlockState(new BlockPos(x, y, z)).is(Tags.Blocks.COBBLESTONE) ||
                                        pLevel.getBlockState(new BlockPos(x, y, z)).is(Tags.Blocks.STONE) ||
                                        pLevel.getBlockState(new BlockPos(x, y, z)).is(Blocks.MOSS_BLOCK))){
                                    return false;
                                }
                                //checking for air blocks
                            }else {
                                if (!pLevel.getBlockState(new BlockPos(x, y, z)).is(Blocks.AIR)){
                                    return false;
                                }
                            }
                        }else {
                            boolean flagArchXSide = x == pX - 2 || x == pX + 2;
                            boolean flagArchZSide = z == pZ - 2 || z == pZ + 2;

                            boolean above = y > pY;
                            boolean same = y == pY;
                            boolean below = y < pY;
                            boolean surrounding = (x <= pX+1 && x >= pX-1) && (z <= pZ+1 && z >= pZ-1);

                            if ((flagArchZSide && flagX) || (flagArchXSide && flagZ)){
                                if (!(pLevel.getBlockState(new BlockPos(x, y, z)).is(Tags.Blocks.COBBLESTONE) ||
                                        pLevel.getBlockState(new BlockPos(x, y, z)).is(Tags.Blocks.STONE) ||
                                        pLevel.getBlockState(new BlockPos(x, y, z)).is(Blocks.MOSS_BLOCK))){
                                    return false;
                                }
                            }else if (above){
                                if (!pLevel.getBlockState(new BlockPos(x, y, z)).is(Blocks.AIR)){
                                    return false;
                                }
                            }else if (same){
                                if (!(pLevel.getBlockState(new BlockPos(x, y, z)).is(Blocks.AIR) ||
                                        pLevel.getBlockState(new BlockPos(x, y, z)).is(BlockTags.FLOWERS))){
                                    return false;
                                }
                            }else if (below){
                                if (surrounding){
                                    if (!pLevel.getBlockState(new BlockPos(x, y, z)).is(Blocks.MOSS_BLOCK)){
                                        return false;
                                    }
                                }else{
                                    if (!(pLevel.getBlockState(new BlockPos(x, y, z)).is(Blocks.AIR) ||
                                            pLevel.getBlockState(new BlockPos(x, y, z)).is(BlockTags.FLOWERS))){
                                        return false;
                                    }
                                }
                            }

                        }

                    }

                }
            }
        }
        for (int x = startX; x <= endX; x++){
            for (int z = startZ; z <= endZ; z++) {
                //very top layer
                boolean flagX = x == startX || x == endX;
                boolean flagZ = z == startZ || z == endZ;

                //checking for the totems
                if ((z == pZ && flagX) || (x == pX && flagZ)){
                    if (pLevel.getRandom().nextInt(16)==0){
                        pLevel.addParticle(ModParticles.SEEDLING.get(),  pX+0.5, pY+1, pZ+0.5,
                                x-pX, startY-pY-0.5, z-pZ);
                    }
                }
            }
        }

        return true;
    }

    private static int updateLevel(Level pLevel, int pX, int pY, int pZ) {
        int yLevel;

        int i = 0;
        if (isValidArrangement(pLevel, pX, pY, pZ)){
            for(int modifier = 0; modifier <= 5; modifier++) {
                boolean flag = true;

                if (modifier<=1){
                    yLevel = pY;
                }else {
                    yLevel = pY-1;
                }

                for(int x = pX - modifier; x <= pX + modifier && flag; ++x) {
                    for(int z = pZ - modifier; z <= pZ + modifier && flag; ++z) {

                        int startX = pX - modifier;
                        int endX = pX + modifier;
                        int startZ = pZ - modifier;
                        int endZ = pZ + modifier;

                        boolean flagX = x == startX || x == endX;
                        boolean flagZ = z == startZ || z == endZ;

                        if (flagX || flagZ){
                            if (!pLevel.getBlockState(new BlockPos(x, yLevel, z)).is(BlockTags.FLOWERS)) {
                                flag = false;
                            }
                        }
                    }
                }

                if (!flag) {
                    break;
                }else {
                    i = modifier;
                }
            }
        }else {
            return -1;
        }

        return i;
    }

    private static void applyEffects(Level pLevel, BlockPos pPos, int pLevels) {
        if (!pLevel.isClientSide) {

            double radius = switch (pLevels) {
                case 1 -> 12;
                case 2 -> 24;
                case 3 -> 36;
                case 4 -> 48;
                case 5 -> 60;
                default -> 10;
            };

            AABB aabb = (new AABB(pPos)).inflate(radius).expandTowards(0.0D, pLevel.getHeight(), 0.0D);
            List<LivingEntity> list = pLevel.getEntitiesOfClass(LivingEntity.class, aabb);

            for(LivingEntity entity : list) {
                if (entity.getType().is(ModTags.EntityTypes.BLOOM_ENTITIES) || entity.getItemBySlot(EquipmentSlot.HEAD).is(ModTags.Items.FLOWER_CROWNS))
                    entity.addEffect(new MobEffectInstance(ModEffects.BLOOM.get(), 10*20, 0, true, true));
            }

        }
    }

    public static void playSound(Level pLevel, BlockPos pPos, SoundEvent pSound) {
        pLevel.playSound((Player)null, pPos, pSound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    public void setLevel(Level pLevel) {
        super.setLevel(pLevel);
        this.lastCheckY = pLevel.getMinBuildHeight() - 1;
    }

    public List<BloomBeaconBeamSection> getBeamSections() {
        return this.levels > -1 ? this.beamSections : ImmutableList.of();
    }

    public static class BloomBeaconBeamSection {
        final float[] color;
        private int height;

        public BloomBeaconBeamSection(float[] pColor) {
            this.color = pColor;
            this.height = 1;
        }

        protected void increaseHeight() {
            ++this.height;
        }

        public float[] getColor() {
            return this.color;
        }

        public int getHeight() {
            return this.height;
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.BLOOM_BEACON.get();
    }

    public static final AABB INFINITE_EXTENT_AABB = new net.minecraft.world.phys.AABB(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

    @Override
    public AABB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
