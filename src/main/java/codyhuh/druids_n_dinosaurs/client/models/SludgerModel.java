package codyhuh.druids_n_dinosaurs.client.models;

import codyhuh.druids_n_dinosaurs.client.anims.SludgerAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Sludger;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class SludgerModel<T extends Sludger> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart sludger;
	private final ModelPart body;
	private final ModelPart tongue;

	public SludgerModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.root = root.getChild("root");
		this.sludger = this.root.getChild("sludger");
		this.body = this.sludger.getChild("body");
		this.tongue = this.body.getChild("tongue");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition sludger = root.addOrReplaceChild("sludger", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = sludger.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-6.0F, -14.0F, -4.5F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tongue = body.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(-7, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 7.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.sludger.yRot += Math.toRadians(180) + netHeadYaw * ((float)Math.PI / 180F);

		this.animate(entity.idleAnimationState, SludgerAnims.IDLE, ageInTicks);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}