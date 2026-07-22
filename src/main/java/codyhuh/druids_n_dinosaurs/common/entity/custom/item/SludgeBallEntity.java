package codyhuh.druids_n_dinosaurs.common.entity.custom.item;

import codyhuh.druids_n_dinosaurs.registry.ModEffects;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SludgeBallEntity extends ThrowableItemProjectile {

    public SludgeBallEntity(EntityType<? extends SludgeBallEntity> p_37473_, Level p_37474_) {
        super(p_37473_, p_37474_);
    }

    public SludgeBallEntity(EntityType<? extends SludgeBallEntity> p_37473_, Level p_37481_, LivingEntity p_37482_) {
        super(p_37473_, p_37482_, p_37481_);
    }

    public SludgeBallEntity(EntityType<? extends SludgeBallEntity> p_37473_, Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
        super(p_37473_, p_37477_, p_37478_, p_37479_, p_37476_);
    }

    public SludgeBallEntity(Level p_37481_, LivingEntity p_37482_) {
        super(ModEntities.SLUDGE_BALL.get(), p_37482_, p_37481_);
    }

    public SludgeBallEntity(Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
        super(ModEntities.SLUDGE_BALL.get(), p_37477_, p_37478_, p_37479_, p_37476_);
    }

    public void handleEntityEvent(byte p_37484_) {
        if (p_37484_ == 3) {
            for(int $$2 = 0; $$2 < 8; ++$$2) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
        if (entity instanceof LivingEntity living)
            living.addEffect(new MobEffectInstance(ModEffects.SLUDGED.get(), 25*20, 1, true, false, true));
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    protected Item getDefaultItem() {
        return ModItems.SLUDGE_BALL.get();
    }
}
