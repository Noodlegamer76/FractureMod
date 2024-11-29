package com.noodlegamer76.fracture.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.projectile.GiantSnowballProjectile;
import com.noodlegamer76.fracture.util.ModVectors;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;

public class GiantSnowballSpreadLaunch<E extends MultiAttackMonster> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(new Pair[]{Pair.of(
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED),
            Pair.of(MemoryModuleType.PATH, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)});

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected void start(E entity) {
         for (int i = 0; i < 12; i++) {
             Vec3 position = new Vec3(Math.random(), 0, Math.random()).normalize()
                     .add(entity.getEyePosition());

             GiantSnowballProjectile snowball = new GiantSnowballProjectile(InitEntities.GIANT_SLOWBALL.get(), entity.level());
             snowball.setPos(position);
             snowball.addDeltaMovement(new Vec3(entity.level().random.nextDouble() - 0.5, ModVectors.getForwardVector(entity).y() - 0.05, entity.level().random.nextDouble() - 0.5).normalize());
             entity.level().addFreshEntity(snowball);
         }
    }

    @Override
    protected void stop(E entity) {
        entity.attackNumber = 0;
        entity.attackTimeout = 100;
    }
}
