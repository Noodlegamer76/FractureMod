package com.noodlegamer76.fracture.entity.ai.behavior.playermimic;

import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.entity.monster.PlayerMimic;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;

public class TryToSwimFast<T extends PlayerMimic> extends ExtendedBehaviour<T> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED),
            Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected void tick(T entity) {
        if (entity.isUnderWater()) {
            entity.setSwimming(true);
        }
    }

    @Override
    protected void stop(T entity) {
        if (!entity.isInWater()) {
            entity.setSwimming(false);
        }
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, T entity) {
        return entity.isInWater();
    }

    @Override
    protected boolean shouldKeepRunning(T entity) {
        return entity.isInWater();
    }
}
