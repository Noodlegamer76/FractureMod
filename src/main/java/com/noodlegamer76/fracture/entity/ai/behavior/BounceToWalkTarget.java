package com.noodlegamer76.fracture.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import com.noodlegamer76.fracture.entity.BloodSlimeEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
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
        super.tick(entity);

        if (bounceTimeout > bounceTimeoutMax && entity.onGround()) {
            bounceTimeout = 0;
        }
        if (entity.onGround() && bounceTimeout == bounceTimeoutMax) {
            Vec3 direction = entity.getPosition(0).subtract(entity.getNavigation().getTargetPos().getCenter()).normalize().reverse();
            entity.setDeltaMovement(direction.x / 1.75, 0.7, direction.z / 1.75);
            entity.defaultAttacks++;
            bounceTimeout = 0;
        }
        bounceTimeout++;
    }

    @Override
    protected void stop(E entity) {
        super.stop(entity);
    }
}
