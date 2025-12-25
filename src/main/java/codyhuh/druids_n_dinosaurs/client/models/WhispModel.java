package codyhuh.druids_n_dinosaurs.client.models;// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import codyhuh.druids_n_dinosaurs.client.anims.WhispAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Whisp;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class WhispModel<T extends Whisp> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart whisp;
	private final ModelPart right_arm;
	private final ModelPart body;
	private final ModelPart left_arm;

	public WhispModel(ModelPart root) {
		this.root = root.getChild("root");
		this.whisp = this.root.getChild("whisp");
		this.right_arm = this.whisp.getChild("right_arm");
		this.body = this.whisp.getChild("body");
		this.left_arm = this.whisp.getChild("left_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition whisp = root.addOrReplaceChild("whisp", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, 0.0F));

		PartDefinition right_arm = whisp.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-3.5F, 0.5F, -1.5F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(10, 22).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition body = whisp.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -4.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(-3.5F, 2.5F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(20, 22).addBox(-1.5F, -6.5F, -3.75F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = whisp.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(3.5F, 0.5F, -1.5F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, 0.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(entity.idleAnimationState, WhispAnims.IDLE, ageInTicks, 1.0F);

		this.animate(entity.spinAnimationState, WhispAnims.THROW, ageInTicks, 1.0F);

		this.whisp.yRot = netHeadYaw * 0.017453292F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();

		poseStack.translate(0, -0.75, 0);

		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	@Override
	public ModelPart root() {
		return root;
	}
}