package com.nano.devilry.entity.custom;

import com.nano.devilry.events.ModSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class OwlEntity extends Parrot
{
    public OwlEntity(EntityType<? extends Parrot> type, Level levelIn) {
        super(type, levelIn);
        this.moveControl = new OwlMoveControl(this, 10, false);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,10.0D)
//                .add(Attributes.FLYING_SPEED, 1.3D)
//                .add(Attributes.MOVEMENT_SPEED,1.6D)
                ;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1,new PanicGoal(this,1.25D));
        this.goalSelector.addGoal(2,new WaterAvoidingRandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(3,new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7,new RandomLookAroundGoal(this));
    }

    @Override
    protected int getExperienceReward(Player player)
    {
        return 1 + this.level.random.nextInt(4);
    }

    @Override
    public SoundEvent getAmbientSound() {
        this.playSound(ModSoundEvents.OWL_AMBIENT.get(), 0.2F, 1.0F);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        this.playSound(ModSoundEvents.OWL_HURT.get(), 1.0F, 1.7F);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(ModSoundEvents.OWL_DEATH.get(), 0.7F, 2.0F);
        return null;
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(5.0D, (5.5F * this.getEyeHeight()), (this.getBbWidth() * 5.4F));
    }
}

