package codyhuh.druids_n_dinosaurs.client.models;

import codyhuh.druids_n_dinosaurs.client.anims.MudSpitterAnims;
import codyhuh.druids_n_dinosaurs.client.anims.MudlingAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.MudSpitter;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MudSpitterModel<T extends MudSpitter> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart mudspitter;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart tongue;
	private final ModelPart face;
	private final ModelPart frills;
	private final ModelPart right_frill;
	private final ModelPart left_frill;
	private final ModelPart legs;
	private final ModelPart right_leg;
	private final ModelPart left_leg;

	public MudSpitterModel(ModelPart root) {
		this.root = root.getChild("root");
		this.mudspitter = this.root.getChild("mudspitter");
		this.body = this.mudspitter.getChild("body");
		this.tail = this.body.getChild("tail");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.tongue = this.jaw.getChild("tongue");
		this.face = this.head.getChild("face");
		this.frills = this.face.getChild("frills");
		this.right_frill = this.frills.getChild("right_frill");
		this.left_frill = this.frills.getChild("left_frill");
		this.legs = this.mudspitter.getChild("legs");
		this.right_leg = this.legs.getChild("right_leg");
		this.left_leg = this.legs.getChild("left_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, -7.0F));

		PartDefinition mudspitter = root.addOrReplaceChild("mudspitter", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = mudspitter.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -10.0F, -4.0F, 16.0F, 20.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(2, 99).addBox(-3.0F, -10.0F, 11.0F, 6.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 8.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 35).addBox(0.0F, -12.0F, 0.0F, 0.0F, 15.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 8.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 73).addBox(-4.0F, -15.0F, -4.0F, 8.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, 3.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(30, 79).addBox(-5.0F, 0.0F, -9.5F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.5F));

		PartDefinition tongue = jaw.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(70, 88).addBox(-2.0F, 0.2632F, -6.0164F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -2.5F));

		PartDefinition face = head.addOrReplaceChild("face", CubeListBuilder.create().texOffs(46, 62).addBox(-5.0F, -3.0F, -11.0F, 10.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(3, 37).addBox(-4.0F, 3.0F, -10.0F, 8.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 2.0F));

		PartDefinition frills = face.addOrReplaceChild("frills", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition right_frill = frills.addOrReplaceChild("right_frill", CubeListBuilder.create().texOffs(46, 35).mirror().addBox(-2.0F, -13.0F, -0.5F, 18.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 0.0F, 0.0F));

		PartDefinition left_frill = frills.addOrReplaceChild("left_frill", CubeListBuilder.create().texOffs(46, 35).addBox(-16.0F, -13.0F, -0.5F, 18.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, 0.0F));

		PartDefinition legs = mudspitter.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-0.125F, 9.0F, 8.0F));

		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(84, 36).addBox(-4.75F, -1.0F, -2.5F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(84, 27).addBox(-8.75F, 10.95F, -6.5F, 8.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.125F, 0.0F, 0.0F));

		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(84, 36).mirror().addBox(0.0F, -1.0F, -2.5F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(84, 27).mirror().addBox(0.0F, 10.95F, -6.5F, 8.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.125F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(entity.idleAnimationState, MudSpitterAnims.IDLE, ageInTicks, 1.0F);
		this.animate(entity.spitAnimationState, MudSpitterAnims.SPIT, ageInTicks, 1.0F);

		if (entity.isSprinting())
			this.animateWalk(MudSpitterAnims.RUN, limbSwing*(entity.isVehicle() ? 0.5f : 0.75f), limbSwingAmount*4, 2.5f, 1);
		else{
			this.animateWalk(MudSpitterAnims.WALK, limbSwing*4, limbSwingAmount*8, 2.5f, 1);
		}

		this.neck.xRot += headPitch * ((float)Math.PI / 180F);
		this.neck.zRot -= netHeadYaw * ((float)Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}