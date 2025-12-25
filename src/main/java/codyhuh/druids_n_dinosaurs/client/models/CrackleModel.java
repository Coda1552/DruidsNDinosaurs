package codyhuh.druids_n_dinosaurs.client.models;// Made with Blockbench 5.0.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import codyhuh.druids_n_dinosaurs.client.anims.CrackleAnims;
import codyhuh.druids_n_dinosaurs.client.anims.WhispAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Crackle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class CrackleModel<T extends Crackle> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart crackle;
	private final ModelPart body;
	private final ModelPart eyes;
	private final ModelPart shell_top;
	private final ModelPart shell_bottom;
	private final ModelPart tail;
	private final ModelPart tail_tip;
	private final ModelPart legs;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public CrackleModel(ModelPart root) {
		this.root = root.getChild("root");
		this.crackle = this.root.getChild("crackle");
		this.body = this.crackle.getChild("body");
		this.eyes = this.body.getChild("eyes");
		this.shell_top = this.body.getChild("shell_top");
		this.shell_bottom = this.body.getChild("shell_bottom");
		this.tail = this.body.getChild("tail");
		this.tail_tip = this.tail.getChild("tail_tip");
		this.legs = this.crackle.getChild("legs");
		this.left_leg = this.legs.getChild("left_leg");
		this.right_leg = this.legs.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition crackle = root.addOrReplaceChild("crackle", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

		PartDefinition body = crackle.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -12.0F, -7.0F, 14.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -5.0F, 0.0F));

		PartDefinition eyes = body.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(4, 107).addBox(-5.5F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.001F))
		.texOffs(4, 107).addBox(0.5F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -2.5F, -7.25F));

		PartDefinition shell_top = body.addOrReplaceChild("shell_top", CubeListBuilder.create().texOffs(0, 31).addBox(-8.5F, -2.0F, -8.0F, 17.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(56, 0).addBox(8.5F, -2.0F, -8.0F, 0.0F, 15.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 63).addBox(-8.5F, -2.0F, -8.0F, 0.0F, 15.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(66, 31).addBox(-8.5F, -2.0F, -8.0F, 17.0F, 15.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(66, 46).addBox(-8.5F, -2.0F, 8.0F, 17.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -12.0F, 0.0F));

		PartDefinition shell_bottom = body.addOrReplaceChild("shell_bottom", CubeListBuilder.create().texOffs(0, 47).addBox(-9.0F, 3.0F, -8.0F, 17.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(64, 63).addBox(8.0F, -4.0F, -8.0F, 0.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(32, 63).addBox(-9.0F, -4.0F, -8.0F, 0.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(62, 86).addBox(-9.0F, -4.0F, -8.0F, 17.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(88, 0).addBox(-9.0F, -4.0F, 8.0F, 17.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(1.0F, 2.0F, 8.0F));

		PartDefinition upper_spines_r1 = tail.addOrReplaceChild("upper_spines_r1", CubeListBuilder.create().texOffs(62, 93).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.001F))
		.texOffs(88, 7).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -3.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition tail_tip = tail.addOrReplaceChild("tail_tip", CubeListBuilder.create(), PartPose.offset(-1.5F, 1.7717F, 5.0701F));

		PartDefinition lower_r1 = tail_tip.addOrReplaceChild("lower_r1", CubeListBuilder.create().texOffs(32, 86).addBox(-1.5F, 1.0F, -1.0F, 3.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.7717F, 0.9299F, -0.1745F, 0.0F, 0.0F));

		PartDefinition lower_spines_r1 = tail_tip.addOrReplaceChild("lower_spines_r1", CubeListBuilder.create().texOffs(88, 20).addBox(0.0F, 3.0F, 0.0F, 0.0F, 2.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -5.7717F, 2.9299F, -0.1745F, 0.0F, 0.0F));

		PartDefinition legs = crackle.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-2.5F, 0.0F, 0.0F));

		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(78, 93).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.5F, 0.0F, 0.0F));

		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(78, 93).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T pEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		Entity entity = Minecraft.getInstance().getCameraEntity();

		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(pEntity.idleAnimationState, CrackleAnims.IDLE, ageInTicks, 1.0F);

		this.animate(pEntity.blinkAnimationState, CrackleAnims.BLINK, ageInTicks, 1.0F);

		this.animateWalk(CrackleAnims.WALK, limbSwing, limbSwingAmount, 2.5f, 1);

		if (entity != null) {
			Vec3 vec3 = entity.getEyePosition(0.0F);
			Vec3 vec31 = pEntity.getEyePosition(0.0F);

			Vec3 vec32 = pEntity.getViewVector(0.0F);
			vec32 = new Vec3(vec32.x, 0.0D, vec32.z);
			Vec3 vec33 = (new Vec3(vec31.x - vec3.x, 0.0D, vec31.z - vec3.z)).normalize().yRot(((float)Math.PI / 2F));
			double d1 = vec32.dot(vec33);
			this.eyes.x = (Mth.sqrt((float)Math.abs(d1)) * 2.0F * (float)Math.signum(d1));
		}

		this.body.xRot = headPitch * ((float)Math.PI / 180F)/4;
		this.body.yRot = netHeadYaw * ((float)Math.PI / 180F)/4;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}