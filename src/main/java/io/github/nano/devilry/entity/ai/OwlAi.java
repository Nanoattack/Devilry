package io.github.nano.devilry.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import io.github.nano.devilry.entity.ModEntityTypes;
import io.github.nano.devilry.entity.OwlEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class OwlAi extends BrainAi<OwlEntity> {

    public OwlAi(OwlEntity entity) {
        super(entity);
    }

    @Override
    public void initMemories(RandomSource pRandom) {

    }

    @Override
    protected void initCoreActivity(Brain<OwlEntity> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(new AnimalPanic(2f), new LookAtTargetSink(45, 90), new MoveToTargetSink(), new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)));
    }

    @Override
    protected void initIdleActivity(Brain<OwlEntity> brain) {
        brain.addActivityWithConditions(Activity.IDLE, ImmutableList.of(Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6f, UniformInt.of(30, 60))), Pair.of(0, new AnimalMakeLove(ModEntityTypes.OWL.get(), 1.0f)), Pair.of(1, new FollowTemptation(entity -> 1.25f)),
                Pair.of(2, new RunOne<>(ImmutableMap.of(MemoryRegistry.FLY_TARGET.get(), MemoryStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(RandomStroll.fly(1f), 1), Pair.of(SetFlyTargetFromLookTarget.create(1f, 3), 1), Pair.of(SetWalkTargetFromLookTarget.create(Entity::isOnGround,e -> 1f, 3), 1), Pair.of(BehaviorBuilder.triggerIf(Entity::isOnGround), 2))))), ImmutableSet.of(Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT)));
    }

    @Override
    public void updateActivity() {
        entity.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
    }

    @Override
    public Ingredient getTemptations() {
        return Ingredient.of(Items.RABBIT);
    }
}
