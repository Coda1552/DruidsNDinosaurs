package codyhuh.druids_n_dinosaurs.client.models;

import codyhuh.druids_n_dinosaurs.common.entity.custom.GourdRaptorEntity;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

import java.util.List;

public class GourdRaptorModel<T extends GourdRaptorEntity> extends AgeableListModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	private final ModelPart tail;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart head;
	private final ModelPart beak;
	private final ModelPart jaw;

	public GourdRaptorModel(ModelPart root) {
		super(true, 0.0F, 0.0F);
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.rightLeg = this.body.getChild("rightLeg");
		this.leftLeg = this.body.getChild("leftLeg");
		this.rightWing = this.body.getChild("rightWing");
		this.tail = this.body.getChild("tail");
		this.leftWing = this.body.getChild("leftWing");
		this.head = this.root.getChild("head");
		this.beak = this.head.getChild("beak");
		this.jaw = this.head.getChild("jaw");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, -1.5F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -10.0F, -4.5F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 2.0F));

		PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(40, 24).mirror().addBox(-1.5F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, -4.0F, 0.5F));

		PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(40, 24).addBox(-0.5F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -4.0F, 0.5F));

		PartDefinition rightWing = body.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(37, 17).addBox(-7.0F, -1.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(41, 7).addBox(-5.0F, -1.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -8.0F, -0.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(40, 0).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 4.5F));

		PartDefinition leftWing = body.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(47, 17).addBox(5.0F, -1.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(40, 12).addBox(0.0F, -1.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -8.0F, -0.5F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(30, 32).addBox(-3.0F, -5.0F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(40, 17).addBox(0.0F, -8.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -0.5F));

		PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, -2.0F, -12.0F, 8.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -2.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -12.0F, 8.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(30, 43).mirror().addBox(1.0F, -1.0F, -11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(30, 43).addBox(-3.0F, -1.0F, -11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return List.of(head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return List.of(body);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root.getAllParts().forEach(ModelPart::resetPose);

		if (young) {
			this.head.y = 25.5F;
			this.head.xScale = 0.8F;
			this.head.yScale = 0.8F;
			this.head.zScale = 0.8F;
		}
		else {
			this.head.y = 14.0F;
		}

		this.jaw.xRot = 0.1F + Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
		this.rightWing.zRot = -1.5708F;
		this.leftWing.zRot = 1.5708F;
		this.body.y = 24.0F;
		this.head.xRot = headPitch * 0.017453292F;
		this.head.yRot = netHeadYaw * 0.017453292F;
		this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.rightWing.zRot += ageInTicks;
        this.leftWing.zRot -= ageInTicks;
	}
}