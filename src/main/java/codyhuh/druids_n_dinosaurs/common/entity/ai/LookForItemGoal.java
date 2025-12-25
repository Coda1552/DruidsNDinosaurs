package codyhuh.druids_n_dinosaurs.common.entity.ai;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class LookForItemGoal extends Goal {
    private final Mob base;
    private final Predicate<ItemEntity> item;
    private float speed;

    public LookForItemGoal(Mob base, Predicate<ItemEntity> allowedItems, float pSpeed) {
        this.base = base;
        this.item = allowedItems;
        this.speed = pSpeed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!base.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) return false;
        if (base.getTarget() != null || base.getLastHurtByMob() != null) return false;
        if (base.getRandom().nextInt(reducedTickDelay(10)) != 0) return false;
        List<ItemEntity> list = base.level().getEntitiesOfClass(ItemEntity.class, base.getBoundingBox().inflate(8.0, 8.0, 8.0), item);
        return !list.isEmpty() && base.canPickUpLoot();
    }

    @Override
    public void tick() {
        List<ItemEntity> list = base.level().getEntitiesOfClass(ItemEntity.class, base.getBoundingBox().inflate(8.0, 8.0, 8.0), item);

        if (base.canPickUpLoot() && !list.isEmpty()) {
            base.getNavigation().moveTo(list.get(0), speed);
        }
    }

    @Override
    public void start() {
        List<ItemEntity> list = base.level().getEntitiesOfClass(ItemEntity.class, base.getBoundingBox().inflate(8.0, 8.0, 8.0), item);
        if (!list.isEmpty()) {
            base.getNavigation().moveTo(list.get(0), speed);
        }
    }
}
