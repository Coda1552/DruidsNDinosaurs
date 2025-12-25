package codyhuh.druids_n_dinosaurs.client.models;// Made with Blockbench 5.0.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import codyhuh.druids_n_dinosaurs.client.anims.HueHogAnims;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import codyhuh.druids_n_dinosaurs.common.entity.custom.HueHog;
import net.minecraft.world.entity.HumanoidArm;

public class HueHogModel<T extends HueHog> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart hue_hog;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart right_leg;
	private final ModelPart left_leg;

	public HueHogModel(ModelPart root) {
		this.root = root.getChild("root");
		this.hue_hog = this.root.getChild("hue_hog");
		this.body = this.hue_hog.getChild("body");
		this.left_arm = this.body.getChild("left_arm");
		this.right_arm = this.body.getChild("right_arm");
		this.head = this.body.getChild("head");
		this.tail = this.body.getChild("tail");
		this.right_leg = this.hue_hog.getChild("right_leg");
		this.left_leg = this.hue_hog.getChild("left_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition hue_hog = root.addOrReplaceChild("hue_hog", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition body = hue_hog.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -2.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, -1.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -3.0F, -1.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(-2.0F, -1.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, -3.0F, -1.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(12, 18).addBox(0.5F, 0.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 26).addBox(-1.5F, 0.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 10).addBox(-2.5F, -3.0F, -2.0F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(8, 26).addBox(-3.0F, -5.0F, -1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 26).addBox(1.0F, -5.0F, -1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 24).addBox(-1.5F, -2.0F, -3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -1.0F));

		PartDefinition horn_r1 = head.addOrReplaceChild("horn_r1", CubeListBuilder.create().texOffs(17, 17).addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.0F, -1.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 16).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-2.0F, -2.0F, 5.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(20, 22).addBox(-1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition right_leg = hue_hog.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(24, 6).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 1.0F, 0.0F));

		PartDefinition left_leg = hue_hog.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(12, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 1.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T pEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(pEntity.idleAnimationState, HueHogAnims.IDLE, ageInTicks, 1.0F);

		if (this.young)
			this.applyStatic(HueHogAnims.BABY);

		this.animateWalk(HueHogAnims.WALK, limbSwing, limbSwingAmount, 2.5f, 1);

		this.animate(pEntity.danceAnimationState, HueHogAnims.DANCE, ageInTicks, 1.0F);
		this.animate(pEntity.collectAnimationState, HueHogAnims.TAIL_WAG, ageInTicks, 1.0F);
		this.animate(pEntity.turnHeadAnimationState1, HueHogAnims.HEAD_TILT_1, ageInTicks, 1.0F);
		this.animate(pEntity.turnHeadAnimationState2, HueHogAnims.HEAD_TILT_2, ageInTicks, 1.0F);

		float prevHeadxRot = this.head.xRot;
		float prevHeadyRot = this.head.yRot;

		this.head.xRot = prevHeadxRot + headPitch * ((float)Math.PI / 180F);
		this.head.yRot = prevHeadyRot + netHeadYaw * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();

		if (this.young){
			poseStack.scale(0.6f, 0.6f, 0.6f);
			poseStack.translate(0, 1, 0);
		}

		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	@Override
	public ModelPart root() {
		return root;
	}


	public void translateToMouth(PoseStack pPoseStack) {
		float f = 1.0F;
		float f1 = 3.0F;
		this.root.translateAndRotate(pPoseStack);
		this.hue_hog.translateAndRotate(pPoseStack);
		this.body.translateAndRotate(pPoseStack);
		this.head.translateAndRotate(pPoseStack);

		pPoseStack.translate(0.0F, 0.0625F, 0.1875F);
		pPoseStack.scale(0.25F, 0.25F, 0.25F);
		pPoseStack.translate(0, -0.25F, -0.85f);

	}
}