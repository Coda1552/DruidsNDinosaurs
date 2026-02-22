package codyhuh.druids_n_dinosaurs.client.models;

import codyhuh.druids_n_dinosaurs.common.entity.custom.JadeAutomaton;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.util.Mth;

public class JadeAutomatonModel<T extends JadeAutomaton> extends PlayerModel<T> {
    public JadeAutomatonModel(ModelPart pRoot) {
        super(pRoot, false);
    }

    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        if (pEntity.isAggressive()) {
            float f = Mth.sin(this.attackTime * (float)Math.PI);
            float f1 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float)Math.PI);
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightSleeve.zRot = 0.0F;
            this.leftSleeve.zRot = 0.0F;

            this.rightArm.yRot = -(0.1F - f * 0.6F);
            this.leftArm.yRot = 0.1F - f * 0.6F;
            this.rightSleeve.yRot = -(0.1F - f * 0.6F);
            this.leftSleeve.yRot = 0.1F - f * 0.6F;

            this.rightArm.xRot = (-(float)Math.PI / 2F);
            this.leftArm.xRot = (-(float)Math.PI / 2F);
            this.rightSleeve.xRot = (-(float)Math.PI / 2F);
            this.leftSleeve.xRot = (-(float)Math.PI / 2F);

            this.rightArm.xRot -= f * 1.2F - f1 * 0.4F;
            this.leftArm.xRot -= f * 1.2F - f1 * 0.4F;
            this.rightSleeve.xRot -= f * 1.2F - f1 * 0.4F;
            this.leftSleeve.xRot -= f * 1.2F - f1 * 0.4F;
            AnimationUtils.bobArms(this.rightArm, this.leftArm, pAgeInTicks);
        }
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(createMesh(CubeDeformation.NONE, false), 64, 64);
    }
}
