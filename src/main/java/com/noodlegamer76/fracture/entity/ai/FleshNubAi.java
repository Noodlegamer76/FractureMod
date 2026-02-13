package com.noodlegamer76.fracture.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.entity.ai.behavior.MeleeAttackWithDelay;
import com.noodlegamer76.fracture.entity.ai.memory.InitMemoryModuleTypes;
import com.noodlegamer76.fracture.entity.monster.boss.FleshNub;
import com.noodlegamer76.fracture.entity.monster.multiattack.AttackType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.hoglin.HoglinAi;
import net.minecraft.world.entity.schedule.Activity;

import java.util.Optional;

public class FleshNubAi {
    public static Brain<?> makeBrain(Brain<FleshNub> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<FleshNub> brain) {
        brain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink()
                )
        );
    }

    private static void initIdleActivity(Brain<FleshNub> pBrain) {
        pBrain.addActivity(
                Activity.IDLE,
                10,
                ImmutableList.of(
                        StartAttacking.create(FleshNubAi::findNearestValidAttackTarget),
                        SetEntityLookTargetSometimes.create(8.0F, UniformInt.of(30, 60)),
                        createIdleMovementBehaviors()
                )
        );
    }

    private static void initFightActivity(Brain<FleshNub> pBrain) {
        pBrain.addActivityAndRemoveMemoryWhenStopped(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F),
                        StopAttackingIfTargetInvalid.create(),
                        new RunOne<>(
                                ImmutableList.of(
                                        Pair.of(new MeleeAttackWithDelay(20, 20, AttackType.BITE), 1)
                                )
                        )
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(FleshNub fleshNub) {
        return fleshNub.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
    }

    private static RunOne<FleshNub> createIdleMovementBehaviors() {
        return new RunOne<>(
                ImmutableList.of(
                        Pair.of(RandomStroll.stroll(1.0F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
                        Pair.of(new DoNothing(30, 60), 1)
                )
        );
    }

    public static void updateActivity(FleshNub fleshNub) {
        Brain<FleshNub> brain = fleshNub.getBrain();

        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));

        fleshNub.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }
}
