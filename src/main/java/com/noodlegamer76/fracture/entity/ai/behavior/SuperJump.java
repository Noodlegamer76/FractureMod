package com.noodlegamer76.fracture.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import com.noodlegamer76.fracture.entity.monster.BloodSlimeEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;

import static com.noodlegamer76.fracture.entity.monster.BloodSlimeEntity.DATA_EXPLODE;

public class SuperJump<E extends MultiAttackMonster> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS;
    private int completionTime = 40;
    private boolean doGroundPound = true;

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }


    @Override
    protected void start(E entity) {
        super.start(entity);
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return doGroundPound;
    }

    @Override
    protected void tick(E entity) {
        if (completionTime == 2) {
            Vec3 direction = entity.getPosition(0).subtract(entity.getNavigation().getTargetPos().getCenter()).normalize().reverse();
            entity.setDeltaMovement(direction.x, 2, direction.z);
        }

        if (entity.onGround() && doGroundPound && completionTime <= 0) {
            entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 4.5f, Level.ExplosionInteraction.NONE);
            doGroundPound = !doGroundPound;
            if (entity instanceof BloodSlimeEntity slime) {
                slime.getEntityData().set(DATA_EXPLODE, true);
            }
        }
        completionTime--;
    }

    @Override
    protected void stop(E entity) {
        completionTime = 40;
        doGroundPound = true;
        entity.attackNumber = 0;
    }

    static {
        MEMORY_REQUIREMENTS = ObjectArrayList.of(new Pair[]{Pair.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED), Pair.of(MemoryModuleType.PATH, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT)});
    }
}
