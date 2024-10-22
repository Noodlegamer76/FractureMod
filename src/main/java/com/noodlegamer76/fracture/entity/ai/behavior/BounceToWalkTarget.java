package com.noodlegamer76.fracture.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import com.noodlegamer76.fracture.entity.BloodSlimeEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class BounceToWalkTarget<E extends MultiAttackMonster> extends MoveToWalkTarget<E> {

    private int bounceTimeoutMax = 0;
    private int bounceTimeout = 0;
    public boolean defaultAttack = true;

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        Brain<?> brain = entity.getBrain();
        WalkTarget walkTarget = BrainUtils.getMemory(brain, MemoryModuleType.WALK_TARGET);

        if (walkTarget == null) {
            return false;
        }

        return super.checkExtraStartConditions(level, entity);
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return bounceTimeout <= bounceTimeoutMax;
    }

    @Override
    protected void startOnNewPath(E entity) {
        BrainUtils.setMemory(entity, MemoryModuleType.PATH, this.path);
        entity.getNavigation().moveTo(this.path, 0);
    }

    public BounceToWalkTarget<E> setBounceTimeout(int timeout) {
        this.bounceTimeoutMax = timeout;
        return this;
    }

    @Override
    protected void tick(E entity) {
        Brain<?> brain = entity.getBrain();
        WalkTarget walkTarget = BrainUtils.getMemory(brain, MemoryModuleType.WALK_TARGET);

        if (walkTarget != null) {
            super.tick(entity);
        }

        if (entity.onGround() && bounceTimeout == 0) {
            Vec3 direction = entity.getPosition(0).subtract(entity.getNavigation().getTargetPos().getCenter()).normalize().reverse();
            entity.setDeltaMovement(direction.x, 0.7, direction.z);
        }
        bounceTimeout++;
    }

    @Override
    protected void stop(E entity) {
        bounceTimeout = 0;
        entity.defaultAttacks++;
        super.stop(entity);
    }
}
