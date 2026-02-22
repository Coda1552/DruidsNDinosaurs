package codyhuh.druids_n_dinosaurs.client.models;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import codyhuh.druids_n_dinosaurs.client.anims.TotemPoleAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.TuffTotemPole;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class TotemPoleModel<T extends TuffTotemPole> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart totem;
	private final ModelPart bottom;
	private final ModelPart middle;
	private final ModelPart top;
	private final ModelPart right_wing;
	private final ModelPart left_wing;
	private final ModelPart left_hart_ear;
	private final ModelPart right_hart_ear;
	private final ModelPart right_hart_arm;
	private final ModelPart left_hart_arm;
	private final ModelPart right_ear;
	private final ModelPart left_ear;
	private final ModelPart trunk;
	private final ModelPart right_arm;
	private final ModelPart left_arm;
	private final ModelPart right_leg;
	private final ModelPart left_leg;

	public TotemPoleModel(ModelPart root) {
		this.root = root.getChild("root");
		this.totem = this.root.getChild("totem");
		this.bottom = this.totem.getChild("bottom");
		this.middle = this.bottom.getChild("middle");
		this.top = this.middle.getChild("top");
		this.right_wing = this.top.getChild("right_wing");
		this.left_wing = this.top.getChild("left_wing");
		this.left_hart_ear = this.middle.getChild("left_hart_ear");
		this.right_hart_ear = this.middle.getChild("right_hart_ear");
		this.right_hart_arm = this.middle.getChild("right_hart_arm");
		this.left_hart_arm = this.middle.getChild("left_hart_arm");
		this.right_ear = this.bottom.getChild("right_ear");
		this.left_ear = this.bottom.getChild("left_ear");
		this.trunk = this.bottom.getChild("trunk");
		this.right_arm = this.totem.getChild("right_arm");
		this.left_arm = this.totem.getChild("left_arm");
		this.right_leg = this.totem.getChild("right_leg");
		this.left_leg = this.totem.getChild("left_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition totem = root.addOrReplaceChild("totem", CubeListBuilder.create(), PartPose.offset(7.0F, -7.0F, -7.0F));

		PartDefinition bottom = totem.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(65, 0).addBox(-8.0F, -13.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -3.0F, 7.0F));

		PartDefinition middle = bottom.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(65, 33).addBox(-9.0F, -4.0F, -9.0F, 18.0F, 4.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-8.0F, -24.0F, -8.0F, 16.0F, 20.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(72, 114).addBox(-13.0F, -24.0F, -3.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 100).mirror().addBox(-18.0F, -22.0F, -4.0F, 10.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(42, 114).addBox(-18.0F, -27.0F, -4.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(57, 114).addBox(16.0F, -27.0F, -4.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 100).addBox(8.0F, -22.0F, -4.0F, 10.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(114, 85).addBox(11.0F, -24.0F, -3.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

		PartDefinition top = middle.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 37).addBox(-8.0F, -24.0F, -8.0F, 16.0F, 20.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 79).addBox(-9.0F, -26.0F, -9.0F, 18.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(65, 56).addBox(-9.0F, -4.0F, -9.0F, 18.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		PartDefinition nest_r1 = top.addOrReplaceChild("nest_r1", CubeListBuilder.create().texOffs(73, 79).addBox(0.0F, -3.0F, -10.0F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -26.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition nest_r2 = top.addOrReplaceChild("nest_r2", CubeListBuilder.create().texOffs(73, 79).mirror().addBox(0.0F, -3.0F, -10.0F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, -26.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition nest_r3 = top.addOrReplaceChild("nest_r3", CubeListBuilder.create().texOffs(0, 74).addBox(-11.0F, -3.0F, 0.0F, 20.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -26.0F, 9.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition nest_r4 = top.addOrReplaceChild("nest_r4", CubeListBuilder.create().texOffs(0, 74).addBox(-10.0F, -3.0F, 0.0F, 20.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -26.0F, -9.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition right_wing = top.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(100, 103).addBox(-13.0F, -1.5F, -1.0F, 13.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(19, 114).addBox(-9.0F, 1.5F, -1.0F, 9.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(114, 79).addBox(-6.0F, 4.5F, -1.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -20.5F, 2.0F));

		PartDefinition left_wing = top.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(114, 79).mirror().addBox(0.0F, 4.5F, -1.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(19, 114).mirror().addBox(0.0F, 1.5F, -1.0F, 9.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(100, 103).mirror().addBox(0.0F, -1.5F, -1.0F, 13.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, -20.5F, 2.0F));

		PartDefinition left_hart_ear = middle.addOrReplaceChild("left_hart_ear", CubeListBuilder.create().texOffs(100, 109).addBox(0.0F, -1.0F, -2.0F, 7.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -17.0F, -4.0F));

		PartDefinition right_hart_ear = middle.addOrReplaceChild("right_hart_ear", CubeListBuilder.create().texOffs(100, 109).mirror().addBox(-7.0F, -1.0F, -2.0F, 7.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, -17.0F, -4.0F));

		PartDefinition right_hart_arm = middle.addOrReplaceChild("right_hart_arm", CubeListBuilder.create().texOffs(56, 103).addBox(-8.0F, -2.5F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -11.5F, -2.5F));

		PartDefinition left_hart_arm = middle.addOrReplaceChild("left_hart_arm", CubeListBuilder.create().texOffs(56, 103).mirror().addBox(0.0F, -2.5F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, -11.5F, -2.5F));

		PartDefinition right_ear = bottom.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 109).addBox(-7.0F, -6.0F, -1.0F, 7.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -6.0F, -5.0F));

		PartDefinition left_ear = bottom.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 109).mirror().addBox(0.0F, -6.0F, -1.0F, 7.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, -6.0F, -5.0F));

		PartDefinition trunk = bottom.addOrReplaceChild("trunk", CubeListBuilder.create().texOffs(83, 103).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -8.0F));

		PartDefinition right_arm = totem.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(31, 100).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-13.0F, 0.0F, 1.0F));

		PartDefinition left_arm = totem.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(31, 100).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 1.0F));

		PartDefinition right_leg = totem.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(31, 100).mirror().addBox(-3.0F, 0.0F, -1.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-13.0F, 0.0F, 11.0F));

		PartDefinition left_leg = totem.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(31, 100).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 11.0F));

		return LayerDefinition.create(meshdefinition, 144, 144);
	}

	@Override
	public void setupAnim(T pEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (pEntity.getEggPhase() == 2)
			this.animate(pEntity.idleAnimationState, TotemPoleAnims.HART_WAVING, ageInTicks, 1.0F);
		else{
			if (pEntity.getEggPhase() == 1)
				this.applyStatic(TotemPoleAnims.BOTTOM_HOLDING_EGG);

			this.animate(pEntity.idleAnimationState, TotemPoleAnims.IDLE_ARMS, ageInTicks, 1.0F);
			this.animateWalk(TotemPoleAnims.WALK_ARMS, limbSwing*2, limbSwingAmount, 2.5f, 7);
		}

		this.animate(pEntity.idleAnimationState, TotemPoleAnims.IDLE, ageInTicks, 1.0F);
		this.animate(pEntity.cawingAnimationState, TotemPoleAnims.CAW, ageInTicks, 1.0F);
		this.animateWalk(TotemPoleAnims.WALK, limbSwing*2, limbSwingAmount, 2.5f, 7);

		this.middle.xRot = middle.xRot + headPitch * ((float)Math.PI / 180F)/4;
		this.middle.yRot = middle.yRot + netHeadYaw * ((float)Math.PI / 180F)/2;
		this.top.xRot = top.xRot + headPitch * ((float)Math.PI / 180F)/4;
		this.top.yRot = top.yRot + netHeadYaw * ((float)Math.PI / 180F)/2;
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
		this.root.translateAndRotate(pPoseStack);
		this.totem.translateAndRotate(pPoseStack);
		this.bottom.translateAndRotate(pPoseStack);
		this.trunk.translateAndRotate(pPoseStack);

		pPoseStack.translate(0.0F, 0.06F, 0);
		pPoseStack.translate(0, 0.7F, -0.1);
		pPoseStack.mulPose(Axis.XN.rotationDegrees(90));
	}

	public void translateToHart(PoseStack pPoseStack, int pAgeInTicks) {
		this.root.translateAndRotate(pPoseStack);
		this.totem.translateAndRotate(pPoseStack);
		this.bottom.translateAndRotate(pPoseStack);
		this.middle.translateAndRotate(pPoseStack);

		pPoseStack.translate(0, -0.65F, -1);
		pPoseStack.translate(0, Math.cos(pAgeInTicks*0.1)*0.15, 0);
		pPoseStack.mulPose(Axis.ZN.rotationDegrees(180));
	}

	public void translateToNest(PoseStack pPoseStack, int pAgeInTicks) {
		this.root.translateAndRotate(pPoseStack);
		this.totem.translateAndRotate(pPoseStack);
		this.bottom.translateAndRotate(pPoseStack);
		this.middle.translateAndRotate(pPoseStack);
		this.top.translateAndRotate(pPoseStack);

		pPoseStack.translate(0, -1.65F, 0);
		pPoseStack.mulPose(Axis.ZN.rotationDegrees(180));
	}
}