package com.noodlegamer76.fracture.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;

import java.util.List;

public class ThrowAttackTargetUp<E extends Monster> extends AnimatableMeleeAttack<E> {
    double throwHeight = 1.0;
    public ThrowAttackTargetUp(int delayTicks, double throwHeight) {
        super(delayTicks);
        this.throwHeight = throwHeight;
    }

    @Override
    protected void start(E entity) {
        super.start(entity);
    }
}
