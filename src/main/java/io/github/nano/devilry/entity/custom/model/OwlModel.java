package io.github.nano.devilry.entity.custom.model;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.entity.custom.OwlEntity;
import io.github.nano.devilry.entity.custom.animations.OwlAnimation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
//fixme
//todo

@SuppressWarnings("unused")
public class OwlModel extends HierarchicalModel<OwlEntity>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ModMain.MOD_ID, "owl.png"), "all");

    private final ModelPart root;
    public OwlModel(ModelPart root) {
        this.root = root;
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, -1.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -1.3303F, -3.1667F, 9.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(4.5F, -2.3303F, -1.1667F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 19).addBox(-5.5F, -2.3303F, -1.1667F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.7396F, -10.6697F, 0.8497F));
        PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create(), PartPose.offset(-0.5F, 12.6697F, -1.1667F));
        PartDefinition beakBottomR1 = beak.addOrReplaceChild("beak_bottom_r1", CubeListBuilder.create().texOffs(18, 14).addBox(0.0F, -7.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 14).addBox(-1.0F, -8.0F, -4.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        PartDefinition mainBody = root.addOrReplaceChild("main_body", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -7.0F, 0.0F, 5.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition crest = mainBody.addOrReplaceChild("crest", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition crestBottomR1 = crest.addOrReplaceChild("crest_bottom_r1", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -8.0F, -1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 25).addBox(-2.0F, -9.0F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.0436F, 0.0F, 0.0F));
        PartDefinition rightWing = mainBody.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(16, 28).addBox(-1.0F, 5.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -7.0F, 1.0F));
        PartDefinition leftWing = mainBody.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(17, 14).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(26, 0).addBox(0.0F, 5.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -7.0F, 1.0F));
        PartDefinition tail = mainBody.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.5F, -2.0525F, 3.338F));
        PartDefinition tailR1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(25, 28).addBox(-1.0F, -1.0F, 3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 2.0525F, -3.338F, 0.3054F, 0.0F, 0.0F));
        PartDefinition legLeft = root.addOrReplaceChild("leg_left", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 1.0F));
        PartDefinition legLeftR1 = legLeft.addOrReplaceChild("leg_left_r1", CubeListBuilder.create().texOffs(25, 14).addBox(-2.0F, -1.0F, -3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3054F, 0.0F));
        PartDefinition legRight = root.addOrReplaceChild("leg_right", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 1.0F));
        PartDefinition legRightR1 = legRight.addOrReplaceChild("leg_right_r1", CubeListBuilder.create().texOffs(8, 24).addBox(1.0F, -1.0F, -3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3054F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public @NotNull ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(@NotNull OwlEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(pEntity.flyAnimationState, OwlAnimation.FLY, pAgeInTicks);
        this.animate(pEntity.flutterAnimationState, OwlAnimation.FLUTTER, pAgeInTicks);
        this.animate(pEntity.walkAnimationState, OwlAnimation.WALK, pAgeInTicks);
        this.animate(pEntity.headTurnAnimationState, OwlAnimation.HEAD_TURN, pAgeInTicks);
        this.animate(pEntity.idleAnimationState, OwlAnimation.IDLE, pAgeInTicks);
    }
}
