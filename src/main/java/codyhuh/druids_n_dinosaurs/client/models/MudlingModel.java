package codyhuh.druids_n_dinosaurs.client.models;

import codyhuh.druids_n_dinosaurs.client.anims.EggRaptorAnims;
import codyhuh.druids_n_dinosaurs.client.anims.MudlingAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Mudling;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MudlingModel<T extends Mudling> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart mudling;
	private final ModelPart legs;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart tongue;
	private final ModelPart arms;
	private final ModelPart left_arm;
	private final ModelPart right_arm;

	public MudlingModel(ModelPart root) {
		this.root = root.getChild("root");
		this.mudling = this.root.getChild("mudling");
		this.legs = this.mudling.getChild("legs");
		this.left_leg = this.legs.getChild("left_leg");
		this.right_leg = this.legs.getChild("right_leg");
		this.body = this.mudling.getChild("body");
		this.tail = this.body.getChild("tail");
		this.head = this.body.getChild("head");
		this.tongue = this.head.getChild("tongue");
		this.arms = this.mudling.getChild("arms");
		this.left_arm = this.arms.getChild("left_arm");
		this.right_arm = this.arms.getChild("right_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(4.0F, 21.0F, 5.0F));

		PartDefinition mudling = root.addOrReplaceChild("mudling", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, -2.5F));

		PartDefinition legs = mudling.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, 2.5F));

		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(40, 28).addBox(0.0F, 0.0F, -1.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(40, 28).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, 0.0F, 0.0F));

		PartDefinition body = mudling.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -7.5F, 8.0F, 3.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(28, 32).addBox(-3.0F, -4.0F, -4.5F, 6.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -5.0F, 0.0F, 0.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 3.5F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -4.0F, -9.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.5F));

		PartDefinition tongue = head.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(30, 57).addBox(-3.5F, 0.0F, -3.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -9.0F));

		PartDefinition arms = mudling.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, -3.5F));

		PartDefinition left_arm = arms.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 28).addBox(0.0F, 0.0F, -1.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = arms.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 28).mirror().addBox(-5.0F, 0.0F, -2.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, 0.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(entity.idleAnimationState, MudlingAnims.IDLE, ageInTicks, 1.0F);

		if (entity.isSprinting())
			this.animateWalk(MudlingAnims.RUN, limbSwing/2, limbSwingAmount*4, 2.5f, 1);
		else{
			this.animateWalk(MudlingAnims.WALK, limbSwing*2, limbSwingAmount*2, 2.5f, 1);
		}

		this.head.xRot += headPitch * ((float)Math.PI / 180F);
		this.head.yRot += netHeadYaw * ((float)Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}