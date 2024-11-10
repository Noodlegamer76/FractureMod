package com.noodlegamer76.fracture.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import com.noodlegamer76.fracture.entity.monster.FleshSlimeEntity;
import com.noodlegamer76.fracture.entity.InitEntities;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;

public class BloodSlimeSplit<E extends MultiAttackMonster> extends ExtendedBehaviour<E> {
    private int timeout = 0;
    private boolean stop = true;
    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return List.of();
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return stop;
    }

    @Override
    protected void start(E entity) {
       for(int i = 0; i < 7; i++) {
           FleshSlimeEntity slime = new FleshSlimeEntity(InitEntities.FLESH_SLIME.get(), entity.level());
           slime.setSize((int) (Math.random() * 3 + 1), true);
           slime.setPos(entity.position());
           entity.level().addFreshEntity(slime);
       }
    }

    @Override
    protected void tick(E entity) {
        timeout++;
        if (timeout == 150) {
            stop = false;
        }
    }

    @Override
    protected void stop(E entity) {
        entity.attackNumber = 0;
    }
}
