package codyhuh.druids_n_dinosaurs.client.models;// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import codyhuh.druids_n_dinosaurs.client.anims.GildedGallumpherAnims;
import codyhuh.druids_n_dinosaurs.client.anims.TerraThunkAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.TerraThunk;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class TerraThunkModel<T extends TerraThunk> extends AgeableHierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart terra_thunk;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart right_arm;
	private final ModelPart left_arm;
	private final ModelPart right_leg;
	private final ModelPart left_leg;

	public TerraThunkModel(ModelPart root) {
        super(0.6f, 16);
        this.root = root.getChild("root");
		this.terra_thunk = this.root.getChild("terra_thunk");
		this.body = this.terra_thunk.getChild("body");
		this.head = this.body.getChild("head");
		this.tail = this.body.getChild("tail");
		this.right_arm = this.terra_thunk.getChild("right_arm");
		this.left_arm = this.terra_thunk.getChild("left_arm");
		this.right_leg = this.terra_thunk.getChild("right_leg");
		this.left_leg = this.terra_thunk.getChild("left_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(1.0F, 4.0F, 12.0F));

		PartDefinition terra_thunk = root.addOrReplaceChild("terra_thunk", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, -12.0F));

		PartDefinition body = terra_thunk.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, -31.0F, -21.0F, 42.0F, 42.0F, 42.0F, new CubeDeformation(0.0F))
		.texOffs(0, 84).addBox(-23.0F, 3.0F, -23.0F, 46.0F, 9.0F, 46.0F, new CubeDeformation(0.0F))
		.texOffs(0, 139).addBox(-23.0F, 12.0F, -23.0F, 46.0F, 4.0F, 46.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(166, 0).addBox(-9.0F, -7.0F, -14.0F, 18.0F, 12.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(188, 184).addBox(-2.5F, 1.0F, -16.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -19.0F));

		PartDefinition left_horn_r1 = head.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(184, 184).addBox(0.0F, -7.0F, -1.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -6.0F, -7.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition right_horn_r1 = head.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(184, 184).addBox(-6.0F, -7.0F, -1.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -6.0F, -7.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(184, 66).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(168, 43).addBox(-5.0F, -6.0F, 12.0F, 10.0F, 10.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 21.0F));

		PartDefinition right_arm = terra_thunk.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(184, 88).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 6.0F, -12.0F));

		PartDefinition left_arm = terra_thunk.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(184, 88).mirror().addBox(-5.0F, 0.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(12.0F, 6.0F, -12.0F));

		PartDefinition right_leg = terra_thunk.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(184, 88).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 6.0F, 12.0F));

		PartDefinition left_leg = terra_thunk.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(184, 88).mirror().addBox(-5.0F, 0.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(12.0F, 6.0F, 12.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (this.young)
			this.applyStatic(TerraThunkAnims.BABY);

		if (!entity.isBouncing()){
			this.animate(entity.idleAnimationState, TerraThunkAnims.IDLE, ageInTicks, 1.0F);
			this.animateWalk(TerraThunkAnims.WALK, limbSwing*5, limbSwingAmount*200, 2.5f, 100);
		}

		this.animate(entity.sitPoseAnimationState, TerraThunkAnims.INSIDE_POSE, ageInTicks, 1.0F);
		this.animate(entity.sitAnimationState, TerraThunkAnims.RECEDE, ageInTicks, 1.0F);
		this.animate(entity.standUpAnimationState, TerraThunkAnims.EXIT, ageInTicks, 1.0F);
		this.animate(entity.bounceAnimationState, TerraThunkAnims.SPINNING, ageInTicks, 1.0F);


		this.head.xRot = head.xRot + headPitch * ((float)Math.PI / 180F)/2;
		this.head.yRot = head.yRot + netHeadYaw * ((float)Math.PI / 180F)/2;
	}

	@Override
	public ModelPart root() {
		return root;
	}
}