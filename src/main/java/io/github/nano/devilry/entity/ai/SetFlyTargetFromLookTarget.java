package io.github.nano.devilry.entity.ai;

import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;

import java.util.function.Predicate;

public class SetFlyTargetFromLookTarget {
    public static OneShot<LivingEntity> create(float speedModifier, int closeEnoughDist) {
        return create(entity -> true, entity -> speedModifier, closeEnoughDist);
    }

    public static OneShot<LivingEntity> create(Predicate<LivingEntity> canSetTarget, Object2FloatFunction<LivingEntity> speedModifier, int closeEnoughDist) {
        return BehaviorBuilder.create(instance -> instance.group(instance.absent(MemoryRegistry.FLY_TARGET.get()), instance.present(MemoryModuleType.LOOK_TARGET)).apply(instance, (accessor, target) ->
                (level, entity, gameTime) -> {
            if (!canSetTarget.test(entity)) {
                return false;
            } else {
                accessor.set(new WalkTarget(instance.get(target), speedModifier.apply(entity), closeEnoughDist));
                return true;
            }
        }));
    }
}
