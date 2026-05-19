package codyhuh.druids_n_dinosaurs.client.models;

import codyhuh.druids_n_dinosaurs.client.anims.EggRaptorAnims;
import codyhuh.druids_n_dinosaurs.client.anims.GildedGallumpherAnims;
import codyhuh.druids_n_dinosaurs.common.entity.custom.GildedGallumpher;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class GildedGallumpherModel<T extends GildedGallumpher> extends AgeableHierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart gallumpher;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart trim;
	private final ModelPart right_arm;
	private final ModelPart left_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public GildedGallumpherModel(ModelPart root) {
        super(0.6f, 16f);
        this.root = root.getChild("root");
		this.gallumpher = this.root.getChild("gallumpher");
		this.body = this.gallumpher.getChild("body");
		this.tail = this.body.getChild("tail");
		this.head = this.body.getChild("head");
		this.trim = this.body.getChild("trim");
		this.right_arm = this.gallumpher.getChild("right_arm");
		this.left_arm = this.gallumpher.getChild("left_arm");
		this.left_leg = this.gallumpher.getChild("left_leg");
		this.right_leg = this.gallumpher.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -2.0F));

        PartDefinition gallumpher = root.addOrReplaceChild("gallumpher", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 3.0F));

        PartDefinition body = gallumpher.addOrReplaceChild("body", CubeListBuilder.create().texOffs(86, 152).addBox(-16.0F, -16.0F, -18.0F, 7.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(161, 0).addBox(9.0F, -16.0F, -18.0F, 7.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(161, 16).mirror().addBox(-16.0F, -16.0F, 9.0F, 7.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(161, 16).addBox(9.0F, -16.0F, 9.0F, 7.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 98).addBox(-15.0F, -21.0F, -17.0F, 30.0F, 19.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-19.0F, -2.0F, -21.0F, 38.0F, 8.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 152).addBox(-3.0F, -3.0204F, -1.4218F, 6.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(129, 135).addBox(-8.0F, -5.0204F, 15.5782F, 16.0F, 9.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.3F, 17.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(129, 98).addBox(-9.0F, -7.5229F, -19.9772F, 18.0F, 12.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(161, 32).addBox(-5.5F, -2.5229F, -23.9772F, 11.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -16.0F));

        PartDefinition left_horn_r1 = head.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(161, 44).mirror().addBox(0.0F, -12.0F, 0.0F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -6.5229F, -12.9772F, 0.0F, 0.0F, 0.7854F));

        PartDefinition right_horn_r1 = head.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(161, 44).addBox(-5.0F, -12.0F, 0.0F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -6.5229F, -12.9772F, 0.0F, 0.0F, -0.7854F));

        PartDefinition trim = body.addOrReplaceChild("trim", CubeListBuilder.create().texOffs(0, 51).addBox(-19.0F, 0.0F, -21.0F, 38.0F, 4.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition right_arm = gallumpher.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(49, 152).addBox(-5.0F, -4.0F, -4.0F, 9.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 5.0F, -11.0F));

        PartDefinition left_arm = gallumpher.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(49, 152).mirror().addBox(-5.0F, -4.0F, -4.0F, 9.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0F, 5.0F, -11.0F));

        PartDefinition left_leg = gallumpher.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(49, 152).mirror().addBox(-5.0F, -4.0F, -4.0F, 9.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0F, 5.0F, 11.0F));

        PartDefinition right_leg = gallumpher.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(49, 152).addBox(-4.0F, -4.0F, -4.0F, 9.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 5.0F, 11.0F));

        return LayerDefinition.create(meshdefinition, 224, 224);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

        if (this.young)
            this.applyStatic(GildedGallumpherAnims.BABY);

		this.animate(entity.idleAnimationState, GildedGallumpherAnims.IDLE, ageInTicks, 1.0F);
		this.animate(entity.mudAnimationState, GildedGallumpherAnims.MUD, ageInTicks, 1.0F);
		this.animate(entity.eatAnimationState, GildedGallumpherAnims.GRAZING, ageInTicks, 1.0F);

		this.animateWalk(GildedGallumpherAnims.WALK, limbSwing*5, limbSwingAmount*200, 2.5f, 100);

		this.head.xRot = head.xRot + headPitch * ((float)Math.PI / 180F)/2;
		this.head.yRot = head.yRot + netHeadYaw * ((float)Math.PI / 180F)/2;
	}

	@Override
	public ModelPart root() {
		return root;
	}

	public void translateToBody(PoseStack pPoseStack, int slot) {
		this.root.translateAndRotate(pPoseStack);
		this.gallumpher.translateAndRotate(pPoseStack);
		this.body.translateAndRotate(pPoseStack);

		pPoseStack.translate(0.0F, -0.45, 0);
		pPoseStack.mulPose(Axis.ZP.rotation((float) Math.toRadians(180)));;
		switch (slot){
			case 1:
				pPoseStack.translate(0.0F, 0, -1.15);
				break;
			case 2:
				pPoseStack.translate(0.9, 0, 0);
				pPoseStack.mulPose(Axis.YP.rotation((float) Math.toRadians(90)));;
				break;
			case 3:
				pPoseStack.translate(0.0F, 0, 1.025);
				break;
			default:
				pPoseStack.translate(-0.9, 0, 0);
				pPoseStack.mulPose(Axis.YN.rotation((float) Math.toRadians(90)));;
		}
		pPoseStack.scale(1.45f, 1.45f, 1.45f);

		pPoseStack.translate(0.0F, -0.2, 0);
	}
}