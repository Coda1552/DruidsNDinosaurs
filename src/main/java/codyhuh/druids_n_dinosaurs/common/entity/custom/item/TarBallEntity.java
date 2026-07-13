package codyhuh.druids_n_dinosaurs.common.entity.custom.item;

import codyhuh.druids_n_dinosaurs.common.entity.custom.GourdRaptorEntity;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class TarBallEntity extends SludgeBallEntity{
    public TarBallEntity(EntityType<? extends SludgeBallEntity> p_37473_, Level p_37474_) {
        super(p_37473_, p_37474_);
    }

    public TarBallEntity(Level p_37481_, LivingEntity p_37482_) {
        super(ModEntities.SLUDGE_BALL.get(), p_37481_, p_37482_);
    }

    public TarBallEntity(Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
        super(ModEntities.SLUDGE_BALL.get(), p_37476_, p_37478_, p_37479_, p_37477_);
    }

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        if (!this.level().isClientSide) {

            GourdRaptorEntity baby = ModEntities.GOURD_RAPTOR.get().create(this.level());
            if (baby != null) {
                baby.setAge(-24000);
                baby.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);

                if (random.nextFloat() >= 0.9F) {
                    baby.setVariant(1);
                }
                this.level().addFreshEntity(baby);
            }

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }
}
