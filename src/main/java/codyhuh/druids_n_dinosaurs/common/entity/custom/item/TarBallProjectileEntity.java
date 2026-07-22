package codyhuh.druids_n_dinosaurs.common.entity.custom.item;

import codyhuh.druids_n_dinosaurs.common.entity.custom.Sludger;
import codyhuh.druids_n_dinosaurs.registry.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class TarBallProjectileEntity extends SludgeBallEntity{
    public TarBallProjectileEntity(EntityType<? extends SludgeBallEntity> p_37473_, Level p_37474_) {
        super(p_37473_, p_37474_);
    }

    public TarBallProjectileEntity(Level p_37481_, LivingEntity p_37482_) {
        super(ModEntities.TAR_BALL.get(), p_37481_, p_37482_);
    }

    public TarBallProjectileEntity(Level pLevel, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(ModEntities.TAR_BALL.get(), pLevel, pOffsetX, pOffsetY, pOffsetZ);
    }

    @Override
    protected void onHit(HitResult pResult) {

        if (!this.level().isClientSide) {
            Sludger baby = ModEntities.SLUDGER.get().create(this.level());
            if (baby != null) {
                baby.setSize(1, true);
                baby.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                this.level().addFreshEntity(baby);
            }
        }

        super.onHit(pResult);
    }
}
