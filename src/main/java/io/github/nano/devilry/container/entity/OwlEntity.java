package io.github.nano.devilry.container.entity;

import com.google.common.collect.Sets;
import io.github.nano.devilry.container.entity.ai.OwlMoveControl;
import io.github.nano.devilry.events.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Set;
//todo

public class OwlEntity extends Parrot {

    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(OwlEntity.class, EntityDataSerializers.INT);

    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState headTurnAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flutterAnimationState = new AnimationState();
    private static final Set<Item> TAME_FOOD = Sets.newHashSet(Items.RABBIT);
    public float flapSpeed;
    private float nextFlap = 0.3F;


    public OwlEntity(EntityType<OwlEntity> type, Level levelIn) {
        super(type, levelIn);
        this.moveControl = new OwlMoveControl(this, 10, false);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
    }

    public OwlEntity(Level levelIn) {
        this(ModEntityTypes.OWL.get(), levelIn);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.FLYING_SPEED, 0.4F)
                .add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
        this.goalSelector.addGoal(2, new OwlEntity.OwlWanderGoal(this, 1.0D));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_VARIANT_ID, 0);
    }

    @Nullable
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob entity) {
        return new OwlEntity(level);
    }

    @Override
    public SoundEvent getAmbientSound() {
        this.playSound(ModSoundEvents.OWL_AMBIENT.get(), 0.2F, 0.8F + level().random.nextFloat() * 0.4F);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        this.playSound(ModSoundEvents.OWL_HURT.get(), 1.0F, 1.7F + level().random.nextFloat() * 0.4F);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(ModSoundEvents.OWL_DEATH.get(), 0.7F, 2.0F + level().random.nextFloat() * 0.4F);
        return null;
    }

    protected void playStepSound(@NotNull BlockPos p_29419_, @NotNull BlockState p_29420_) {
        this.playSound(SoundEvents.PARROT_STEP, 0.15F, 1.0F);
    }

    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    protected void onFlap() {
        this.playSound(SoundEvents.PARROT_FLY, 0.05F, 1.0F);
        this.nextFlap = this.flyDist + this.flapSpeed / 3.0F;
    }

    @Override
    public @NotNull Vec3 getLeashOffset() {
        return new Vec3(5.0D, (5.5F * this.getEyeHeight()), (this.getBbWidth() * 5.4F));
    }

    protected float getStandingEyeHeight(@NotNull Pose p_29411_, EntityDimensions p_29412_) {
        return p_29412_.height * 0.6F;
    }

    public @NotNull InteractionResult mobInteract(Player p_29414_, @NotNull InteractionHand p_29415_) {
        ItemStack itemstack = p_29414_.getItemInHand(p_29415_);
        if (!this.isTame() && TAME_FOOD.contains(itemstack.getItem())) {
            if (!p_29414_.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PARROT_EAT, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }

            if (!this.level().isClientSide) {
                if (this.random.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_29414_)) {
                    this.tame(p_29414_);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (!this.isFlying() && this.isTame() && this.isOwnedBy(p_29414_)) {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(!this.isOrderedToSit());
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(p_29414_, p_29415_);
        }
    }

    static class OwlWanderGoal extends WaterAvoidingRandomFlyingGoal {
        public OwlWanderGoal(PathfinderMob p_186224_, double p_186225_) {
            super(p_186224_, p_186225_);
        }

        @Nullable
        protected Vec3 getPosition() {
            Vec3 vec3 = null;
            if (this.mob.isInWater()) {
                vec3 = LandRandomPos.getPos(this.mob, 15, 15);
            }

            if (this.mob.getRandom().nextFloat() >= this.probability) {
                vec3 = this.getTreePos();
            }

            return vec3 == null ? super.getPosition() : vec3;
        }

        @Nullable
        private Vec3 getTreePos() {
            BlockPos blockpos = this.mob.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

            for (BlockPos blockpos1 : BlockPos.betweenClosed(Mth.floor(this.mob.getX() - 3.0D), Mth.floor(this.mob.getY() - 6.0D), Mth.floor(this.mob.getZ() - 3.0D), Mth.floor(this.mob.getX() + 3.0D), Mth.floor(this.mob.getY() + 6.0D), Mth.floor(this.mob.getZ() + 3.0D))) {
                if (!blockpos.equals(blockpos1)) {
                    BlockState blockstate = this.mob.level().getBlockState(blockpos$mutableblockpos1.setWithOffset(blockpos1, Direction.DOWN));
                    boolean flag = blockstate.getBlock() instanceof LeavesBlock || blockstate.is(BlockTags.LOGS);
                    if (flag && this.mob.level().isEmptyBlock(blockpos1) && this.mob.level().isEmptyBlock(blockpos$mutableblockpos.setWithOffset(blockpos1, Direction.UP))) {
                        return Vec3.atBottomCenterOf(blockpos1);
                    }
                }
            }
            return null;
        }
    }

    public boolean causeFallDamage(float p_148989_, float p_148990_, @NotNull DamageSource p_148991_) {
        return false;
    }

    protected void checkFallDamage(double p_29370_, boolean p_29371_, @NotNull BlockState p_29372_, @NotNull BlockPos p_29373_) {
    }

    public boolean canMate(@NotNull Animal p_29381_) {
        return false;
    }


    public boolean isFlying() {
        return !this.onGround();
    }

    public boolean isPushable() {
        return true;
    }

    protected void doPush(@NotNull Entity p_29367_) {
        if (!(p_29367_ instanceof Player)) {
            super.doPush(p_29367_);
        }
    }

    public boolean hurt(@NotNull DamageSource p_29378_, float p_29379_) {
        if (this.isInvulnerableTo(p_29378_)) {
            return false;
        } else {
            this.setOrderedToSit(false);
            return super.hurt(p_29378_, p_29379_);
        }
    }

    protected @NotNull PathNavigation createNavigation(@NotNull Level p_29417_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_29417_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }
}

