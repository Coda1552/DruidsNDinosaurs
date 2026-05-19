package codyhuh.druids_n_dinosaurs.client.models;

import codyhuh.druids_n_dinosaurs.client.anims.ChiselchirpAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.Chiselchirp;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ChiselchirpModel<T extends Chiselchirp> extends AgeableHierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart fly_rot;
	private final ModelPart chiselchirp;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	private final ModelPart legs;
	private final ModelPart sherd;
	private final ModelPart right_leg;
	private final ModelPart left_leg;

	public ChiselchirpModel(ModelPart root) {
        super(0.6f, 16);
        this.root = root.getChild("root");
		this.fly_rot = this.root.getChild("fly_rot");
		this.chiselchirp = this.fly_rot.getChild("chiselchirp");
		this.body = this.chiselchirp.getChild("body");
		this.head = this.body.getChild("head");
		this.tail = this.body.getChild("tail");
		this.left_wing = this.body.getChild("left_wing");
		this.right_wing = this.body.getChild("right_wing");
		this.legs = this.chiselchirp.getChild("legs");
		this.sherd = this.legs.getChild("sherd");
		this.right_leg = this.legs.getChild("right_leg");
		this.left_leg = this.legs.getChild("left_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, -2.0F));

		PartDefinition fly_rot = root.addOrReplaceChild("fly_rot", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, 1.0F));

		PartDefinition chiselchirp = fly_rot.addOrReplaceChild("chiselchirp", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = chiselchirp.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 11).addBox(-3.0F, -2.0F, -1.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 1.0F, 0.0F));

		PartDefinition chest_feathers_r1 = body.addOrReplaceChild("chest_feathers_r1", CubeListBuilder.create().texOffs(12, 27).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(26, 20).addBox(-1.0F, -3.0F, -11.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, 0.0F));

		PartDefinition head_feathers_r1 = head.addOrReplaceChild("head_feathers_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-4.0F, 0.0F, -1.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -1.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 27).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 2.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 17).mirror().addBox(0.0F, -2.5F, 0.0F, 13.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 0.1131F, 1.0824F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 17).addBox(-13.0F, -2.5F, 0.0F, 13.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.1131F, 1.0824F));

		PartDefinition legs = chiselchirp.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-0.5F, 4.0F, 1.0F));

		PartDefinition sherd = legs.addOrReplaceChild("sherd", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(28, 0).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 1.0F, 0.0F));

		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(28, 0).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 1.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (this.young)
			this.applyStatic(ChiselchirpAnims.BABY);

		if (entity.isFlying())
			this.animate(entity.idleAnimationState, ChiselchirpAnims.FLY, ageInTicks, 1.0F);
		else
			this.animate(entity.idleAnimationState, ChiselchirpAnims.GROUND, ageInTicks, 1.0F);

		this.head.xRot = head.xRot + headPitch * ((float)Math.PI / 180F);
		this.head.yRot = head.yRot + netHeadYaw * ((float)Math.PI / 180F);

		this.animate(entity.chiselAnimationState, ChiselchirpAnims.CHISEL, ageInTicks, 1.0F);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	public void translateToClaws(PoseStack pPoseStack) {
		this.root.translateAndRotate(pPoseStack);
		this.fly_rot.translateAndRotate(pPoseStack);
		this.chiselchirp.translateAndRotate(pPoseStack);
		this.legs.translateAndRotate(pPoseStack);
		this.sherd.translateAndRotate(pPoseStack);

		pPoseStack.mulPose(Axis.ZP.rotation((float) Math.toRadians(180)));;
		pPoseStack.translate(0.0F, -0.35, 0);
//		pPoseStack.scale(0.25F, 0.25F, 0.25F);
//		pPoseStack.translate(0, -0.25F, -0.85f);
	}
}