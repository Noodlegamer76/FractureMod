package com.noodlegamer76.fracture.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;

public class SuperJump<E extends MultiAttackMonster> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS;
    private int completionTime = 48;
    private boolean doGroundPound = true;

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {

        return doGroundPound;
    }

    @Override
    protected void tick(E entity) {
        if (completionTime == 25) {
            entity.setDeltaMovement(0, 2, 0);
        }

        if (entity.onGround() && doGroundPound && completionTime <= 23) {
            entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 4f, Level.ExplosionInteraction.MOB);
            doGroundPound = !doGroundPound;
        }
        completionTime--;
    }

    @Override
    protected void stop(E entity) {
        completionTime = 49;
        entity.attackNumber = 2;
    }

    static {
        MEMORY_REQUIREMENTS = ObjectArrayList.of(new Pair[]{Pair.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED), Pair.of(MemoryModuleType.PATH, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT)});
    }
}
