package codyhuh.druids_n_dinosaurs.client.anims;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class HartAnims {

	public static final AnimationDefinition BABY = AnimationDefinition.Builder.withLength(0.0F)
			.addAnimation("antler_l", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("antler_r", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.4F, 1.4F, 1.4F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(4.0F).looping()
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.0F, 0.0F, -2.83F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(-2.0F, 0.0F, 2.83F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5F, KeyframeAnimations.degreeVec(2.0F, 0.0F, 2.83F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.degreeVec(-2.0F, 0.0F, -2.83F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.17F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.17F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.17F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.17F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, -0.17F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("ear_l", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 55.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 35.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 55.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 35.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("ear_r", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -45.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -55.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -35.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -55.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -35.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -45.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(-4.62F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(-1.91F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.625F, KeyframeAnimations.degreeVec(1.91F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(4.62F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.125F, KeyframeAnimations.degreeVec(4.62F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.375F, KeyframeAnimations.degreeVec(1.91F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.625F, KeyframeAnimations.degreeVec(-1.91F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.875F, KeyframeAnimations.degreeVec(-4.62F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.125F, KeyframeAnimations.degreeVec(-4.62F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.375F, KeyframeAnimations.degreeVec(-1.91F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.625F, KeyframeAnimations.degreeVec(1.91F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.875F, KeyframeAnimations.degreeVec(4.62F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.125F, KeyframeAnimations.degreeVec(4.62F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.375F, KeyframeAnimations.degreeVec(1.91F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.625F, KeyframeAnimations.degreeVec(-1.91F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.875F, KeyframeAnimations.degreeVec(-4.62F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.17F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.17F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.17F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.17F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, -0.17F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-1.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0F, KeyframeAnimations.degreeVec(-1.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0F, KeyframeAnimations.degreeVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -0.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.posVec(0.0F, -0.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(1.0F).looping()
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 1.41F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-2.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 1.41F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, -1.41F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-2.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, -1.41F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.2F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.9F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.2F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.2F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.9F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.2F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.2F, -1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("ear_l", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("ear_r", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_leg_l", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_leg_l", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 1.25F, -0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_leg_r", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_leg_r", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 1.25F, -0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_leg_l", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_leg_l", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 2.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_leg_r", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_leg_r", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 2.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(-2.0F, 1.41F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(2.0F, -1.41F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.625F, KeyframeAnimations.degreeVec(-2.0F, -1.41F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(2.0F, 1.41F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition BODY_OFFSET = AnimationDefinition.Builder.withLength(0.0F)
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition SCRATCH = AnimationDefinition.Builder.withLength(2.5F)
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-0.0413F, 0.848F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5417F, KeyframeAnimations.degreeVec(107.28F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(54.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.1667F, KeyframeAnimations.degreeVec(77.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(53.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.625F, KeyframeAnimations.degreeVec(77.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-6.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 0.19F, 0.3F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, -1.5F, -1.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.67F, -1.42F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -1.5F, -1.4F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -0.67F, -1.42F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, -1.5F, -1.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9167F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition GALLOP = AnimationDefinition.Builder.withLength(0.5F).looping()
		.addAnimation("hart", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(7.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.67F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -1.33F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -1.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.67F, -1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("ear_l", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("ear_r", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_leg_l", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0833F, KeyframeAnimations.degreeVec(33.75F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_leg_l", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_leg_r", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(33.75F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(33.75F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_leg_r", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_leg_l", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(33.75F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(33.75F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_leg_l", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.67F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.67F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_leg_r", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_leg_r", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();
}