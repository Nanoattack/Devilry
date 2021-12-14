package com.nano.devilry.entity.custom.model;// Made with Blockbench 4.0.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nano.devilry.entity.custom.OwlEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class OwlModel<T extends OwlEntity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "OwlModel"), "main");
	private final ModelPart bb_main;

	public OwlModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 24).addBox(4.0F, -7.0F, -2.0F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(16, 28).addBox(4.0F, -2.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(17, 14).addBox(-2.0F, -7.0F, -2.0F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(26, 0).addBox(-2.0F, -2.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(-1.0F, -7.0F, -1.0F, 5.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(30, 19).addBox(-4.0F, -14.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(6.0F, -14.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -13.0F, -3.0F, 9.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition beak_top_r1 = bb_main.addOrReplaceChild("beak_top_r1", CubeListBuilder.create().texOffs(14, 14).addBox(-1.0F, -8.0F, -4.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 14).addBox(0.0F, -7.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -1.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition tail_r1 = bb_main.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(25, 28).addBox(-1.0F, -2.0F, 3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -1.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition crest_top_r1 = bb_main.addOrReplaceChild("crest_top_r1", CubeListBuilder.create().texOffs(19, 25).addBox(-2.0F, -9.0F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(-1.0F, -8.0F, -1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, -1.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition leg_right_r1 = bb_main.addOrReplaceChild("leg_right_r1", CubeListBuilder.create().texOffs(8, 24).addBox(1.0F, -1.0F, -3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, -0.3054F, 0.0F));

		PartDefinition leg_left_r1 = bb_main.addOrReplaceChild("leg_left_r1", CubeListBuilder.create().texOffs(25, 14).addBox(-2.0F, -1.0F, -3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.3054F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, buffer, packedLight, packedOverlay);
	}
}