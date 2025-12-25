package codyhuh.druids_n_dinosaurs.client.models;

import codyhuh.druids_n_dinosaurs.client.anims.EggRaptorAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.EggRaptor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class EggRaptorModel<T extends EggRaptor> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart egg_raptor;
	private final ModelPart legs;
	private final ModelPart right_leg;
	private final ModelPart left_leg;
	private final ModelPart body;
	private final ModelPart arms;
	private final ModelPart right_arm;
	private final ModelPart left_arm;
	private final ModelPart butt;
	private final ModelPart head;

	public EggRaptorModel(ModelPart root) {
		this.root = root.getChild("root");
		this.egg_raptor = this.root.getChild("egg_raptor");
		this.legs = this.egg_raptor.getChild("legs");
		this.right_leg = this.legs.getChild("right_leg");
		this.left_leg = this.legs.getChild("left_leg");
		this.body = this.egg_raptor.getChild("body");
		this.arms = this.body.getChild("arms");
		this.right_arm = this.arms.getChild("right_arm");
		this.left_arm = this.arms.getChild("left_arm");
		this.butt = this.body.getChild("butt");
		this.head = this.body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition egg_raptor = root.addOrReplaceChild("egg_raptor", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition legs = egg_raptor.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-2.5F, 0.0F, 0.0F));

		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(42, 49).addBox(-1.0F, -1.75F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.35F, 1.0F));

		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(50, 49).addBox(-1.0F, -1.75F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -5.35F, 1.0F));

		PartDefinition body = egg_raptor.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 1.0F));

		PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -5.0F));

		PartDefinition right_arm = arms.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 20).addBox(-2.5F, -1.3858F, -13.426F, 3.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(10, 53).addBox(-2.5F, -1.3858F, -15.426F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition left_arm = arms.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 20).addBox(-0.5F, -1.3858F, -13.426F, 3.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 53).addBox(-0.5F, -1.3858F, -15.426F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition butt = body.addOrReplaceChild("butt", CubeListBuilder.create().texOffs(46, -2).addBox(0.5F, -9.8384F, 7.9651F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -3.8384F, -3.0349F, 11.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(23, 22).addBox(-2.0F, -9.8384F, 0.9651F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -2.0F, -3.5F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(30, 49).addBox(0.0F, -18.0F, -1.0F, 0.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 37).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(30, 37).addBox(-3.5F, -12.0F, -3.0F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(46, 14).addBox(-1.5F, -11.0F, -6.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T pEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(pEntity.idleAnimationState, EggRaptorAnims.IDLE, ageInTicks, 1.0F);

		if (this.young)
			this.applyStatic(EggRaptorAnims.BABY);

		if (pEntity.isSprinting())
			this.animateWalk(EggRaptorAnims.RUN, limbSwing, limbSwingAmount, 2.5f, 1);
		else{
			this.animateWalk(EggRaptorAnims.WALK, limbSwing, limbSwingAmount, 2.5f, 1);
		}

		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
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