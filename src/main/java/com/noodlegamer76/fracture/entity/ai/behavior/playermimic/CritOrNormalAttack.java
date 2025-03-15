package com.noodlegamer76.fracture.entity.ai.behavior.playermimic;

import com.noodlegamer76.fracture.entity.monster.PlayerMimic;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

public class CritOrNormalAttack<T extends PlayerMimic> extends AnimatableMeleeAttack<T> {
    public final float sprintJumpDistance;

    public CritOrNormalAttack(int delayTicks, float sprintJumpDistance) {
        super(delayTicks);
        this.sprintJumpDistance = sprintJumpDistance;
    }

    @Override
    protected void tick(T entity) {
        super.tick(entity);
    }

    @Override
    protected void start(T entity) {
        super.start(entity);
        entity.setSprinting(true);
    }

    @Override
    protected void stop(T entity) {
        super.stop(entity);
        entity.setSprinting(false);
    }

    @Override
    protected void doDelayedAction(T entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));

        if (this.target == null)
            return;

        if (!entity.getSensing().hasLineOfSight(this.target) || !entity.isWithinMeleeAttackRange(this.target))
            return;

        if (canCrit(entity)) {
            entity.critAttack = true;
            entity.doHurtTarget(this.target);
            entity.critAttack = false;
        }
        else {
            entity.doHurtTarget(this.target);
        }
    }

    public boolean canCrit(T entity) {
        return entity.fallDistance > 0.0F &&
                !entity.onGround() &&
                !entity.onClimbable() &&
                !entity.isInWater() &&
                !entity.hasEffect(MobEffects.BLINDNESS) &&
                !entity.isPassenger();
    }
}
