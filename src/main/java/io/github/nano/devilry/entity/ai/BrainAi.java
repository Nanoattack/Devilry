package io.github.nano.devilry.entity.ai;

import com.google.common.collect.ImmutableSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class BrainAi <E extends LivingEntity>{
    protected final E entity;

    public BrainAi(E entity) {
        this.entity = entity;
    }

    public abstract void initMemories(RandomSource pRandom);

    public Brain<E> makeBrain(Brain<E> brain) {
           initCoreActivity(brain);
           initIdleActivity(brain);
           brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
           brain.setDefaultActivity(Activity.IDLE);
           brain.useDefaultActivity();
           return brain;
    }

    protected abstract void initCoreActivity(Brain<E> brain);
    protected abstract void initIdleActivity(Brain<E> brain);
    public abstract void updateActivity();
    public abstract Ingredient getTemptations();
}
