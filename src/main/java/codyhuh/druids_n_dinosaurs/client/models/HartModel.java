package codyhuh.druids_n_dinosaurs.client.models;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import codyhuh.druids_n_dinosaurs.client.anims.HartAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Hart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class HartModel<T extends Hart> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart hart;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart antlers;
	private final ModelPart antler_l;
	private final ModelPart antler_r;
	private final ModelPart ear_l;
	private final ModelPart ear_r;
	private final ModelPart back_leg_r;
	private final ModelPart front_leg_l;
	private final ModelPart back_leg_l;
	private final ModelPart front_leg_r;

	public HartModel(ModelPart root) {
		this.root = root.getChild("root");
		this.hart = this.root.getChild("hart");
		this.body = this.hart.getChild("body");
		this.tail = this.body.getChild("tail");
		this.head = this.body.getChild("head");
		this.antlers = this.head.getChild("antlers");
		this.antler_l = this.antlers.getChild("antler_l");
		this.antler_r = this.antlers.getChild("antler_r");
		this.ear_l = this.head.getChild("ear_l");
		this.ear_r = this.head.getChild("ear_r");
		this.back_leg_r = this.hart.getChild("back_leg_r");
		this.front_leg_l = this.hart.getChild("front_leg_l");
		this.back_leg_l = this.hart.getChild("back_leg_l");
		this.front_leg_r = this.hart.getChild("front_leg_r");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition hart = root.addOrReplaceChild("hart", CubeListBuilder.create(), PartPose.offset(0.0F, -8.5F, 4.0F));

		PartDefinition body = hart.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.5F, -6.0F, 8.0F, 5.0F, 13.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(43, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, 7.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 19).addBox(-3.0F, -8.0F, -4.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(22, 41).addBox(-2.0F, -6.0F, -5.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -3.0F));

		PartDefinition antlers = head.addOrReplaceChild("antlers", CubeListBuilder.create(), PartPose.offset(-2.0F, -8.0F, -1.0F));

		PartDefinition antler_l = antlers.addOrReplaceChild("antler_l", CubeListBuilder.create().texOffs(42, 45).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(7, 50).addBox(-1.5F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(23, 46).addBox(-1.5F, -4.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 46).addBox(-1.5F, -7.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(43, 6).addBox(0.5F, -3.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(49, 41).addBox(2.5F, -5.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 0.0F, 0.0F));

		PartDefinition antler_r = antlers.addOrReplaceChild("antler_r", CubeListBuilder.create().texOffs(49, 46).addBox(0.5F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 44).addBox(-0.5F, -4.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 44).addBox(0.5F, -7.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(35, 45).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 48).addBox(-3.5F, -5.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 37).addBox(-3.5F, -3.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.0F, 0.0F));

		PartDefinition ear_l = head.addOrReplaceChild("ear_l", CubeListBuilder.create().texOffs(35, 41).addBox(0.0F, -1.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -7.0F, -1.0F));

		PartDefinition ear_r = head.addOrReplaceChild("ear_r", CubeListBuilder.create().texOffs(42, 19).addBox(-4.0F, -1.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -7.0F, -1.0F));

		PartDefinition back_leg_r = hart.addOrReplaceChild("back_leg_r", CubeListBuilder.create().texOffs(25, 30).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(42, 30).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 2.5F, 1.0F));

		PartDefinition front_leg_l = hart.addOrReplaceChild("front_leg_l", CubeListBuilder.create().texOffs(11, 35).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(43, 10).addBox(-1.0F, 5.0F, -1.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 2.5F, -7.5F));

		PartDefinition back_leg_l = hart.addOrReplaceChild("back_leg_l", CubeListBuilder.create().texOffs(25, 19).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(42, 23).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 2.5F, 1.0F));

		PartDefinition front_leg_r = hart.addOrReplaceChild("front_leg_r", CubeListBuilder.create().texOffs(43, 14).addBox(-1.0F, 5.0F, -1.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 35).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 2.5F, -7.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T pEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(pEntity.idleAnimationState, HartAnims.IDLE, ageInTicks, 1.0F);

		this.applyStatic(HartAnims.BODY_OFFSET);
		if (this.young)
			this.applyStatic(HartAnims.BABY);

		int walkScale = 6;
		if (!pEntity.isBaby()){

			if (pEntity.isVehicle() || pEntity.isSprinting())
				this.animateWalk(HartAnims.GALLOP, limbSwing, limbSwingAmount, 2.5f, 1);
			else
				this.animateWalk(HartAnims.WALK, limbSwing, limbSwingAmount, 2.5f, walkScale);

			this.animate(pEntity.scratchAnimationState, HartAnims.SCRATCH, ageInTicks, 1.0F);

			if (!pEntity.getLeftAntler() && this.antler_l.visible)
				this.antler_l.visible = false;
			if (pEntity.getLeftAntler() && !this.antler_l.visible)
				this.antler_l.visible = true;

			if (!pEntity.getRightAntler() && this.antler_r.visible)
				this.antler_r.visible = false;
			if (pEntity.getRightAntler() && !this.antler_r.visible)
				this.antler_r.visible = true;
		}else {

			if (pEntity.isVehicle() || pEntity.isSprinting())
				this.animateWalk(HartAnims.GALLOP, limbSwing/2, limbSwingAmount, 2.5f, 1);
			else
				this.animateWalk(HartAnims.WALK, limbSwing/2, limbSwingAmount, 2.5f, walkScale);
		}

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
}