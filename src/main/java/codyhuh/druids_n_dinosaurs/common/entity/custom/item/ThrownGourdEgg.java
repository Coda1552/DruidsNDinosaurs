package codyhuh.druids_n_dinosaurs.common.entity.custom.item;

import codyhuh.druids_n_dinosaurs.common.entity.custom.GourdRaptorEntity;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownGourdEgg extends ThrowableItemProjectile {

    public ThrownGourdEgg(EntityType<? extends ThrownGourdEgg> p_37473_, Level p_37474_) {
        super(p_37473_, p_37474_);
    }

    public ThrownGourdEgg(Level p_37481_, LivingEntity p_37482_) {
        super(ModEntities.GOURD_EGG.get(), p_37482_, p_37481_);
    }

    public ThrownGourdEgg(Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
        super(ModEntities.GOURD_EGG.get(), p_37477_, p_37478_, p_37479_, p_37476_);
    }

    public void handleEntityEvent(byte p_37484_) {
        if (p_37484_ == 3) {
            for(int $$2 = 0; $$2 < 8; ++$$2) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }

    }

    protected void onHitEntity(EntityHitResult p_37486_) {
        super.onHitEntity(p_37486_);
        p_37486_.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        if (!this.level().isClientSide) {
            if (this.random.nextInt(8) == 0) {

                GourdRaptorEntity baby = ModEntities.GOURD_RAPTOR.get().create(this.level());
                if (baby != null) {
                    baby.setAge(-24000);
                    baby.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);

                    if (random.nextFloat() >= 0.9F) {
                        baby.setVariant(1);
                    }
                    this.level().addFreshEntity(baby);
                }
            }

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    protected Item getDefaultItem() {
        return ModItems.GOURD_EGG.get();
    }
}
