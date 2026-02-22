package codyhuh.druids_n_dinosaurs.client.models;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import codyhuh.druids_n_dinosaurs.client.anims.JadeElephantAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.JadeElephant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class JadeElephantModel<T extends JadeElephant> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart elephant;
	private final ModelPart body;
	private final ModelPart left_decor;
	private final ModelPart right_decor;
	private final ModelPart head;
	private final ModelPart trunk;
	private final ModelPart left_ear;
	private final ModelPart right_ear;
	private final ModelPart right_leg;
	private final ModelPart left_leg;
	private final ModelPart right_arm;
	private final ModelPart left_arm;

	public JadeElephantModel(ModelPart root) {
		this.root = root.getChild("root");
		this.elephant = this.root.getChild("elephant");
		this.body = this.elephant.getChild("body");
		this.left_decor = this.body.getChild("left_decor");
		this.right_decor = this.body.getChild("right_decor");
		this.head = this.body.getChild("head");
		this.trunk = this.head.getChild("trunk");
		this.left_ear = this.head.getChild("left_ear");
		this.right_ear = this.head.getChild("right_ear");
		this.right_leg = this.elephant.getChild("right_leg");
		this.left_leg = this.elephant.getChild("left_leg");
		this.right_arm = this.elephant.getChild("right_arm");
		this.left_arm = this.elephant.getChild("left_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition elephant = root.addOrReplaceChild("elephant", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, 0.0F));

		PartDefinition body = elephant.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -17.0F, -14.0F, 20.0F, 20.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition left_decor = body.addOrReplaceChild("left_decor", CubeListBuilder.create().texOffs(21, 73).addBox(-1.0F, -7.0F, -4.0F, 4.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(82, 45).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -3.0F, -2.0F));

		PartDefinition left_talisman_r1 = left_decor.addOrReplaceChild("left_talisman_r1", CubeListBuilder.create().texOffs(82, 50).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 1.0F, -1.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition left_ribbons_r1 = left_decor.addOrReplaceChild("left_ribbons_r1", CubeListBuilder.create().texOffs(0, 45).addBox(1.0F, 0.0F, -11.0F, 0.0F, 5.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition right_decor = body.addOrReplaceChild("right_decor", CubeListBuilder.create().texOffs(21, 73).mirror().addBox(-3.0F, -8.0F, -1.0F, 4.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(82, 45).addBox(-3.0F, -1.0F, 1.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, -1.0F, -5.0F));

		PartDefinition right_ball_r1 = right_decor.addOrReplaceChild("right_ball_r1", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(1.0F, -1.0F, -10.0F, 0.0F, 5.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -3.0F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition right_talisman_r1 = right_decor.addOrReplaceChild("right_talisman_r1", CubeListBuilder.create().texOffs(82, 50).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(45, 45).addBox(-6.0F, -7.0F, -6.0F, 12.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -14.0F));

		PartDefinition right_tusk_r1 = head.addOrReplaceChild("right_tusk_r1", CubeListBuilder.create().texOffs(74, 64).addBox(-2.0F, -2.0F, -10.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 4.0F, -5.0F, 0.384F, 0.1571F, 0.0524F));

		PartDefinition left_tusk_r1 = head.addOrReplaceChild("left_tusk_r1", CubeListBuilder.create().texOffs(74, 64).mirror().addBox(-2.0F, -2.0F, -10.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, 4.0F, -5.0F, 0.384F, -0.1571F, -0.0524F));

		PartDefinition trunk = head.addOrReplaceChild("trunk", CubeListBuilder.create().texOffs(0, 73).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -5.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(74, 77).mirror().addBox(0.0F, -6.0F, -1.0F, 8.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -3.0F, -3.0F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(74, 77).addBox(-8.0F, -6.0F, -1.0F, 8.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -3.0F, -3.0F));

		PartDefinition right_leg = elephant.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(45, 64).addBox(-3.5F, -3.0F, -3.5F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 3.0F, 6.5F));

		PartDefinition left_leg = elephant.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(45, 64).mirror().addBox(-4.5F, -3.0F, -3.5F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.5F, 3.0F, 6.5F));

		PartDefinition right_arm = elephant.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(45, 64).addBox(-3.5F, -3.0F, -3.5F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 3.0F, -6.5F));

		PartDefinition left_arm = elephant.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(45, 64).mirror().addBox(-3.5F, -3.0F, -3.5F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.5F, 3.0F, -6.5F));

		return LayerDefinition.create(meshdefinition, 112, 112);
	}

	@Override
	public void setupAnim(T pEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(pEntity.idleAnimationState, JadeElephantAnims.IDLE, ageInTicks, 1.0F);
		this.animateWalk(JadeElephantAnims.WALK, limbSwing*4, limbSwingAmount*3, 2.5f, 1);
		this.animate(pEntity.interestedAnimationState, JadeElephantAnims.INTERESTED, ageInTicks, 1.0F);
		this.animate(pEntity.attackAnimationState, JadeElephantAnims.ATTACK, ageInTicks, 1);

		this.head.xRot = this.head.xRot + (headPitch * ((float)Math.PI / 180F)/2);
		this.head.yRot = this.head.yRot + (netHeadYaw * ((float)Math.PI / 180F)/4);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	public void translateToTrunk(PoseStack pPoseStack) {
		float f = 1.0F;
		float f1 = 3.0F;
		this.root.translateAndRotate(pPoseStack);
		this.elephant.translateAndRotate(pPoseStack);
		this.body.translateAndRotate(pPoseStack);
		this.head.translateAndRotate(pPoseStack);
		this.trunk.translateAndRotate(pPoseStack);

		pPoseStack.translate(0.0F, 0.06F, 0);
		pPoseStack.scale(0.75F, 0.75F, 0.75F);
		pPoseStack.translate(-0.1F, 1.25F, -0.2);
		pPoseStack.mulPose(Axis.ZN.rotationDegrees(45));
	}
}